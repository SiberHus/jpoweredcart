package com.jpoweredcart.common.entity.localisation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class Currency implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	@Size(min=3, max=32)
	protected String title;
	
	@Size(min=3, max=3)
	protected String code;
	
	protected String symbolLeft;
	
	protected String symbolRight;
	
	@Min(0) @Max(9)
	protected int decimalPlace; //TODO: I don't know why OpenCart define this data as char type
	
	protected BigDecimal value;
	
	protected short status = 1;
	
	protected Date dateModified;
	
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSymbolLeft() {
		return symbolLeft;
	}

	public void setSymbolLeft(String symbolLeft) {
		this.symbolLeft = symbolLeft;
	}

	public String getSymbolRight() {
		return symbolRight;
	}

	public void setSymbolRight(String symbolRight) {
		this.symbolRight = symbolRight;
	}

	public int getDecimalPlace() {
		return decimalPlace;
	}

	public void setDecimalPlace(int decimalPlace) {
		this.decimalPlace = decimalPlace;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
	
}
