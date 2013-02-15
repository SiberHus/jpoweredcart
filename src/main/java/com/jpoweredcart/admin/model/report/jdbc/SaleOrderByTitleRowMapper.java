package com.jpoweredcart.admin.model.report.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.bean.report.SaleOrderByTitle;

public class SaleOrderByTitleRowMapper implements RowMapper<SaleOrderByTitle>{

	@Override
	public SaleOrderByTitle mapRow(ResultSet rs, int rowNum) throws SQLException {
		SaleOrderByTitle sotr = new SaleOrderByTitle();
		sotr.setDateStart(rs.getDate("date_start"));
		sotr.setDateEnd(rs.getDate("date_end"));
		sotr.setTitle(rs.getString("title"));
		sotr.setOrders(rs.getInt("orders"));
		sotr.setTotal(rs.getBigDecimal("total"));
		return sotr;
	}
	
}
