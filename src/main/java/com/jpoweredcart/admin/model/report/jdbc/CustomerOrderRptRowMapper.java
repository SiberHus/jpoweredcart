package com.jpoweredcart.admin.model.report.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.bean.report.CustomerOrderRpt;

public class CustomerOrderRptRowMapper implements RowMapper<CustomerOrderRpt>{
	
	@Override
	public CustomerOrderRpt mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		
		CustomerOrderRpt order = new CustomerOrderRpt();
		
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
