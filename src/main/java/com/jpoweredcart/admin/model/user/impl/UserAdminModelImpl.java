package com.jpoweredcart.admin.model.user.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.jpoweredcart.admin.form.user.UserForm;
import com.jpoweredcart.admin.model.user.UserAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.QueryBean;
import com.jpoweredcart.common.entity.user.User;
import com.jpoweredcart.common.entity.user.jdbc.UserRowMapper;
import com.jpoweredcart.common.security.Password;


public class UserAdminModelImpl extends BaseModel implements UserAdminModel {
	
	
	@Transactional
	@Override
	public void create(UserForm userForm) {
		String sql = "INSERT INTO " + quoteTable("user") + " SET code='', username = ?, salt = ?, password = ?," +
				"firstname = ?, lastname = ?, email = ?, user_group_id = ?, status = ?, date_added = ?";
		String salt = Password.generateSalt();
		String password = Password.encode(salt, userForm.getPassword());
		getJdbcOperations().update(sql, userForm.getUsername(), salt, password,
				userForm.getFirstName(), userForm.getLastName(), userForm.getEmail(),
				userForm.getUserGroupId(), userForm.getStatus(), new Date());
	}
	
	@Transactional
	@Override
	public void update(UserForm userForm) {
		String sql = "UPDATE " + quoteTable("user") + " SET username = ?, firstname = ?, lastname = ?, " +
				"email = ?, user_group_id = ?, status = ? WHERE user_id = ?";
		getJdbcOperations().update(sql, userForm.getUsername(), userForm.getFirstName(), userForm.getLastName(), 
				userForm.getEmail(), userForm.getUserGroupId(), userForm.getStatus(), userForm.getId());
		
		if(!StringUtils.isBlank(userForm.getPassword())){
			updatePassword(userForm.getId(), userForm.getPassword());
		}
	}
	
	@Transactional
	@Override
	public void updatePassword(Integer userId, String password) {
		String salt = Password.generateSalt();
		password = Password.encode(salt, password);
		String sql = "UPDATE " + quoteTable("user") + " SET salt = ?, password = ? WHERE user_id = ?";
		getJdbcOperations().update(sql, salt, password, userId);
	}
	
	@Transactional
	@Override
	public String generateCode(String email) {
		String code = RandomStringUtils.randomAlphanumeric(32);
		String sql = "UPDATE " +quoteTable("user")+ " SET code = ? WHERE email = ?";
		getJdbcOperations().update(sql, code, email);
		return code;
	}
	
	@Transactional
	@Override
	public void delete(Integer userId) {
		String sql = "DELETE FROM " +quoteTable("user")+ " WHERE user_id = ?";
		getJdbcOperations().update(sql, userId);
	}
	
	@Override
	public UserForm getForm(Integer userId) {
		String sql = "SELECT * FROM " +quoteTable("user")+ " WHERE user_id = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{userId}, 
				new UserRowMapper.Form());
	}

	@Override
	public User get(Integer userId) {
		String sql = "SELECT * FROM " +quoteTable("user")+ " WHERE user_id = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{userId}, new UserRowMapper());
	}
	
	@Override
	public User getOneByUsername(String username) {
		String sql = "SELECT * FROM " +quoteTable("user")+ " WHERE username = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{username}, new UserRowMapper());
	}
	
	@Override
	public User getUserByCode(String code) {
		String sql = "SELECT * FROM " +quoteTable("user")+ " WHERE code = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{code}, new UserRowMapper());
	}
	
	@Override
	public List<User> getList(PageParam pageParam) {
		
		//sortedKeys={"username", "status", "date_added"}
		String sql = "SELECT * FROM "+quoteTable("user");
		QueryBean query = createPaginationQuery(sql, pageParam);
		List<User> userList = getJdbcOperations().query(query.getSql(), 
				query.getParameters(), new UserRowMapper());
		return userList;
	}

	@Override
	public int getTotal() {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("user");
		return getJdbcOperations().queryForObject(sql, Integer.class);
	}
	
	@Override
	public int getTotalByGroupId(Integer userGroupId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("user")+ " WHERE user_group_id = ?"; 
		return getJdbcOperations().queryForObject(sql, new Object[]{userGroupId}, Integer.class);
	}
	
	@Override
	public int getTotalByEmail(String email) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("user")+ " WHERE email = ?"; 
		return getJdbcOperations().queryForObject(sql, new Object[]{email}, Integer.class);
	}
	
	
}
