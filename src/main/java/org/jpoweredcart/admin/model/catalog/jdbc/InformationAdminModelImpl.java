package org.jpoweredcart.admin.model.catalog.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jpoweredcart.admin.model.catalog.InformationAdminModel;
import org.jpoweredcart.common.BaseModel;
import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.QueryBean;
import org.jpoweredcart.common.entity.catalog.Information;
import org.jpoweredcart.common.entity.catalog.InformationDesc;
import org.jpoweredcart.common.entity.catalog.InformationToLayout;
import org.jpoweredcart.common.entity.catalog.InformationToStore;
import org.jpoweredcart.common.entity.catalog.form.InformationForm;
import org.jpoweredcart.common.service.SettingKey;
import org.jpoweredcart.common.service.SettingService;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

public class InformationAdminModelImpl extends BaseModel implements InformationAdminModel {
	
	public InformationAdminModelImpl(SettingService settingService, JdbcOperations jdbcOperations){
		super(settingService, jdbcOperations);
	}
	
	@Transactional
	@Override
	public void addInformation(final InformationForm info) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcOperations().update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				String sql = "INSERT INTO " +quoteTable("information")+ "(sort_order, bottom, status) VALUES(?,?,?) ";
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, info.getSortOrder());
				ps.setInt(2, info.getBottom());
				ps.setShort(3, info.getStatus());
				return ps;
			}
		}, keyHolder);
		Integer infoId = keyHolder.getKey().intValue();
		addDescsToInformation(infoId, info.getDescs());
		addStoresToInformation(infoId, info.getStores());
		addLayoutsToInformation(infoId, info.getLayouts());
		setKeyword(infoId, info.getKeyword());
		info.setId(infoId);
	}
	
	@Transactional
	@Override
	public void updateInformation(InformationForm info) {
		
		String sql = "UPDATE " +quoteTable("information")+ " SET sort_order=?, bottom=?, status=? WHERE information_id=?";
		getJdbcOperations().update(sql, info.getSortOrder(), info.getBottom(), info.getStatus(), info.getId());
		
		sql = "DELETE FROM " +quoteTable("information_description")+ " WHERE information_id=?";
		getJdbcOperations().update(sql, info.getId());
		addDescsToInformation(info.getId(), info.getDescs());
		
		sql = "DELETE FROM " +quoteTable("information_to_store")+ " WHERE information_id=?";
		getJdbcOperations().update(sql, info.getId());
		addStoresToInformation(info.getId(), info.getStores());
		
		sql = "DELETE FROM " +quoteTable("information_to_layout")+ " WHERE information_id=?";
		getJdbcOperations().update(sql, info.getId());
		addLayoutsToInformation(info.getId(), info.getLayouts());
		
		sql = "DELETE FROM " +quoteTable("url_alias")+ " WHERE query=?";
		getJdbcOperations().update(sql, "information_id="+info.getId());
		setKeyword(info.getId(), info.getKeyword());
	}
	
	protected void setKeyword(Integer infoId, String keyword){
		if(StringUtils.isNotBlank(keyword)){
			String sql = "INSERT INTO " +quoteTable("url_alias") + "(query, keyword) VALUES(?,?)";
			getJdbcOperations().update(sql, "information_id="+infoId, keyword);
		}
	}
	
	protected void addDescsToInformation(Integer infoId, List<InformationDesc> descs){
		for(InformationDesc desc: descs){
			String sql = "INSERT INTO " +quoteTable("information_description")
					+ "(information_id, language_id, title, description) VALUES(?,?,?,?)";
			getJdbcOperations().update(sql, infoId, desc.getLanguageId(),
					desc.getTitle(), desc.getDescription());
		}
	}
	
	protected void addStoresToInformation(Integer infoId, List<InformationToStore> stores){
		for(InformationToStore store: stores){
			String sql = "INSERT INTO " +quoteTable("information_to_store")
					+ "(information_id, store_id) VALUES(?,?)";
			getJdbcOperations().update(sql, infoId, store.getStoreId());
		}
	}
	
	protected void addLayoutsToInformation(Integer infoId, List<InformationToLayout> layouts){
		for(InformationToLayout layout: layouts){
			String sql = "INSERT INTO " +quoteTable("information_to_layout")
					+ "(information_id, store_id, layout_id) VALUES(?,?,?)";
			getJdbcOperations().update(sql, infoId, layout.getStoreId(), layout.getLayoutId());
		}
	}
	
	@Transactional
	@Override
	public void deleteInformation(Integer infoId) {
		
		String sql = "DELETE FROM " +quoteTable("url_alias")+ " WHERE query=?";
		getJdbcOperations().update(sql, "information_id="+infoId);
		
		String tables[] = new String[]{"information_description", "information_to_store", 
				"information_to_layout", "information"};
		for(String table: tables){
			sql = "DELETE FROM " +quoteTable(table)+ " WHERE information_id=?";
			getJdbcOperations().update(sql, infoId);
		}
	}
	
	@Override
	public Information getInformation(Integer infoId) {
		
		String sql = "SELECT DISTINCT *, (SELECT keyword FROM " +quoteTable("url_alias")+ " WHERE query = ?) AS keyword FROM " 
				+ quoteTable("information")+" WHERE information_id = ?";
		Information info = getJdbcOperations().queryForObject(sql, 
				new Object[]{infoId}, new InformationRowMapper(){
					@Override
					public Information mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Information info = super.mapRow(rs, rowNum);
						info.setKeyword(rs.getString("keyword"));
						return info;
					}
		});
		return info;
	}
	
	@Override
	public List<Information> getInformations(PageParam pageParam) {
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		String sql = "SELECT * FROM " +quoteTable("information")+ " i LEFT JOIN " +quoteTable("information_description")+ " id ON (i.information_id = id.information_id) WHERE id.language_id = ?";
		QueryBean query = createPaginationQueryFromSql(sql, pageParam, 
				new String[]{"id.title", "i.sort_order"});
		query.addParameter(languageId);
		List<Information> infoList = getJdbcOperations().query(query.getSql(), 
				query.getParameters(), new InformationRowMapper(){
			@Override
			public Information mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				Information info = super.mapRow(rs, rowNum);
				info.setTitle(rs.getString("title"));
				return info;
			}
		});
		return infoList;
	}
	
	@Override
	public List<InformationDesc> getInformationDescriptions(Integer infoId){
		String sql = "SELECT * FROM "+quoteTable("information_description")+" WHERE information_id =?"; 
		return getJdbcOperations().query(sql, new Object[]{infoId}, 
				new InformationRowMapper.Desc());
	}
	
	@Override
	public List<InformationToStore> getInformationStores(Integer infoId) {
		String sql = "SELECT * FROM "+quoteTable("information_to_store")+" WHERE information_id =?"; 
		return getJdbcOperations().query(sql, new Object[]{infoId}, 
				new InformationRowMapper.Store());
	}

	@Override
	public List<InformationToLayout> getInformationLayouts(Integer infoId) {
		String sql = "SELECT * FROM "+quoteTable("information_to_layout")+" WHERE information_id =?"; 
		return getJdbcOperations().query(sql, new Object[]{infoId}, 
				new InformationRowMapper.Layout());
	}
	
	@Override
	public int getTotalInformations() {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("information");
		return getJdbcOperations().queryForInt(sql);
	}

	@Override
	public int getTotalInformationsByLayoutId(Integer layoutId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("information_to_layout")
				+" WHERE layout_id=?";
		return getJdbcOperations().queryForInt(sql, layoutId);
	}
	
}
