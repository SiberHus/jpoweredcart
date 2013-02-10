package com.jpoweredcart.admin.model.report.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.bean.report.SalesCoupon;

public class SalesCouponRowMapper implements RowMapper<SalesCoupon>{

	@Override
	public SalesCoupon mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		SalesCoupon scr = new SalesCoupon();
		scr.setCouponId(rs.getInt("coupon_id"));
		scr.setName(rs.getString("name"));
		scr.setCode(rs.getString("code"));
		scr.setOrders(rs.getInt("orders"));
		scr.setTotal(rs.getInt("total"));
		return scr;
	}

}
