package org.jpoweredcart.common.security;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;



public class UserPermissions {
	
	public static boolean canModify(String entityName){
		return getUserDetails().getPermissions()
				.get("modify").contains(entityName);
	}
	
	public static void checkModify(String entityName, RuntimeException ex){
		if(!canModify(entityName)){
			throw ex;
		}
	}
	
	public static boolean canAccess(String entityName){
		return getUserDetails().getPermissions()
				.get("access").contains(entityName);
	}
	
	public static void checkAccess(String entityName, RuntimeException ex){
		if(!canAccess(entityName)){
			throw ex;
		}
	}
	
	public static boolean can(String action, String entityName){
		return getUserDetails().getPermissions()
				.get(action).contains(entityName);
	}
	
	public static CartUserDetails getUserDetails(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			return (CartUserDetails)auth.getPrincipal();
		}
		throw new AuthenticationCredentialsNotFoundException("User details not found");
	}
	
	public static Integer getUserId(){
		return getUserDetails().getUserId();
	}
	
	
	/**
	 * Serialize permission map to PHP serialized data format.
	 * 
	 * @param data
	 * @return
	 */
	public static String serializePermissions(Map<String, Set<String>> data){
		StringBuilder result = new StringBuilder();
		result.append("a:").append(data.size()).append(":{");
		for(Map.Entry<String, Set<String>> entry:  data.entrySet()){
			String permName = entry.getKey();
			Set<String> permValues = entry.getValue();
			result.append("s:").append(permName.length()).append(":\"")
			.append(permName).append("\";")
			.append("a:").append(permValues.size()).append(":{");
			int i=0;
			for(String perm: permValues){
				result.append("i:").append(i).append(";s:").append(perm.length())
				.append(":\"").append(perm).append("\";");
				i++;
			}
			result.append("}");
		}
		result.append("}");
		return result.toString();
	}
	
	/**
	 * Unserialize PHP serialized data to permission map
	 * This method is not compatible with PHP's unserialize function
	 * It can parse the known structure only
	 *   
	 * @param data
	 * @return
	 */
	public static Map<String, Set<String>> unserializePermissions(String data){
		
		char lastChar = 0;
		boolean inString = false;
		boolean isPermName = false;
		String string = null;
		Map<String, Set<String>> permissions = new HashMap<String, Set<String>>();
		Set<String> moduleNames = null;
		StringBuilder cache = null;
		for(int i=0; i < data.length(); i++){
			char currentChar = data.charAt(i);
			if(inString==false){
				if(currentChar=='"'){
					inString = true;
					cache = new StringBuilder();
				}else if(currentChar=='{'){
					isPermName = !isPermName;
				}else if(currentChar=='}'){
					isPermName = true;
				}else if(currentChar==';' && lastChar == '"'){
					if(isPermName){
						moduleNames = new HashSet<String>();
						permissions.put(string, moduleNames);
					}else{
						moduleNames.add(string);
					}
				}
			}else{
				if(currentChar=='"'){
					inString = false;
					string = cache.toString();
					cache = new StringBuilder();
				}else{
					cache.append(currentChar);
				}
			}
			lastChar = currentChar;
		}
		
		return permissions;
	}
	
	
}
