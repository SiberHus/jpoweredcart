package com.jpoweredcart.admin.bean.sale;

import java.util.List;

import javax.validation.Valid;

import org.springframework.util.AutoPopulatingList;

import com.jpoweredcart.common.entity.sale.CustomerGroup;
import com.jpoweredcart.common.entity.sale.CustomerGroupDesc;

public class CustomerGroupForm extends CustomerGroup {
	
	private static final long serialVersionUID = 1L;

	@Valid
	protected List<CustomerGroupDesc> descs = new AutoPopulatingList<CustomerGroupDesc>(CustomerGroupDesc.class);
	
	public List<CustomerGroupDesc> getDescs() {
		return descs;
	}

	public void setDescs(List<CustomerGroupDesc> descs) {
		this.descs = new AutoPopulatingList<CustomerGroupDesc>(descs, CustomerGroupDesc.class);
	}
	
}
