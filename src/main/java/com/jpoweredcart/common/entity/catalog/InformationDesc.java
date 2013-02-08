package org.jpoweredcart.common.entity.catalog;

import org.hibernate.validator.constraints.Length;
import org.jpoweredcart.common.entity.Description;

public class InformationDesc extends Description {
	
	private static final long serialVersionUID = 1L;
	
	protected Integer informationId;
	
	@Length(min=3, max=32)
	protected String title;
	
	@Length(min=3, max=64)
	protected String description;

	public Integer getInformationId() {
		return informationId;
	}

	public void setInformationId(Integer informationId) {
		this.informationId = informationId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
