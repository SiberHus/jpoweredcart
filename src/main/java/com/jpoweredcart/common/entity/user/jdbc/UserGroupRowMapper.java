package com.jpoweredcart.common.entity.user.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.user.UserGroup;
import com.jpoweredcart.common.jdbc.ObjectFactoryRowMapper;


public class UserGroupRowMapper extends ObjectFactoryRowMapper<UserGroup>{
	
	@Override
	public UserGroup mapRow(ResultSet rs, UserGroup object) throws SQLException {
		object.setId(rs.getInt("user_group_id"));
		object.setName(rs.getString("name"));
		object.setPermission(rs.getString("permission"));
		return object;
	}
	
}
