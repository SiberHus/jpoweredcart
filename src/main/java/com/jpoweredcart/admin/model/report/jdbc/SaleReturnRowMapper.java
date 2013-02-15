package com.jpoweredcart.admin.model.report.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.bean.report.SaleReturn;

public class SaleReturnRowMapper implements RowMapper<SaleReturn>{

	@Override
	public SaleReturn mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		
		SaleReturn srr = new SaleReturn();
		srr.setStartDate(rs.getDate("start_date"));
		srr.setEndDate(rs.getDate("end_date"));
		srr.setReturns(rs.getInt("returns"));
		
		return srr;
	}
	
}
