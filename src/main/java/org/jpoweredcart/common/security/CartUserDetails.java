package org.jpoweredcart.common.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CartUserDetails implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	protected List<String> authorities = new ArrayList<String>();
	protected Map<String, Set<String>> permissions;
	protected String salt;
	protected String password;
	protected String username;
	protected Integer userId;
	protected boolean accountNonExpired = true; //TODO: add this feature
	protected boolean accountNonLocked = true; //TODO: add this feature
	protected boolean credentialsNonExpired = true; //TODO: add this feature
	protected boolean enabled;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		for(String authority : authorities) {
			grantedAuthorities.add(new SimpleGrantedAuthority(authority));
		}
		return grantedAuthorities;
	}
	
	public Map<String, Set<String>> getPermissions(){
		return permissions;
	}
	
	public String getSalt(){
		return salt;
	}
	
	@Override
	public String getPassword() {
		
		return password;
	}

	@Override
	public String getUsername() {
		
		return username;
	}
	
	public Integer getUserId() {
		return userId;
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		
		return enabled;
	}
	
}
