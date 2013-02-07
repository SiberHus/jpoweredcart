package org.jpoweredcart.admin.model.localisation.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.jpoweredcart.admin.model.localisation.GeoZoneAdminModel;
import org.jpoweredcart.common.BaseModel;
import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.QueryBean;
import org.jpoweredcart.common.entity.localisation.GeoZone;
import org.jpoweredcart.common.entity.localisation.ZoneToGeoZone;
import org.jpoweredcart.common.service.SettingService;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;


public class GeoZoneAdminModelImpl extends BaseModel implements GeoZoneAdminModel{
	
	public GeoZoneAdminModelImpl(SettingService settingService, JdbcOperations jdbcOperations){
		super(settingService, jdbcOperations);
	}
	
	@Transactional
	@Override
	public void create(final GeoZone geoZone) {
		
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
	public void update(GeoZone geoZone) {
		
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
	public GeoZone get(Integer geoZoneId) {
		String sql = "SELECT * FROM " +quoteTable("geo_zone")+ " WHERE geo_zone_id = ?";
		GeoZone geoZone = getJdbcOperations().queryForObject(sql, 
				new Object[]{geoZoneId}, new GeoZoneRowMapper());
		sql = "SELECT * FROM "+quoteTable("zone_to_geo_zone")+ " WHERE geo_zone_id =?";
		List<ZoneToGeoZone> zoneToGeoZones = getJdbcOperations()
				.query(sql, new Object[]{geoZoneId}, new ZoneToGeoZoneRowMapper());
		geoZone.setZoneToGeoZones(zoneToGeoZones);
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
