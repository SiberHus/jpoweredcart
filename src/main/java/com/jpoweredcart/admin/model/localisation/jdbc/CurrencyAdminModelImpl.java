package com.jpoweredcart.admin.model.localisation.jdbc;

import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcOperations;

import com.jpoweredcart.admin.bean.localisation.CurrencyForm;
import com.jpoweredcart.admin.model.localisation.CurrencyAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.QueryBean;
import com.jpoweredcart.common.entity.localisation.Currency;
import com.jpoweredcart.common.service.SettingService;


public class CurrencyAdminModelImpl extends BaseModel implements CurrencyAdminModel {
	
	public CurrencyAdminModelImpl(SettingService settingService, JdbcOperations jdbcOperations){
		super(settingService, jdbcOperations);
	}
	
	@Override
	public void create(CurrencyForm currencyForm) {
		String sql = "INSERT INTO " +quoteTable("currency")+ "(title, code, symbol_left, symbol_right, " +
				"decimal_place, value, status, date_modifiled) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		getJdbcOperations().update(sql, currencyForm.getTitle(), currencyForm.getCode(), currencyForm.getSymbolLeft(),
				currencyForm.getSymbolRight(), currencyForm.getDecimalPlace(), currencyForm.getValue(), 
				currencyForm.getStatus(), new Date());
	}
	
	@Override
	public void update(CurrencyForm currencyForm) {
		String sql = "UPDATE " +quoteTable("currency")+ " SET title=?, code=?, symbol_left=?, symbol_right=?, " +
				"decimal_place=?, value=?, status=?, date_modified=? WHERE currency_id=?";
		getJdbcOperations().update(sql, currencyForm.getTitle(), currencyForm.getCode(), currencyForm.getSymbolLeft(),
				currencyForm.getSymbolRight(), currencyForm.getDecimalPlace(), currencyForm.getValue(), 
				currencyForm.getStatus(), new Date(), currencyForm.getId());
	}
	
	@Override
	public void delete(Integer currencyId) {
		String sql = "DELETE FROM "+quoteTable("currency")+" WHERE currency_id=?";
		getJdbcOperations().update(sql, currencyId);
	}
	
	@Override
	public CurrencyForm newForm() {
		
		return new CurrencyForm();
	}

	@Override
	public CurrencyForm getForm(Integer currencyId) {
		String sql = "SELECT * FROM " +quoteTable("currency")+ " WHERE currency_id = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{currencyId}, new CurrencyRowMapper.Form());
	}
	
	@Override
	public Currency get(Integer currencyId) {
		return getForm(currencyId);
	}
	
	@Override
	public Currency getOneByCode(String code) {
		String sql = "SELECT * FROM " +quoteTable("currency")+ " WHERE code = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{code}, new CurrencyRowMapper());
	}
	
	@Override
	public List<Currency> getList(PageParam pageParam) {
		QueryBean query = createPaginationQuery("currency", pageParam, 
				new String[]{"title", "code", "value", "date_modified"});
		List<Currency> currencyList = getJdbcOperations().query(query.getSql(), 
				query.getParameters(), new CurrencyRowMapper());
		//TODO: Do we need to update the cache here?
		return currencyList;
	}
	
	@Override
	public void updateDatabase(boolean force) {
		//TODO: Implement this method
	}

	@Override
	public int getTotal() {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("currency");
		return getJdbcOperations().queryForInt(sql);
	}
	
	
}
