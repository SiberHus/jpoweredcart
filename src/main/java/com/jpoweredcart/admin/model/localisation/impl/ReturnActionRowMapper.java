package com.jpoweredcart.admin.model.localisation.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.localisation.ReturnActions.ReturnAction;
import org.springframework.jdbc.core.RowMapper;

public class ReturnActionRowMapper implements RowMapper<ReturnAction> {
	
	private boolean init = false;
	
	private boolean hasExtraColumns = false;
	
	@Override
	public ReturnAction mapRow(ResultSet rs, int rowNum) throws SQLException {
		
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
		
		ReturnAction returnAction = new ReturnAction();
		returnAction.setId(rs.getInt("return_action_id"));
		returnAction.setLanguageId(rs.getInt("language_id"));
		returnAction.setName(rs.getString("name"));
		
		if(hasExtraColumns){
			returnAction.setLanguageName(rs.getString("language_name"));
			returnAction.setLanguageImage(rs.getString("language_image"));
		}
		
		return returnAction;
	}
	
	
}