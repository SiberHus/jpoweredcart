package com.jpoweredcart.admin.model.report.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.bean.report.CustomerOnlineRpt;

public class CustomerOnlineRptRowMapper implements RowMapper<CustomerOnlineRpt>{
	
	@Override
	public CustomerOnlineRpt mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		
		CustomerOnlineRpt rpt = new CustomerOnlineRpt();
		rpt.setIp(rs.getString("ip"));
		rpt.setCustomerId(rs.getInt("customer_id"));
		String customerName = rs.getString("firstname")+
				" "+rs.getString("lastname");
		rpt.setCustomerName(customerName);
		rpt.setUrl(rs.getString("url"));
		rpt.setReferer(rs.getString("referer"));
		rpt.setDateModified(rs.getTimestamp("date_added"));
		
		return rpt;
	}
	
}
