package com.jpoweredcart.common.entity.sale.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.sale.VoucherHistory;
import org.springframework.jdbc.core.RowMapper;

public class VoucherHistoryRowMapper implements RowMapper<VoucherHistory>{
	
	public VoucherHistory newObject(){
		return new VoucherHistory();
	}
	
	@Override
	public VoucherHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
		VoucherHistory vh = newObject();
		vh.setId(rs.getInt("voucher_history_id"));
		vh.setVoucherId(rs.getInt("voucher_id"));
		vh.setOrderId(rs.getInt("order_id"));
		vh.setAmount(rs.getBigDecimal("amount"));
		vh.setDateAdded(rs.getDate("date_added"));
		return vh;
	}

}
