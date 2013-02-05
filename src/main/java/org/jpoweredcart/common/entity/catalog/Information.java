package org.jpoweredcart.common.entity.catalog;

import java.io.Serializable;

public class Information implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	protected int bottom;
	
	protected int sortOrder;
	
	protected short status;
	
	protected String keyword;
	
	protected String title;//title for default language
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public int getBottom() {
		return bottom;
	}

	public void setBottom(int bottom) {
		this.bottom = bottom;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}
	
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
