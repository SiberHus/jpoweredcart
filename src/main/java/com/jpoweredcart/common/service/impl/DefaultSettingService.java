package com.jpoweredcart.common.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.service.DefaultSettings;
import com.jpoweredcart.common.service.SettingGroup;
import com.jpoweredcart.common.service.SettingKey;
import com.jpoweredcart.common.service.SettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.ResultSetExtractor;

public class DefaultSettingService implements SettingService, InitializingBean {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	private Ehcache settingCache;
	
	private HashMap<String, Object> fakeCache;
	
	private Environment env;
	
	private JdbcOperations jdbcOperations;
	
	private Map<String, DerivedConfigProcessor> derivedConfigProcessors;
	
	public void setSettingCache(Ehcache settingCache) {
		this.settingCache = settingCache;
	}
	
	public void setEnvironment(Environment env){
		this.env = env;
	}
	
	@Override
	public Environment getEnvironment(){
		return this.env;
	}
	
	public void setJdbcOperations(JdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}
	
	public JdbcOperations getJdbcOperations(){
		return this.jdbcOperations;
	}
	
	@Override
	public Map<String, Object> getAll(Integer storeId, String group) {

		String sql = "SELECT * FROM " + quoteTable("setting") + " WHERE store_id=? AND "
				+ quoteName("group") + "=?";
		
		Map<String, Object> valueMap = null;
		
		valueMap = jdbcOperations.query(sql, new ResultSetExtractor<Map<String, Object>>(){
			private Map<String, Object> valueMap = new HashMap<String, Object>();
			@Override
			public Map<String, Object> extractData(ResultSet rs)
					throws SQLException, DataAccessException {
				while(rs.next()){
					Object value = rs.getString("value");
					if(rs.getBoolean("serialized")){
						//TODO: add de-serialize function					
					}
					valueMap.put(rs.getString("key"), value);
				}
				return valueMap;
			}
		}, storeId, group);
		
		return valueMap;
	}

	@Override
	public String get(Integer storeId, String group, String key) {
		
		return get(storeId, group, key, String.class);
	}
	
	@Override
	public <T> T get(Integer storeId, String group, String key, Class<T> type) {
		String cacheKey = createCacheKey(storeId, group, key);
		Element elem = null;
		if(settingCache!=null){
			elem = settingCache.get(cacheKey);
		}
		if(elem==null){
			if(derivedConfigProcessors.containsKey(key)){
				DerivedConfigProcessor dcp = derivedConfigProcessors.get(key);
				dcp.process(storeId, group, this);
				return getFromCache(cacheKey, type);
			}
			String sql = "SELECT "+ quoteName("value")+" FROM " + quoteTable("setting") 
					+ " WHERE store_id=? AND "+ quoteName("group")+"=? AND "+ quoteName("key")+"=?";
			T value = jdbcOperations.queryForObject(sql, new Object[]{storeId, group, key}, type);
			elem = new Element(cacheKey, value);
			if(settingCache!=null){
				settingCache.put(elem);
			}
			return value;
		}
		
		@SuppressWarnings("unchecked")
		T value = (T)elem.getValue();
		return value;
	}
	
	public <T> T getFromCache(String cacheKey, Class<T> type){
		if(settingCache!=null){
			Element elem = settingCache.get(cacheKey);
			if(elem!=null){
				@SuppressWarnings("unchecked")
				T value = (T)elem.getValue();
				return value;
			}
		}else{
			@SuppressWarnings("unchecked")
			T value = (T)fakeCache.get(cacheKey);
			if(value!=null){
				fakeCache.remove(cacheKey);
				return value;
			}
		}
		throw new RuntimeException("Unable to find configuration for cacheKey="+cacheKey);
	}
	
	@Override
	public void set(Integer storeId, String group, String key, Object value) {
		//TODO: implement saving to database
//		String sql = "UPDATE "+BaseModel.quoteTable(env, "setting") + " SET "
//				+BaseModel.quoteName(env, "value")+"=? WHERE store_id=? AND "
//				+BaseModel.quoteName(env, "key")+"=?";
//		jdbcTemplate.update(sql, value, storeId, key);
		String cacheKey = createCacheKey(storeId, group, key);
		if(settingCache!=null){
			settingCache.put(new Element(cacheKey, value));
		}else{
			fakeCache.put(cacheKey, value);
		}
	}
	
	@Override
	public String getConfig(Integer storeId, String key) {
		return get(storeId, SettingGroup.CONFIG, key);
	}

	@Override
	public <T> T getConfig(Integer storeId, String key, Class<T> type) {
		return get(storeId, SettingGroup.CONFIG, key, type);
	}

	@Override
	public void setConfig(Integer storeId, String key, Object value) {
		set(storeId, SettingGroup.CONFIG, key, value);
	}

	@Override
	public String get(String group, String key) {
		return get(DefaultSettings.STORE_ID, group, key);
	}

	@Override
	public <T> T get(String group, String key, Class<T> type) {
		return get(DefaultSettings.STORE_ID, group, key, type);
	}

	@Override
	public void set(String group, String key, String value) {
		set(DefaultSettings.STORE_ID, group, key, value);
	}
	
	@Override
	public String getConfig(String key) {
		return get(SettingGroup.CONFIG, key);
	}

	@Override
	public <T> T getConfig(String key, Class<T> type) {
		return get(SettingGroup.CONFIG, key, type);
	}

	@Override
	public void setConfig(String key, String value) {
		set(SettingGroup.CONFIG, key, value);
	}

	public void setDerivedConfigProcessors(
			Map<String, DerivedConfigProcessor> derivedConfigProcessors) {
		this.derivedConfigProcessors = derivedConfigProcessors;
	}
	
	@Override
	public void afterPropertiesSet() {
		
		if(settingCache==null){
			logger.info("Configuration cache is disabled!!! [This setting is not recommended for production]");
			fakeCache = new HashMap<String, Object>();
		}
		
		derivedConfigProcessors = new HashMap<String, DerivedConfigProcessor>();
		derivedConfigProcessors.put(SettingKey.ADMIN_LANGUAGE_ID, new LanguageIdDcp());
		
	}
	
	protected String quoteTable(String tableName){
		
		return BaseModel.quoteTable(env, tableName);
	}
	
	protected String quoteName(String name){
		
		return BaseModel.quoteName(env, name);
	}
	
	protected String createCacheKey(Integer storeId, String group, String key){
		return storeId+"_"+group+"_"+key;
	}
	
	//========================== Derived Config =========================//
	public static interface DerivedConfigProcessor {
		
		public void process(Integer storeId, String group, DefaultSettingService configService);
	}
	
	public static class LanguageIdDcp implements DerivedConfigProcessor {
		
		@Override
		public void process(Integer storeId, String group, DefaultSettingService configService) {
			
			group = SettingGroup.CONFIG;
			String languageCode = configService.get(storeId, group, SettingKey.CFG_ADMIN_LANGUAGE);
			
			String sql = "SELECT language_id FROM "+configService.quoteTable("language")+" WHERE code=?";
			
			Map<String, Object> language = configService.getJdbcOperations().queryForMap(sql, languageCode);
			Integer languageId = ((Number)language.get("language_id")).intValue();
			configService.set(storeId, group, SettingKey.ADMIN_LANGUAGE_ID, languageId);
		}
		
	}
	
	
}
