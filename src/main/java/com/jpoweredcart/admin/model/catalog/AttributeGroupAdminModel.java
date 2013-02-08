package com.jpoweredcart.admin.model.catalog;

import java.util.List;

import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.catalog.AttributeGroup;


public interface AttributeGroupAdminModel {
	
	public void create(AttributeGroup attrGrp);
	
	public void update(AttributeGroup attrGrp);
	
	public void delete(Integer attrGrpId);
	
	public AttributeGroup get(Integer attrGrpId);
	
	public List<AttributeGroup> getList(PageParam pageParam);
	
	public int getTotal();
	
}
