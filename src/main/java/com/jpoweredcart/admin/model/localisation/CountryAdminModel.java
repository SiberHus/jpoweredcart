package com.jpoweredcart.admin.model.localisation;

import java.util.List;

import com.jpoweredcart.admin.form.localisation.CountryForm;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.localisation.Country;


public interface CountryAdminModel {
	
	public void create(CountryForm country);
	
	public void update(CountryForm country);
	
	public void delete(Integer countryId);
	
	public CountryForm newForm();
	
	public CountryForm getForm(Integer countryId);
	
	public Country get(Integer countryId, Class<? extends Country> clazz);
	
	public List<Country> getAll();
	
	public List<Country> getList(PageParam pageParam);
	
	public int getTotal();
	
}
