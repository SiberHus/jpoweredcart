package com.jpoweredcart.admin.model.sale;

import java.math.BigDecimal;
import java.util.List;

import com.jpoweredcart.admin.form.sale.OrderForm;
import com.jpoweredcart.admin.form.sale.filter.TotalOrdersFilter;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.sale.Order;
import com.jpoweredcart.common.entity.sale.OrderDownload;
import com.jpoweredcart.common.entity.sale.OrderHistory;
import com.jpoweredcart.common.entity.sale.OrderOption;
import com.jpoweredcart.common.entity.sale.OrderProduct;
import com.jpoweredcart.common.entity.sale.OrderTotal;
import com.jpoweredcart.common.entity.sale.OrderVoucher;

public interface OrderAdminModel {

	public void create(OrderForm orderForm);
	
	public void update(OrderForm orderForm);
	
	public void delete(Integer orderId);
	
	public OrderForm newForm();
	
	public OrderForm getForm(Integer orderId);
	
	public Order get(Integer orderId, Class<? extends Order> clazz);
	
	public List<Order> getList(PageParam pageParam);
	
	public List<OrderProduct> getProducts(Integer orderId);
	
	public OrderOption getOption(Integer orderId, Integer orderOptionId);
	
	public List<OrderOption> getOptions(Integer orderId, Integer orderProductId);
	
	public List<OrderDownload> getDownloads(Integer orderId, Integer orderProductId);
	
	public List<OrderVoucher> getVouchers(Integer orderId);
	
	public OrderVoucher getVoucherByVoucherId(Integer voucherId);
	
	public List<OrderTotal> getOrderTotals(Integer orderId);
	
	public int getTotal(TotalOrdersFilter filter);
	
	public int getTotalByStoreId(Integer storeId);
	
	public int getTotalByOrderStatusId(Integer orderStatusId);
	
	public int getTotalByLanguageId(Integer languageId);
	
	public int getTotalByCurrencyId(Integer currencyId);
	
	public BigDecimal getTotalSales();
	
	public BigDecimal getTotalSalesByYear(int year);
	
	public String createInvoiceNo(Integer orderId);
	
	public void addHistory(OrderHistory orderHistory);
	
	public List<OrderHistory> getHistories(Integer orderId, int start, int limit);
	
	public int getTotalHistories(Integer orderId);
	
	public int getTotalHistoriesByOrderStatusId(Integer orderStatusId);
	
	public List<String> getEmailsByProductsOrdered(Integer productIds[], int start, int end);
	
	public int getTotalEmailsByProductsOrdered(Integer productIds[]);
	
	
}


