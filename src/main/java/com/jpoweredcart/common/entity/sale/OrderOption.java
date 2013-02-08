package org.jpoweredcart.common.entity.sale;

import java.io.Serializable;

public class OrderOption implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	protected Integer orderId;
	
	protected Integer orderProductId;
	
	protected Integer productOptionId;
	
	protected Integer productOptionValueId;
	
	protected String name;
	
	protected String value;
	
	protected String type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getOrderProductId() {
		return orderProductId;
	}

	public void setOrderProductId(Integer orderProductId) {
		this.orderProductId = orderProductId;
	}

	public Integer getProductOptionId() {
		return productOptionId;
	}

	public void setProductOptionId(Integer productOptionId) {
		this.productOptionId = productOptionId;
	}

	public Integer getProductOptionValueId() {
		return productOptionValueId;
	}

	public void setProductOptionValueId(Integer productOptionValueId) {
		this.productOptionValueId = productOptionValueId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
