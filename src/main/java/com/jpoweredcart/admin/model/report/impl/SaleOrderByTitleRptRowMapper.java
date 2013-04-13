package com.jpoweredcart.admin.model.report.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.form.report.SaleOrderByTitleRpt;

public class SaleOrderByTitleRptRowMapper implements RowMapper<SaleOrderByTitleRpt>{

	@Override
	public SaleOrderByTitleRpt mapRow(ResultSet rs, int rowNum) throws SQLException {
		SaleOrderByTitleRpt sotr = new SaleOrderByTitleRpt();
		sotr.setDateStart(rs.getDate("date_start"));
		sotr.setDateEnd(rs.getDate("date_end"));
		sotr.setTitle(rs.getString("title"));
		sotr.setOrders(rs.getInt("orders"));
		sotr.setTotal(rs.getBigDecimal("total"));
		return sotr;
	}
	
}
