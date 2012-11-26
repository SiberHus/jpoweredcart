package org.jpoweredcart.common.service;

import org.springframework.core.env.Environment;


public interface ConfigService {
	
	public Environment getEnvironment();
	
	public String get(Integer storeId, String key);
	
	public <T>T get(Integer storeId, String key, Class<T> type);
	
	public void set(Integer storeId, String key, Object value);
	
	/*========== get/set configuration of default store ========*/
	
	public String get(String key);
	
	public <T>T get(String key, Class<T> type);
	
	public void set(String key, String value);
	
}
