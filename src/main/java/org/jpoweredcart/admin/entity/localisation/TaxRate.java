package org.jpoweredcart.admin.entity.localisation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TaxRate implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	protected Integer geoZoneId;
	
	protected String name;
	
	protected BigDecimal rate;
	
	protected String type;
	
	protected Date dateAdded;
	
	protected Date dateModified;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGeoZoneId() {
		return geoZoneId;
	}

	public void setGeoZoneId(Integer geoZoneId) {
		this.geoZoneId = geoZoneId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
	
}
