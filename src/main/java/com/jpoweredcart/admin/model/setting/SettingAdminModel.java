package com.jpoweredcart.admin.model.setting;

import java.util.Map;



public interface SettingAdminModel {

	public Map<String, Object> getSettings(String group, Integer storeId);
	
	public void updateSettings(String group, Map<String, Object> settings, Integer storeId);
	
	public void deleteSettings(String group, Integer storeId);
	
}
