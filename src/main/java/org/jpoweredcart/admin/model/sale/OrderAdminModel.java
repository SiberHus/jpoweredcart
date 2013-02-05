package org.jpoweredcart.admin.model.sale;

import java.util.List;

import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.sale.Order;
import org.jpoweredcart.common.entity.sale.OrderDownload;
import org.jpoweredcart.common.entity.sale.OrderHistory;
import org.jpoweredcart.common.entity.sale.OrderOption;
import org.jpoweredcart.common.entity.sale.OrderProduct;
import org.jpoweredcart.common.entity.sale.OrderTotal;
import org.jpoweredcart.common.entity.sale.OrderVoucher;

public interface OrderAdminModel {

	public void addOrder(Order order);
	
	public void updateOrder(Integer orderId);
	
	public void deleteOrder(Integer orderId);
	
	public Order getOrder(Integer orderId);
	
	public List<Order> getOrders(PageParam pageParam);
	
	public List<OrderProduct> getOrderProducts(Integer orderId);
	
	public OrderOption getOrderOption(Integer orderId, Integer orderOptionId);
	
	public List<OrderOption> getOrderOptions(Integer orderId, Integer orderProductId);
	
	public List<OrderDownload> getOrderDownloads(Integer orderId, Integer orderProductId);
	
	public List<OrderVoucher> getOrderVouchers(Integer orderId);
	
	public OrderVoucher getOrderVoucherByVoucherId(Integer voucherId);
	
	public List<OrderTotal> getOrderTotals(Integer orderId);
	
	public int getTotalOrders(TotalOrdersFilter filter);
	
	public int getTotalOrdersByStoreId(Integer storeId);
	
	public int getTotalOrdersByOrderStatusId(Integer orderStatusId);
	
	public int getTotalOrdersByLanguageId(Integer languageId);
	
	public int getTotalOrdersByCurrencyId(Integer currencyId);
	
	public int getTotalSales();
	
	public int getTotalSalesByYear(int year);
	
	public String createInvoiceNo(Integer orderId);
	
	public void addOrderHistory(OrderHistory orderHistory);
	
	public List<OrderHistory> getOrderHistories(Integer orderId, int start, int limit);
	
	public int getTotalOrderHistories(Integer orderId);
	
	public int getTotalOrderHistoriesByOrderStatusId(Integer orderStatusId);
	
	public List<String> getEmailsByProductsOrdered(Integer productIds[], int start, int end);
	
	public int getTotalEmailsByProductsOrdered(Integer productIds[]);
	
	
}


