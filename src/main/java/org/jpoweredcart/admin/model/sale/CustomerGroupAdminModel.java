package org.jpoweredcart.admin.model.sale;

import java.util.List;

import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.sale.CustomerGroup;

public interface CustomerGroupAdminModel {

	public void addCustomerGroup(CustomerGroup customerGroup);
	
	public void updateCustomerGroup(CustomerGroup customerGroup);
	
	public void deleteCustomerGroup(Integer customerGroupId);
	
	public CustomerGroup getCustomerGroup(Integer customerGroupId);
	
	public List<CustomerGroup> getCustomerGroups(PageParam pageParam);
	
	public int getTotalCustomerGroups();
	
}
