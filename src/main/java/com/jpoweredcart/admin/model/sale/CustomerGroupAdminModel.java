package com.jpoweredcart.admin.model.sale;

import java.util.List;

import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.sale.CustomerGroup;

public interface CustomerGroupAdminModel {

	public void create(CustomerGroup customerGroup);
	
	public void update(CustomerGroup customerGroup);
	
	public void delete(Integer customerGroupId);
	
	public CustomerGroup get(Integer customerGroupId);
	
	public List<CustomerGroup> getList(PageParam pageParam);
	
	public int getTotal();
	
}
