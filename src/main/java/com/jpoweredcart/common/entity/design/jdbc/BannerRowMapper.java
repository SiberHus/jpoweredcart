package com.jpoweredcart.common.entity.design.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.design.Banner;
import com.jpoweredcart.common.jdbc.ObjectFactoryRowMapper;

public class BannerRowMapper extends ObjectFactoryRowMapper<Banner>{
	
	@Override
	public Banner mapRow(ResultSet rs, Banner object) throws SQLException {
		
		object.setId(rs.getInt("banner_id"));
		object.setName(rs.getString("name"));
		object.setStatus(rs.getInt("status"));
		return object;
	}
	
}
