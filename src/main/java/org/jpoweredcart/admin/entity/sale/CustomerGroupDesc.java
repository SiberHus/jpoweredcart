package org.jpoweredcart.admin.entity.sale;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

public class CustomerGroupDesc implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected Integer lengthClassId;
	
	protected Integer languageId;
	
	protected String languageName;
	
	protected String languageImage;
	
	@Length(min=3, max=32)
	protected String name;
	
	@Length(min=1, max=4)
	protected String description;
	
	public Integer getLengthClassId() {
		return lengthClassId;
	}

	public void setLengthClassId(Integer lengthClassId) {
		this.lengthClassId = lengthClassId;
	}

	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public String getLanguageImage() {
		return languageImage;
	}

	public void setLanguageImage(String languageImage) {
		this.languageImage = languageImage;
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
	
}
