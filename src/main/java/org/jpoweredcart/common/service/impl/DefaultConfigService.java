package org.jpoweredcart.common.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.jpoweredcart.common.BaseModel;
import org.jpoweredcart.common.Default;
import org.jpoweredcart.common.ConfigKey;
import org.jpoweredcart.common.service.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcOperations;

public class DefaultConfigService implements ConfigService, InitializingBean {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	private Ehcache configCache;
	
	private HashMap<String, Object> fakeCache;
	
	private Environment env;
	
	private JdbcOperations jdbcOperations;
	
	private Map<String, DerivedConfigProcessor> derivedConfigProcessors;
	
	public void setConfigCache(Ehcache configCache) {
		this.configCache = configCache;
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
	public String get(Integer storeId, String key) {
		
		return get(storeId, key, String.class);
	}
	
	@Override
	public <T> T get(Integer storeId, String key, Class<T> type) {
		String cacheKey = storeId+"_"+key;
		Element elem = null;
		if(configCache!=null){
			elem = configCache.get(cacheKey);
		}
		if(elem==null){
			if(derivedConfigProcessors.containsKey(key)){
				DerivedConfigProcessor dcp = derivedConfigProcessors.get(key);
				dcp.process(storeId, this);
				return getFromCache(cacheKey, type);
			}
			String sql = "SELECT "+ quoteName("value")+" FROM " + quoteTable("setting") 
					+ " WHERE store_id=? AND "+ quoteName("key")+"=?";
			T value = jdbcOperations.queryForObject(sql, new Object[]{storeId, key}, type);
			elem = new Element(cacheKey, value);
			if(configCache!=null){
				configCache.put(elem);
			}
			return value;
		}
		
		@SuppressWarnings("unchecked")
		T value = (T)elem.getValue();
		return value;
	}
	
	public <T> T getFromCache(String cacheKey, Class<T> type){
		if(configCache!=null){
			Element elem = configCache.get(cacheKey);
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
	public void set(Integer storeId, String key, Object value) {
		
//		String sql = "UPDATE "+BaseModel.quoteTable(env, "setting") + " SET "
//				+BaseModel.quoteName(env, "value")+"=? WHERE store_id=? AND "
//				+BaseModel.quoteName(env, "key")+"=?";
//		jdbcTemplate.update(sql, value, storeId, key);
		String cacheKey = storeId+"_"+key;
		if(configCache!=null){
			configCache.put(new Element(cacheKey, value));
		}else{
			fakeCache.put(cacheKey, value);
		}
	}
	
	@Override
	public String get(String key) {
		return get(Default.STORE_ID, key);
	}

	@Override
	public <T> T get(String key, Class<T> type) {
		return get(Default.STORE_ID, key, type);
	}

	@Override
	public void set(String key, String value) {
		set(Default.STORE_ID, key, value);
	}
	
	public void setDerivedConfigProcessors(
			Map<String, DerivedConfigProcessor> derivedConfigProcessors) {
		this.derivedConfigProcessors = derivedConfigProcessors;
	}
	
	@Override
	public void afterPropertiesSet() {
		
		if(configCache==null){
			logger.info("Configuration cache is disabled!!! [This setting is not recommended for production]");
			fakeCache = new HashMap<String, Object>();
		}
		
		derivedConfigProcessors = new HashMap<String, DerivedConfigProcessor>();
		derivedConfigProcessors.put(ConfigKey.ADMIN_LANGUAGE_ID, new LanguageIdDcp());
		
	}
	
	protected String quoteTable(String tableName){
		
		return BaseModel.quoteTable(env, tableName);
	}
	
	protected String quoteName(String name){
		
		return BaseModel.quoteName(env, name);
	}
	
	//========================== Derived Config =========================//
	public static interface DerivedConfigProcessor {
		
		public void process(Integer storeId, DefaultConfigService configService);
	}
	
	public static class LanguageIdDcp implements DerivedConfigProcessor {
		
		@Override
		public void process(Integer storeId, DefaultConfigService configService) {
			
			String languageCode = configService.get(storeId, ConfigKey.CFG_ADMIN_LANGUAGE);
			
			String sql = "SELECT language_id FROM "+configService.quoteTable("language")+" WHERE code=?";
			
			Map<String, Object> language = configService.getJdbcOperations().queryForMap(sql, languageCode);
			Integer languageId = ((Number)language.get("language_id")).intValue();
			configService.set(storeId, ConfigKey.ADMIN_LANGUAGE_ID, languageId);
		}
		
	}
	
	
}
