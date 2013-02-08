package com.jpoweredcart.admin.model.sale;

import java.util.List;

import com.jpoweredcart.admin.bean.sale.TotalOrdersFilter;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.sale.Order;
import com.jpoweredcart.common.entity.sale.OrderDownload;
import com.jpoweredcart.common.entity.sale.OrderHistory;
import com.jpoweredcart.common.entity.sale.OrderOption;
import com.jpoweredcart.common.entity.sale.OrderProduct;
import com.jpoweredcart.common.entity.sale.OrderTotal;
import com.jpoweredcart.common.entity.sale.OrderVoucher;

public interface OrderAdminModel {

	public void create(Order order);
	
	public void update(Integer orderId);
	
	public void delete(Integer orderId);
	
	public Order get(Integer orderId);
	
	public List<Order> getList(PageParam pageParam);
	
	public List<OrderProduct> getOrderProducts(Integer orderId);
	
	public OrderOption getOrderOption(Integer orderId, Integer orderOptionId);
	
	public List<OrderOption> getOrderOptions(Integer orderId, Integer orderProductId);
	
	public List<OrderDownload> getOrderDownloads(Integer orderId, Integer orderProductId);
	
	public List<OrderVoucher> getOrderVouchers(Integer orderId);
	
	public OrderVoucher getOrderVoucherByVoucherId(Integer voucherId);
	
	public List<OrderTotal> getOrderTotals(Integer orderId);
	
	public int getTotal(TotalOrdersFilter filter);
	
	public int getTotalByStoreId(Integer storeId);
	
	public int getTotalByOrderStatusId(Integer orderStatusId);
	
	public int getTotalByLanguageId(Integer languageId);
	
	public int getTotalByCurrencyId(Integer currencyId);
	
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


