package com.jpoweredcart.admin.model.setting.jdbc;

import java.util.Map;

import com.jpoweredcart.admin.model.setting.SettingAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.service.SettingService;
import org.springframework.jdbc.core.JdbcOperations;

public class SettingAdminModelImpl extends BaseModel implements SettingAdminModel{
	
	public SettingAdminModelImpl(SettingService settingService, JdbcOperations jdbcOperations){
		super(settingService, jdbcOperations);
	}
	
	@Override
	public Map<String, Object> getSettings(String group, Integer storeId) {
		String sql = "SELECT * FROM "+quoteTable("setting")+" WHERE store_id=? AND "
				+quoteName("group")+"=?";
		Map<String, Object> settings = getJdbcOperations().queryForMap(sql, storeId, group);
		//TODO: convert serializable obj
		return settings;
	}
	
	@Override
	public void updateSettings(String group, Map<String, Object> settings,
			Integer storeId) {
		//TODO: 
	}

	@Override
	public void deleteSettings(String group, Integer storeId) {
		// TODO Auto-generated method stub
		
	}

	
	
}