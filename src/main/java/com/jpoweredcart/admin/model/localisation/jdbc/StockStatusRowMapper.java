package org.jpoweredcart.admin.model.localisation.jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.jpoweredcart.common.entity.localisation.StockStatuses.StockStatus;
import org.springframework.jdbc.core.RowMapper;

public class StockStatusRowMapper implements RowMapper<StockStatus> {
	
	private boolean init = false;
	
	private boolean hasExtraColumns = false;
	
	@Override
	public StockStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
		
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
		
		StockStatus stockStatus = new StockStatus();
		stockStatus.setId(rs.getInt("stock_status_id"));
		stockStatus.setLanguageId(rs.getInt("language_id"));
		stockStatus.setName(rs.getString("name"));
		
		if(hasExtraColumns){
			stockStatus.setLanguageName(rs.getString("language_name"));
			stockStatus.setLanguageImage(rs.getString("language_image"));
		}
		
		return stockStatus;
	}
	
	
}