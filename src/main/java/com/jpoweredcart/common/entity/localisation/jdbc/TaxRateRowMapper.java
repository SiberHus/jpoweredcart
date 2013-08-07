package com.jpoweredcart.common.entity.localisation.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.localisation.TaxRate;
import com.jpoweredcart.common.jdbc.ObjectFactoryRowMapper;

public class TaxRateRowMapper extends ObjectFactoryRowMapper<TaxRate>{

	
	@Override
	public TaxRate mapRow(ResultSet rs, TaxRate object) throws SQLException {
		object.setId(rs.getInt("tax_rate_id"));
		object.setGeoZoneId(rs.getInt("geo_zone_id"));
		object.setName(rs.getString("name"));
		object.setRate(rs.getBigDecimal("rate"));
		object.setType(rs.getString("type"));
		object.setDateAdded(rs.getDate("date_added"));
		object.setDateModified(rs.getDate("date_modified"));
		return object;
	}
	
}
