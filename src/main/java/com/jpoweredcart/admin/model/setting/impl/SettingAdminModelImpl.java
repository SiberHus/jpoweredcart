package com.jpoweredcart.admin.model.setting.impl;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.jpoweredcart.admin.model.setting.SettingAdminModel;
import com.jpoweredcart.common.BaseModel;

public class SettingAdminModelImpl extends BaseModel implements SettingAdminModel{
	
	
	@Override
	public Map<String, Object> getSettings(String group, Integer storeId) {
		String sql = "SELECT * FROM "+quoteTable("setting")+" WHERE store_id=? AND "
				+quoteName("group")+"=?";
		Map<String, Object> settings = getJdbcOperations().queryForMap(sql, storeId, group);
		//TODO: convert serializable obj
		return settings;
	}
	
	@Transactional
	@Override
	public void updateSettings(String group, Map<String, Object> settings,
			Integer storeId) {
		//TODO: 
	}

	@Transactional
	@Override
	public void deleteSettings(String group, Integer storeId) {
		// TODO Auto-generated method stub
		
	}

	
	
}
