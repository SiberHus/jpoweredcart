package org.jpoweredcart.admin.model.catalog.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.jpoweredcart.admin.form.catalog.InformationForm;
import org.jpoweredcart.admin.model.catalog.InformationAdminModel;
import org.jpoweredcart.admin.model.setting.StoreAdminModel;
import org.jpoweredcart.common.BaseModel;
import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.QueryBean;
import org.jpoweredcart.common.entity.catalog.Information;
import org.jpoweredcart.common.entity.catalog.InformationDesc;
import org.jpoweredcart.common.entity.catalog.InformationToLayout;
import org.jpoweredcart.common.entity.catalog.InformationToStore;
import org.jpoweredcart.common.entity.setting.Store;
import org.jpoweredcart.common.jdbc.ScalarResultSetExtractor;
import org.jpoweredcart.common.service.SettingKey;
import org.jpoweredcart.common.service.SettingService;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

public class InformationAdminModelImpl extends BaseModel implements InformationAdminModel {
	
	@Inject
	private StoreAdminModel storeAdminModel;
	
	public InformationAdminModelImpl(SettingService settingService, JdbcOperations jdbcOperations){
		super(settingService, jdbcOperations);
	}
	
	@Transactional
	@Override
	public void create(final InformationForm infoForm) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcOperations().update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				String sql = "INSERT INTO " +quoteTable("information")+ "(sort_order, bottom, status) VALUES(?,?,?) ";
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, infoForm.getSortOrder());
				ps.setInt(2, infoForm.getBottom());
				ps.setShort(3, infoForm.getStatus());
				return ps;
			}
		}, keyHolder);
		Integer infoId = keyHolder.getKey().intValue();
		infoForm.setId(infoId);
		setAdditionalFormValues(infoForm);
	}
	
	@Transactional
	@Override
	public void update(InformationForm infoForm) {
		
		String sql = "UPDATE " +quoteTable("information")+ " SET sort_order=?, bottom=?, status=? WHERE information_id=?";
		getJdbcOperations().update(sql, infoForm.getSortOrder(), infoForm.getBottom(), 
				infoForm.getStatus(), infoForm.getId());
		
		sql = "DELETE FROM " +quoteTable("information_description")+ " WHERE information_id=?";
		getJdbcOperations().update(sql, infoForm.getId());

		sql = "DELETE FROM " +quoteTable("information_to_store")+ " WHERE information_id=?";
		getJdbcOperations().update(sql, infoForm.getId());

		sql = "DELETE FROM " +quoteTable("information_to_layout")+ " WHERE information_id=?";
		getJdbcOperations().update(sql, infoForm.getId());
		
		sql = "DELETE FROM " +quoteTable("url_alias")+ " WHERE query=?";
		getJdbcOperations().update(sql, "information_id="+infoForm.getId());
		
		setAdditionalFormValues(infoForm);
	}
	
	protected void setAdditionalFormValues(InformationForm infoForm){
		if(StringUtils.isNotBlank(infoForm.getKeyword())){
			String sql = "INSERT INTO " +quoteTable("url_alias") + "(query, keyword) VALUES(?,?)";
			getJdbcOperations().update(sql, "information_id="+infoForm.getId(), infoForm.getKeyword());
		}
		for(InformationDesc desc: infoForm.getDescs()){
			String sql = "INSERT INTO " +quoteTable("information_description")
					+ "(information_id, language_id, title, description) VALUES(?,?,?,?)";
			getJdbcOperations().update(sql, infoForm.getId(), desc.getLanguageId(),
					desc.getTitle(), desc.getDescription());
		}
		for(InformationToStore its: infoForm.getStores()){
			if(its.getStoreId()!=null){
				String sql = "INSERT INTO " +quoteTable("information_to_store")
					+ "(information_id, store_id) VALUES(?,?)";
				getJdbcOperations().update(sql, infoForm.getId(), its.getStoreId());
			}
		}
		for(InformationToLayout itl: infoForm.getLayouts()){
			if(itl.getLayoutId()!=null){
				String sql = "INSERT INTO " +quoteTable("information_to_layout")
					+ "(information_id, store_id, layout_id) VALUES(?,?,?)";
				getJdbcOperations().update(sql, infoForm.getId(), 
						itl.getStoreId(), itl.getLayoutId());
			}
		}
	}
	
	@Transactional
	@Override
	public void delete(Integer infoId) {
		
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
	public InformationForm getForm(Integer infoId) {
		
		String sql = "SELECT DISTINCT *, (SELECT keyword FROM " +quoteTable("url_alias")
				+ " WHERE query = ?) AS keyword FROM " 
				+ quoteTable("information")+" WHERE information_id = ?";
		InformationForm infoForm = getJdbcOperations().queryForObject(sql, 
				new Object[]{"information_id="+infoId, infoId}, 
				new InformationRowMapper.Form());
		infoForm.setDescs(getInfoDescriptions(infoId));
		infoForm.setStores(getInfoStores(infoId));
		infoForm.setLayouts(getInfoLayouts(infoId));
		
		return infoForm;
	}
	
	@Override
	public Information get(Integer infoId) {
		
		String sql = "SELECT * FROM " + quoteTable("information")+" WHERE information_id = ?";
		Information info = getJdbcOperations().queryForObject(sql, 
				new Object[]{infoId}, new InformationRowMapper());
		return info;
	}
	
	@Override
	public List<Information> getList(PageParam pageParam) {
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		String sql = "SELECT * FROM " +quoteTable("information")+ " i LEFT JOIN " +quoteTable("information_description")
				+ " id ON (i.information_id = id.information_id) WHERE id.language_id = ?";
		QueryBean query = createPaginationQueryFromSql(sql, pageParam, 
				new String[]{"id.title", "i.sort_order"});
		query.addParameter(languageId);
		List<Information> informationList = getJdbcOperations().query(query.getSql(), 
				query.getParameters(), new InformationRowMapper(){
			@Override
			public Information mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				Information information = super.mapRow(rs, rowNum);
				information.setTitle(rs.getString("title"));
				return information;
			}
		});
		return informationList;
	}
	
	@Override
	public List<InformationDesc> getInfoDescriptions(Integer infoId){
		String sql = "SELECT id.information_id, id.language_id, l.name AS language_name, l.image AS language_image, id.title, id.description FROM " +
				quoteTable("information_description")+" id INNER JOIN "
				+quoteTable("language")+" l ON id.language_id=l.language_id WHERE id.information_id=?";
		return getJdbcOperations().query(sql, new Object[]{infoId}, 
				new InformationRowMapper.Desc());
	}
	
	@Override
	public List<InformationToStore> getInfoStores(Integer infoId) {
		String sql = "SELECT * FROM "+quoteTable("information_to_store")
				+" WHERE information_id =?"; 
		return getJdbcOperations().query(sql, new Object[]{infoId}, 
				new InformationRowMapper.Store());
	}

	@Override
	public List<InformationToLayout> getInfoLayouts(Integer infoId) {
		List<InformationToLayout> itlList = new ArrayList<InformationToLayout>();
		for(Store store: storeAdminModel.getAll()){
			InformationToLayout itl = new InformationToLayout();
			itl.setStoreId(store.getId());
			itl.setStoreName(store.getName());
			String sql = "SELECT layout_id FROM "+quoteTable("information_to_layout")
					+" WHERE information_id=? AND store_id=?";
			Integer layoutId = getJdbcOperations().query(sql, 
					new Object[]{infoId, store.getId()}, 
					new ScalarResultSetExtractor<Integer>());
			itl.setLayoutId(layoutId);
			itlList.add(itl);
		}
		return itlList;
	}
	
	@Override
	public int getTotal() {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("information");
		return getJdbcOperations().queryForInt(sql);
	}

	@Override
	public int getTotalByLayoutId(Integer layoutId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("information_to_layout")
				+" WHERE layout_id=?";
		return getJdbcOperations().queryForInt(sql, layoutId);
	}
	
}
