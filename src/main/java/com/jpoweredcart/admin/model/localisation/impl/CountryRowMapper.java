package com.jpoweredcart.admin.model.localisation.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.bean.localisation.CountryForm;
import com.jpoweredcart.common.entity.localisation.Country;

public class CountryRowMapper implements RowMapper<Country> {
	
	private static void setProperties(ResultSet rs, Country country) throws SQLException{
		country.setId(rs.getInt("country_id"));
		country.setName(rs.getString("name"));
		country.setIsoCode2(rs.getString("iso_code_2"));
		country.setIsoCode3(rs.getString("iso_code_3"));
		country.setAddressFormat(rs.getString("address_format"));
		country.setPostcodeRequired(rs.getBoolean("postcode_required"));
		country.setStatus(rs.getShort("status"));
	}
	
	@Override
	public Country mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Country country = new Country();
		setProperties(rs, country);
		return country;
	}
	
	public static class Form implements RowMapper<CountryForm> {
		
		@Override
		public CountryForm mapRow(ResultSet rs, int rowNum) throws SQLException {
			CountryForm countryForm = new CountryForm();
			setProperties(rs, countryForm);
			return countryForm;
		}
		
	}
}
