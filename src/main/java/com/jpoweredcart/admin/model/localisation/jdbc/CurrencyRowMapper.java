package com.jpoweredcart.admin.model.localisation.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.bean.localisation.CurrencyForm;
import com.jpoweredcart.common.entity.localisation.Currency;


public class CurrencyRowMapper implements RowMapper<Currency> {

	private static void setProperties(ResultSet rs, Currency currency) throws SQLException{
		currency.setId(rs.getInt("currency_id"));
		currency.setTitle(rs.getString("title"));
		currency.setCode(rs.getString("code"));
		currency.setSymbolLeft(rs.getString("symbol_left"));
		currency.setSymbolRight(rs.getString("symbol_right"));
		currency.setDecimalPlace(rs.getString("decimal_place"));
		currency.setValue(rs.getBigDecimal("value"));
		currency.setStatus(rs.getInt("status"));
		currency.setDateModified(rs.getDate("date_modified"));
	}
	
	@Override
	public Currency mapRow(ResultSet rs, int rowNum) throws SQLException {
		Currency currency = new Currency();
		setProperties(rs, currency);
		return currency;
	}
	
	public static class Form implements RowMapper<CurrencyForm> {

		@Override
		public CurrencyForm mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			CurrencyForm currencyForm = new CurrencyForm();
			setProperties(rs, currencyForm);
			return currencyForm;
		}
		
	}
}

