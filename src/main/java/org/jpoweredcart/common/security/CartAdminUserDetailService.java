package org.jpoweredcart.common.security;

import javax.inject.Inject;

import org.jpoweredcart.admin.model.user.UserAdminModel;
import org.jpoweredcart.admin.model.user.UserGroupAdminModel;
import org.jpoweredcart.common.entity.user.User;
import org.jpoweredcart.common.entity.user.UserGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class CartAdminUserDetailService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(CartAdminUserDetailService.class);
	
	@Inject
	private UserAdminModel userModel;
	
	@Inject
	private UserGroupAdminModel userGroupModel;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		try{
			User user = userModel.getOneByUsername(username);
			if(user==null) throw new EmptyResultDataAccessException(1);
			
			CartUserDetails userDetails = new CartUserDetails();
			userDetails.userId = user.getId();
			userDetails.salt = user.getSalt();
			userDetails.password = user.getPassword();
			userDetails.username = user.getUsername();
			userDetails.enabled = user.getStatus()==1;
			UserGroup userGroup = userGroupModel.getUserGroup(user.getUserGroupId());
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
