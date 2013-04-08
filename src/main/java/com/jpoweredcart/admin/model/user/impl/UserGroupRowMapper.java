package com.jpoweredcart.admin.model.user.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.user.UserGroup;
import org.springframework.jdbc.core.RowMapper;


public class UserGroupRowMapper implements RowMapper<UserGroup>{

	@Override
	public UserGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserGroup group = new UserGroup();
		group.setId(rs.getInt("user_group_id"));
		group.setName(rs.getString("name"));
		group.setPermission(rs.getString("permission"));
		return group;
	}
	
}