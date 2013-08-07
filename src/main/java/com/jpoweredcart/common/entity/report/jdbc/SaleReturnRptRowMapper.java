package com.jpoweredcart.common.entity.report.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.form.report.SaleReturnRpt;

public class SaleReturnRptRowMapper implements RowMapper<SaleReturnRpt>{

	@Override
	public SaleReturnRpt mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		
		SaleReturnRpt srr = new SaleReturnRpt();
		srr.setStartDate(rs.getDate("start_date"));
		srr.setEndDate(rs.getDate("end_date"));
		srr.setReturns(rs.getInt("returns"));
		
		return srr;
	}
	
}
