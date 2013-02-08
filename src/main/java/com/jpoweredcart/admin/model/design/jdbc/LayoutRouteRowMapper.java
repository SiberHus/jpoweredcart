package org.jpoweredcart.admin.model.design.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jpoweredcart.common.entity.design.LayoutRoute;
import org.springframework.jdbc.core.RowMapper;

public class LayoutRouteRowMapper implements RowMapper<LayoutRoute>{

	@Override
	public LayoutRoute mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		LayoutRoute layoutRoute = new LayoutRoute();
		layoutRoute.setId(rs.getInt("layout_route_id"));
		layoutRoute.setLayoutId(rs.getInt("layout_id"));
		layoutRoute.setStoreId(rs.getInt("store_id"));
		layoutRoute.setRoute(rs.getString("route"));
		return layoutRoute;
	}

	
}
