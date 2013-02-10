package com.jpoweredcart.admin.model.report.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.bean.report.SalesOrderByTitleReport;

public class SalesOrderTitleReportRowMapper implements RowMapper<SalesOrderByTitleReport>{

	@Override
	public SalesOrderByTitleReport mapRow(ResultSet rs, int rowNum) throws SQLException {
		SalesOrderByTitleReport sotr = new SalesOrderByTitleReport();
		sotr.setDateStart(rs.getDate("date_start"));
		sotr.setDateEnd(rs.getDate("date_end"));
		sotr.setTitle(rs.getString("title"));
		sotr.setOrders(rs.getInt("orders"));
		sotr.setTotal(rs.getInt("total"));
		return sotr;
	}
	
}
