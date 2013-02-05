package org.jpoweredcart.common.entity.sale;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import org.springframework.util.AutoPopulatingList;

public class CustomerGroup  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	@Valid
	protected List<CustomerGroupDesc> descs = new AutoPopulatingList<CustomerGroupDesc>(CustomerGroupDesc.class);
	
	protected boolean approval;
	
	protected boolean companyIdDisplay;
	
	protected boolean companyIdRequired;
	
	protected boolean taxIdDisplay;
	
	protected boolean taxIdRequired;
	
	protected Integer sortOrder;
	
	//============================== Transient attributes ======================================//
	protected String name;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public List<CustomerGroupDesc> getDescs() {
		return descs;
	}

	public void setDescs(List<CustomerGroupDesc> descs) {
		this.descs = descs;
	}
	
	public boolean isApproval() {
		return approval;
	}

	public void setApproval(boolean approval) {
		this.approval = approval;
	}

	public boolean isCompanyIdDisplay() {
		return companyIdDisplay;
	}

	public void setCompanyIdDisplay(boolean companyIdDisplay) {
		this.companyIdDisplay = companyIdDisplay;
	}

	public boolean isCompanyIdRequired() {
		return companyIdRequired;
	}

	public void setCompanyIdRequired(boolean companyIdRequired) {
		this.companyIdRequired = companyIdRequired;
	}

	public boolean isTaxIdDisplay() {
		return taxIdDisplay;
	}

	public void setTaxIdDisplay(boolean taxIdDisplay) {
		this.taxIdDisplay = taxIdDisplay;
	}

	public boolean isTaxIdRequired() {
		return taxIdRequired;
	}

	public void setTaxIdRequired(boolean taxIdRequired) {
		this.taxIdRequired = taxIdRequired;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
