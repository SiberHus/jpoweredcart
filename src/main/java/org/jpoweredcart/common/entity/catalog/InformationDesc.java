package org.jpoweredcart.common.entity.catalog;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

public class InformationDesc implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected Integer informationId;
	
	protected Integer languageId;
	
	protected String languageName;
	
	protected String languageImage;
	
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
