package com.jpoweredcart.admin.model.localisation;

import java.util.List;

import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.localisation.OrderStatuses;
import com.jpoweredcart.common.entity.localisation.OrderStatuses.OrderStatus;

public interface OrderStatusAdminModel {

	public void create(OrderStatus... orderStatuses);
	
	public void update(OrderStatus... orderStatuses);
	
	public void delete(Integer orderStatusId);
	
	public OrderStatus get(Integer orderStatusId);
	
	public List<OrderStatus> getList(PageParam pageParam);
	
	public OrderStatuses getOrderStatuses(Integer orderStatusId);
	
	public int getTotal();
	
}
