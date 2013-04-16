package com.jpoweredcart.admin.model.localisation.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.jpoweredcart.admin.model.localisation.ZoneAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.QueryBean;
import com.jpoweredcart.common.entity.localisation.Zone;
import com.jpoweredcart.common.entity.localisation.jdbc.ZoneRowMapper;


public class ZoneAdminModelImpl extends BaseModel implements ZoneAdminModel {
	
	@Transactional
	@Override
	public void create(Zone zone) {
		String sql = "INSERT INTO " +quoteTable("zone")+ "(country_id, name, code, status) " +
				"VALUES(?, ?, ?, ?)";
		getJdbcOperations().update(sql, zone.getCountryId(), zone.getName(), 
				zone.getCode(), zone.getStatus());
	}
	
	@Transactional
	@Override
	public void update(Zone zone) {
		String sql = "UPDATE " +quoteTable("zone")+ " SET country_id=?, name=?, code=?, status=? " +
				"WHERE zone_id=?";
		getJdbcOperations().update(sql, zone.getCountryId(), zone.getName(), 
				zone.getCode(), zone.getStatus(), zone.getId());
		//TODO: delete cache??
	}
	
	@Transactional
	@Override
	public void delete(Integer zoneId) {
		String sql = "DELETE FROM "+quoteTable("zone")+" WHERE zone_id=?";
		getJdbcOperations().update(sql, zoneId);
		//TODO: delete cache??
	}

	@Override
	public Zone get(Integer zoneId) {
		String sql = "SELECT * FROM " +quoteTable("zone")+ " WHERE zone_id = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{zoneId}, new ZoneRowMapper());
	}
	
	@Override
	public List<Zone> getList(PageParam pageParam) {
		String sql = "SELECT z.zone_id, z.country_id, z.code, z.name, z.status, c.name AS country_name FROM " +
				quoteTable("zone") + " z LEFT JOIN "+quoteTable("country") + " c ON (z.country_id = c.country_id)";
		//sortedKeys={"c.name", "z.name", "z.code"}
		QueryBean query = createPaginationQuery(sql, pageParam);
		List<Zone> zoneList = getJdbcOperations().query(query.getSql(), 
				query.getParameters(), new ZoneRowMapper());
		return zoneList;
	}
	
	@Override
	public List<Zone> getAllByCountryId(Integer countryId){
		String sql = "SELECT * FROM " +quoteTable("zone")+ " WHERE country_id = ? ORDER BY name";
		List<Zone> zoneList = getJdbcOperations().query(sql, 
				new Object[]{countryId}, new ZoneRowMapper());
		//TODO: set cache ??
		return zoneList;
	}
	
	@Override
	public int getTotal() {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("zone");
		return getJdbcOperations().queryForObject(sql, Integer.class);
	}
	
	@Override
	public int getTotalByCountryId(Integer countryId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("zone") +
				" WHERE country_id=?";
		return getJdbcOperations().queryForObject(sql, Integer.class, countryId);
	}
}
