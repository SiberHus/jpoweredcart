package com.jpoweredcart.admin.bean.sale;

import java.io.Serializable;
import java.util.List;

public class ContactForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static enum ToTarget {
		Newsletter, CustomerAll, CustomerGroup, Customer, 
		AffiliateAll, Affiliate, Product
	}
	
	protected String subject;
	
	protected String message;
	
	protected Integer storeId;
	
	protected ToTarget toTarget;
	
	protected Integer customerGroupId;
	
	protected List<Integer> customerIds;
	
	protected List<Integer> affiliateIds;
	
	protected List<Integer> productIds;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public ToTarget getToTarget() {
		return toTarget;
	}

	public void setToTarget(ToTarget toTarget) {
		this.toTarget = toTarget;
	}

	public Integer getCustomerGroupId() {
		return customerGroupId;
	}

	public void setCustomerGroupId(Integer customerGroupId) {
		this.customerGroupId = customerGroupId;
	}

	public List<Integer> getCustomerIds() {
		return customerIds;
	}

	public void setCustomerIds(List<Integer> customerIds) {
		this.customerIds = customerIds;
	}

	public List<Integer> getAffiliateIds() {
		return affiliateIds;
	}

	public void setAffiliateIds(List<Integer> affiliateIds) {
		this.affiliateIds = affiliateIds;
	}

	public List<Integer> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<Integer> productIds) {
		this.productIds = productIds;
	}
	
}
