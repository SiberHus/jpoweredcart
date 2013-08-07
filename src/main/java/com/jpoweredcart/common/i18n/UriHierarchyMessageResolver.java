package com.jpoweredcart.common.i18n;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

/**
 * This message resolver use the URI convention to resolve all the possible message paths
 * The URI is from HttpServletRequest.requestURI but without context path and parameters
 * For example: 
 * Suppose that the context path is /jpoweredcart
 * The request URI is /jpoweredcart/admin/common/login
 * The result of getMessagePaths will be the array of string
 * {admin, admin/common, admin/common/login}
 * 
 * @author hussachai
 *
 */
public class UriHierarchyMessageResolver extends AbstractMessageResolver {
	
	public String getName() { return "UriHierarchyMessageResolver"; }
	
	@Override
	public String[] getMessagePaths(HttpServletRequest request) {
		
		String requestPath = extractRequestPath(request);
		
		String parts[] = requestPath.split("/+");
		
		int length = parts.length;
		String p = "";
		String results[] = new String[length];
		for(int i=0;i<length;i++){
			p = p+File.separatorChar+parts[i];
			results[i] = p;
		}
		
		return results;
	}
	
}
