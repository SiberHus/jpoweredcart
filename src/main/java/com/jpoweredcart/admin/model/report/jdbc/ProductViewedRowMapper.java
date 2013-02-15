package com.jpoweredcart.admin.model.report.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.bean.report.ProductViewed;

public class ProductViewedRowMapper implements RowMapper<ProductViewed>{

	private int totalProductViews;
	
	public ProductViewedRowMapper(int totalProductViews){
		this.totalProductViews = totalProductViews;
	}
	
	@Override
	public ProductViewed mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		ProductViewed pvr = new ProductViewed();
		pvr.setName(rs.getString("name"));
		pvr.setModel(rs.getString("model"));
		int viewed = rs.getInt("viewed");
		pvr.setViewed(viewed);
		float percent = ((float)viewed)/totalProductViews*100;
		pvr.setPercent(percent);
		return pvr;
	}
	
	
}
