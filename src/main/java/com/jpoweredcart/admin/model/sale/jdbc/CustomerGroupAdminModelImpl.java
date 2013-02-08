package com.jpoweredcart.admin.model.sale.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.jpoweredcart.admin.model.sale.CustomerGroupAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.QueryBean;
import com.jpoweredcart.common.entity.sale.CustomerGroup;
import com.jpoweredcart.common.service.SettingKey;
import com.jpoweredcart.common.service.SettingService;
import org.springframework.jdbc.core.JdbcOperations;

public class CustomerGroupAdminModelImpl extends BaseModel implements CustomerGroupAdminModel {

	public CustomerGroupAdminModelImpl(SettingService settingService, JdbcOperations jdbcOperations){
		super(settingService, jdbcOperations);
	}
	
	@Override
	public void create(CustomerGroup customerGroup) {
		
	}

	@Override
	public void update(CustomerGroup customerGroup) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer customerGroupId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CustomerGroup get(Integer customerGroupId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CustomerGroup> getList(PageParam pageParam) {
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
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
	public int getTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

}
