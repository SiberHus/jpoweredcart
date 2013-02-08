package org.jpoweredcart.common.entity.localisation;

import java.io.Serializable;

public class TaxRule  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	protected Integer taxClassId;
	
	protected Integer taxRateId;
	
	protected String based;
	
	protected int priority;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTaxClassId() {
		return taxClassId;
	}

	public void setTaxClassId(Integer taxClassId) {
		this.taxClassId = taxClassId;
	}

	public Integer getTaxRateId() {
		return taxRateId;
	}

	public void setTaxRateId(Integer taxRateId) {
		this.taxRateId = taxRateId;
	}

	public String getBased() {
		return based;
	}

	public void setBased(String based) {
		this.based = based;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	
}
