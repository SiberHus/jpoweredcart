package com.jpoweredcart.admin.model.user;

import java.util.List;

import com.jpoweredcart.admin.form.user.UserGroupForm;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.user.UserGroup;


public interface UserGroupAdminModel {
	
	public void create(UserGroupForm userGroupForm);
	
	public void update(UserGroupForm userGroupForm);
	
	public void delete(Integer userGroupId);
	
	public UserGroupForm newForm();
	
	public UserGroupForm getForm(Integer userGroupId);
	
	public UserGroup get(Integer userGroupId, Class<? extends UserGroup> clazz);
	
	public void addPermission(Integer userId, String type, String page);
	
	public List<UserGroup> getList(PageParam pageParam);
	
	public int getTotal();
	
}
