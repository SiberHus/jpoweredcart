package com.jpoweredcart.admin.model.localisation.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.localisation.ReturnReasons.ReturnReason;
import org.springframework.jdbc.core.RowMapper;

public class ReturnReasonRowMapper implements RowMapper<ReturnReason> {
	
	private boolean init = false;
	
	private boolean hasExtraColumns = false;
	
	@Override
	public ReturnReason mapRow(ResultSet rs, int rowNum) throws SQLException {
		
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
		
		ReturnReason returnReason = new ReturnReason();
		returnReason.setId(rs.getInt("return_reason_id"));
		returnReason.setLanguageId(rs.getInt("language_id"));
		returnReason.setName(rs.getString("name"));
		
		if(hasExtraColumns){
			returnReason.setLanguageName(rs.getString("language_name"));
			returnReason.setLanguageImage(rs.getString("language_image"));
		}
		
		return returnReason;
	}
	
	
}