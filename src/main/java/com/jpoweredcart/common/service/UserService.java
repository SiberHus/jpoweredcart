package com.jpoweredcart.common.service;

import com.jpoweredcart.common.entity.sale.Affiliate;
import com.jpoweredcart.common.entity.sale.Customer;
import com.jpoweredcart.common.entity.user.User;
import com.jpoweredcart.common.entity.user.UserGroup;

public interface UserService {
	
	
	User getSystemUserByUsername(String username);
	
	UserGroup getSystemUserGroupById(Integer groupId);
	
	Affiliate getAffiliateByEmail(String email);
	
	Customer getCustomerByEmail(String email);
	
}
