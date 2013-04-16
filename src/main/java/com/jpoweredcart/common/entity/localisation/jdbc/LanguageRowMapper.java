package com.jpoweredcart.common.entity.localisation.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.util.StringUtils;

import com.jpoweredcart.common.entity.localisation.Language;
import com.jpoweredcart.common.jdbc.ObjectFactoryRowMapper;


public class LanguageRowMapper extends ObjectFactoryRowMapper<Language>{
	
	@Override
	public Language mapRow(ResultSet rs, Language object) throws SQLException {
		object.setId(rs.getInt("language_id"));
		object.setName(rs.getString("name"));
		object.setCode(rs.getString("code"));
		object.setLocale(StringUtils.parseLocaleString(rs.getString("locale")));
		object.setImage(rs.getString("image"));
		object.setDirectory(rs.getString("directory"));
		object.setFilename(rs.getString("filename"));
		object.setSortOrder(rs.getInt("sort_order"));
		object.setStatus(rs.getShort("status"));
		return object;
	}
	
}


