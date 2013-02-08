package org.jpoweredcart.admin.model.design;

import java.util.List;

import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.design.Layout;
import org.jpoweredcart.common.entity.design.LayoutRoute;

public interface LayoutAdminModel {
	
	public void create(Layout layout);
	
	public void update(Layout layout);
	
	public void delete(Integer layoutId);
		
	public Layout get(Integer layoutId);
	
	public List<Layout> getList(PageParam pageParam);
	
	public List<LayoutRoute> getLayoutRoutes(Integer layoutId);
	
	public int getTotal();
	
}
