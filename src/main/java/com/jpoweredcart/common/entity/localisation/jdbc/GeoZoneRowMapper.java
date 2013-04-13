package com.jpoweredcart.common.entity.localisation.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.common.entity.localisation.GeoZone;

public class GeoZoneRowMapper implements RowMapper<GeoZone> {
	
	public GeoZone newObject(){
		return new GeoZone();
	}
	
	@Override
	public GeoZone mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		GeoZone geoZone = newObject();
		geoZone.setId(rs.getInt("geo_zone_id"));
		geoZone.setName(rs.getString("name"));
		geoZone.setDescription(rs.getString("description"));
		geoZone.setDateModified(rs.getDate("date_modified"));
		geoZone.setDateAdded(rs.getDate("date_added"));
		return geoZone;
	}
	
	
}
