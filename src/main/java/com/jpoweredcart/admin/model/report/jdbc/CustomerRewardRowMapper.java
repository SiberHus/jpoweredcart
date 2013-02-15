package com.jpoweredcart.admin.model.report.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.bean.report.CustomerReward;

public class CustomerRewardRowMapper implements RowMapper<CustomerReward>{
	
	@Override
	public CustomerReward mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		CustomerReward reward = new CustomerReward();
		
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
