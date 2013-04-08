package com.jpoweredcart.admin.model.sale.impl;

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

import com.jpoweredcart.admin.bean.sale.CustomerGroupForm;
import com.jpoweredcart.admin.model.localisation.LanguageAdminModel;
import com.jpoweredcart.admin.model.sale.CustomerGroupAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.QueryBean;
import com.jpoweredcart.common.entity.sale.CustomerGroup;
import com.jpoweredcart.common.entity.sale.CustomerGroupDesc;
import com.jpoweredcart.common.service.setting.SettingKey;

public class CustomerGroupAdminModelImpl extends BaseModel implements CustomerGroupAdminModel {

	@Inject
	private LanguageAdminModel languageAdminModel;
	
	@Transactional
	@Override
	public void create(final CustomerGroupForm cgForm) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcOperations().update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				String sql = "INSERT INTO "+quoteTable("customer_group")+" SET approval = ?, sort_order = ?";
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, cgForm.getApproval());
				ps.setInt(2, cgForm.getSortOrder());
				return ps;
			}
		}, keyHolder);
		Integer attrId = keyHolder.getKey().intValue();
		cgForm.setId(attrId);
		setAdditionalFormValues(cgForm);
	}

	@Transactional
	@Override
	public void update(CustomerGroupForm cgForm) {
		String sql = "UPDATE " +quoteTable("customer_group")+ " SET approval=?, sort_order=? WHERE customer_group_id=?";
		getJdbcOperations().update(sql, cgForm.getApproval(), 
				cgForm.getSortOrder(), cgForm.getId());
		
		sql = "DELETE FROM " +quoteTable("customer_group_description")+ " WHERE customer_group_id=?";
		getJdbcOperations().update(sql, cgForm.getId());
		
		setAdditionalFormValues(cgForm);
	}
	
	protected void setAdditionalFormValues(CustomerGroupForm cgForm){
		for(CustomerGroupDesc desc: cgForm.getDescs()){
			String sql = "INSERT INTO "+quoteTable("customer_group_description")+
				" SET customer_group_id = ?, language_id = ?, name = ?, description = ?";
			getJdbcOperations().update(sql, cgForm.getId(), desc.getLanguageId(), 
					desc.getName(), desc.getDescription());
		}
	}
	
	@Transactional
	@Override
	public void delete(Integer cgId) {
		String tables[] = new String[]{"customer_group", "customer_group_description",
				"product_discount","product_special","product_reward"
		};
		for(String table: tables){
			String sql = "DELETE FROM " +quoteTable(table)+ " WHERE customer_group_id=?";
			getJdbcOperations().update(sql, cgId);
		}
	}
	
	@Override
	public CustomerGroupForm newForm(){
		CustomerGroupForm cgForm = new CustomerGroupForm();
		cgForm.setDescs(languageAdminModel
				.createDescriptionList(CustomerGroupDesc.class));
		return cgForm;
	}
	
	@Override
	public CustomerGroupForm getForm(Integer cgId){
//		String sql = "SELECT DISTINCT * FROM " +quoteTable("customer_group")+ " cg LEFT JOIN " +quoteTable("customer_group_description")+ 
//				" cgd ON (cg.customer_group_id = cgd.customer_group_id) WHERE cg.customer_group_id = ? AND cgd.language_id = ?";
		String sql = "SELECT DISTINCT * FROM " +quoteTable("customer_group")+ " cg WHERE cg.customer_group_id = ?";
		CustomerGroupForm cgForm = getJdbcOperations().queryForObject(sql, 
				new Object[]{cgId}, new CustomerGroupRowMapper.Form());
		cgForm.setDescs(getDescriptions(cgId));
		return cgForm;
	}
	
	@Override
	public CustomerGroup get(Integer cgId) {
		
		return getForm(cgId);
	}
	
	@Override
	public List<CustomerGroup> getList(PageParam pageParam) {
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		String sql = "SELECT * FROM " +quoteTable("customer_group")+ " cg LEFT JOIN " +quoteTable("customer_group_description")
				+ " cgd ON (cg.customer_group_id = cgd.customer_group_id) WHERE cgd.language_id = ?";
		QueryBean query = createPaginationQueryFromSql(sql, pageParam, 
				new String[]{"cgd.name", "cg.sort_order"});
		query.addParameters(languageId);
		List<CustomerGroup> customerGroupList = getJdbcOperations()
				.query(query.getSql(), query.getParameters(), new CustomerGroupRowMapper(){
					@Override
					public CustomerGroup mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						CustomerGroup customerGroup = super.mapRow(rs, rowNum);
						customerGroup.setName(rs.getString("name"));
						return customerGroup;
					}
				});
		return customerGroupList;
	}
	
	public List<CustomerGroupDesc> getDescriptions(Integer cgId){
		String sql = "SELECT cgd.customer_group_id, cgd.language_id, l.name AS language_name, l.image AS language_image, cgd.name, cgd.description FROM " +
				quoteTable("customer_group_description")+" cgd INNER JOIN "
				+quoteTable("language")+" l ON cgd.language_id=l.language_id WHERE cgd.customer_group_id=?";
		
		return getJdbcOperations().query(sql, 
				new Object[]{cgId}, new CustomerGroupRowMapper.Desc());
	}
	
	@Override
	public int getTotal() {
		String sql = "SELECT COUNT(*) AS total FROM " + quoteTable("customer_group");
		return getJdbcOperations().queryForInt(sql);
	}

}
