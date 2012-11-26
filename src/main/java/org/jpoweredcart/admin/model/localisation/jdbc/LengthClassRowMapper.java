package org.jpoweredcart.admin.model.localisation.jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.jpoweredcart.admin.entity.localisation.LengthClass;
import org.jpoweredcart.admin.entity.localisation.LengthClassDesc;
import org.springframework.jdbc.core.RowMapper;

public class LengthClassRowMapper implements RowMapper<LengthClass> {
	
	private boolean init = false;
	
	private boolean hasExtraColumns = false;
	
	@Override
	public LengthClass mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		if(!this.init){
			this.init = true;
			ResultSetMetaData rsmd = rs.getMetaData();
			for(int i=1;i<=rsmd.getColumnCount();i++){
				String columnName = rsmd.getColumnLabel(i);
				if("title".equals(columnName)){
					hasExtraColumns = true;
					break;
				}
			}
		}
		
		LengthClass wc = new LengthClass();
		wc.setId(rs.getInt("length_class_id"));
		wc.setValue(rs.getBigDecimal("value"));
		if(hasExtraColumns){
			wc.setTitle(rs.getString("title"));
			wc.setUnit(rs.getString("unit"));
		}
		
		return wc;
	}
	
	public static class Desc implements RowMapper<LengthClassDesc>{

		@Override
		public LengthClassDesc mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			
			LengthClassDesc desc = new LengthClassDesc();
			desc.setLengthClassId(rs.getInt("length_class_id"));
			desc.setLanguageId(rs.getInt("language_id"));
			desc.setLanguageName(rs.getString("language_name"));
			desc.setLanguageImage(rs.getString("language_image"));
			desc.setTitle(rs.getString("title"));
			desc.setUnit(rs.getString("unit"));
			return desc;
		}
		
		
	}
}
