package org.jpoweredcart.admin.entity.localisation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaxClass implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	protected String title;
	
	protected String description;
	
	protected Date dateAdded;
	
	protected Date dateModified;

	protected List<TaxRule> taxRules = new ArrayList<TaxRule>();
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public List<TaxRule> getTaxRules() {
		return taxRules;
	}

	public void setTaxRules(List<TaxRule> taxRules) {
		this.taxRules = taxRules;
	}
	
}
