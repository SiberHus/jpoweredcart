package com.jpoweredcart.admin.model.report.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.bean.report.SaleCoupon;

public class SaleCouponRowMapper implements RowMapper<SaleCoupon>{

	@Override
	public SaleCoupon mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		SaleCoupon scr = new SaleCoupon();
		scr.setCouponId(rs.getInt("coupon_id"));
		scr.setName(rs.getString("name"));
		scr.setCode(rs.getString("code"));
		scr.setOrders(rs.getInt("orders"));
		scr.setTotal(rs.getBigDecimal("total"));
		return scr;
	}

}
