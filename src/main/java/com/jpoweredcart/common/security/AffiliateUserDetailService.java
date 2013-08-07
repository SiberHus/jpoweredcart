package com.jpoweredcart.common.security;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jpoweredcart.common.entity.sale.Affiliate;
import com.jpoweredcart.common.service.UserService;

public class AffiliateUserDetailService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(AffiliateUserDetailService.class);
	
	@Inject
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {
		try{
			Affiliate affiliate = userService.getAffiliateByEmail(email);
			if(affiliate==null) throw new EmptyResultDataAccessException(1);
			
			CartUserDetails userDetails = new CartUserDetails();
			userDetails.userId = affiliate.getId();
			userDetails.salt = affiliate.getSalt();
			userDetails.password = affiliate.getPassword();
			userDetails.username = affiliate.getEmail();
			userDetails.enabled = affiliate.getStatus()==1;
			
			logger.debug("Found user email={}, enabled={}", email, userDetails.enabled);
			
			return userDetails;
			
		}catch(EmptyResultDataAccessException e){
			logger.debug("Email: {} not found", email);
			throw new UsernameNotFoundException(email+" not found");
		}
	}
	
}
