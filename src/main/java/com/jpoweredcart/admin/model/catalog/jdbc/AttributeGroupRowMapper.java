package com.jpoweredcart.admin.model.catalog.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.bean.catalog.AttributeGroupForm;
import com.jpoweredcart.common.entity.catalog.AttributeGroup;
import com.jpoweredcart.common.entity.catalog.AttributeGroupDesc;


public class AttributeGroupRowMapper implements RowMapper<AttributeGroup>{
	
	@Override
	public AttributeGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		AttributeGroup entity = new AttributeGroup();
		entity.setId(rs.getInt("attribute_group_id"));
		entity.setSortOrder(rs.getInt("sort_order"));
		return entity;
	}

	public static class Form implements RowMapper<AttributeGroupForm>{

		@Override
		public AttributeGroupForm mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			AttributeGroupForm form = new AttributeGroupForm();
			form.setId(rs.getInt("attribute_group_id"));
			form.setSortOrder(rs.getInt("sort_order"));
			return form;
		}
	}
	
	public static class Desc implements RowMapper<AttributeGroupDesc>{
		
		@Override
		public AttributeGroupDesc mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			AttributeGroupDesc desc = new AttributeGroupDesc();
			desc.setAttributeGroupId(rs.getInt("attribute_group_id"));
			desc.setLanguageId(rs.getInt("language_id"));
			desc.setLanguageName(rs.getString("language_name"));
			desc.setLanguageImage(rs.getString("language_image"));
			desc.setName(rs.getString("name"));
			return desc;
		}
	}
	
}
