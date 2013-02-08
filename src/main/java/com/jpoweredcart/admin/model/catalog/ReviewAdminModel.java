package org.jpoweredcart.admin.model.catalog;

import java.util.List;

import org.jpoweredcart.admin.form.catalog.ReviewForm;
import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.catalog.Review;

public interface ReviewAdminModel {
	
	public void create(ReviewForm review);
	
	public void update(ReviewForm reviewForm);
	
	public void delete(Integer reviewId);
	
	public ReviewForm getForm(Integer reviewId);
	
	public Review get(Integer reviewId);
	
	public List<Review> getList(PageParam pageParam);
	
	public int getTotal();
	
	public int getTotalAwaitingApproval();
}
