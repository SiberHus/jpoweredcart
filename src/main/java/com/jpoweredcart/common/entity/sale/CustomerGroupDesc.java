package com.jpoweredcart.common.entity.sale;

import org.hibernate.validator.constraints.Length;
import com.jpoweredcart.common.entity.Description;

public class CustomerGroupDesc extends Description {
	
	private static final long serialVersionUID = 1L;
	
	protected Integer lengthClassId;
	
	@Length(min=3, max=32)
	protected String name;
	
	@Length(min=1, max=4)
	protected String description;
	
	public Integer getLengthClassId() {
		return lengthClassId;
	}

	public void setLengthClassId(Integer lengthClassId) {
		this.lengthClassId = lengthClassId;
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
