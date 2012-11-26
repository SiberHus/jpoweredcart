package org.jpoweredcart.admin.model.design.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jpoweredcart.admin.entity.design.Layout;
import org.springframework.jdbc.core.RowMapper;

public class LayoutRowMapper implements RowMapper<Layout>{

	@Override
	public Layout mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Layout layout = new Layout();
		layout.setId(rs.getInt("layout_id"));
		layout.setName(rs.getString("name"));
		return layout;
	}
	
}
