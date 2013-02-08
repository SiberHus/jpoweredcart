package com.jpoweredcart.admin.model.localisation.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.localisation.GeoZone;
import org.springframework.jdbc.core.RowMapper;

public class GeoZoneRowMapper implements RowMapper<GeoZone> {
	
	@Override
	public GeoZone mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		GeoZone geoZone = new GeoZone();
		geoZone.setId(rs.getInt("geo_zone_id"));
		geoZone.setName(rs.getString("name"));
		geoZone.setDescription(rs.getString("description"));
		geoZone.setDateModified(rs.getDate("date_modified"));
		geoZone.setDateAdded(rs.getDate("date_added"));
		
		return geoZone;
	}
	
}
