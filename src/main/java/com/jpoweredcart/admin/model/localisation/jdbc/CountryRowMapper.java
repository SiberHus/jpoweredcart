package com.jpoweredcart.admin.model.localisation.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.localisation.Country;
import org.springframework.jdbc.core.RowMapper;

public class CountryRowMapper implements RowMapper<Country> {
	
	@Override
	public Country mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Country country = new Country();
		country.setId(rs.getInt("country_id"));
		country.setName(rs.getString("name"));
		country.setIsoCode2(rs.getString("iso_code_2"));
		country.setIsoCode3(rs.getString("iso_code_3"));
		country.setAddressFormat(rs.getString("address_format"));
		country.setPostcodeRequired(rs.getBoolean("postcode_required"));
		country.setStatus(rs.getInt("status"));
		return country;
	}
	
	
}
