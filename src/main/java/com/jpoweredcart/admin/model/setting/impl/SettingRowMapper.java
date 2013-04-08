package com.jpoweredcart.admin.model.setting.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.setting.Setting;
import org.springframework.jdbc.core.RowMapper;

public class SettingRowMapper implements RowMapper<Setting>{

	@Override
	public Setting mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Setting setting = new Setting();
		setting.setId(rs.getInt("setting_id"));
		setting.setStoreId(rs.getInt("store_id"));
		setting.setGroup(rs.getString("group"));
		setting.setKey(rs.getString("key"));
		setting.setValue(rs.getString("value"));
		setting.setSerialized(rs.getBoolean("serialized"));
		return setting;
	}
	
	
}
