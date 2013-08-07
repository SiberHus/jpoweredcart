package com.jpoweredcart.common.entity.localisation;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class Country implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	@NotBlank @Size(min=3, max=128)
	protected String name;
	
	protected String isoCode2;
	
	protected String isoCode3;
	
	protected String addressFormat;
	
	protected boolean postcodeRequired;
	
	protected short status = 1;
	
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

	public String getIsoCode2() {
		return isoCode2;
	}

	public void setIsoCode2(String isoCode2) {
		this.isoCode2 = isoCode2;
	}

	public String getIsoCode3() {
		return isoCode3;
	}

	public void setIsoCode3(String isoCode3) {
		this.isoCode3 = isoCode3;
	}

	public String getAddressFormat() {
		return addressFormat;
	}

	public void setAddressFormat(String addressFormat) {
		this.addressFormat = addressFormat;
	}

	public boolean isPostcodeRequired() {
		return postcodeRequired;
	}

	public void setPostcodeRequired(boolean postcodeRequired) {
		this.postcodeRequired = postcodeRequired;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}
	
}
