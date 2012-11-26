package org.jpoweredcart.admin.model.setting.jdbc;

import java.util.List;

import org.jpoweredcart.admin.entity.setting.Store;
import org.jpoweredcart.admin.model.setting.StoreAdminModel;
import org.jpoweredcart.common.BaseModel;
import org.jpoweredcart.common.service.ConfigService;
import org.springframework.jdbc.core.JdbcOperations;

public class StoreAdminModelImpl extends BaseModel implements StoreAdminModel {

	public StoreAdminModelImpl(ConfigService configService, JdbcOperations jdbcOperations){
		super(configService, jdbcOperations);
	}
	
	@Override
	public void addStore(Store store) {
		String sql = "INSERT INTO " +quoteTable("store")+ "(name, url, ssl) VALUES (?,?,?)";
		getJdbcOperations().update(sql, store.getName(), store.getUrl(), store.isSsl());
	}

	@Override
	public void updateStore(Store store) {
		String sql = "UPDATE " +quoteTable("store")+ " SET name=?, url=?, ssl=? WHERE store_id=?";
		getJdbcOperations().update(sql, store.getName(), store.getUrl(), store.isSsl(), store.getId());
	}
	
	@Override
	public void deleteStore(Integer storeId) {
		String sql = "DELETE FROM "+quoteTable("store")+" WHERE store_id=?";
		getJdbcOperations().update(sql, storeId);
	}

	@Override
	public Store getStore(Integer storeId) {
		String sql = "SELECT * FROM " +quoteTable("store")+ " WHERE store_id = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{storeId}, new StoreRowMapper());
	}
	
	@Override
	public List<Store> getAllStores() {
		String sql = "SELECT * FROM " +quoteTable("store")+ " ORDER BY url";
		return getJdbcOperations().query(sql, new StoreRowMapper());
	}
	
	@Override
	public int getTotalStores() {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("store");
		return getJdbcOperations().queryForInt(sql);
	}

	@Override
	public int getTotalStoresByLayoutId(Integer layoutId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("setting")+ " WHERE "+quoteName("key")
				+"= 'config_layout_id' AND "+quoteName("value")+" = ? AND store_id != '0'";
		return getJdbcOperations().queryForInt(sql, layoutId);
	}

	@Override
	public int getTotalStoresByLanguageCode(String languageCode) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("setting")+ " WHERE "+quoteName("key")
				+" = 'config_language' AND "+quoteName("value")+" = ? AND store_id != '0'";
		return getJdbcOperations().queryForInt(sql, languageCode);
	}
	
	@Override
	public int getTotalStoresByCurrencyCode(String currencyCode) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("setting")+ " WHERE "+quoteName("key")
				+" = 'config_currency' AND "+quoteName("value")+" = ? AND store_id != '0'";
		return getJdbcOperations().queryForInt(sql, currencyCode);
	}
	
	@Override
	public int getTotalStoresByCountryId(Integer countryId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("setting")+ " WHERE "+quoteName("key")
				+" = 'config_country_id' AND "+quoteName("value")+" = ? AND store_id != '0'";
		return getJdbcOperations().queryForInt(sql, countryId);
	}
	
	@Override
	public int getTotalStoresByZoneId(Integer zoneId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("setting")+ " WHERE "+quoteName("key")
				+" = 'config_zone_id' AND "+quoteName("value")+" = ? AND store_id != '0'";
		return getJdbcOperations().queryForInt(sql, zoneId);
	}
	
	@Override
	public int getTotalStoresByCustomerGroupId(Integer customerGroupId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("setting")+ " WHERE "+quoteName("key")
				+" = 'config_customer_group_id' AND "+quoteName("value")+" = ? AND store_id != '0'";
		return getJdbcOperations().queryForInt(sql, customerGroupId);
	}

	@Override
	public int getTotalStoresByInformationId(Integer informationId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("setting")+ " WHERE "+quoteName("key")
				+" = 'config_account_id' AND "+quoteName("value")+" = ? AND store_id != '0'";
		int x1 = getJdbcOperations().queryForInt(sql, informationId);
		sql = "SELECT COUNT(*) AS total FROM " +quoteTable("setting")+ " WHERE "+quoteName("key")
				+" = 'config_checkout_id' AND "+quoteName("value")+" = ? AND store_id != '0'";
		int x2 = getJdbcOperations().queryForInt(sql, informationId);
		return x1+x2;
	}
	
	@Override
	public int getTotalStoresByOrderStatusId(Integer orderStatusId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("setting")+ " WHERE "+quoteName("key")
				+" = 'config_order_status_id' AND "+quoteName("value")+" = ? AND store_id != '0'";
		return getJdbcOperations().queryForInt(sql, orderStatusId);
	}
	
}
