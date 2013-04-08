package com.jpoweredcart.admin.model.localisation.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.jpoweredcart.admin.bean.localisation.CountryForm;
import com.jpoweredcart.admin.model.localisation.CountryAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.QueryBean;
import com.jpoweredcart.common.entity.localisation.Country;

public class CountryAdminModelImpl extends BaseModel implements CountryAdminModel {
	
	@Transactional
	@Override
	public void create(CountryForm countryForm) {
		String sql = "INSERT INTO " +quoteTable("country")+ "(name, iso_code_2, iso_code_3, " +
				"address_format, postcode_required, status) VALUES (?,?,?,?,?,?)";
		getJdbcOperations().update(sql, countryForm.getName(), countryForm.getIsoCode2(), countryForm.getIsoCode3(),
				countryForm.getAddressFormat(), countryForm.isPostcodeRequired(), countryForm.getStatus());
	}
	
	@Transactional
	@Override
	public void update(CountryForm countryForm) {
		String sql = "UPDATE " +quoteTable("country")+ " SET name=?, iso_code_2=?, iso_code_3=?, " +
				"address_format=?, postcode_required=?, status=? WHERE country_id=?";
		getJdbcOperations().update(sql, countryForm.getName(), countryForm.getIsoCode2(), countryForm.getIsoCode3(),
				countryForm.getAddressFormat(), countryForm.isPostcodeRequired(), countryForm.getStatus(),
				countryForm.getId());
	}
	
	@Transactional
	@Override
	public void delete(Integer countryId) {
		String sql = "DELETE FROM "+quoteTable("country")+" WHERE country_id=?";
		getJdbcOperations().update(sql, countryId);
	}
	
	@Override
	public CountryForm newForm() {
		
		return new CountryForm();
	}

	@Override
	public CountryForm getForm(Integer countryId) {
		String sql = "SELECT * FROM " +quoteTable("country")+ " WHERE country_id = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{countryId}, new CountryRowMapper.Form());
	}
	
	@Override
	public Country get(Integer countryId) {
		return getForm(countryId);
	}
	
	@Override
	public List<Country> getAll() {
		String sql = "SELECT * FROM " +quoteTable("country") +" ORDER BY name ASC";
		return getJdbcOperations().query(sql, new CountryRowMapper());
	}
	
	@Override
	public List<Country> getList(PageParam pageParam) {
		QueryBean query = createPaginationQuery("country", pageParam, 
				new String[]{"name", "iso_code_2", "iso_code_3"});
		List<Country> countries = getJdbcOperations().query(query.getSql(), 
				query.getParameters(), new CountryRowMapper());
		return countries;
	}
	
	@Override
	public int getTotal() {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("country");
		return getJdbcOperations().queryForInt(sql);
	}
	
}
