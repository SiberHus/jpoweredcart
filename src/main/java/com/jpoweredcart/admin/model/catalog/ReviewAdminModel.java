package com.jpoweredcart.admin.model.catalog;

import java.util.List;

import com.jpoweredcart.admin.form.catalog.ReviewForm;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.catalog.Review;

public interface ReviewAdminModel {
	
	public void create(ReviewForm review);
	
	public void update(ReviewForm reviewForm);
	
	public void delete(Integer reviewId);
	
	public ReviewForm newForm();
	
	public ReviewForm getForm(Integer reviewId);
	
	public Review get(Integer reviewId, Class<? extends Review> clazz);
	
	public List<Review> getList(PageParam pageParam);
	
	public int getTotal();
	
	public int getTotalAwaitingApproval();
}
