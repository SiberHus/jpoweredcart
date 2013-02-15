package com.jpoweredcart.admin.model.report.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.bean.report.ProductPurchasedRpt;

public class ProductPurchasedRptRowMapper implements RowMapper<ProductPurchasedRpt>{
	
	@Override
	public ProductPurchasedRpt mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		ProductPurchasedRpt pp = new ProductPurchasedRpt();
		pp.setName(rs.getString("name"));
		pp.setModel(rs.getString("model"));
		pp.setQuantity(rs.getInt("quantity"));
		pp.setTotal(rs.getBigDecimal("total"));
		return pp;
	}
	
}
