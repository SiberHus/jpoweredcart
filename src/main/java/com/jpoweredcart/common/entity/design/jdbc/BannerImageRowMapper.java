package com.jpoweredcart.common.entity.design.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.common.entity.design.BannerImage;
import com.jpoweredcart.common.entity.design.BannerImageDesc;
import com.jpoweredcart.common.jdbc.ObjectFactoryRowMapper;

public class BannerImageRowMapper extends ObjectFactoryRowMapper<BannerImage>{

	@Override
	public BannerImage mapRow(ResultSet rs, BannerImage object) throws SQLException {
		
		object.setId(rs.getInt("banner_image_id"));
		object.setBannerId(rs.getInt("banner_id"));
		object.setLink(rs.getString("link"));
		object.setImage(rs.getString("image"));
		return object;
	}

	
	public static class Desc implements RowMapper<BannerImageDesc> {

		@Override
		public BannerImageDesc mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			
			BannerImageDesc desc = new BannerImageDesc();
			desc.setBannerImageId(rs.getInt("banner_image_id"));
			desc.setLanguageId(rs.getInt("language_id"));
			desc.setBannerId(rs.getInt("banner_id"));
			desc.setTitle(rs.getString("title"));
			return desc;
		}
		
	}
	
}
