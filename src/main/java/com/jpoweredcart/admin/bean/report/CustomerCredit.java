package com.jpoweredcart.admin.bean.report;

import java.math.BigDecimal;

public class CustomerCredit extends AbstractCustomerRpt {
	
	private static final long serialVersionUID = 1L;
	
	protected BigDecimal total;

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
}
