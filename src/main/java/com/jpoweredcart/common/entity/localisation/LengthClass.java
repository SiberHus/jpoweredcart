package com.jpoweredcart.common.entity.localisation;

import java.io.Serializable;
import java.math.BigDecimal;

public class LengthClass implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	protected String title;//title for default language
	
	protected String unit;//unit for default language
	
	protected BigDecimal value;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getValue() {
		return value;
	}
	
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	
}
