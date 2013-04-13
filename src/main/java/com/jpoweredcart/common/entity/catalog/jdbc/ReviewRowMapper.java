package com.jpoweredcart.common.entity.catalog.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.common.entity.catalog.Review;

public class ReviewRowMapper implements RowMapper<Review>{
	
	public Review newObject(){
		return new Review();
	}
	
	@Override
	public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
		Review review = newObject();
		review.setId(rs.getInt("review_id"));
		review.setProductId(rs.getInt("product_id"));
		review.setProductName(rs.getString("product_name"));
		review.setCustomerId(rs.getInt("customer_id"));
		review.setAuthor(rs.getString("author"));
		review.setText(rs.getString("text"));
		review.setRating(rs.getInt("rating"));
		review.setStatus(rs.getShort("status"));
		review.setDateAdded(rs.getDate("date_added"));
		review.setDateModified(rs.getDate("date_modified"));
		return review;
	}
	
}
