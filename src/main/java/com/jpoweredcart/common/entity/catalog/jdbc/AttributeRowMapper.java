package com.jpoweredcart.common.entity.catalog.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.common.entity.catalog.Attribute;
import com.jpoweredcart.common.entity.catalog.AttributeDesc;
import com.jpoweredcart.common.jdbc.ObjectFactoryRowMapper;


public class AttributeRowMapper extends ObjectFactoryRowMapper<Attribute>{
	
	@Override
	public Attribute mapRow(ResultSet rs, Attribute object) throws SQLException {
		object.setId(rs.getInt("attribute_id"));
		object.setAttributeGroupId(rs.getInt("attribute_group_id"));
		object.setSortOrder(rs.getInt("sort_order"));
		return object;
	}
	
	public static class Desc implements RowMapper<AttributeDesc>{
		
		@Override
		public AttributeDesc mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			AttributeDesc desc = new AttributeDesc();
			desc.setAttributeId(rs.getInt("attribute_id"));
			desc.setLanguageId(rs.getInt("language_id"));
			desc.setLanguageName(rs.getString("language_name"));
			desc.setLanguageImage(rs.getString("language_image"));
			desc.setName(rs.getString("name"));
			return desc;
		}
	}
	
}
