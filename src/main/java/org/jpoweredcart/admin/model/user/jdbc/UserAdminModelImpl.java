package org.jpoweredcart.admin.model.user.jdbc;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jpoweredcart.admin.entity.user.User;
import org.jpoweredcart.admin.model.user.UserAdminModel;
import org.jpoweredcart.common.BaseModel;
import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.QueryBean;
import org.jpoweredcart.common.security.Password;
import org.jpoweredcart.common.service.ConfigService;
import org.springframework.jdbc.core.JdbcOperations;


public class UserAdminModelImpl extends BaseModel implements UserAdminModel {
	
	
	public UserAdminModelImpl(ConfigService configService, JdbcOperations jdbcOperations){
		super(configService, jdbcOperations);
	}
	
	@Override
	public void addUser(User user) {
		String sql = "INSERT INTO " + quoteTable("user") + " SET code='', username = ?, salt = ?, password = ?," +
				"firstname = ?, lastname = ?, email = ?, user_group_id = ?, status = ?, date_added = ?";
		String salt = Password.generateSalt();
		String password = Password.encode(salt, user.getPassword());
		getJdbcOperations().update(sql, user.getUsername(), salt, password,
				user.getFirstName(), user.getLastName(), user.getEmail(),
				user.getUserGroupId(), user.getStatus(), new Date());
	}
	
	@Override
	public void updateUser(User user) {
		String sql = "UPDATE " + quoteTable("user") + " SET username = ?, firstname = ?, lastname = ?, " +
				"email = ?, user_group_id = ?, status = ? WHERE user_id = ?";
		getJdbcOperations().update(sql, user.getUsername(), user.getFirstName(), user.getLastName(), 
				user.getEmail(), user.getUserGroupId(), user.getStatus(), user.getId());
		
		if(!StringUtils.isBlank(user.getPassword())){
			updatePassword(user.getId(), user.getPassword());
		}
	}

	@Override
	public void saveUser(User user){
		if(user.getId()!=null){
			updateUser(user);
		}else{
			addUser(user);
		}
	}
	
	@Override
	public void updatePassword(Integer userId, String password) {
		String salt = Password.generateSalt();
		password = Password.encode(salt, password);
		String sql = "UPDATE " + quoteTable("user") + " SET salt = ?, password = ? WHERE user_id = ?";
		getJdbcOperations().update(sql, salt, password, userId);
	}
	
	@Override
	public void updateCode(String email, String code) {
		String sql = "UPDATE " +quoteTable("user")+ " SET code = ? WHERE email = ?";
		getJdbcOperations().update(sql, code, email);
	}
	
	@Override
	public void deleteUser(Integer userId) {
		String sql = "DELETE FROM " +quoteTable("user")+ " WHERE user_id = ?";
		getJdbcOperations().update(sql, userId);
	}
	
	@Override
	public User getUser(Integer userId) {
		String sql = "SELECT * FROM " +quoteTable("user")+ " WHERE user_id = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{userId}, new UserRowMapper());
	}
	
	@Override
	public User getUserByUsername(String username) {
		String sql = "SELECT * FROM " +quoteTable("user")+ " WHERE username = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{username}, new UserRowMapper());
	}
	
	@Override
	public User getUserByCode(String code) {
		String sql = "SELECT * FROM " +quoteTable("user")+ " WHERE code = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{code}, new UserRowMapper());
	}
	
	@Override
	public List<User> getUsers(PageParam pageParam) {
		
		QueryBean query = createPaginationQuery("user", pageParam, 
				new String[]{"username", "status", "date_added"});
		List<User> userList = getJdbcOperations().query(query.getSql(), 
				query.getParameters(), new UserRowMapper());
		return userList;
	}

	@Override
	public int getTotalUsers() {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("user");
		return getJdbcOperations().queryForInt(sql);
	}
	
	@Override
	public int getTotalUsersByGroupId(Integer userGroupId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("user")+ " WHERE user_group_id = ?"; 
		return getJdbcOperations().queryForInt(sql, new Object[]{userGroupId});
	}
	
	@Override
	public int getTotalUsersByEmail(String email) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("user")+ " WHERE email = ?"; 
		return getJdbcOperations().queryForInt(sql, new Object[]{email});
	}
	
	
}
