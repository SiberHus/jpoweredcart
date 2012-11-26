package org.jpoweredcart.admin.entity.localisation;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

public class Zone implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	protected Integer countryId;
	
	protected String countryName;
	
	@NotBlank
	protected String name;
	
	protected String code;
	
	protected int status = 1;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}
	
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
