package com.jpoweredcart.common.service.impl;

import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.entity.sale.Affiliate;
import com.jpoweredcart.common.entity.sale.Customer;
import com.jpoweredcart.common.entity.sale.jdbc.AffiliateRowMapper;
import com.jpoweredcart.common.entity.sale.jdbc.CustomerRowMapper;
import com.jpoweredcart.common.entity.user.User;
import com.jpoweredcart.common.entity.user.UserGroup;
import com.jpoweredcart.common.entity.user.jdbc.UserGroupRowMapper;
import com.jpoweredcart.common.entity.user.jdbc.UserRowMapper;
import com.jpoweredcart.common.service.UserService;

public class UserServiceImpl extends BaseModel implements UserService {
	
	@Override
	public User getSystemUserByUsername(String username) {
		String sql = "SELECT * FROM " +quoteTable("user")+ " WHERE username = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{username}, new UserRowMapper());
	}
	
	@Override
	public UserGroup getSystemUserGroupById(Integer groupId) {
		String sql = "SELECT * FROM "+quoteTable("user_group")+" WHERE user_group_id=?";
		return getJdbcOperations().queryForObject(sql, new Object[]{groupId}, new UserGroupRowMapper());
	}
	
	@Override
	public Affiliate getAffiliateByEmail(String email) {
		String sql = "SELECT * FROM "+quoteTable("affiliate")+" WHERE LCASE(email) =?";
		return getJdbcOperations().queryForObject(sql, new Object[]{email}, 
				new AffiliateRowMapper());
	}
	
	@Override
	public Customer getCustomerByEmail(String email) {
		String sql = "SELECT * FROM "+quoteTable("customer")+" WHERE LCASE(email) =?";
		return getJdbcOperations().queryForObject(sql, new Object[]{email}, 
				new CustomerRowMapper());
	}
	
	
}
