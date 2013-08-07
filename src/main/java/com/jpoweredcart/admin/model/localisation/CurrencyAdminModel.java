package com.jpoweredcart.admin.model.localisation;

import java.util.List;

import com.jpoweredcart.admin.form.localisation.CurrencyForm;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.localisation.Currency;


public interface CurrencyAdminModel {
	
	public void create(CurrencyForm currencyForm);
	
	public void update(CurrencyForm currencyForm);
	
	public void delete(Integer currencyId);
	
	public CurrencyForm newForm();
	
	public CurrencyForm getForm(Integer currencyId);
	
	public Currency get(Integer currencyId, Class<? extends Currency> clazz);
	
	public Currency getOneByCode(String code);
	
	public List<Currency> getList(PageParam pageParam);
	
	public void updateDatabase(boolean force);
	
	public int getTotal();
	
	
}
