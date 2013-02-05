package org.jpoweredcart.admin.model.user.jdbc;

import java.util.List;

import org.jpoweredcart.admin.model.user.UserGroupAdminModel;
import org.jpoweredcart.common.BaseModel;
import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.QueryBean;
import org.jpoweredcart.common.entity.user.UserGroup;
import org.jpoweredcart.common.service.SettingService;
import org.springframework.jdbc.core.JdbcOperations;


public class UserGroupAdminModelImpl extends BaseModel implements UserGroupAdminModel {
	
	
	public UserGroupAdminModelImpl(SettingService configService, JdbcOperations jdbcOperations){
		super(configService, jdbcOperations);
	}
	
	@Override
	public List<UserGroup> getUserGroupsByUsername(String username) {
		
		String sql = "SELECT ug.user_group_id, ug.name, ug.permission FROM " 
				+ quoteTable("user_group") + " ug INNER JOIN "
				+ quoteTable("user")+ " u ON u.user_group_id=ug.user_group_id WHERE u.username=?";
		List<UserGroup> userGroups = getJdbcOperations().query(sql, 
				new Object[]{username}, new UserGroupRowMapper());
		return userGroups;
	}
	
	@Override
	public void addUserGroup(UserGroup userGroup) {
		//TODO: (isset($data['permission']) ? serialize($data['permission']) : '')
		String sql = "INSERT INTO "+quoteTable("user_group")+"(name, permission) VALUES(?,?)";
		getJdbcOperations().update(sql, userGroup.getName(), userGroup.getPermission());
	}
	
	@Override
	public void updateUserGroup(UserGroup userGroup) {
		
		String sql = "UPDATE "+quoteTable("user_group")+" SET name=?, permission=? WHERE user_group_id=?";
		getJdbcOperations().update(sql, userGroup.getName(), userGroup.getPermission(), userGroup.getId());
	}
	
	@Override
	public void saveUserGroup(UserGroup userGroup){
		if(userGroup.getId()!=null){
			updateUserGroup(userGroup);
		}else{
			addUserGroup(userGroup);
		}
	}
	
	@Override
	public void deleteUserGroup(Integer userGroupId) {
		
		String sql = "DELETE FROM "+quoteTable("user_group")+" WHERE user_group_id=?";
		getJdbcOperations().update(sql, userGroupId);
	}
	
	@Override
	public void addPermission(Integer userId, String type, String page) {
		
		String sql = "SELECT COUNT(DISTINCT user_group_id) FROM "+quoteTable("user")+" WHERE user_id=?";
//		if(getJdbcOperations().queryForInt(sql, userId)>0){
//			sql = "SELECT DISTINCT * FROM "+getDbPrefix()+"user_group "
//		}
		//TODO: implement this
	}
	
	@Override
	public UserGroup getUserGroup(Integer userGroupId) {
		String sql = "SELECT DISTINCT * FROM "+quoteTable("user_group")+" WHERE user_group_id=?";
		return getJdbcOperations().queryForObject(sql, new Object[]{userGroupId}, new UserGroupRowMapper());
	}
	
	@Override
	public List<UserGroup> getUserGroups(PageParam pageParam) {
		
		QueryBean query = createPaginationQuery("user_group", pageParam, 
				new String[]{"name"});
		List<UserGroup> userGroupList = getJdbcOperations().query(query.getSql(), 
				query.getParameters(), new UserGroupRowMapper());
		return userGroupList;
	}
	
	@Override
	public int getTotalUserGroups() {
		
		String sql = "SELECT COUNT(*) AS total FROM "+quoteTable("user_group");
		return getJdbcOperations().queryForInt(sql);
	}
	
	
}
