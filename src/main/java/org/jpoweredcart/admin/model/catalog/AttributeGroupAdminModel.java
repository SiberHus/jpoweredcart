package org.jpoweredcart.admin.model.catalog;

import java.util.List;

import org.jpoweredcart.admin.entity.catalog.AttributeGroup;
import org.jpoweredcart.common.PageParam;


public interface AttributeGroupAdminModel {
	
	public void addAttributeGroup(AttributeGroup attrGrp);
	
	public void updateAttributeGroup(AttributeGroup attrGrp);
	
	public void deleteAttributeGroup(Integer attrGrpId);
	
	public AttributeGroup getAttributeGroup(Integer attrGrpId);
	
	public List<AttributeGroup> getAttributeGroups(PageParam pageParam);
	
	public int getTotalAttributeGroups();
	
}
