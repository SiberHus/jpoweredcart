package com.jpoweredcart.admin.model.localisation.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.localisation.TaxRate;
import org.springframework.jdbc.core.RowMapper;

public class TaxRateRowMapper implements RowMapper<TaxRate>{

	
	@Override
	public TaxRate mapRow(ResultSet rs, int rowNum) throws SQLException {
		TaxRate taxRate = new TaxRate();
		taxRate.setId(rs.getInt("tax_rate_id"));
		taxRate.setGeoZoneId(rs.getInt("geo_zone_id"));
		taxRate.setName(rs.getString("name"));
		taxRate.setRate(rs.getBigDecimal("rate"));
		taxRate.setType(rs.getString("type"));
		taxRate.setDateAdded(rs.getDate("date_added"));
		taxRate.setDateModified(rs.getDate("date_modified"));
		return taxRate;
	}
	
}
