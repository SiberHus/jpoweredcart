package com.jpoweredcart.admin.model.user.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jpoweredcart.admin.bean.user.UserForm;
import com.jpoweredcart.common.entity.user.User;


@Component
public class UserRowMapper implements RowMapper<User>{
	
	private static void setProperties(ResultSet rs, User user) throws SQLException{
		
		user.setId(rs.getInt("user_id"));
		user.setUserGroupId(rs.getInt("user_group_id"));
		user.setUsername(rs.getString("username"));
		user.setPassword(rs.getString("password"));
		user.setSalt(rs.getString("salt"));
		user.setFirstName(rs.getString("firstname"));
		user.setLastName(rs.getString("lastname"));
		user.setEmail(rs.getString("email"));
		user.setCode(rs.getString("code"));
		user.setIp(rs.getString("ip"));
		user.setStatus(rs.getInt("status"));
		user.setDateAdded(rs.getDate("date_added"));
	}
	
	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		setProperties(rs, user);
		return user;
	}
	
	
	public static class Form implements RowMapper<UserForm>{

		@Override
		public UserForm mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserForm form = new UserForm();
			setProperties(rs, form);
			return form;
		}
		
	}
}
