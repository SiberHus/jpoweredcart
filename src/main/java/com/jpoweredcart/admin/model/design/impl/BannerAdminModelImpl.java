package com.jpoweredcart.admin.model.design.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import com.jpoweredcart.admin.model.design.BannerAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.QueryBean;
import com.jpoweredcart.common.entity.design.Banner;
import com.jpoweredcart.common.entity.design.BannerImage;
import com.jpoweredcart.common.entity.design.BannerImageDesc;

public class BannerAdminModelImpl extends BaseModel implements BannerAdminModel {

	
	@Transactional
	@Override
	public void create(final Banner banner) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcOperations().update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				String sql = "INSERT INTO " +quoteTable("banner")+ " SET name = ?, status = ?";
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, banner.getName());
				ps.setInt(2, banner.getStatus());
				return ps;
			}
		}, keyHolder);
		
		int bannerId = keyHolder.getKey().intValue();
		
		banner.setId(bannerId);
		addBannerImages(banner);
	}

	@Transactional
	@Override
	public void update(Banner banner) {
		
		String sql = "UPDATE " +quoteTable("banner")+ " SET name = ?, status = ? WHERE banner_id = ?";
		getJdbcOperations().update(sql, banner.getName(), banner.getStatus(), banner.getId());
		sql = "DELETE FROM " +quoteTable("banner_image")+ " WHERE banner_id = ?";
		getJdbcOperations().update(sql, banner.getId());
		sql = "DELETE FROM " +quoteTable("banner_image_description")+ " WHERE banner_id = ?";
		getJdbcOperations().update(sql, banner.getId());
		
		addBannerImages(banner);
	}
	
	private void addBannerImages(final Banner banner){
		if(banner.getImages().size()>0){
			for(final BannerImage image: banner.getImages()){
				
				KeyHolder keyHolder = new GeneratedKeyHolder();
				getJdbcOperations().update(new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection con)
							throws SQLException {
						String sql = "INSERT INTO " +quoteTable("banner_image")+ " SET banner_id = ?, link = ?, image = ?";
						PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
						ps.setInt(1, banner.getId());
						ps.setString(2, image.getLink());
						ps.setString(3, image.getImage());
						return ps;
					}
				}, keyHolder);
				
				int imageId = keyHolder.getKey().intValue();
				
				if(image.getDescs().size()>0){
					String sql = "INSERT INTO " +quoteTable("banner_image_description")
							+ " SET banner_image_id = ?, language_id = ?, banner_id = ?, title = ?";
					for(BannerImageDesc desc: image.getDescs()){
						getJdbcOperations().update(sql, imageId, desc.getLanguageId(), 
								banner.getId(), desc.getTitle());
					}
				}
			}
		}
	}
	
	@Transactional
	@Override
	public void delete(Integer bannerId) {
		String tables[] = new String[]{"banner", "banner_image", "banner_image_description"};
		for(String table: tables){
			String sql = "DELETE FROM "+quoteTable(table)+" WHERE banner_id=?";
			getJdbcOperations().update(sql, bannerId);
		}
	}
	
	@Override
	public Banner get(Integer bannerId) {
		
		String sql = "SELECT * FROM " +quoteTable("banner")+ " WHERE banner_id = ?";
		
		Banner banner = getJdbcOperations().queryForObject(sql, 
				new Object[]{bannerId}, new BannerRowMapper());
		
		List<BannerImage> imageList = getBannerImages(bannerId);
		banner.setImages(imageList);
		return banner;
	}
	
	@Override
	public List<Banner> getList(PageParam pageParam) {
		QueryBean query = createPaginationQuery("banner", pageParam, 
				new String[]{"name", "status"});
		List<Banner> bannerList = getJdbcOperations().query(query.getSql(), 
				query.getParameters(), new BannerRowMapper());
		return bannerList;
	}
	
	@Override
	public List<BannerImage> getBannerImages(Integer bannerId) {
		
		String sql = "SELECT * FROM " +quoteTable("banner_image")+ " WHERE banner_id = ?";
		List<BannerImage> imageList = getJdbcOperations().query(sql, 
				new Object[]{bannerId}, new BannerImageRowMapper());
		sql = "SELECT * FROM " +quoteTable("banner_image_description")+ " WHERE banner_image_id = ? AND banner_id = ?";
		for(BannerImage image: imageList){
			List<BannerImageDesc> descList = getJdbcOperations().query(sql, new Object[]{image.getId(), 
					image.getBannerId()}, new BannerImageRowMapper.Desc());
			image.setDescs(descList);
		}
		
		return imageList;
	}

	@Override
	public int getTotal() {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("banner");
		return getJdbcOperations().queryForInt(sql);
	}
	
}
