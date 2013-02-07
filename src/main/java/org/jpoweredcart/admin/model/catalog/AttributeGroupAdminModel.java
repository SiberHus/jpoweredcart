package org.jpoweredcart.admin.model.catalog;

import java.util.List;

import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.catalog.AttributeGroup;


public interface AttributeGroupAdminModel {
	
	public void create(AttributeGroup attrGrp);
	
	public void update(AttributeGroup attrGrp);
	
	public void delete(Integer attrGrpId);
	
	public AttributeGroup get(Integer attrGrpId);
	
	public List<AttributeGroup> getList(PageParam pageParam);
	
	public int getTotal();
	
}
