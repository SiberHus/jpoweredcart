package org.jpoweredcart.common.entity.sale;

import java.io.Serializable;

import javax.validation.constraints.Size;

public class IpBlacklist implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	@Size(min=1, max=40)
	protected String ip;

	//============ Transient attributes ===============//
	protected int totalCustomers;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getTotalCustomers() {
		return totalCustomers;
	}

	public void setTotalCustomers(int totalCustomers) {
		this.totalCustomers = totalCustomers;
	}
	
}
