package com.jpoweredcart.common.entity.design.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.common.entity.design.Layout;

public class LayoutRowMapper implements RowMapper<Layout>{

	public Layout newObject(){
		return new Layout();
	}
	
	@Override
	public Layout mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Layout layout = newObject();
		layout.setId(rs.getInt("layout_id"));
		layout.setName(rs.getString("name"));
		return layout;
	}
	
}
