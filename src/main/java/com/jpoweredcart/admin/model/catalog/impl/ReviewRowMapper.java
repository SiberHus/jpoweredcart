package com.jpoweredcart.admin.model.catalog.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.admin.bean.catalog.ReviewForm;
import com.jpoweredcart.common.entity.catalog.Review;
import org.springframework.jdbc.core.RowMapper;

public class ReviewRowMapper implements RowMapper<Review>{
	
	private static void setProperties(ResultSet rs, Review review) throws SQLException{
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
	}
	
	@Override
	public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
		Review review = new Review();
		setProperties(rs, review);
		return review;
	}

	public static class Form implements RowMapper<ReviewForm>{

		@Override
		public ReviewForm mapRow(ResultSet rs, int rowNum) throws SQLException {
			ReviewForm reviewForm = new ReviewForm();
			setProperties(rs, reviewForm);
			return reviewForm;
		}
		
	}
}
