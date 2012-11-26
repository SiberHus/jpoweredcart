package org.jpoweredcart.admin.model.design;

import java.util.List;

import org.jpoweredcart.admin.entity.design.Layout;
import org.jpoweredcart.admin.entity.design.LayoutRoute;
import org.jpoweredcart.common.PageParam;

public interface LayoutAdminModel {
	
	public void addLayout(Layout layout);
	
	public void updateLayout(Layout layout);
	
	public void deleteLayout(Integer layoutId);
		
	public Layout getLayout(Integer layoutId);
	
	public List<Layout> getLayouts(PageParam pageParam);
	
	public List<LayoutRoute> getLayoutRoutes(Integer layoutId);
	
	public int getTotalLayouts();
	
}
