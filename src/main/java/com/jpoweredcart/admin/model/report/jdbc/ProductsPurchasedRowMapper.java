package com.jpoweredcart.admin.model.report.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.bean.report.ProductsPurchased;

public class ProductsPurchasedRowMapper implements RowMapper<ProductsPurchased>{

	@Override
	public ProductsPurchased mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		ProductsPurchased pp = new ProductsPurchased();
		pp.setName(rs.getString("name"));
		pp.setModel(rs.getString("model"));
		pp.setQuantity(rs.getInt("quantity"));
		pp.setTotal(rs.getInt("total"));
		return pp;
	}

}
