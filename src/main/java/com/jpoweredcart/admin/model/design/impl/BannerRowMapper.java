package com.jpoweredcart.admin.model.design.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.design.Banner;
import org.springframework.jdbc.core.RowMapper;

public class BannerRowMapper implements RowMapper<Banner>{
	
	@Override
	public Banner mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Banner banner = new Banner();
		banner.setId(rs.getInt("banner_id"));
		banner.setName(rs.getString("name"));
		banner.setStatus(rs.getInt("status"));
		return banner;
	}
	
}
