package com.jpoweredcart.common.entity.report.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.form.report.CustomerRewardRpt;

public class CustomerRewardRptRowMapper implements RowMapper<CustomerRewardRpt>{
	
	@Override
	public CustomerRewardRpt mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		CustomerRewardRpt reward = new CustomerRewardRpt();
		
		reward.setCustomerId(rs.getInt("customer_id"));
		String customerName = rs.getString("firstname")+" "+
				rs.getString("lastname");
		reward.setCustomerName(customerName);
		reward.setEmail(rs.getString("email"));
		reward.setCustomerGroup(rs.getString("customer_group"));
		reward.setStatus(rs.getShort("status"));
		
		reward.setPoints(rs.getInt("points"));
		reward.setOrders(rs.getInt("orders"));
		reward.setTotal(rs.getBigDecimal("total"));
		
		return reward;
	}
	
}
