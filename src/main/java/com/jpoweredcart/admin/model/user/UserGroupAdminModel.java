package com.jpoweredcart.admin.model.user;

import java.util.List;

import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.user.UserGroup;


public interface UserGroupAdminModel {
	
	/**
	 * Add this method to support Spring Security Authorization.
	 * This design limit to 1 user per 1 role (authority) but I want
	 * to make everything compatible with the Opencart database without
	 * any extra tables. However, the method return List in order to
	 * support the change in the future.
	 * 
	 * @param username
	 * @return
	 */
	public List<UserGroup> getOneByUsername(String username);
	
	public void create(UserGroup userGroup);
	
	public void update(UserGroup userGroup);
	
	public void delete(Integer userGroupId);
	
	public void addPermission(Integer userId, String type, String page);
	
	public UserGroup get(Integer userGroupId);
	
	public List<UserGroup> getList(PageParam pageParam);
	
	public int getTotal();
	
}
