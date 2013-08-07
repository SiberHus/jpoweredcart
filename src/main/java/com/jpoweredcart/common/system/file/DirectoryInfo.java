package com.jpoweredcart.common.system.file;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DirectoryInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String data = "";
	
	private Map<String, String> attributes = new HashMap<String, String>();
	
	private String children = "";
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public void addAttribute(String name, String value){
		this.attributes.put(name, value);
	}
	
	public Map<String, String> getAttributes() {
		return attributes;
	}
	
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public String getChildren() {
		return children;
	}

	public void setChildren(String children) {
		this.children = children;
	}
	
}
