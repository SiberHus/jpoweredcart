package com.jpoweredcart.common.entity;

import java.io.Serializable;

public class UrlAlias implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	protected String query;
	
	protected String keyword;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
}
