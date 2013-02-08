package com.jpoweredcart.admin.bean.catalog;

import com.jpoweredcart.common.entity.catalog.Review;

public class ReviewForm extends Review {
	
	private static final long serialVersionUID = 1L;
	
	public int[] getRatingChoices(){
		return new int[]{1,2,3,4,5};
	}
	
}
