package com.jpoweredcart.admin.model.catalog.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import com.jpoweredcart.admin.bean.catalog.AttributeGroupForm;
import com.jpoweredcart.admin.model.catalog.AttributeGroupAdminModel;
import com.jpoweredcart.admin.model.localisation.LanguageAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.QueryBean;
import com.jpoweredcart.common.entity.catalog.AttributeGroup;
import com.jpoweredcart.common.entity.catalog.AttributeGroupDesc;
import com.jpoweredcart.common.service.SettingKey;
import com.jpoweredcart.common.service.SettingService;

public class AttributeGroupAdminModelImpl extends BaseModel implements AttributeGroupAdminModel{

	@Inject
	private LanguageAdminModel languageAdminModel;
	
	public AttributeGroupAdminModelImpl(SettingService settingService, JdbcOperations jdbcOperations){
		super(settingService, jdbcOperations);
	}
	
	@Transactional
	@Override
	public void create(final AttributeGroupForm attrGrpForm) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcOperations().update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				String sql = "INSERT INTO " +quoteTable("attribute_group")+ "(sort_order) VALUES(?) ";
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, attrGrpForm.getSortOrder());
				return ps;
			}
		}, keyHolder);
		Integer attrGrpId = keyHolder.getKey().intValue();
		attrGrpForm.setId(attrGrpId);
		setAdditionalFormValues(attrGrpForm);
	}

	@Transactional
	@Override
	public void update(AttributeGroupForm attrGrpForm) {
		String sql = "UPDATE " +quoteTable("attribute_group")+ " SET sort_order=? WHERE attribute_group_id=?";
		getJdbcOperations().update(sql, attrGrpForm.getSortOrder(), attrGrpForm.getId());
		
		sql = "DELETE FROM " +quoteTable("attribute_group_description")+ " WHERE attribute_group_id=?";
		getJdbcOperations().update(sql, attrGrpForm.getId());
		
		setAdditionalFormValues(attrGrpForm);
	}
	
	protected void setAdditionalFormValues(AttributeGroupForm attrGrpForm){
		for(AttributeGroupDesc desc: attrGrpForm.getDescs()){
			String sql = "INSERT INTO " +quoteTable("attribute_group_description")
					+ "(attribute_group_id, language_id, name) VALUES(?,?,?)";
			getJdbcOperations().update(sql, attrGrpForm.getId(), 
					desc.getLanguageId(), desc.getName());
		}
	}
	
	@Transactional
	@Override
	public void delete(Integer attrGrpId) {
		String sql = "DELETE FROM " +quoteTable("attribute_group")+ " WHERE attribute_group_id=?";
		getJdbcOperations().update(sql, attrGrpId);
		sql = "DELETE FROM " +quoteTable("attribute_group_description")+ " WHERE attribute_group_id=?";
		getJdbcOperations().update(sql, attrGrpId);
	}
	
	@Override
	public AttributeGroupForm newForm(){
		AttributeGroupForm attrGrpForm = new AttributeGroupForm();
		attrGrpForm.setDescs(languageAdminModel
				.createDescriptionList(AttributeGroupDesc.class));
		return attrGrpForm;
	}
	
	@Override
	public AttributeGroupForm getForm(Integer attrGrpId) {
		AttributeGroupForm attrGrpForm = getJdbcOperations().queryForObject(getSelectSql(), 
			new Object[]{attrGrpId}, new AttributeGroupRowMapper.Form());
		attrGrpForm.setDescs(getDescriptions(attrGrpId));
		return attrGrpForm;
	}
	
	@Override
	public AttributeGroup get(Integer attrGrpId) {
		return getJdbcOperations().queryForObject(getSelectSql(), 
			new Object[]{attrGrpId}, new AttributeGroupRowMapper());
	}
	
	protected String getSelectSql(){
		return "SELECT * FROM " + quoteTable("attribute_group")+" WHERE attribute_group_id = ?";
	}
	
	@Override
	public List<AttributeGroup> getList(PageParam pageParam) {
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		String sql = "SELECT * FROM "+quoteTable("attribute_group")+" ag LEFT JOIN "+quoteTable("attribute_group_description")
				+" agd ON (ag.attribute_group_id = agd.attribute_group_id) WHERE agd.language_id = ?";
		QueryBean query = createPaginationQueryFromSql(sql, pageParam, 
				new String[]{"agd.name", "ag.sort_order"});
		query.addParameters(languageId);
		return getJdbcOperations().query(query.getSql(), 
				query.getParameters(), new AttributeGroupRowMapper(){
					@Override
					public AttributeGroup mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						AttributeGroup attrGrp = super.mapRow(rs, rowNum);
						attrGrp.setName(rs.getString("name"));
						return attrGrp;
					}
		});
	}

	@Override
	public List<AttributeGroupDesc> getDescriptions(Integer attrGrpId) {
		String sql = "SELECT agd.attribute_group_id, agd.language_id, l.name AS language_name, l.image AS language_image, agd.name FROM " +
				quoteTable("attribute_group_description")+" agd INNER JOIN "
				+quoteTable("language")+" l ON agd.language_id=l.language_id WHERE agd.attribute_group_id=?";
		return getJdbcOperations().query(sql, 
				new Object[]{attrGrpId}, new AttributeGroupRowMapper.Desc());
	}
	
	@Override
	public int getTotal() {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("attribute_group");
		return getJdbcOperations().queryForInt(sql);
	}
	
}
