package com.jpoweredcart.common.entity.sale;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.jpoweredcart.common.entity.Description;

public class CustomerGroupDesc extends Description {
	
	private static final long serialVersionUID = 1L;
	
	protected Integer customerGroupId;
	
	@NotBlank @Length(min=3, max=32)
	protected String name;
	
	protected String description;
	
	public Integer getCustomerGroupId() {
		return customerGroupId;
	}

	public void setCustomerGroupId(Integer customerGroupId) {
		this.customerGroupId = customerGroupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
