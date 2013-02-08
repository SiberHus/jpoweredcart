package com.jpoweredcart.admin.bean.catalog;

import java.util.List;

import javax.validation.Valid;

import org.springframework.util.AutoPopulatingList;

import com.jpoweredcart.common.entity.catalog.Attribute;
import com.jpoweredcart.common.entity.catalog.AttributeDesc;

public class AttributeForm extends Attribute {
	
	private static final long serialVersionUID = 1L;
	
	@Valid
	protected List<AttributeDesc> descs = new AutoPopulatingList<AttributeDesc>(AttributeDesc.class);
	
	public List<AttributeDesc> getDescs() {
		return descs;
	}
	
	public void setDescs(List<AttributeDesc> descs) {
		this.descs = new AutoPopulatingList<AttributeDesc>(descs, AttributeDesc.class);
	}
	
}
