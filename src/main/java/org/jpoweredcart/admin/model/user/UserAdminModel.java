package org.jpoweredcart.admin.model.user;

import java.util.List;

import org.jpoweredcart.admin.entity.user.User;
import org.jpoweredcart.common.PageParam;


public interface UserAdminModel {
	
	public void addUser(User user);
	
	public void updateUser(User user);
	
	public void saveUser(User user);
	
	public void updatePassword(Integer userId, String password);
	
	public void updateCode(String email, String code);
	
	public void deleteUser(Integer userId);
	
	public User getUser(Integer userId);
	
	public User getUserByUsername(String username);
	
	public User getUserByCode(String code);
	
	public List<User> getUsers(PageParam pageParam);
	
	public int getTotalUsers();
	
	public int getTotalUsersByGroupId(Integer userGroupId);
	
	public int getTotalUsersByEmail(String email);
	
}
