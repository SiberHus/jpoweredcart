package org.jpoweredcart.common.entity.localisation;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

public class WeightClassDesc implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected Integer weightClassId;
	
	protected Integer languageId;
	
	protected String languageName;
	
	protected String languageImage;
	
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
}
