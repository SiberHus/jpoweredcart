package org.jpoweredcart.admin.model.localisation.jdbc;

import java.util.Date;
import java.util.List;

import org.jpoweredcart.admin.model.localisation.CurrencyAdminModel;
import org.jpoweredcart.common.BaseModel;
import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.QueryBean;
import org.jpoweredcart.common.entity.localisation.Currency;
import org.jpoweredcart.common.service.SettingService;
import org.springframework.jdbc.core.JdbcOperations;


public class CurrencyAdminModelImpl extends BaseModel implements CurrencyAdminModel {
	
	public CurrencyAdminModelImpl(SettingService settingService, JdbcOperations jdbcOperations){
		super(settingService, jdbcOperations);
	}
	
	@Override
	public void addCurrency(Currency currency) {
		String sql = "INSERT INTO " +quoteTable("currency")+ "(title, code, symbol_left, symbol_right, " +
				"decimal_place, value, status, date_modifiled) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		getJdbcOperations().update(sql, currency.getTitle(), currency.getCode(), currency.getSymbolLeft(),
				currency.getSymbolRight(), currency.getDecimalPlace(), currency.getValue(), 
				currency.getStatus(), new Date());
	}
	
	@Override
	public void updateCurrency(Currency currency) {
		String sql = "UPDATE " +quoteTable("currency")+ " SET title=?, code=?, symbol_left=?, symbol_right=?, " +
				"decimal_place=?, value=?, status=?, date_modified=? WHERE currency_id=?";
		getJdbcOperations().update(sql, currency.getTitle(), currency.getCode(), currency.getSymbolLeft(),
				currency.getSymbolRight(), currency.getDecimalPlace(), currency.getValue(), 
				currency.getStatus(), new Date(), currency.getId());
	}
	
	@Override
	public void saveCurrency(Currency currency) {
		if(currency.getId()!=null){
			updateCurrency(currency);
		}else{
			addCurrency(currency);
		}
	}
	
	@Override
	public void deleteCurrency(Integer currencyId) {
		String sql = "DELETE FROM "+quoteTable("currency")+" WHERE currency_id=?";
		getJdbcOperations().update(sql, currencyId);
	}

	@Override
	public Currency getCurrency(Integer currencyId) {
		String sql = "SELECT * FROM " +quoteTable("currency")+ " WHERE currency_id = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{currencyId}, new CurrencyRowMapper());
	}
	
	@Override
	public Currency getCurrencyByCode(String code) {
		String sql = "SELECT * FROM " +quoteTable("currency")+ " WHERE code = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{code}, new CurrencyRowMapper());
	}
	
	@Override
	public List<Currency> getCurrencies(PageParam pageParam) {
		QueryBean query = createPaginationQuery("currency", pageParam, 
				new String[]{"title", "code", "value", "date_modified"});
		List<Currency> currencyList = getJdbcOperations().query(query.getSql(), 
				query.getParameters(), new CurrencyRowMapper());
		//TODO: Do we need to update the cache here?
		return currencyList;
	}
	
	@Override
	public void updateCurrencies(boolean force) {
		//TODO: Implement this method
	}

	@Override
	public int getTotalCurrencies() {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("currency");
		return getJdbcOperations().queryForInt(sql);
	}
	
	
}
