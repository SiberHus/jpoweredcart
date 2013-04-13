package com.jpoweredcart.common.entity.localisation.jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.localisation.ReturnStatuses.ReturnStatus;
import org.springframework.jdbc.core.RowMapper;

public class ReturnStatusRowMapper implements RowMapper<ReturnStatus> {
	
	private boolean init = false;
	
	private boolean hasExtraColumns = false;
	
	@Override
	public ReturnStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
		
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
		
		ReturnStatus returnStatus = new ReturnStatus();
		returnStatus.setId(rs.getInt("return_status_id"));
		returnStatus.setLanguageId(rs.getInt("language_id"));
		returnStatus.setName(rs.getString("name"));
		
		if(hasExtraColumns){
			returnStatus.setLanguageName(rs.getString("language_name"));
			returnStatus.setLanguageImage(rs.getString("language_image"));
		}
		
		return returnStatus;
	}
	
	
}