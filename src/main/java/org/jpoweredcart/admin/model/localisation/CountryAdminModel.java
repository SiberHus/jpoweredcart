package org.jpoweredcart.admin.model.localisation;

import java.util.List;

import org.jpoweredcart.admin.entity.localisation.Country;
import org.jpoweredcart.common.PageParam;


public interface CountryAdminModel {
	
	public void addCountry(Country country);
	
	public void updateCountry(Country country);
	
	public void saveCountry(Country country);
	
	public void deleteCountry(Integer countryId);
	
	public Country getCountry(Integer countryId);
	
	public List<Country> getAllCountries();
	
	public List<Country> getCountries(PageParam pageParam);
	
	public int getTotalCountries();
	
}
