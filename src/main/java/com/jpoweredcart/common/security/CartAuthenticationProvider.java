package org.jpoweredcart.common.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.Assert;

public class CartAuthenticationProvider extends
		AbstractUserDetailsAuthenticationProvider {
	
	private UserDetailsService userDetailsService;
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		
		if (authentication.getCredentials() == null) {
			logger.debug("Authentication failed: no credentials provided");

			throw new BadCredentialsException(messages.getMessage(
					"AbstractUserDetailsAuthenticationProvider.badCredentials",
					"Bad credentials"));
		}
		
		String presentedPasswd = authentication.getCredentials().toString();
		
		String salt = null;
		if(userDetails instanceof CartUserDetails){
			CartUserDetails cartUserDetails = (CartUserDetails)userDetails;
			salt = cartUserDetails.getSalt();
		}else{
			throw new AuthenticationServiceException("UserDetails cannot be casted to CartUserDetails");
		}
		
		String encPass = Password.encode(salt.toString(), presentedPasswd);
		
		if(!StringUtils.equals(userDetails.getPassword(), encPass)){
			logger.debug("Authentication failed: password does not match stored value");
			
			throw new BadCredentialsException(messages.getMessage(
					"AbstractUserDetailsAuthenticationProvider.badCredentials",
					"Bad credentials"));
		}
	}
	
	@Override
	protected UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		Assert.notNull(userDetailsService);
		return userDetailsService.loadUserByUsername(username);
	}
	
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
}
