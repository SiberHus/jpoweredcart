package com.jpoweredcart.common.entity.sale.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.sale.VoucherHistory;
import com.jpoweredcart.common.jdbc.ObjectFactoryRowMapper;

public class VoucherHistoryRowMapper extends ObjectFactoryRowMapper<VoucherHistory>{
	
	@Override
	public VoucherHistory mapRow(ResultSet rs, VoucherHistory object) throws SQLException {
		object.setId(rs.getInt("voucher_history_id"));
		object.setVoucherId(rs.getInt("voucher_id"));
		object.setOrderId(rs.getInt("order_id"));
		object.setAmount(rs.getBigDecimal("amount"));
		object.setDateAdded(rs.getDate("date_added"));
		return object;
	}

}
