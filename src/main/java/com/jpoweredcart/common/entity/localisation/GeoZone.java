package com.jpoweredcart.common.entity.localisation;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class GeoZone implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	@NotBlank @Size(min=3, max=32)
	protected String name;
	
	@NotBlank @Size(min=3, max=255)
	protected String description;
	
	protected Date dateModified;
	
	protected Date dateAdded;
	
	
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

}
