package com.jpoweredcart.admin.model.catalog.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.catalog.AttributeGroup;
import org.springframework.jdbc.core.RowMapper;


public class AttributeGroupRowMapper implements RowMapper<AttributeGroup>{

	@Override
	public AttributeGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		AttributeGroup attrGrp = new AttributeGroup();
		
		return attrGrp;
	}

}
