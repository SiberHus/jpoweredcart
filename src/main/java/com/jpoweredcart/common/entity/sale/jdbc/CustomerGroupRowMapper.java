package com.jpoweredcart.common.entity.sale.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.common.entity.sale.CustomerGroup;
import com.jpoweredcart.common.entity.sale.CustomerGroupDesc;
import com.jpoweredcart.common.jdbc.ObjectFactoryRowMapper;

public class CustomerGroupRowMapper extends ObjectFactoryRowMapper<CustomerGroup>{
	
	@Override
	public CustomerGroup mapRow(ResultSet rs, CustomerGroup object) throws SQLException {
		
		object.setId(rs.getInt("customer_group_id"));
		object.setApproval(rs.getInt("approval"));
		object.setSortOrder(rs.getInt("sort_order"));
//		cg.setName(rs.getString("name"));
		return object;
	}
	
	public static class Desc implements RowMapper<CustomerGroupDesc>{

		@Override
		public CustomerGroupDesc mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			
			CustomerGroupDesc desc = new CustomerGroupDesc();
			desc.setCustomerGroupId(rs.getInt("customer_group_id"));
			desc.setLanguageId(rs.getInt("language_id"));
			desc.setLanguageName(rs.getString("language_name"));
			desc.setLanguageImage(rs.getString("language_image"));
			desc.setName(rs.getString("name"));
			desc.setDescription(rs.getString("description"));
			return desc;
		}
		
	}
	
}
