package com.jpoweredcart.admin.model.localisation.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.bean.localisation.LengthClassForm;
import com.jpoweredcart.common.entity.localisation.LengthClass;
import com.jpoweredcart.common.entity.localisation.LengthClassDesc;

public class LengthClassRowMapper implements RowMapper<LengthClass> {
	
	private static void setProperties(ResultSet rs, LengthClass lc) throws SQLException{
		lc.setId(rs.getInt("length_class_id"));
		lc.setValue(rs.getBigDecimal("value"));
	}
	
	@Override
	public LengthClass mapRow(ResultSet rs, int rowNum) throws SQLException {
		LengthClass lc = new LengthClass();
		setProperties(rs, lc);
		lc.setTitle(rs.getString("title"));
		lc.setUnit(rs.getString("unit"));
		return lc;
	}
	
	public static class Form implements RowMapper<LengthClassForm>{

		@Override
		public LengthClassForm mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			LengthClassForm lcForm = new LengthClassForm();
			setProperties(rs, lcForm);
			return lcForm;
		}
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
