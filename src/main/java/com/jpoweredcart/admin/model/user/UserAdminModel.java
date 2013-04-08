package com.jpoweredcart.admin.model.user;

import java.util.List;

import com.jpoweredcart.admin.bean.user.UserForm;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.user.User;

/**
 * 
 * @author hussachai
 *
 */
public interface UserAdminModel {
	
	public void create(UserForm userForm);
	
	public void update(UserForm userForm);
	
	public void updatePassword(Integer userId, String password);
	
	/**
	 * Generate random alphanumeric 32 digits then update it to user record.
	 * The code returning from this method may be used for resetting passport
	 * or doing other purposes for identifying real user from email. 
	 * @param email
	 * @return
	 */
	public String generateCode(String email);
	
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
