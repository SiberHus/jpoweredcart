package com.jpoweredcart.admin.model.report.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.bean.report.CustomerCredit;

public class CustomerCreditRowMapper implements RowMapper<CustomerCredit> {

	@Override
	public CustomerCredit mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		CustomerCredit credit = new CustomerCredit();
		
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
