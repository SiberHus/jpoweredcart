package org.jpoweredcart.admin.model.localisation;

import java.util.List;

import org.jpoweredcart.admin.entity.localisation.Currency;
import org.jpoweredcart.common.PageParam;


public interface CurrencyAdminModel {
	
	public void addCurrency(Currency currency);
	
	public void updateCurrency(Currency currency);
	
	public void saveCurrency(Currency currency);
	
	public void deleteCurrency(Integer currencyId);
	
	public Currency getCurrency(Integer currencyId);
	
	public Currency getCurrencyByCode(String code);
	
	public List<Currency> getCurrencies(PageParam pageParam);
	
	public void updateCurrencies(boolean force);
	
	public int getTotalCurrencies();
	
	
}
