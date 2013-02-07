package org.jpoweredcart.common.entity.localisation;

import org.hibernate.validator.constraints.Length;
import org.jpoweredcart.common.entity.Description;

public class LengthClassDesc extends Description {
	
	private static final long serialVersionUID = 1L;
	
	protected Integer lengthClassId;
	
	@Length(min=3, max=32)
	protected String title;
	
	@Length(min=1, max=4)
	protected String unit;
	
	public Integer getLengthClassId() {
		return lengthClassId;
	}

	public void setLengthClassId(Integer lengthClassId) {
		this.lengthClassId = lengthClassId;
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
