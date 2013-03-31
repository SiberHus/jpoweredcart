package com.jpoweredcart.admin.model.setting.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.common.entity.setting.Extension;

public class ExtensionRowMapper implements RowMapper<Extension>{

	@Override
	public Extension mapRow(ResultSet rs, int rowNum) throws SQLException {
		Extension ex = new Extension();
		ex.setId(rs.getInt("extension_id"));
		ex.setType(rs.getString("type"));
		ex.setCode(rs.getString("code"));
		return ex;
	}
	
}
