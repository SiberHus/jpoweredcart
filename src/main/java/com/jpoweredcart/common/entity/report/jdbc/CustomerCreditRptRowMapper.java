package com.jpoweredcart.common.entity.report.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.form.report.CustomerCreditRpt;

public class CustomerCreditRptRowMapper implements RowMapper<CustomerCreditRpt> {

	@Override
	public CustomerCreditRpt mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		CustomerCreditRpt credit = new CustomerCreditRpt();
		
		credit.setCustomerId(rs.getInt("customer_id"));
		String customerName = rs.getString("firstname")+" "+
				rs.getString("lastname");
		credit.setCustomerName(customerName);
		credit.setEmail(rs.getString("email"));
		credit.setCustomerGroup(rs.getString("customer_group"));
		credit.setStatus(rs.getShort("status"));
		
		credit.setTotal(rs.getBigDecimal("total"));
		
		return credit;
	}

}
