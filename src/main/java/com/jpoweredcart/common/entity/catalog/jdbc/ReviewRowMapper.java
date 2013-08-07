package com.jpoweredcart.common.entity.catalog.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.catalog.Review;
import com.jpoweredcart.common.jdbc.ObjectFactoryRowMapper;

public class ReviewRowMapper extends ObjectFactoryRowMapper<Review>{
	
	@Override
	public Review mapRow(ResultSet rs, Review object) throws SQLException {
		object.setId(rs.getInt("review_id"));
		object.setProductId(rs.getInt("product_id"));
		object.setProductName(rs.getString("product_name"));
		object.setCustomerId(rs.getInt("customer_id"));
		object.setAuthor(rs.getString("author"));
		object.setText(rs.getString("text"));
		object.setRating(rs.getInt("rating"));
		object.setStatus(rs.getShort("status"));
		object.setDateAdded(rs.getDate("date_added"));
		object.setDateModified(rs.getDate("date_modified"));
		return object;
	}
	
}
