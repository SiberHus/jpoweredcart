package com.jpoweredcart.admin.model.localisation.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import com.jpoweredcart.admin.bean.localisation.GeoZoneForm;
import com.jpoweredcart.admin.model.localisation.GeoZoneAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.QueryBean;
import com.jpoweredcart.common.entity.localisation.GeoZone;
import com.jpoweredcart.common.entity.localisation.ZoneToGeoZone;


public class GeoZoneAdminModelImpl extends BaseModel implements GeoZoneAdminModel{
	
	
	@Transactional
	@Override
	public void create(final GeoZoneForm geoZone) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcOperations().update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				String sql = "INSERT INTO "+quoteTable("geo_zone")+"(name, description, date_added) VALUES(?,?,?)";
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, geoZone.getName());
				ps.setString(2, geoZone.getDescription());
				ps.setTimestamp(3, new Timestamp(new Date().getTime()));
				return ps;
			}
		}, keyHolder);
		
		int geoZoneId = keyHolder.getKey().intValue();
		
		addZoneToGeoZones(geoZoneId, geoZone.getZoneToGeoZones());
		
	}
	
	@Transactional
	@Override
	public void update(GeoZoneForm geoZone) {
		
		String sql = "UPDATE " +quoteTable("geo_zone")+ " SET name = ?, description = ?, " +
				"date_modified = ? WHERE geo_zone_id = ?";
		getJdbcOperations().update(sql, geoZone.getName(), geoZone.getDescription(),
				new Date(), geoZone.getId());
		
		sql = "DELETE FROM " +quoteTable("zone_to_geo_zone")+ " WHERE geo_zone_id = ?";
		
		getJdbcOperations().update(sql, geoZone.getId());
		
		addZoneToGeoZones(geoZone.getId(), geoZone.getZoneToGeoZones());
		
	}
	
	private void addZoneToGeoZones(Integer geoZoneId, List<ZoneToGeoZone> zoneToGeoZones){
		if(zoneToGeoZones!=null){
			String sql = "INSERT INTO "+quoteTable("zone_to_geo_zone")
					+"(country_id, zone_id, geo_zone_id, date_added) VALUES(?,?,?,?)";
			for(ZoneToGeoZone ztgz: zoneToGeoZones){
				getJdbcOperations().update(sql, ztgz.getCountryId(), ztgz.getZoneId(),
						geoZoneId, new Date());
			}
		}
	}
	
	@Transactional
	@Override
	public void delete(Integer geoZoneId) {
		
		String sql = "DELETE FROM " +quoteTable("geo_zone")+ " WHERE geo_zone_id = ?";
		getJdbcOperations().update(sql, geoZoneId);
		
		sql = "DELETE FROM " +quoteTable("zone_to_geo_zone")+ " WHERE geo_zone_id = ?";
		getJdbcOperations().update(sql, geoZoneId);
		
	}
	
	@Override
	public GeoZoneForm newForm(){
		return new GeoZoneForm();
	}
	
	@Override
	public GeoZoneForm getForm(Integer geoZoneId){
		String sql = "SELECT * FROM " +quoteTable("geo_zone")+ " WHERE geo_zone_id = ?";
		GeoZoneForm geoZoneForm = getJdbcOperations().queryForObject(sql, 
				new Object[]{geoZoneId}, new GeoZoneRowMapper.Form());
		sql = "SELECT * FROM "+quoteTable("zone_to_geo_zone")+ " WHERE geo_zone_id =?";
		List<ZoneToGeoZone> zoneToGeoZones = getJdbcOperations()
				.query(sql, new Object[]{geoZoneId}, new ZoneToGeoZoneRowMapper());
		geoZoneForm.setZoneToGeoZones(zoneToGeoZones);
		return geoZoneForm;
	}
	
	@Override
	public GeoZone get(Integer geoZoneId) {
		String sql = "SELECT * FROM " +quoteTable("geo_zone")+ " WHERE geo_zone_id = ?";
		GeoZone geoZone = getJdbcOperations().queryForObject(sql, 
				new Object[]{geoZoneId}, new GeoZoneRowMapper());
		return geoZone;
	}
	
	@Override
	public List<GeoZone> getList(PageParam pageParam) {
		QueryBean query = createPaginationQuery("geo_zone", pageParam, 
				new String[]{"name", "description"});
		List<GeoZone> geoZoneList = getJdbcOperations()
				.query(query.getSql(), query.getParameters(), new GeoZoneRowMapper());
		return geoZoneList;
	}
	
	@Override
	public int getTotal() {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("geo_zone");
		return getJdbcOperations().queryForInt(sql);
	}
	
}
