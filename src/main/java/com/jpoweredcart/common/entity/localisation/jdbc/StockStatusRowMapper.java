package com.jpoweredcart.common.entity.localisation.jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.localisation.StockStatuses.StockStatus;
import com.jpoweredcart.common.jdbc.ObjectFactoryRowMapper;

public class StockStatusRowMapper extends ObjectFactoryRowMapper<StockStatus> {
	
	private boolean init = false;
	
	private boolean hasExtraColumns = false;
	
	@Override
	public StockStatus mapRow(ResultSet rs, StockStatus object) throws SQLException {
		
		if(!this.init){
			this.init = true;
			ResultSetMetaData rsmd = rs.getMetaData();
			for(int i=1;i<=rsmd.getColumnCount();i++){
				String columnName = rsmd.getColumnLabel(i);
				if("language_name".equals(columnName)){
					hasExtraColumns = true;
					break;
				}
			}
		}
		
		object.setId(rs.getInt("stock_status_id"));
		object.setLanguageId(rs.getInt("language_id"));
		object.setName(rs.getString("name"));
		
		if(hasExtraColumns){
			object.setLanguageName(rs.getString("language_name"));
			object.setLanguageImage(rs.getString("language_image"));
		}
		
		return object;
	}
	
	
}