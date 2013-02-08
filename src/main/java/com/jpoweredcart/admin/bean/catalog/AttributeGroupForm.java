package com.jpoweredcart.admin.bean.catalog;

import java.util.List;

import javax.validation.Valid;

import org.springframework.util.AutoPopulatingList;

import com.jpoweredcart.common.entity.catalog.AttributeGroup;
import com.jpoweredcart.common.entity.catalog.AttributeGroupDesc;

public class AttributeGroupForm extends AttributeGroup {
	
	private static final long serialVersionUID = 1L;
	
	@Valid
	protected List<AttributeGroupDesc> descs = new AutoPopulatingList<AttributeGroupDesc>(AttributeGroupDesc.class);
	
	public List<AttributeGroupDesc> getDescs() {
		return descs;
	}
	
	public void setDescs(List<AttributeGroupDesc> descs) {
		this.descs = new AutoPopulatingList<AttributeGroupDesc>(descs, AttributeGroupDesc.class);
	}
	
}
