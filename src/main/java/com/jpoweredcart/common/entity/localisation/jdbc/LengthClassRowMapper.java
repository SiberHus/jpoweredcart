package com.jpoweredcart.common.entity.localisation.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.common.entity.localisation.LengthClass;
import com.jpoweredcart.common.entity.localisation.LengthClassDesc;

public class LengthClassRowMapper implements RowMapper<LengthClass> {
	
	public LengthClass newObject(){
		return new LengthClass();
	}
	
	@Override
	public LengthClass mapRow(ResultSet rs, int rowNum) throws SQLException {
		LengthClass lc = newObject();
		lc.setId(rs.getInt("length_class_id"));
		lc.setValue(rs.getBigDecimal("value"));
		
		return lc;
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
