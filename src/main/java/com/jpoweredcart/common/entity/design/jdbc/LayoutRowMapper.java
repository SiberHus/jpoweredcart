package com.jpoweredcart.common.entity.design.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.design.Layout;
import com.jpoweredcart.common.jdbc.ObjectFactoryRowMapper;

public class LayoutRowMapper extends ObjectFactoryRowMapper<Layout>{
	
	@Override
	public Layout mapRow(ResultSet rs, Layout object) throws SQLException {
		
		object.setId(rs.getInt("layout_id"));
		object.setName(rs.getString("name"));
		return object;
	}
	
}
