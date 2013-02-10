package com.jpoweredcart.admin.model.report.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.bean.report.SalesReturnReport;

public class SalesReturnReportRowMapper implements RowMapper<SalesReturnReport>{

	@Override
	public SalesReturnReport mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		
		SalesReturnReport srr = new SalesReturnReport();
		srr.setStartDate(rs.getDate("start_date"));
		srr.setEndDate(rs.getDate("end_date"));
		srr.setReturns(rs.getInt("returns"));
		
		return srr;
	}
	
}
