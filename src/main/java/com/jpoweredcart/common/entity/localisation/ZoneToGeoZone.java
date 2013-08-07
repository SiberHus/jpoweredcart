package com.jpoweredcart.common.entity.localisation;

import java.io.Serializable;
import java.util.Date;

public class ZoneToGeoZone implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	protected Integer countryId;
	
	protected Integer zoneId;
	
	protected Integer geoZoneId;
	
	protected Date dateModified;
	
	protected Date dateAdded;

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

	public Integer getZoneId() {
		return zoneId;
	}

	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}

	public Integer getGeoZoneId() {
		return geoZoneId;
	}

	public void setGeoZoneId(Integer geoZoneId) {
		this.geoZoneId = geoZoneId;
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
