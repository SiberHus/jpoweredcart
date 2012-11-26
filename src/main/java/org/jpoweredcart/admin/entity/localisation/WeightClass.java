package org.jpoweredcart.admin.entity.localisation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.util.AutoPopulatingList;

public class WeightClass implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	@Valid
	protected List<WeightClassDesc> descs = new AutoPopulatingList<WeightClassDesc>(WeightClassDesc.class);
	
	protected String title;//title for default language
	
	protected String unit;//unit for default language
	
	protected BigDecimal value;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public List<WeightClassDesc> getDescs() {
		return descs;
	}
	
	public void setDescs(List<WeightClassDesc> descs) {
		this.descs = descs = new AutoPopulatingList<WeightClassDesc>(descs, WeightClassDesc.class);
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
