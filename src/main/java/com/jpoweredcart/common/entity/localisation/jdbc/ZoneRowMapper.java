package com.jpoweredcart.common.entity.localisation.jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.localisation.Zone;
import com.jpoweredcart.common.jdbc.ObjectFactoryRowMapper;

public class ZoneRowMapper extends ObjectFactoryRowMapper<Zone> {
	
	private boolean init = false;
	
	private boolean hasExtraColumn = false;
	
	@Override
	public Zone mapRow(ResultSet rs, Zone zone) throws SQLException {
		if(!this.init){
			this.init = true;
			ResultSetMetaData rsmd = rs.getMetaData();
			for(int i=1;i<=rsmd.getColumnCount();i++){
				String columnName = rsmd.getColumnLabel(i);
				if("country_name".equals(columnName)){
					hasExtraColumn = true;
					break;
				}
			}
		}
		zone.setId(rs.getInt("zone_id"));
		zone.setCountryId(rs.getInt("country_id"));
		if(hasExtraColumn){
			zone.setCountryName(rs.getString("country_name"));
		}
		zone.setName(rs.getString("name"));
		zone.setCode(rs.getString("code"));
		zone.setStatus(rs.getInt("status"));
		return zone;
	}
	
	
}
