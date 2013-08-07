package com.jpoweredcart.common.entity.design.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.design.LayoutRoute;
import com.jpoweredcart.common.jdbc.ObjectFactoryRowMapper;

public class LayoutRouteRowMapper extends ObjectFactoryRowMapper<LayoutRoute>{

	@Override
	public LayoutRoute mapRow(ResultSet rs, LayoutRoute object) throws SQLException {
		
		object.setId(rs.getInt("layout_route_id"));
		object.setLayoutId(rs.getInt("layout_id"));
		object.setStoreId(rs.getInt("store_id"));
		object.setRoute(rs.getString("route"));
		return object;
	}

	
}
