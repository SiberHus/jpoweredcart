package org.jpoweredcart.admin.model.localisation;

import java.util.List;

import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.localisation.Country;


public interface CountryAdminModel {
	
	public void create(Country country);
	
	public void update(Country country);
	
	public void delete(Integer countryId);
	
	public Country get(Integer countryId);
	
	public List<Country> getAll();
	
	public List<Country> getList(PageParam pageParam);
	
	public int getTotal();
	
}
