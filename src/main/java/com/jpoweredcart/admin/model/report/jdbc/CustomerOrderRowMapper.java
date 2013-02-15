package com.jpoweredcart.admin.model.report.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.bean.report.CustomerOrder;

public class CustomerOrderRowMapper implements RowMapper<CustomerOrder>{
	
	@Override
	public CustomerOrder mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		
		CustomerOrder order = new CustomerOrder();
		
		order.setCustomerId(rs.getInt("customer_id"));
		String customerName = rs.getString("firstname")+" "+
				rs.getString("lastname");
		order.setCustomerName(customerName);
		order.setEmail(rs.getString("email"));
		order.setCustomerGroup(rs.getString("customer_group"));
		order.setStatus(rs.getShort("status"));
		
		order.setOrders(rs.getInt("orders"));
		order.setProducts(rs.getInt("products"));
		order.setTotal(rs.getBigDecimal("total"));
		return order;
	}
	
}
