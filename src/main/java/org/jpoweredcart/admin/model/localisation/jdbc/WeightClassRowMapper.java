package org.jpoweredcart.admin.model.localisation.jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.jpoweredcart.admin.entity.localisation.WeightClass;
import org.jpoweredcart.admin.entity.localisation.WeightClassDesc;
import org.springframework.jdbc.core.RowMapper;

public class WeightClassRowMapper implements RowMapper<WeightClass> {
	
	private boolean init = false;
	
	private boolean hasExtraColumns = false;
	
	@Override
	public WeightClass mapRow(ResultSet rs, int rowNum) throws SQLException {
		
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
		
		WeightClass wc = new WeightClass();
		wc.setId(rs.getInt("weight_class_id"));
		wc.setValue(rs.getBigDecimal("value"));
		if(hasExtraColumns){
			wc.setTitle(rs.getString("title"));
			wc.setUnit(rs.getString("unit"));
		}
		
		return wc;
	}
	
	public static class Desc implements RowMapper<WeightClassDesc>{

		@Override
		public WeightClassDesc mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			
			WeightClassDesc desc = new WeightClassDesc();
			desc.setWeightClassId(rs.getInt("weight_class_id"));
			desc.setLanguageId(rs.getInt("language_id"));
			desc.setLanguageName(rs.getString("language_name"));
			desc.setLanguageImage(rs.getString("language_image"));
			desc.setTitle(rs.getString("title"));
			desc.setUnit(rs.getString("unit"));
			return desc;
		}
		
		
	}
}
