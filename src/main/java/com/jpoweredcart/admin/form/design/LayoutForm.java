package com.jpoweredcart.admin.form.design;

import java.util.List;

import org.springframework.util.AutoPopulatingList;

import com.jpoweredcart.common.entity.design.Layout;
import com.jpoweredcart.common.entity.design.LayoutRoute;

public class LayoutForm extends Layout {
	
	private static final long serialVersionUID = 1L;
	
	protected List<LayoutRoute> layoutRoutes = new AutoPopulatingList<LayoutRoute>(LayoutRoute.class);
	
	public List<LayoutRoute> getLayoutRoutes() {
		return layoutRoutes;
	}
	
	public void setLayoutRoutes(List<LayoutRoute> layoutRoutes) {
		this.layoutRoutes = new AutoPopulatingList<LayoutRoute>(layoutRoutes, LayoutRoute.class);
	}
	
}
