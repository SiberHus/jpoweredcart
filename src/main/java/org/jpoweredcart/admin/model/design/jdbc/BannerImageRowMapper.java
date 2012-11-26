package org.jpoweredcart.admin.model.design.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jpoweredcart.admin.entity.design.BannerImage;
import org.jpoweredcart.admin.entity.design.BannerImageDesc;
import org.springframework.jdbc.core.RowMapper;

public class BannerImageRowMapper implements RowMapper<BannerImage>{

	@Override
	public BannerImage mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		BannerImage image = new BannerImage();
		image.setId(rs.getInt("banner_image_id"));
		image.setBannerId(rs.getInt("banner_id"));
		image.setLink(rs.getString("link"));
		image.setImage(rs.getString("image"));
		return image;
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
