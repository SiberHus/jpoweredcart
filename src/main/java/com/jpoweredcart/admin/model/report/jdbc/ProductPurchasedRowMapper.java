package com.jpoweredcart.admin.model.report.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.bean.report.ProductPurchased;

public class ProductPurchasedRowMapper implements RowMapper<ProductPurchased>{
	
	@Override
	public ProductPurchased mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		ProductPurchased pp = new ProductPurchased();
		pp.setName(rs.getString("name"));
		pp.setModel(rs.getString("model"));
		pp.setQuantity(rs.getInt("quantity"));
		pp.setTotal(rs.getBigDecimal("total"));
		return pp;
	}
	
}
