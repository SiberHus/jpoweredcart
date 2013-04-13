package com.jpoweredcart.admin.model.design;

import java.util.List;

import com.jpoweredcart.admin.form.design.LayoutForm;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.design.Layout;
import com.jpoweredcart.common.entity.design.LayoutRoute;

public interface LayoutAdminModel {
	
	public void create(LayoutForm layoutForm);
	
	public void update(LayoutForm layoutForm);
	
	public void delete(Integer layoutId);
	
	public LayoutForm newForm();
	
	public LayoutForm getForm(Integer layoutId);
	
	public Layout get(Integer layoutId);
	
	public List<Layout> getList(PageParam pageParam);
	
	public List<LayoutRoute> getLayoutRoutes(Integer layoutId);
	
	public int getTotal();
	
}
