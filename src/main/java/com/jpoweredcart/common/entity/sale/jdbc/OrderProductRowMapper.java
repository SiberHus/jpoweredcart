package com.jpoweredcart.common.entity.sale.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.sale.OrderProduct;
import com.jpoweredcart.common.jdbc.ObjectFactoryRowMapper;

public class OrderProductRowMapper extends ObjectFactoryRowMapper<OrderProduct>{
	
	@Override
	public OrderProduct mapRow(ResultSet rs, OrderProduct object) throws SQLException {
		
		object.setId(rs.getInt("order_product_id"));
		object.setOrderId(rs.getInt("order_id"));
		object.setProductId(rs.getInt("product_id"));
		object.setName(rs.getString("name"));
		object.setModel(rs.getString("model"));
		object.setQuantity(rs.getInt("quantity"));
		object.setPrice(rs.getBigDecimal("price"));
		object.setTotal(rs.getBigDecimal("total"));
		object.setTax(rs.getBigDecimal("tax"));
		object.setReward(rs.getInt("reward"));
		return object;
	}
	
}
