package com.jpoweredcart.admin.bean.report;

import java.io.Serializable;

public class ProductViewed implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected String name;
	
	protected String model;
	
	protected int viewed;
	
	protected float percent;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getViewed() {
		return viewed;
	}

	public void setViewed(int viewed) {
		this.viewed = viewed;
	}

	public float getPercent() {
		return percent;
	}

	public void setPercent(float percent) {
		this.percent = percent;
	}
	
}
