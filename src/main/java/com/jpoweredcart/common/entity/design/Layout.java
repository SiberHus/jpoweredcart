package com.jpoweredcart.common.entity.design;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class Layout implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	@NotBlank @Size(min=3, max=64)
	protected String name;
	
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
	
}
