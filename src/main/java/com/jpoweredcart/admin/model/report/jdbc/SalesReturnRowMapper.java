package com.jpoweredcart.admin.model.report.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.bean.report.SalesReturn;

public class SalesReturnRowMapper implements RowMapper<SalesReturn>{

	@Override
	public SalesReturn mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		
		SalesReturn srr = new SalesReturn();
		srr.setStartDate(rs.getDate("start_date"));
		srr.setEndDate(rs.getDate("end_date"));
		srr.setReturns(rs.getInt("returns"));
		
		return srr;
	}
	
}
