package org.jpoweredcart.admin.model.sale.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jpoweredcart.common.entity.sale.CustomerGroup;
import org.jpoweredcart.common.entity.sale.CustomerGroupDesc;
import org.springframework.jdbc.core.RowMapper;

public class CustomerGroupRowMapper implements RowMapper<CustomerGroup>{

	@Override
	public CustomerGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		CustomerGroup customerGroup = new CustomerGroup();
		customerGroup.setId(rs.getInt("customer_group_id"));
		customerGroup.setApproval(rs.getBoolean("approval"));
		customerGroup.setCompanyIdDisplay(rs.getBoolean("company_id_display"));
		customerGroup.setCompanyIdRequired(rs.getBoolean("company_id_required"));
		customerGroup.setTaxIdDisplay(rs.getBoolean("tax_id_display"));
		customerGroup.setTaxIdRequired(rs.getBoolean("tax_id_required"));
		customerGroup.setSortOrder(rs.getInt("sort_order"));
		
		return customerGroup;
	}

	public static class Desc implements RowMapper<CustomerGroupDesc>{

		@Override
		public CustomerGroupDesc mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			
			CustomerGroupDesc desc = new CustomerGroupDesc();
			desc.setLengthClassId(rs.getInt("length_class_id"));
			desc.setLanguageId(rs.getInt("language_id"));
			desc.setLanguageName(rs.getString("language_name"));
			desc.setLanguageImage(rs.getString("language_image"));
			desc.setName(rs.getString("name"));
			desc.setDescription(rs.getString("description"));
			return desc;
		}
		
		
	}
}
