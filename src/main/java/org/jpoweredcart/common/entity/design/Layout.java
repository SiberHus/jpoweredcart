package org.jpoweredcart.common.entity.design;

import java.io.Serializable;
import java.util.List;

import org.springframework.util.AutoPopulatingList;

public class Layout implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	protected String name;

	protected List<LayoutRoute> layoutRoutes = new AutoPopulatingList<LayoutRoute>(LayoutRoute.class);
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<LayoutRoute> getLayoutRoutes() {
		return layoutRoutes;
	}
	
	public void setLayoutRoutes(List<LayoutRoute> layoutRoutes) {
		this.layoutRoutes = new AutoPopulatingList<LayoutRoute>(layoutRoutes, LayoutRoute.class);
	}
	
}
