package org.jpoweredcart.admin.model.localisation.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jpoweredcart.common.entity.localisation.Currency;
import org.springframework.jdbc.core.RowMapper;


public class CurrencyRowMapper implements RowMapper<Currency> {

	@Override
	public Currency mapRow(ResultSet rs, int rowNum) throws SQLException {
		Currency currency = new Currency();
		currency.setId(rs.getInt("currency_id"));
		currency.setTitle(rs.getString("title"));
		currency.setCode(rs.getString("code"));
		currency.setSymbolLeft(rs.getString("symbol_left"));
		currency.setSymbolRight(rs.getString("symbol_right"));
		currency.setDecimalPlace(rs.getString("decimal_place"));
		currency.setValue(rs.getBigDecimal("value"));
		currency.setStatus(rs.getInt("status"));
		currency.setDateModified(rs.getDate("date_modified"));
		return currency;
	}
}

