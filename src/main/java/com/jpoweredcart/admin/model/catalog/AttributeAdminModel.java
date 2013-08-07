package com.jpoweredcart.admin.model.catalog;

import java.util.List;

import com.jpoweredcart.admin.form.catalog.AttributeForm;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.catalog.Attribute;
import com.jpoweredcart.common.entity.catalog.AttributeDesc;


public interface AttributeAdminModel {
	
	public void create(AttributeForm attrForm);
	
	public void update(AttributeForm attrForm);
	
	public void delete(Integer attrId);
	
	public AttributeForm newForm();
	
	public AttributeForm getForm(Integer attrId);
	
	public Attribute get(Integer attrId, Class<? extends Attribute> clazz);
	
	public List<Attribute> getList(PageParam pageParam);
	
	public List<AttributeDesc> getDescriptions(Integer attrId);
	
//	public List<Attribute> getAllByAttributeGroupId(Integer attrGrpId);
	
	public int getTotal();
	
	public int getTotalByAttributeGroupId(Integer attrGrpId);
	
}
