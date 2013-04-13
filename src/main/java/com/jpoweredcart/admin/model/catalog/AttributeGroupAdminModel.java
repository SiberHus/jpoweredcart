package com.jpoweredcart.admin.model.catalog;

import java.util.List;

import com.jpoweredcart.admin.form.catalog.AttributeGroupForm;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.catalog.AttributeGroup;
import com.jpoweredcart.common.entity.catalog.AttributeGroupDesc;


public interface AttributeGroupAdminModel {
	
	public void create(AttributeGroupForm attrGrpForm);
	
	public void update(AttributeGroupForm attrGrpForm);
	
	public void delete(Integer attrGrpId);
	
	public AttributeGroupForm newForm();
	
	public AttributeGroupForm getForm(Integer attrGrpId);
	
	public AttributeGroup get(Integer attrGrpId);
	
	public List<AttributeGroup> getList(PageParam pageParam);
	
	public List<AttributeGroupDesc> getDescriptions(Integer attrGrpId);
	
	public int getTotal();
	
}
