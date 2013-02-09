package com.jpoweredcart.admin.model.report.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.bean.report.SalesOrderReport;

public class SalesOrderReportRowMapper implements RowMapper<SalesOrderReport>{

	@Override
	public SalesOrderReport mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		SalesOrderReport so = new SalesOrderReport();
		so.setDateStart(rs.getDate("date_start"));
		so.setDateEnd(rs.getDate("date_end"));
		so.setOrders(rs.getInt("orders"));
		so.setProducts(rs.getInt("products"));
		so.setTax(rs.getInt("tax"));
		so.setTotal(rs.getInt("total"));
		return so;
	}
	
}
