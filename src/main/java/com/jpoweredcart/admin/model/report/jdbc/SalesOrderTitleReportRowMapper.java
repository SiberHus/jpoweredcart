package com.jpoweredcart.admin.model.report.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.bean.report.SalesOrderTitleReport;

public class SalesOrderTitleReportRowMapper implements RowMapper<SalesOrderTitleReport>{

	@Override
	public SalesOrderTitleReport mapRow(ResultSet rs, int rowNum) throws SQLException {
		SalesOrderTitleReport sotr = new SalesOrderTitleReport();
		sotr.setDateStart(rs.getDate("date_start"));
		sotr.setDateEnd(rs.getDate("date_end"));
		sotr.setTitle(rs.getString("title"));
		sotr.setOrders(rs.getInt("orders"));
		sotr.setTotal(rs.getInt("total"));
		return sotr;
	}
	
}
