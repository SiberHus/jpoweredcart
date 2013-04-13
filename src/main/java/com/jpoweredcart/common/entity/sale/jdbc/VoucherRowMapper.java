package com.jpoweredcart.common.entity.sale.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.sale.Voucher;
import org.springframework.jdbc.core.RowMapper;

public class VoucherRowMapper implements RowMapper<Voucher>{

	public Voucher newObject(){
		return new Voucher();
	}
	
	@Override
	public Voucher mapRow(ResultSet rs, int rowNum) throws SQLException {
		Voucher voucher = newObject();
		voucher.setId(rs.getInt("voucher_id"));
		voucher.setOrderId(rs.getInt("order_id"));
		voucher.setCode(rs.getString("code"));
		voucher.setFromName(rs.getString("from_name"));
		voucher.setFromEmail(rs.getString("from_email"));
		voucher.setToName(rs.getString("to_name"));
		voucher.setToEmail(rs.getString("to_email"));
		voucher.setVoucherThemeId(rs.getInt("voucher_theme_id"));
		voucher.setMessage(rs.getString("message"));
		voucher.setAmount(rs.getBigDecimal("amount"));
		voucher.setStatus(rs.getInt("status"));
		voucher.setDateAdded(rs.getDate("date_added"));
		return voucher;
	}
	
}
