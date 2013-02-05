package org.jpoweredcart.admin.model.setting;

import java.util.List;

import org.jpoweredcart.common.entity.setting.Store;

public interface StoreAdminModel {
	
	public void addStore(Store store);
	
	public void updateStore(Store store);
	
	public void deleteStore(Integer storeId);
	
	public Store getStore(Integer storeId);
	
	public List<Store> getAllStores();
	
	public int getTotalStores();
	
	public int getTotalStoresByLayoutId(Integer layoutId);
	
	public int getTotalStoresByLanguageCode(String languageCode);
	
	public int getTotalStoresByCurrencyCode(String currencyCode);
	
	public int getTotalStoresByCountryId(Integer countryId);
	
	public int getTotalStoresByZoneId(Integer zoneId);
	
	public int getTotalStoresByCustomerGroupId(Integer customerGroupId);
	
	public int getTotalStoresByInformationId(Integer informationId);
	
	public int getTotalStoresByOrderStatusId(Integer orderStatusId);
	
}
