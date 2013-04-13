package com.jpoweredcart.admin.form.report;

import java.io.Serializable;
import java.math.BigDecimal;

public class AffiliateCommissionRpt implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected Integer affiliateId;
	
	protected String affiliateName;
	
	protected String email;
	
	protected short status;
	
	protected BigDecimal commission;
	
	protected int orders;
	
	protected BigDecimal total;

	public Integer getAffiliateId() {
		return affiliateId;
	}

	public void setAffiliateId(Integer affiliateId) {
		this.affiliateId = affiliateId;
	}

	public String getAffiliateName() {
		return affiliateName;
	}

	public void setAffiliateName(String affiliateName) {
		this.affiliateName = affiliateName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public int getOrders() {
		return orders;
	}

	public void setOrders(int orders) {
		this.orders = orders;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
}
