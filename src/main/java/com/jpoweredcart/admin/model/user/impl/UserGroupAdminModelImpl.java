package com.jpoweredcart.admin.model.user.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.jpoweredcart.admin.form.user.UserGroupForm;
import com.jpoweredcart.admin.model.user.UserGroupAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.QueryBean;
import com.jpoweredcart.common.entity.user.UserGroup;
import com.jpoweredcart.common.entity.user.jdbc.UserGroupRowMapper;


public class UserGroupAdminModelImpl extends BaseModel implements UserGroupAdminModel {
	
	
	@Transactional
	@Override
	public void create(UserGroupForm userGroupForm) {
		//TODO: (isset($data['permission']) ? serialize($data['permission']) : '')
		String sql = "INSERT INTO "+quoteTable("user_group")+"(name, permission) VALUES(?,?)";
		getJdbcOperations().update(sql, userGroupForm.getName(), userGroupForm.getPermission());
	}
	
	@Transactional
	@Override
	public void update(UserGroupForm userGroupForm) {
		
		String sql = "UPDATE "+quoteTable("user_group")+" SET name=?, permission=? WHERE user_group_id=?";
		getJdbcOperations().update(sql, userGroupForm.getName(), userGroupForm.getPermission(), 
				userGroupForm.getId());
	}
	
	@Transactional
	@Override
	public void delete(Integer userGroupId) {
		
		String sql = "DELETE FROM "+quoteTable("user_group")+" WHERE user_group_id=?";
		getJdbcOperations().update(sql, userGroupId);
	}
	
	@Override
	public UserGroupForm newForm() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserGroupForm getForm(Integer userGroupId) {
		return (UserGroupForm)get(userGroupId, UserGroupForm.class);
	}
	
	@Override
	public UserGroup get(Integer userGroupId, Class<? extends UserGroup> clazz) {
		String sql = "SELECT DISTINCT * FROM "+quoteTable("user_group")+" WHERE user_group_id=?";
		return getJdbcOperations().queryForObject(sql, new Object[]{userGroupId}, 
				new UserGroupRowMapper().setTargetClass(clazz));
	}
	
	@Transactional
	@Override
	public void addPermission(Integer userId, String type, String page) {
		
		String sql = "SELECT COUNT(DISTINCT user_group_id) FROM "+quoteTable("user")+" WHERE user_id=?";
//		if(getJdbcOperations().queryForObject(sql, Integer.class, userId)>0){
//			sql = "SELECT DISTINCT * FROM "+getDbPrefix()+"user_group "
//		}
		//TODO: implement this
	}
	
	
	
	@Override
	public List<UserGroup> getList(PageParam pageParam) {
		
		String sql = "SELECT * FROM "+quoteTable("user_group");
		//sortedKeys={"name"}
		QueryBean query = createPaginationQuery(sql, pageParam);
		List<UserGroup> userGroupList = getJdbcOperations().query(query.getSql(), 
				query.getParameters(), new UserGroupRowMapper());
		return userGroupList;
	}
	
	@Override
	public int getTotal() {
		
		String sql = "SELECT COUNT(*) AS total FROM "+quoteTable("user_group");
		return getJdbcOperations().queryForObject(sql, Integer.class);
	}
	
	
}
