package com.jpoweredcart.admin.model.localisation;

import java.util.List;

import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.localisation.Currency;


public interface CurrencyAdminModel {
	
	public void create(Currency currency);
	
	public void update(Currency currency);
	
	public void delete(Integer currencyId);
	
	public Currency get(Integer currencyId);
	
	public Currency getOneByCode(String code);
	
	public List<Currency> getList(PageParam pageParam);
	
	public void updateDatabase(boolean force);
	
	public int getTotal();
	
	
}