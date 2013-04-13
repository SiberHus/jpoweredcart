package com.jpoweredcart.common.entity.localisation.jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.localisation.Zone;
import org.springframework.jdbc.core.RowMapper;

public class ZoneRowMapper implements RowMapper<Zone> {
	
	private boolean init = false;
	
	private boolean hasExtraColumn = false;
	
	@Override
	public Zone mapRow(ResultSet rs, int rowNum) throws SQLException {
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
		Zone zone = new Zone();
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
