package com.jpoweredcart.admin.model.setting.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.jpoweredcart.admin.model.setting.ExtensionAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.entity.setting.Extension;
import com.jpoweredcart.common.entity.setting.jdbc.ExtensionRowMapper;

public class ExtensionAdminModelImpl extends BaseModel implements ExtensionAdminModel {

	@Override
	public List<Extension> getInstalled(String type) {
		
		String sql = "SELECT * FROM "+quoteTable("extension")+ "ex WHERE ex.type=?";
		return getJdbcOperations().query(sql, new Object[]{type}, new ExtensionRowMapper());
	}

	@Transactional
	@Override
	public void install(String type, String code) {
		
		String sql = "INSERT INTO "+quoteTable("extension")+"(type, code) VALUES(?,?)";
		getJdbcOperations().update(sql, type, code);
	}

	@Transactional
	@Override
	public void uninstall(String type, String code) {

		String sql = "DELETE FROM "+quoteTable("extension")+"ex WHERE ex.type=? AND ex.code=?";
		getJdbcOperations().update(sql, type, code);
	}
	
}
