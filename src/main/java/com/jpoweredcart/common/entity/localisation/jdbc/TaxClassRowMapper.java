package com.jpoweredcart.common.entity.localisation.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.localisation.TaxClass;
import com.jpoweredcart.common.jdbc.ObjectFactoryRowMapper;

public class TaxClassRowMapper extends ObjectFactoryRowMapper<TaxClass>{
	
	@Override
	public TaxClass mapRow(ResultSet rs, TaxClass object) throws SQLException {
		object.setId(rs.getInt("tax_class_id"));
		object.setTitle(rs.getString("title"));
		object.setDescription(rs.getString("description"));
		object.setDateAdded(rs.getDate("date_added"));
		object.setDateModified(rs.getDate("date_modified"));
		return object;
	}
	
	
}
