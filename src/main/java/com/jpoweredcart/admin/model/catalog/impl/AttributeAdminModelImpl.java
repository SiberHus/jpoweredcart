package com.jpoweredcart.admin.model.catalog.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import com.jpoweredcart.admin.form.catalog.AttributeForm;
import com.jpoweredcart.admin.model.catalog.AttributeAdminModel;
import com.jpoweredcart.admin.model.localisation.LanguageAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.QueryBean;
import com.jpoweredcart.common.entity.catalog.Attribute;
import com.jpoweredcart.common.entity.catalog.AttributeDesc;
import com.jpoweredcart.common.entity.catalog.jdbc.AttributeRowMapper;
import com.jpoweredcart.common.system.setting.SettingKey;

public class AttributeAdminModelImpl extends BaseModel implements AttributeAdminModel{

	@Inject
	private LanguageAdminModel languageAdminModel;
	
	
	@Transactional
	@Override
	public void create(final AttributeForm attrForm) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcOperations().update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				String sql = "INSERT INTO " +quoteTable("attribute")+ "(attribute_group_id, sort_order) VALUES(?, ?) ";
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, attrForm.getAttributeGroupId());
				ps.setInt(2, attrForm.getSortOrder());
				return ps;
			}
		}, keyHolder);
		Integer attrId = keyHolder.getKey().intValue();
		attrForm.setId(attrId);
		setAdditionalFormValues(attrForm);
	}

	@Transactional
	@Override
	public void update(AttributeForm attrForm) {
		String sql = "UPDATE " +quoteTable("attribute")+ " SET attribute_group_id=?, sort_order=? WHERE attribute_id=?";
		getJdbcOperations().update(sql, attrForm.getAttributeGroupId(), 
				attrForm.getSortOrder(), attrForm.getId());
		
		sql = "DELETE FROM " +quoteTable("attribute_description")+ " WHERE attribute_id=?";
		getJdbcOperations().update(sql, attrForm.getId());
		
		setAdditionalFormValues(attrForm);
	}
	
	protected void setAdditionalFormValues(AttributeForm attrForm){
		for(AttributeDesc desc: attrForm.getDescs()){
			String sql = "INSERT INTO " +quoteTable("attribute_description")
					+ "(attribute_id, language_id, name) VALUES(?,?,?)";
			getJdbcOperations().update(sql, attrForm.getId(), 
					desc.getLanguageId(), desc.getName());
		}
	}
	
	@Transactional
	@Override
	public void delete(Integer attrId) {
		String sql = "DELETE FROM " +quoteTable("attribute")+ " WHERE attribute_id=?";
		getJdbcOperations().update(sql, attrId);
		sql = "DELETE FROM " +quoteTable("attribute_description")+ " WHERE attribute_id=?";
		getJdbcOperations().update(sql, attrId);
	}
	
	@Override
	public AttributeForm newForm(){
		AttributeForm attrForm = new AttributeForm();
		attrForm.setDescs(languageAdminModel
				.createDescriptionList(AttributeDesc.class));
		return attrForm;
	}
	
	@Override
	public AttributeForm getForm(Integer attrId) {
		AttributeForm attrForm = (AttributeForm)get(attrId, AttributeForm.class);
		attrForm.setDescs(getDescriptions(attrId));
		return attrForm;
	}
	
	@Override
	public Attribute get(Integer attrId, Class<? extends Attribute> clazz) {
		String sql = "SELECT * FROM " + quoteTable("attribute")+" WHERE attribute_id = ?";
		return (Attribute)getJdbcOperations().queryForObject(
				sql, new Object[]{attrId}, new AttributeRowMapper().setTargetClass(clazz));
	}
	
	@Override
	public List<Attribute> getList(PageParam pageParam) {
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		String sql = "SELECT *, (SELECT agd.name FROM "+quoteTable("attribute_group_description")
				+" agd WHERE agd.attribute_group_id = a.attribute_group_id AND agd.language_id = ?) AS attribute_group FROM "
				+quoteTable("attribute")+ " a LEFT JOIN " +quoteTable("attribute_description")+ " ad ON (a.attribute_id = ad.attribute_id) WHERE ad.language_id =?";
		//sortedKeys={"ad.name", "attribute_group", "a.sort_order"}
		QueryBean query = createPaginationQuery(sql, pageParam);
		query.addParameters(languageId, languageId);
		return getJdbcOperations().query(query.getSql(), 
				query.getParameters(), new AttributeRowMapper(){
					@Override
					public Attribute mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Attribute attr = super.mapRow(rs, rowNum);
						attr.setName(rs.getString("name"));
						attr.setAttributeGroupName(rs.getString("attribute_group"));
						return attr;
					}
			}.setTargetClass(Attribute.class));
	}

	@Override
	public List<AttributeDesc> getDescriptions(Integer attrId) {
		String sql = "SELECT ad.attribute_id, ad.language_id, l.name AS language_name, l.image AS language_image, ad.name FROM " +
				quoteTable("attribute_description")+" ad INNER JOIN "
				+quoteTable("language")+" l ON ad.language_id=l.language_id WHERE ad.attribute_id=?";
		return getJdbcOperations().query(sql, 
				new Object[]{attrId}, new AttributeRowMapper.Desc());
	}
	
	@Override
	public int getTotal() {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("attribute");
		return getJdbcOperations().queryForObject(sql, Integer.class);
	}
	
	@Override
	public int getTotalByAttributeGroupId(Integer attrGrpId){
		
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("attribute")
				+ "WHERE attribute_group_id=?";
		return getJdbcOperations().queryForObject(sql, Integer.class, attrGrpId);
	}
	
}
