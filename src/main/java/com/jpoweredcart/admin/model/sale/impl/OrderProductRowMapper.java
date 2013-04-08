package com.jpoweredcart.admin.model.sale.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.sale.OrderProduct;
import org.springframework.jdbc.core.RowMapper;

public class OrderProductRowMapper implements RowMapper<OrderProduct>{

	@Override
	public OrderProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		OrderProduct orderProduct = new OrderProduct();
		orderProduct.setId(rs.getInt("order_product_id"));
		orderProduct.setOrderId(rs.getInt("order_id"));
		orderProduct.setProductId(rs.getInt("product_id"));
		orderProduct.setName(rs.getString("name"));
		orderProduct.setModel(rs.getString("model"));
		orderProduct.setQuantity(rs.getInt("quantity"));
		orderProduct.setPrice(rs.getBigDecimal("price"));
		orderProduct.setTotal(rs.getBigDecimal("total"));
		orderProduct.setTax(rs.getBigDecimal("tax"));
		orderProduct.setReward(rs.getInt("reward"));
		return orderProduct;
	}
	
}
