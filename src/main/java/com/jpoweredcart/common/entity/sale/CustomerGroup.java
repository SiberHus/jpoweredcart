package com.jpoweredcart.common.entity.sale;

import java.io.Serializable;

public class CustomerGroup implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	protected int approval;
	
	protected int sortOrder;
	
	//============================== Transient attributes ======================================//
	protected String name;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public int getApproval() {
		return approval;
	}

	public void setApproval(int approval) {
		this.approval = approval;
	}
	
	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
