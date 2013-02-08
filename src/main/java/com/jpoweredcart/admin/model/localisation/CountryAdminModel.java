package com.jpoweredcart.admin.model.localisation;

import java.util.List;

import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.localisation.Country;


public interface CountryAdminModel {
	
	public void create(Country country);
	
	public void update(Country country);
	
	public void delete(Integer countryId);
	
	public Country get(Integer countryId);
	
	public List<Country> getAll();
	
	public List<Country> getList(PageParam pageParam);
	
	public int getTotal();
	
}
