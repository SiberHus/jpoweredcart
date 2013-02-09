package com.jpoweredcart.admin.model.localisation.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.bean.localisation.LanguageForm;
import com.jpoweredcart.common.entity.localisation.Language;


public class LanguageRowMapper implements RowMapper<Language>{
	
	private static void setProperties(ResultSet rs, Language lang) throws SQLException{
		lang.setId(rs.getInt("language_id"));
		lang.setName(rs.getString("name"));
		lang.setCode(rs.getString("code"));
		lang.setLocale(rs.getString("locale"));
		lang.setImage(rs.getString("image"));
		lang.setDirectory(rs.getString("directory"));
		lang.setFilename(rs.getString("filename"));
		lang.setSortOrder(rs.getInt("sort_order"));
		lang.setStatus(rs.getShort("status"));
	}
	
	@Override
	public Language mapRow(ResultSet rs, int rowNum) throws SQLException {
		Language lang = new Language();
		setProperties(rs, lang);
		return lang;
	}
	
	public static class Form implements RowMapper<LanguageForm> {

		@Override
		public LanguageForm mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			LanguageForm langForm = new LanguageForm();
			setProperties(rs, langForm);
			return langForm;
		}
		
	}
	
}


