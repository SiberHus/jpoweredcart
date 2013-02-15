package com.jpoweredcart.admin.model.report.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.bean.report.ProductViewedRpt;

public class ProductViewedRptRowMapper implements RowMapper<ProductViewedRpt>{

	private int totalProductViews;
	
	public ProductViewedRptRowMapper(int totalProductViews){
		this.totalProductViews = totalProductViews;
	}
	
	@Override
	public ProductViewedRpt mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		ProductViewedRpt pvr = new ProductViewedRpt();
		pvr.setName(rs.getString("name"));
		pvr.setModel(rs.getString("model"));
		int viewed = rs.getInt("viewed");
		pvr.setViewed(viewed);
		float percent = ((float)viewed)/totalProductViews*100;
		pvr.setPercent(percent);
		return pvr;
	}
	
	
}
