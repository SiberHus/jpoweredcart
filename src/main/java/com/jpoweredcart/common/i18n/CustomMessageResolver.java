package com.jpoweredcart.common.i18n;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

/**
 * This message resolver use URI convention like UriHierarchyMessageResolver
 * but this is specific to this system URI design only. 
 * 
 * The convention of URI for this system is
 * baseModule/subModule/pageName
 * 
 * For example:
 * admin/localisation/language
 * 
 * The result of getMessagePaths should be {admin, admin/localisation/language}
 * 
 * @author hussachai
 *
 */
public class CustomMessageResolver extends AbstractMessageResolver {
	
	public String getName() { return "CustomMessageResolver"; }
	
	@Override
	public String[] getMessagePaths(HttpServletRequest request){
		
		String requestPath = extractRequestPath(request);
		
		String parts[] = requestPath.split("/+");
		if(parts.length<3){
			throw new RuntimeException("CustomMessageResolver expect URI " +
					"in this pattern /contextPath/moduleName/submoduleName/pageName");
		}
		String p = "";
		String results[] = new String[2];
		p = p+File.separatorChar+parts[0];
		results[0] = p;
		p = p+File.separatorChar+parts[1];
		p = p+File.separatorChar+parts[2];
		results[1] = p;
		return results;
	}
	
}
