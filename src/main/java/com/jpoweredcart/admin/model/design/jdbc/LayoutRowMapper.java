package com.jpoweredcart.admin.model.design.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.admin.bean.design.LayoutForm;
import com.jpoweredcart.common.entity.design.Layout;
import org.springframework.jdbc.core.RowMapper;

public class LayoutRowMapper implements RowMapper<Layout>{

	@Override
	public Layout mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Layout layout = new Layout();
		layout.setId(rs.getInt("layout_id"));
		layout.setName(rs.getString("name"));
		return layout;
	}
	
	public static class Form implements RowMapper<LayoutForm> {
		
		@Override
		public LayoutForm mapRow(ResultSet rs, int rowNum) throws SQLException {
			LayoutForm layoutForm = new LayoutForm();
			layoutForm.setId(rs.getInt("layout_id"));
			layoutForm.setName(rs.getString("name"));
			return layoutForm;
		}
		
	}
	
}
