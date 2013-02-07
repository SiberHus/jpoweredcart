package org.jpoweredcart.admin.model.user;

import java.util.List;

import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.user.User;


public interface UserAdminModel {
	
	public void create(User user);
	
	public void update(User user);
	
	public void saveUser(User user);
	
	public void updatePassword(Integer userId, String password);
	
	public void updateCode(String email, String code);
	
	public void delete(Integer userId);
	
	public User get(Integer userId);
	
	public User getOneByUsername(String username);
	
	public User getUserByCode(String code);
	
	public List<User> getUsers(PageParam pageParam);
	
	public int getTotalUsers();
	
	public int getTotalUsersByGroupId(Integer userGroupId);
	
	public int getTotalUsersByEmail(String email);
	
}
