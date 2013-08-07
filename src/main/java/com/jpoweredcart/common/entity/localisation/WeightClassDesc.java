package com.jpoweredcart.common.entity.localisation;

import org.hibernate.validator.constraints.Length;
import com.jpoweredcart.common.entity.Description;

public class WeightClassDesc extends Description {
	
	private static final long serialVersionUID = 1L;
	
	protected Integer weightClassId;
	
	@Length(min=3, max=32)
	protected String title;
	
	@Length(min=1, max=4)
	protected String unit;
	
	public Integer getWeightClassId() {
		return weightClassId;
	}
	
	public void setWeightClassId(Integer weightClassId) {
		this.weightClassId = weightClassId;
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
	
}
