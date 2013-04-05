package com.jpoweredcart.admin.model.catalog.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.io.FilenameUtils;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import com.jpoweredcart.admin.bean.catalog.DownloadForm;
import com.jpoweredcart.admin.model.catalog.DownloadAdminModel;
import com.jpoweredcart.admin.model.localisation.LanguageAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.QueryBean;
import com.jpoweredcart.common.entity.catalog.Download;
import com.jpoweredcart.common.entity.catalog.DownloadDesc;
import com.jpoweredcart.common.service.file.FileService;
import com.jpoweredcart.common.service.setting.SettingKey;

public class DownloadAdminModelImpl extends BaseModel implements DownloadAdminModel{
	
	@Inject
	private FileService downloadFileService;
	
	@Inject
	private LanguageAdminModel languageAdminModel;
	
	@Transactional
	@Override
	public void create(final DownloadForm dlForm) {
		
		if(dlForm.getFileUpload().isEmpty()){
			throw new RuntimeException("fileupload is empty");
		}
		
		final String origName = dlForm.getFileUpload().getOriginalFilename();
		final String ext = FilenameUtils.getExtension(origName);
		final String randomName = downloadFileService.generateRandomName()+"."+ext;
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcOperations().update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				
				String sql = "INSERT INTO " +quoteTable("download")+ "(filename, mask, remaining, date_added) VALUES(?,?,?,?) ";
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, randomName);
				ps.setString(2, dlForm.getMask());
				ps.setInt(3, dlForm.getRemaining());
				ps.setDate(4, new java.sql.Date(new Date().getTime()));
				return ps;
			}
		}, keyHolder);
		Integer dlId = keyHolder.getKey().intValue();
		dlForm.setId(dlId);
		setAdditionalFormValues(dlForm);
		
		downloadFileService.upload(dlForm.getFileUpload(), randomName);
		
	}
	
	@Transactional
	@Override
	public void update(DownloadForm dlForm) {
		String sql = "UPDATE " +quoteTable("download")+ " SET mask=?, remaining=? WHERE download_id=?";
		getJdbcOperations().update(sql, dlForm.getMask(), 
				dlForm.getRemaining(), dlForm.getId());
		
		if(dlForm.isUpdateOrder()){
			sql = "UPDATE "+quoteTable("order_download")+ " SET  mask=?, remaining=? WHERE filename=?";
			getJdbcOperations().update(sql, dlForm.getMask(), 
				dlForm.getRemaining(), dlForm.getFileName());
		}
		
		sql = "DELETE FROM " +quoteTable("download_description")+ " WHERE download_id=?";
		getJdbcOperations().update(sql, dlForm.getId());
		
		setAdditionalFormValues(dlForm);
	}
	
	protected void setAdditionalFormValues(DownloadForm dlForm){
		for(DownloadDesc desc: dlForm.getDescs()){
			String sql = "INSERT INTO " +quoteTable("download_description")
					+ "(download_id, language_id, name) VALUES(?,?,?)";
			getJdbcOperations().update(sql, dlForm.getId(), 
					desc.getLanguageId(), desc.getName());
		}
	}
	
	@Transactional
	@Override
	public void delete(Integer dlId) {
		String sql = "SELECT filename from "+quoteTable("download")+" WHERE download_id=?";
		String fileName = getJdbcOperations().queryForObject(sql, new Object[]{dlId}, String.class);
		
		sql = "DELETE FROM " +quoteTable("download")+ " WHERE download_id=?";
		getJdbcOperations().update(sql, dlId);
		sql = "DELETE FROM " +quoteTable("download_description")+ " WHERE download_id=?";
		getJdbcOperations().update(sql, dlId);
		
		if(downloadFileService.exists(fileName) && !downloadFileService.delete(fileName)){
			throw new RuntimeException("Unable to delete fileName: "+fileName);
		}
	}
	
	@Override
	public DownloadForm newForm() {
		DownloadForm dlForm = new DownloadForm();
		dlForm.setDescs(languageAdminModel
				.createDescriptionList(DownloadDesc.class));
		return dlForm;
	}
	
	@Override
	public DownloadForm getForm(Integer dlId) {
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		String sql = "SELECT DISTINCT * FROM " +quoteTable("download")+ " d LEFT JOIN "+quoteTable("download_description")+
				" dd ON (d.download_id = dd.download_id) WHERE d.download_id = ? AND dd.language_id = ?";
		DownloadForm dlForm = getJdbcOperations().queryForObject(sql, 
				new Object[]{dlId, languageId}, new DownloadRowMapper.Form());
			dlForm.setDescs(getDescriptions(dlId));
		
		dlForm.setDownloadDir(downloadFileService.getUrl(""));//get only baseUrl
		
		return dlForm;
	}
	
	@Override
	public Download get(Integer dlId) {
		
		return getForm(dlId);
	}
	
	@Override
	public List<Download> getList(PageParam pageParam) {
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		String sql = "SELECT * FROM "+quoteTable("download")+" d LEFT JOIN "+quoteTable("download_description")+
				" dd ON (d.download_id = dd.download_id) WHERE dd.language_id =?";
		QueryBean query = createPaginationQueryFromSql(sql, pageParam, 
				new String[]{"dd.name", "d.remaining"});
		query.addParameters(languageId);
		return getJdbcOperations().query(query.getSql(), 
				query.getParameters(), new DownloadRowMapper(){
					@Override
					public Download mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Download download = super.mapRow(rs, rowNum);
						download.setName(rs.getString("name"));
						return download;
					}
		});
	}
	
	@Override
	public List<DownloadDesc> getDescriptions(Integer dlId) {
		String sql = "SELECT dd.download_id, dd.language_id, l.name AS language_name, l.image AS language_image, dd.name FROM " +
				quoteTable("download_description")+" dd INNER JOIN "
				+quoteTable("language")+" l ON dd.language_id=l.language_id WHERE dd.download_id=?";
		return getJdbcOperations().query(sql, 
				new Object[]{dlId}, new DownloadRowMapper.Desc());
	}

	@Override
	public int getTotal() {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("download");
		return getJdbcOperations().queryForInt(sql);
	}
	
}
