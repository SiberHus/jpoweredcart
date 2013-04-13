package com.jpoweredcart.common.entity.design.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.design.Banner;
import org.springframework.jdbc.core.RowMapper;

public class BannerRowMapper implements RowMapper<Banner>{
	
	public Banner newObject(){
		return new Banner();
	}
	
	@Override
	public Banner mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Banner banner = newObject();
		banner.setId(rs.getInt("banner_id"));
		banner.setName(rs.getString("name"));
		banner.setStatus(rs.getInt("status"));
		return banner;
	}
	
}
