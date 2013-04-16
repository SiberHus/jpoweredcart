package com.jpoweredcart.common.entity.sale.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.sale.Voucher;
import com.jpoweredcart.common.jdbc.ObjectFactoryRowMapper;

public class VoucherRowMapper extends ObjectFactoryRowMapper<Voucher>{
	
	@Override
	public Voucher mapRow(ResultSet rs, Voucher object) throws SQLException {
		object.setId(rs.getInt("voucher_id"));
		object.setOrderId(rs.getInt("order_id"));
		object.setCode(rs.getString("code"));
		object.setFromName(rs.getString("from_name"));
		object.setFromEmail(rs.getString("from_email"));
		object.setToName(rs.getString("to_name"));
		object.setToEmail(rs.getString("to_email"));
		object.setVoucherThemeId(rs.getInt("voucher_theme_id"));
		object.setMessage(rs.getString("message"));
		object.setAmount(rs.getBigDecimal("amount"));
		object.setStatus(rs.getInt("status"));
		object.setDateAdded(rs.getDate("date_added"));
		return object;
	}
	
}
