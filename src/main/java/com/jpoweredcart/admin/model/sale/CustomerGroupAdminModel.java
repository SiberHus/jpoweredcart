package com.jpoweredcart.admin.model.sale;

import java.util.List;

import com.jpoweredcart.admin.form.sale.CustomerGroupForm;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.sale.CustomerGroup;
import com.jpoweredcart.common.entity.sale.CustomerGroupDesc;

public interface CustomerGroupAdminModel {

	public void create(CustomerGroupForm cgForm);
	
	public void update(CustomerGroupForm cgForm);
	
	public void delete(Integer cgId);
	
	public CustomerGroupForm newForm();
	
	public CustomerGroupForm getForm(Integer cgId);
	
	public CustomerGroup get(Integer cgId);
	
	public List<CustomerGroup> getList(PageParam pageParam);
	
	public List<CustomerGroupDesc> getDescriptions(Integer cgId);
	
	public int getTotal();
	
}
