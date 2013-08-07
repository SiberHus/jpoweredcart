package com.jpoweredcart.common.entity.user.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.jpoweredcart.common.entity.user.User;
import com.jpoweredcart.common.jdbc.ObjectFactoryRowMapper;


@Component
public class UserRowMapper extends ObjectFactoryRowMapper<User>{
	
	@Override
	public User mapRow(ResultSet rs, User object) throws SQLException {
		object.setId(rs.getInt("user_id"));
		object.setUserGroupId(rs.getInt("user_group_id"));
		object.setUsername(rs.getString("username"));
		object.setPassword(rs.getString("password"));
		object.setSalt(rs.getString("salt"));
		object.setFirstName(rs.getString("firstname"));
		object.setLastName(rs.getString("lastname"));
		object.setEmail(rs.getString("email"));
		object.setCode(rs.getString("code"));
		object.setIp(rs.getString("ip"));
		object.setStatus(rs.getInt("status"));
		object.setDateAdded(rs.getDate("date_added"));
		return object;
	}
	
}
