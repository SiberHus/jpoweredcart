package com.jpoweredcart.common.entity.localisation.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.localisation.GeoZone;
import com.jpoweredcart.common.jdbc.ObjectFactoryRowMapper;

public class GeoZoneRowMapper extends ObjectFactoryRowMapper<GeoZone> {
	
	@Override
	public GeoZone mapRow(ResultSet rs, GeoZone object) throws SQLException {
		
		object.setId(rs.getInt("geo_zone_id"));
		object.setName(rs.getString("name"));
		object.setDescription(rs.getString("description"));
		object.setDateModified(rs.getDate("date_modified"));
		object.setDateAdded(rs.getDate("date_added"));
		return object;
	}
	
	
}
