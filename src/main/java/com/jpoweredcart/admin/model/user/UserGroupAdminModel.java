package com.jpoweredcart.admin.model.user;

import java.util.List;

import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.user.UserGroup;


public interface UserGroupAdminModel {
	
	public void create(UserGroup userGroup);
	
	public void update(UserGroup userGroup);
	
	public void delete(Integer userGroupId);
	
	public void addPermission(Integer userId, String type, String page);
	
	public UserGroup get(Integer userGroupId);
	
	public List<UserGroup> getList(PageParam pageParam);
	
	public int getTotal();
	
}
