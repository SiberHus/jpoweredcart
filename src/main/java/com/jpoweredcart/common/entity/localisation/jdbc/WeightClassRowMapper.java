package com.jpoweredcart.common.entity.localisation.jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.localisation.WeightClass;
import com.jpoweredcart.common.entity.localisation.WeightClassDesc;
import com.jpoweredcart.common.jdbc.ObjectFactoryRowMapper;

import org.springframework.jdbc.core.RowMapper;

public class WeightClassRowMapper extends ObjectFactoryRowMapper<WeightClass> {
	
	private boolean init = false;
	
	private boolean hasExtraColumns = false;
	
	@Override
	public WeightClass mapRow(ResultSet rs, WeightClass object) throws SQLException {
		
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
		object.setId(rs.getInt("weight_class_id"));
		object.setValue(rs.getBigDecimal("value"));
		if(hasExtraColumns){
			object.setTitle(rs.getString("title"));
			object.setUnit(rs.getString("unit"));
		}
		
		return object;
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
