package org.jpoweredcart.admin.entity.sale;

import java.io.Serializable;

public class OrderDownload implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	protected Integer orderId;
	
	protected Integer orderProductId;
	
	protected String name;
	
	protected String fileName;
	
	protected String mask;
	
	protected int remainig;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMask() {
		return mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}

	public int getRemainig() {
		return remainig;
	}

	public void setRemainig(int remainig) {
		this.remainig = remainig;
	}
	
}
