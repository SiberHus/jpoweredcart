package com.jpoweredcart.common.entity.localisation.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.localisation.Country;
import com.jpoweredcart.common.jdbc.ObjectFactoryRowMapper;

public class CountryRowMapper extends ObjectFactoryRowMapper<Country> {
	
	@Override
	public Country mapRow(ResultSet rs, Country country) throws SQLException {
		
		country.setId(rs.getInt("country_id"));
		country.setName(rs.getString("name"));
		country.setIsoCode2(rs.getString("iso_code_2"));
		country.setIsoCode3(rs.getString("iso_code_3"));
		country.setAddressFormat(rs.getString("address_format"));
		country.setPostcodeRequired(rs.getBoolean("postcode_required"));
		country.setStatus(rs.getShort("status"));
		return country;
	}
	
	
}
