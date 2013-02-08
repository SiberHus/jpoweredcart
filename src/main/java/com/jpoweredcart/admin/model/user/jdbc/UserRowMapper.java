package org.jpoweredcart.admin.model.user.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jpoweredcart.common.entity.user.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;


@Component
public class UserRowMapper implements RowMapper<User>{
	
	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
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
		return user;
	}
	
}
