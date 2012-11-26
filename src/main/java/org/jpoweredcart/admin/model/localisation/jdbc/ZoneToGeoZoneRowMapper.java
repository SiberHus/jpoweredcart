package org.jpoweredcart.admin.model.localisation.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jpoweredcart.admin.entity.localisation.ZoneToGeoZone;
import org.springframework.jdbc.core.RowMapper;

public class ZoneToGeoZoneRowMapper implements RowMapper<ZoneToGeoZone> {

	@Override
	public ZoneToGeoZone mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ZoneToGeoZone ztgz = new ZoneToGeoZone();
		ztgz.setId(rs.getInt("zone_to_geo_zone_id"));
		ztgz.setCountryId(rs.getInt("country_id"));
		ztgz.setZoneId(rs.getInt("zone_id"));
		ztgz.setGeoZoneId(rs.getInt("geo_zone_id"));
		ztgz.setDateAdded(rs.getDate("date_added"));
		ztgz.setDateModified(rs.getDate("date_modified"));
		
		return ztgz;
	}
	
}
