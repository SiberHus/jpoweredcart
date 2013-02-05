package org.jpoweredcart.admin.model.user;

import java.util.List;

import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.user.UserGroup;


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
	public List<UserGroup> getUserGroupsByUsername(String username);
	
	public void addUserGroup(UserGroup userGroup);
	
	public void updateUserGroup(UserGroup userGroup);
	
	public void saveUserGroup(UserGroup userGroup);
	
	public void deleteUserGroup(Integer userGroupId);
	
	public void addPermission(Integer userId, String type, String page);
	
	public UserGroup getUserGroup(Integer userGroupId);
	
	public List<UserGroup> getUserGroups(PageParam pageParam);
	
	public int getTotalUserGroups();
	
}
