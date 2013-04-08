package com.jpoweredcart.admin.model.report.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.bean.report.SaleCouponRpt;

public class SaleCouponRptRowMapper implements RowMapper<SaleCouponRpt>{

	@Override
	public SaleCouponRpt mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		SaleCouponRpt scr = new SaleCouponRpt();
		scr.setCouponId(rs.getInt("coupon_id"));
		scr.setName(rs.getString("name"));
		scr.setCode(rs.getString("code"));
		scr.setOrders(rs.getInt("orders"));
		scr.setTotal(rs.getBigDecimal("total"));
		return scr;
	}

}
