package com.jpoweredcart.admin.model.setting.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.jpoweredcart.admin.model.setting.StoreAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.entity.setting.Store;
import com.jpoweredcart.common.service.setting.DefaultSettings;
import com.jpoweredcart.common.service.setting.SettingKey;

public class StoreAdminModelImpl extends BaseModel implements StoreAdminModel {

	@Transactional
	@Override
	public void create(Store store) {
		String sql = "INSERT INTO " +quoteTable("store")+ "(name, url, ssl) VALUES (?,?,?)";
		getJdbcOperations().update(sql, store.getName(), store.getUrl(), store.isSsl());
	}

	@Transactional
	@Override
	public void update(Store store) {
		String sql = "UPDATE " +quoteTable("store")+ " SET name=?, url=?, ssl=? WHERE store_id=?";
		getJdbcOperations().update(sql, store.getName(), store.getUrl(), store.isSsl(), store.getId());
	}
	
	@Transactional
	@Override
	public void delete(Integer storeId) {
		String sql = "DELETE FROM "+quoteTable("store")+" WHERE store_id=?";
		getJdbcOperations().update(sql, storeId);
	}

	@Override
	public Store get(Integer storeId) {
		String sql = "SELECT * FROM " +quoteTable("store")+ " WHERE store_id = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{storeId}, new StoreRowMapper());
	}
	
	@Override
	public List<Store> getAll() {
		String sql = "SELECT * FROM " +quoteTable("store")+ " ORDER BY url";
		List<Store> storeList = getJdbcOperations().query(sql, new StoreRowMapper());
		Store defaultStore = new Store();
		defaultStore.setId(0);
		String defaultStoreName = getSettingService()
				.getConfig(DefaultSettings.STORE_ID, SettingKey.CFG_STORE_NAME);
		defaultStore.setName(defaultStoreName);
		String defaultStoreUrl = getEnvironment().getProperty("app.http.catalog");
		defaultStore.setUrl(defaultStoreUrl);
		storeList.add(0, defaultStore);
		return storeList;
	}
	
	@Override
	public int getTotal() {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("store");
		return getJdbcOperations().queryForInt(sql);
	}

	@Override
	public int getTotalByLayoutId(Integer layoutId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("setting")+ " WHERE "+quoteName("key")
				+"= 'config_layout_id' AND "+quoteName("value")+" = ? AND store_id != '0'";
		return getJdbcOperations().queryForInt(sql, layoutId);
	}

	@Override
	public int getTotalByLanguageCode(String languageCode) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("setting")+ " WHERE "+quoteName("key")
				+" = 'config_language' AND "+quoteName("value")+" = ? AND store_id != '0'";
		return getJdbcOperations().queryForInt(sql, languageCode);
	}
	
	@Override
	public int getTotalByCurrencyCode(String currencyCode) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("setting")+ " WHERE "+quoteName("key")
				+" = 'config_currency' AND "+quoteName("value")+" = ? AND store_id != '0'";
		return getJdbcOperations().queryForInt(sql, currencyCode);
	}
	
	@Override
	public int getTotalByCountryId(Integer countryId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("setting")+ " WHERE "+quoteName("key")
				+" = 'config_country_id' AND "+quoteName("value")+" = ? AND store_id != '0'";
		return getJdbcOperations().queryForInt(sql, countryId);
	}
	
	@Override
	public int getTotalByZoneId(Integer zoneId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("setting")+ " WHERE "+quoteName("key")
				+" = 'config_zone_id' AND "+quoteName("value")+" = ? AND store_id != '0'";
		return getJdbcOperations().queryForInt(sql, zoneId);
	}
	
	@Override
	public int getTotalByCustomerGroupId(Integer customerGroupId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("setting")+ " WHERE "+quoteName("key")
				+" = 'config_customer_group_id' AND "+quoteName("value")+" = ? AND store_id != '0'";
		return getJdbcOperations().queryForInt(sql, customerGroupId);
	}

	@Override
	public int getTotalByInformationId(Integer informationId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("setting")+ " WHERE "+quoteName("key")
				+" = 'config_account_id' AND "+quoteName("value")+" = ? AND store_id != '0'";
		int x1 = getJdbcOperations().queryForInt(sql, informationId);
		sql = "SELECT COUNT(*) AS total FROM " +quoteTable("setting")+ " WHERE "+quoteName("key")
				+" = 'config_checkout_id' AND "+quoteName("value")+" = ? AND store_id != '0'";
		int x2 = getJdbcOperations().queryForInt(sql, informationId);
		return x1+x2;
	}
	
	@Override
	public int getTotalByOrderStatusId(Integer orderStatusId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("setting")+ " WHERE "+quoteName("key")
				+" = 'config_order_status_id' AND "+quoteName("value")+" = ? AND store_id != '0'";
		return getJdbcOperations().queryForInt(sql, orderStatusId);
	}
	
}
