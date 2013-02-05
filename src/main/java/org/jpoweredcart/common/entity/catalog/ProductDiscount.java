package org.jpoweredcart.common.entity.catalog;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProductDiscount implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	protected Integer productId;
	
	protected Integer customerGroupId;
	
	protected int quantity;
	
	protected int priority;
	
	protected BigDecimal price;
	
	protected Date dateStart;
	
	protected Date dateEnd;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getCustomerGroupId() {
		return customerGroupId;
	}

	public void setCustomerGroupId(Integer customerGroupId) {
		this.customerGroupId = customerGroupId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}
	
	
}
