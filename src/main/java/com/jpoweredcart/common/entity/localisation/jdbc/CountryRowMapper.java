package com.jpoweredcart.common.entity.localisation.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.common.entity.localisation.Country;

public class CountryRowMapper implements RowMapper<Country> {
	
	public Country newObject(){
		return new Country();
	}
	
	@Override
	public Country mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Country country = newObject();
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
