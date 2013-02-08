package com.jpoweredcart.common.entity;

import java.io.Serializable;

public class Description implements Serializable {
	
	private static final long serialVersionUID = 1L;

	protected Integer languageId;
	
	protected String languageName;
	
	protected String languageImage;

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
	
}
