package com.jpoweredcart.admin.model.setting;

import java.util.List;

import com.jpoweredcart.common.entity.setting.Store;

public interface StoreAdminModel {
	
	public void create(Store store);
	
	public void update(Store store);
	
	public void delete(Integer storeId);
	
	public Store get(Integer storeId);
	
	public List<Store> getAll();
	
	public int getTotal();
	
	public int getTotalByLayoutId(Integer layoutId);
	
	public int getTotalByLanguageCode(String languageCode);
	
	public int getTotalByCurrencyCode(String currencyCode);
	
	public int getTotalByCountryId(Integer countryId);
	
	public int getTotalByZoneId(Integer zoneId);
	
	public int getTotalByCustomerGroupId(Integer customerGroupId);
	
	public int getTotalByInformationId(Integer informationId);
	
	public int getTotalByOrderStatusId(Integer orderStatusId);
	
}
