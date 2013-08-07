package com.jpoweredcart.common.security;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jpoweredcart.common.entity.sale.Customer;
import com.jpoweredcart.common.service.UserService;

public class CustomerUserDetailService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerUserDetailService.class);
	
	@Inject
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {
		try{
			Customer customer = userService.getCustomerByEmail(email);
			if(customer==null) throw new EmptyResultDataAccessException(1);
			
			CartUserDetails userDetails = new CartUserDetails();
			userDetails.userId = customer.getId();
			userDetails.salt = customer.getSalt();
			userDetails.password = customer.getPassword();
			userDetails.username = customer.getEmail();
			userDetails.enabled = customer.getStatus()==1;
			
			logger.debug("Found user email={}, enabled={}", email, userDetails.enabled);
			
			return userDetails;
			
		}catch(EmptyResultDataAccessException e){
			logger.debug("Email: {} not found", email);
			throw new UsernameNotFoundException(email+" not found");
		}
	}
	
}
