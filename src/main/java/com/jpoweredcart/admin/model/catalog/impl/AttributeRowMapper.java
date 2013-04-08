package com.jpoweredcart.admin.model.catalog.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.bean.catalog.AttributeForm;
import com.jpoweredcart.common.entity.catalog.Attribute;
import com.jpoweredcart.common.entity.catalog.AttributeDesc;


public class AttributeRowMapper implements RowMapper<Attribute>{
	
	@Override
	public Attribute mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Attribute attr = new Attribute();
		attr.setId(rs.getInt("attribute_id"));
		attr.setAttributeGroupId(rs.getInt("attribute_group_id"));
		attr.setSortOrder(rs.getInt("sort_order"));
		return attr;
	}

	public static class Form implements RowMapper<AttributeForm>{

		@Override
		public AttributeForm mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			AttributeForm attrForm = new AttributeForm();
			attrForm.setId(rs.getInt("attribute_id"));
			attrForm.setAttributeGroupId(rs.getInt("attribute_group_id"));
			attrForm.setSortOrder(rs.getInt("sort_order"));
			return attrForm;
		}
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
