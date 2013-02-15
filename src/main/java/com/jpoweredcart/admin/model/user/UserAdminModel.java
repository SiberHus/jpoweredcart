package com.jpoweredcart.admin.model.user;

import java.util.List;

import com.jpoweredcart.admin.bean.user.UserForm;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.user.User;


public interface UserAdminModel {
	
	public void create(UserForm userForm);
	
	public void update(UserForm userForm);
	
	public void updatePassword(Integer userId, String password);
	
	public void updateCode(String email, String code);
	
	public void delete(Integer userId);
	
	public UserForm getForm(Integer userId);
	
	public User get(Integer userId);
	
	public User getOneByUsername(String username);
	
	public User getUserByCode(String code);
	
	public List<User> getList(PageParam pageParam);
	
	public int getTotal();
	
	public int getTotalByGroupId(Integer userGroupId);
	
	public int getTotalByEmail(String email);
	
}
