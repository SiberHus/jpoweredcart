package com.jpoweredcart.common.service.setting;

import java.util.Map;

import org.springframework.core.env.Environment;


public interface SettingService {
	
	public Environment getEnvironment();
	
	/**
	 * Get all settings as map. The key and value will be string type by default.
	 * If the serialized value is true, the value will be the deserialized object instead
	 * of string. 
	 * *** This method will not cache the values.*** 
	 * @return
	 */
	public Map<String, Object> getAll(Integer storeId, String group);
	
	public String get(Integer storeId, String group, String key);
	
	public <T>T get(Integer storeId, String group, String key, Class<T> type);
	
	public void set(Integer storeId, String group, String key, Object value);
	
	
	public String getConfig(Integer storeId, String key);
	
	public <T>T getConfig(Integer storeId, String key, Class<T> type);
	
	public void setConfig(Integer storeId, String key, Object value);
	
	/*========== get/set configuration of default store ========*/
	
	public String get(String group, String key);
	
	public <T>T get(String group, String key, Class<T> type);
	
	public void set(String group, String key, String value);
	
	
	public String getConfig(String key);
	
	public <T>T getConfig(String key, Class<T> type);
	
	public void setConfig(String key, String value);
	
}
