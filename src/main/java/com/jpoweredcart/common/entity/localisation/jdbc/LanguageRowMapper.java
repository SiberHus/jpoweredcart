package com.jpoweredcart.common.entity.localisation.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.common.entity.localisation.Language;


public class LanguageRowMapper implements RowMapper<Language>{
	
	public Language newObject(){
		return new Language();
	}
	
	@Override
	public Language mapRow(ResultSet rs, int rowNum) throws SQLException {
		Language lang = newObject();
		lang.setId(rs.getInt("language_id"));
		lang.setName(rs.getString("name"));
		lang.setCode(rs.getString("code"));
		lang.setLocale(rs.getString("locale"));
		lang.setImage(rs.getString("image"));
		lang.setDirectory(rs.getString("directory"));
		lang.setFilename(rs.getString("filename"));
		lang.setSortOrder(rs.getInt("sort_order"));
		lang.setStatus(rs.getShort("status"));
		return lang;
	}
	
}


