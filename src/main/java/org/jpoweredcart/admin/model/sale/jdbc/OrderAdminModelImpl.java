package org.jpoweredcart.admin.model.sale.jdbc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.jpoweredcart.admin.entity.localisation.Country;
import org.jpoweredcart.admin.entity.localisation.Currency;
import org.jpoweredcart.admin.entity.localisation.Zone;
import org.jpoweredcart.admin.entity.sale.Order;
import org.jpoweredcart.admin.entity.sale.OrderDownload;
import org.jpoweredcart.admin.entity.sale.OrderHistory;
import org.jpoweredcart.admin.entity.sale.OrderOption;
import org.jpoweredcart.admin.entity.sale.OrderProduct;
import org.jpoweredcart.admin.entity.sale.OrderTotal;
import org.jpoweredcart.admin.entity.sale.OrderVoucher;
import org.jpoweredcart.admin.entity.setting.Store;
import org.jpoweredcart.admin.model.localisation.CountryAdminModel;
import org.jpoweredcart.admin.model.localisation.CurrencyAdminModel;
import org.jpoweredcart.admin.model.localisation.ZoneAdminModel;
import org.jpoweredcart.admin.model.sale.OrderAdminModel;
import org.jpoweredcart.admin.model.sale.TotalOrdersFilter;
import org.jpoweredcart.admin.model.setting.SettingAdminModel;
import org.jpoweredcart.admin.model.setting.StoreAdminModel;
import org.jpoweredcart.common.BaseModel;
import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.ConfigGroup;
import org.jpoweredcart.common.ConfigKey;
import org.jpoweredcart.common.service.ConfigService;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.transaction.annotation.Transactional;

public class OrderAdminModelImpl extends BaseModel implements OrderAdminModel{
	
	private StoreAdminModel storeModel;
	
	private SettingAdminModel settingModel;
	
	private CountryAdminModel countryModel;
	
	private ZoneAdminModel zoneModel;
	
	private CurrencyAdminModel currencyModel;
	
	
	public OrderAdminModelImpl(ConfigService configService, JdbcOperations jdbcOperations){
		super(configService, jdbcOperations);
	}
	
	public void setStoreModel(StoreAdminModel storeModel){
		this.storeModel = storeModel;
	}
	
	public void setSettingModel(SettingAdminModel settingModel){
		this.settingModel = settingModel;
	}
	
	public void setCountryModel(CountryAdminModel countryModel) {
		this.countryModel = countryModel;
	}

	public void setZoneModel(ZoneAdminModel zoneModel) {
		this.zoneModel = zoneModel;
	}

	@Transactional
	@Override
	public void addOrder(Order order) {
		
		Store store = storeModel.getStore(order.getStoreId());
		String storeName = store.getName();
		String storeUrl = store.getUrl();
		
		String invoicePrefix = null;
		Map<String, Object> settings = settingModel.getSettings(ConfigGroup.SETTING, order.getStoreId());
		if(settings.containsKey(ConfigKey.INVOICE_PREFIX)){
			invoicePrefix = ObjectUtils.toString(settings.get(ConfigKey.INVOICE_PREFIX));
		}else{
			invoicePrefix = getConfigService().get(ConfigKey.CFG_INVOICE_PREFIX);
		}
		
		Country country = countryModel.getCountry(order.getPaymentCountryId());
		String shippingCountry = country.getName();
		String shippingAddrFmt = country.getAddressFormat();
		
		Zone zone = zoneModel.getZone(order.getShippingZoneId());
		String shippingZone = zone.getName();
		
		country = countryModel.getCountry(order.getPaymentCountryId());
		String paymentCountry = country.getName();
		String paymentAddrFmt = country.getAddressFormat();
		
		zone = zoneModel.getZone(order.getPaymentZoneId());
		String paymentZone = zone.getName();
		
		String currencyCode = getConfigService().get(ConfigKey.CFG_CURRENCY);
		Currency currency = currencyModel.getCurrencyByCode(currencyCode);
		Integer currencyId = currency.getId();
		BigDecimal currencyValue = currency.getValue();
		
		
	}

	@Override
	public void updateOrder(Integer orderId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteOrder(Integer orderId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Order getOrder(Integer orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getOrders(PageParam pageParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderProduct> getOrderProducts(Integer orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderOption getOrderOption(Integer orderId, Integer orderOptionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderOption> getOrderOptions(Integer orderId,
			Integer orderProductId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderDownload> getOrderDownloads(Integer orderId,
			Integer orderProductId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderVoucher> getOrderVouchers(Integer orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderVoucher getOrderVoucherByVoucherId(Integer voucherId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderTotal> getOrderTotals(Integer orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalOrders(TotalOrdersFilter filter) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalOrdersByStoreId(Integer storeId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalOrdersByOrderStatusId(Integer orderStatusId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalOrdersByLanguageId(Integer languageId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalOrdersByCurrencyId(Integer currencyId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalSales() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalSalesByYear(int year) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String createInvoiceNo(Integer orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addOrderHistory(OrderHistory orderHistory) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<OrderHistory> getOrderHistories(Integer orderId, int start,
			int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalOrderHistories(Integer orderId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalOrderHistoriesByOrderStatusId(Integer orderStatusId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<String> getEmailsByProductsOrdered(Integer[] productIds,
			int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalEmailsByProductsOrdered(Integer[] productIds) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
}
