package com.jpoweredcart.admin.model.localisation.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.bean.localisation.GeoZoneForm;
import com.jpoweredcart.common.entity.localisation.GeoZone;

public class GeoZoneRowMapper implements RowMapper<GeoZone> {
	
	private static void setProperties(ResultSet rs, GeoZone geoZone) throws SQLException{
		geoZone.setId(rs.getInt("geo_zone_id"));
		geoZone.setName(rs.getString("name"));
		geoZone.setDescription(rs.getString("description"));
		geoZone.setDateModified(rs.getDate("date_modified"));
		geoZone.setDateAdded(rs.getDate("date_added"));
	}
	
	@Override
	public GeoZone mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		GeoZone geoZone = new GeoZone();
		setProperties(rs, geoZone);
		return geoZone;
	}
	
	public static class Form implements RowMapper<GeoZoneForm> {

		@Override
		public GeoZoneForm mapRow(ResultSet rs, int rowNum) throws SQLException {
			GeoZoneForm geoZoneForm = new GeoZoneForm();
			setProperties(rs, geoZoneForm);
			return geoZoneForm;
		}
		
	}
	
}
