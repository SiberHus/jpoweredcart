package com.jpoweredcart.admin.model.report.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.bean.report.ProductsViewed;

public class ProductsViewedRowMapper implements RowMapper<ProductsViewed>{

	private int totalProductViews;
	
	public ProductsViewedRowMapper(int totalProductViews){
		this.totalProductViews = totalProductViews;
	}
	
	@Override
	public ProductsViewed mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		ProductsViewed pvr = new ProductsViewed();
		pvr.setName(rs.getString("name"));
		pvr.setModel(rs.getString("model"));
		int viewed = rs.getInt("viewed");
		pvr.setViewed(viewed);
		float percent = ((float)viewed)/totalProductViews*100;
		pvr.setPercent(percent);
		return pvr;
	}
	
	
}
