package org.jpoweredcart.admin.model.sale.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.jpoweredcart.admin.entity.sale.CustomerGroup;
import org.jpoweredcart.admin.model.sale.CustomerGroupAdminModel;
import org.jpoweredcart.common.BaseModel;
import org.jpoweredcart.common.ConfigKey;
import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.QueryBean;
import org.jpoweredcart.common.service.ConfigService;
import org.springframework.jdbc.core.JdbcOperations;

public class CustomerGroupAdminModelImpl extends BaseModel implements CustomerGroupAdminModel {

	public CustomerGroupAdminModelImpl(ConfigService configService, JdbcOperations jdbcOperations){
		super(configService, jdbcOperations);
	}
	
	@Override
	public void addCustomerGroup(CustomerGroup customerGroup) {
		
	}

	@Override
	public void updateCustomerGroup(CustomerGroup customerGroup) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCustomerGroup(Integer customerGroupId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CustomerGroup getCustomerGroup(Integer customerGroupId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CustomerGroup> getCustomerGroups(PageParam pageParam) {
		Integer languageId = getConfigService().get(ConfigKey.ADMIN_LANGUAGE_ID, Integer.class);
		String sql = "SELECT * FROM " +quoteTable("customer_group")+ " cg LEFT JOIN " +quoteTable("customer_group_description")
				+ " cgd ON (cg.customer_group_id = cgd.customer_group_id) WHERE cgd.language_id = ?";
		QueryBean query = createPaginationQueryFromSql(sql, pageParam, 
				new String[]{"cgd.name", "cg.sort_order"});
		query.addParameter(languageId);
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
	
	@Override
	public int getTotalCustomerGroups() {
		// TODO Auto-generated method stub
		return 0;
	}

}
