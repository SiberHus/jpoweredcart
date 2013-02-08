package org.jpoweredcart.common.i18n;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

public class CustomMessageResolver extends AbstractMessageResolver {
	
	@Override
	public String[] getMessagePaths(HttpServletRequest request){
		
		String uri = request.getRequestURI();
		String messagePaths[] = extractMessageParts(uri);
		
		return messagePaths;
		
	}
	
	protected String[] extractMessageParts(String uri){
			
		String uriParts[] = uri.split("/");
		if(uriParts.length < 5){
			throw new IllegalArgumentException("Unable to process URI: "+uri);
		}
		String lastPart = uriParts[4];
		int sepIdx = 0;
		if((sepIdx=lastPart.indexOf(";"))!=-1
				|| (sepIdx=lastPart.indexOf("?"))!=-1) 
			lastPart = lastPart.substring(0, sepIdx);
		
		String messagePaths[] = new String[2]; 
		messagePaths[0] = File.separator + uriParts[2];// admin or catalog
		messagePaths[1] = messagePaths[0] + File.separator + uriParts[3] 
				+ File.separator + lastPart; // moduleName/controllerName
		
		return messagePaths;
	}
	
	
}
