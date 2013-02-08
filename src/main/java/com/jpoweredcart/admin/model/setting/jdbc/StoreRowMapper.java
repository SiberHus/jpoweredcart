package com.jpoweredcart.admin.model.setting.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.setting.Store;
import org.springframework.jdbc.core.RowMapper;

public class StoreRowMapper implements RowMapper<Store>{

	@Override
	public Store mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Store store = new Store();
		store.setId(rs.getInt("store_id"));
		store.setName(rs.getString("name"));
		store.setUrl(rs.getString("url"));
		store.setSsl(rs.getBoolean("ssl"));
		
		return store;
	}

	
}
