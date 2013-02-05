package org.jpoweredcart.admin.model.localisation.jdbc;

import java.util.List;

import org.jpoweredcart.admin.model.localisation.CountryAdminModel;
import org.jpoweredcart.common.BaseModel;
import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.QueryBean;
import org.jpoweredcart.common.entity.localisation.Country;
import org.jpoweredcart.common.service.SettingService;
import org.springframework.jdbc.core.JdbcOperations;

public class CountryAdminModelImpl extends BaseModel implements CountryAdminModel {
	
	public CountryAdminModelImpl(SettingService configService, JdbcOperations jdbcOperations){
		super(configService, jdbcOperations);
	}
	
	@Override
	public void addCountry(Country country) {
		String sql = "INSERT INTO " +quoteTable("country")+ "(name, iso_code_2, iso_code_3, " +
				"address_format, postcode_required, status) VALUES (?,?,?,?,?,?)";
		getJdbcOperations().update(sql, country.getName(), country.getIsoCode2(), country.getIsoCode3(),
				country.getAddressFormat(), country.isPostcodeRequired(), country.getStatus());
	}
	
	@Override
	public void updateCountry(Country country) {
		String sql = "UPDATE " +quoteTable("country")+ " SET name=?, iso_code_2=?, iso_code_3=?, " +
				"address_format=?, postcode_required=?, status=? WHERE country_id=?";
		getJdbcOperations().update(sql, country.getName(), country.getIsoCode2(), country.getIsoCode3(),
				country.getAddressFormat(), country.isPostcodeRequired(), country.getStatus(),
				country.getId());
	}

	@Override
	public void saveCountry(Country country) {
		if(country.getId()!=null){
			updateCountry(country);
		}else{
			addCountry(country);
		}
	}
	
	@Override
	public void deleteCountry(Integer countryId) {
		String sql = "DELETE FROM "+quoteTable("country")+" WHERE country_id=?";
		getJdbcOperations().update(sql, countryId);
	}
	
	@Override
	public Country getCountry(Integer countryId) {
		String sql = "SELECT * FROM " +quoteTable("country")+ " WHERE country_id = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{countryId}, new CountryRowMapper());
	}

	@Override
	public List<Country> getAllCountries() {
		String sql = "SELECT * FROM " +quoteTable("country") +" ORDER BY name ASC";
		return getJdbcOperations().query(sql, new CountryRowMapper());
	}
	
	@Override
	public List<Country> getCountries(PageParam pageParam) {
		QueryBean query = createPaginationQuery("country", pageParam, 
				new String[]{"name", "iso_code_2", "iso_code_3"});
		List<Country> countries = getJdbcOperations().query(query.getSql(), 
				query.getParameters(), new CountryRowMapper());
		return countries;
	}
	
	@Override
	public int getTotalCountries() {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("country");
		return getJdbcOperations().queryForInt(sql);
	}
	
}
