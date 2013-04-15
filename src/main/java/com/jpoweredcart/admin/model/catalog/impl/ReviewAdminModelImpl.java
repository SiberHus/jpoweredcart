package com.jpoweredcart.admin.model.catalog.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jpoweredcart.admin.form.catalog.ReviewForm;
import com.jpoweredcart.admin.model.catalog.ReviewAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.QueryBean;
import com.jpoweredcart.common.entity.catalog.Review;
import com.jpoweredcart.common.entity.catalog.jdbc.ReviewRowMapper;
import com.jpoweredcart.common.system.setting.SettingKey;

public class ReviewAdminModelImpl extends BaseModel implements ReviewAdminModel {
	
	@Transactional
	@Override
	public void create(ReviewForm reviewForm) {
		String sql = "INSERT INTO "+quoteTable("review")
				+"(author, product_id, text, rating, status, date_added) VALUES(?,?,?,?,?,?)";
		getJdbcOperations().update(sql, reviewForm.getAuthor(), reviewForm.getProductId(), 
				reviewForm.getText(), reviewForm.getRating(), reviewForm.getStatus(), new Date());
	}

	@Transactional
	@Override
	public void update(ReviewForm reviewForm) {
		String sql = "UPDATE " +quoteTable("review")
				+ " SET author=?, product_id=?, text=?, rating=?, status=?, date_modified=? WHERE review_id=?";
		getJdbcOperations().update(sql, reviewForm.getAuthor(), reviewForm.getProductId(), 
				reviewForm.getText(), reviewForm.getRating(), reviewForm.getStatus(), new Date(), reviewForm.getId());
	}

	@Transactional
	@Override
	public void delete(Integer reviewId) {
		String sql = "DELETE FROM " +quoteTable("review")+ " WHERE review_id=?";
		getJdbcOperations().update(sql, reviewId);
	}

	@Override
	public ReviewForm newForm(){
		return new ReviewForm();
	}
	
	@Override
	public ReviewForm getForm(Integer reviewId) {
		String sql = "SELECT DISTINCT *, (SELECT pd.name FROM "+quoteTable("product_description")
				+" pd WHERE pd.product_id = r.product_id AND pd.language_id = ?) AS product_name FROM "
				+quoteTable("review")+" r WHERE r.review_id =?";
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		return (ReviewForm)getJdbcOperations().queryForObject(sql, new Object[]{reviewId, languageId}, 
				new ReviewRowMapper(){
					public Review newObject(){
						return new ReviewForm();
					}
		});
	}
	
	@Override
	public Review get(Integer reviewId) {
		return getForm(reviewId);
	}
	
	@Override
	public List<Review> getList(PageParam pageParam) {
		
		String sql = "SELECT r.review_id, pd.name, r.author, r.rating, r.status, r.date_added FROM " 
				+quoteTable("review")+" r LEFT JOIN " +quoteTable("product_description") 
				+" pd ON (r.product_id = pd.product_id) WHERE pd.language_id = ?";
		//sortedKeys={"pd.name","r.author","r.rating","r.status","r.date_added"}
		QueryBean query = createPaginationQuery(sql, pageParam);
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		query.addParameters(languageId);
		return getJdbcOperations().query(query.getSql(), 
				query.getParameters(), new RowMapper<Review>(){
					@Override
					public Review mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Review review = new Review();
						review.setId(rs.getInt("review_id"));
						review.setProductName(rs.getString("name"));
						review.setAuthor(rs.getString("author"));
						review.setRating(rs.getInt("rating"));
						review.setStatus(rs.getShort("status"));
						review.setDateAdded(rs.getDate("date_added"));
						return review;
					}
			
		});
	}
	
	@Override
	public int getTotal() {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("review");
		return getJdbcOperations().queryForObject(sql, Integer.class);
	}

	@Override
	public int getTotalAwaitingApproval() {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("review")+ " WHERE status=0";
		return getJdbcOperations().queryForObject(sql, Integer.class);
	}
	
}
