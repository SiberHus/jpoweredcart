package com.jpoweredcart.common.i18n;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

public class UriHierarchyMessageResolver extends AbstractMessageResolver {
	
	private boolean ignoreContext = true;
	
	private int deep = -1; //-1 means unlimited deep
	
	public void setIgnoreContext(boolean ignoreContext){
		this.ignoreContext = ignoreContext;
	}
	
	public void setDeep(int deep){
		this.deep = deep;
	}
	
	@Override
	public String[] getMessagePaths(HttpServletRequest request){
		
		String uri = request.getRequestURI();
		String messagePaths[] = extractMessageParts(uri);
		
		return messagePaths;
		
	}
	
	protected String[] extractMessageParts(String uri){
		
		String uriParts[] = uri.split("/");
		
		int pathLength = uriParts.length;
		int lastIdx = pathLength-1;
		String lastPart = uriParts[lastIdx];
		int sepIdx = 0;
		//remove parameters
		if((sepIdx=lastPart.indexOf(";"))!=-1
				|| (sepIdx=lastPart.indexOf("?"))!=-1) 
			lastPart = lastPart.substring(0, sepIdx);
		
		int j = 0;
		int i = ignoreContext?2: 1;//ignore context name?
		int arraySize = pathLength-i;
		if("".equals(lastPart)) {
			arraySize--;lastIdx--;
			lastPart = uriParts[lastIdx];
		}
		if(deep!=-1) {
			int deepLastIdx = i+deep-1; 
			if(deepLastIdx>lastIdx){
				deepLastIdx = lastIdx;
			}else{
				arraySize = deep;
			}
			lastPart = uriParts[deepLastIdx];
		}
		String[] messagePaths = new String[arraySize]; 
		
		for(; i < arraySize+1 ;i++, j++){
			String prev = j-1>-1 ? messagePaths[j-1] : "";  
			messagePaths[j] = prev + File.separator + uriParts[i];
		}
		if(messagePaths.length-1==j){
			String prev = j-1>-1 ? messagePaths[j-1] : "";
			messagePaths[j] = prev + File.separator + lastPart;
		}
		
		return messagePaths;
	}
	
	
}
