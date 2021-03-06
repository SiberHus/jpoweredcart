package com.jpoweredcart.common.security;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jpoweredcart.common.entity.user.User;
import com.jpoweredcart.common.entity.user.UserGroup;
import com.jpoweredcart.common.service.UserService;


public class AdminUserDetailService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(AdminUserDetailService.class);
	
	@Inject
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		try{
			User user = userService.getSystemUserByUsername(username);
			if(user==null) throw new EmptyResultDataAccessException(1);
			
			CartUserDetails userDetails = new CartUserDetails();
			userDetails.userId = user.getId();
			userDetails.salt = user.getSalt();
			userDetails.password = user.getPassword();
			userDetails.username = user.getUsername();
			userDetails.enabled = user.getStatus()==1;
			UserGroup userGroup = userService.getSystemUserGroupById(user.getUserGroupId());
			if(userGroup!=null){
				userDetails.authorities.add(userGroup.getName());
				String permString = userGroup.getPermission();
				userDetails.permissions = UserPermissions.unserializePermissions(permString);
			}
			
			logger.debug("Found user username={}, enabled={}", username, userDetails.enabled);
			
			return userDetails;
			
		}catch(EmptyResultDataAccessException e){
			logger.debug("Username: {} not found", username);
			throw new UsernameNotFoundException(username+" not found");
		}
	}
	
}
