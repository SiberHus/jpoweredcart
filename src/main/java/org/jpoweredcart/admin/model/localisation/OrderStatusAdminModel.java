package org.jpoweredcart.admin.model.localisation;

import java.util.List;

import org.jpoweredcart.admin.entity.localisation.OrderStatuses;
import org.jpoweredcart.admin.entity.localisation.OrderStatuses.OrderStatus;
import org.jpoweredcart.common.PageParam;

public interface OrderStatusAdminModel {

	public void addOrderStatus(OrderStatus... orderStatuses);
	
	public void updateOrderStatus(OrderStatus... orderStatuses);
	
	public void deleteOrderStatus(Integer orderStatusId);
	
	public OrderStatus getOrderStatus(Integer orderStatusId);
	
	public List<OrderStatus> getOrderStatuses(PageParam pageParam);
	
	public OrderStatuses getOrderStatuses(Integer orderStatusId);
	
	public int getTotalOrderStatuses();
	
}
