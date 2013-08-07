package com.jpoweredcart.common.entity.localisation.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.localisation.Currency;
import com.jpoweredcart.common.jdbc.ObjectFactoryRowMapper;


public class CurrencyRowMapper extends ObjectFactoryRowMapper<Currency> {
	
	@Override
	public Currency mapRow(ResultSet rs, Currency object) throws SQLException {
		object.setId(rs.getInt("currency_id"));
		object.setTitle(rs.getString("title"));
		object.setCode(rs.getString("code"));
		object.setSymbolLeft(rs.getString("symbol_left"));
		object.setSymbolRight(rs.getString("symbol_right"));
		try{
			object.setDecimalPlace(rs.getInt("decimal_place"));
		}catch(Exception e){
			/*
			 * Opencart define this column as char(1), so it's possible that
			 * user may enter character instead of number
			 */
			e.printStackTrace();//TODO: remove this when confirm that JDBC API can convert type
		}
		object.setValue(rs.getBigDecimal("value"));
		object.setStatus(rs.getShort("status"));
		object.setDateModified(rs.getDate("date_modified"));
		return object;
	}
	
}

