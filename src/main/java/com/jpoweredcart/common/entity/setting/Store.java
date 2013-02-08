package org.jpoweredcart.common.entity.setting;

import java.io.Serializable;

public class Store implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	protected String name;
	
	protected String url;
	
	protected boolean ssl;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isSsl() {
		return ssl;
	}

	public void setSsl(boolean ssl) {
		this.ssl = ssl;
	}
	
}
