package org.jpoweredcart.common.entity.design;

import java.io.Serializable;

public class LayoutRoute implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	protected Integer layoutId;
	
	protected Integer storeId;
	
	protected String route;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLayoutId() {
		return layoutId;
	}

	public void setLayoutId(Integer layoutId) {
		this.layoutId = layoutId;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}
	
}
