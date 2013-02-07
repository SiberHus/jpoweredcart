package org.jpoweredcart.admin.model.sale.jdbc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.ObjectUtils;
import org.jpoweredcart.admin.form.sale.TotalOrdersFilter;
import org.jpoweredcart.admin.model.localisation.CountryAdminModel;
import org.jpoweredcart.admin.model.localisation.CurrencyAdminModel;
import org.jpoweredcart.admin.model.localisation.ZoneAdminModel;
import org.jpoweredcart.admin.model.sale.OrderAdminModel;
import org.jpoweredcart.admin.model.setting.SettingAdminModel;
import org.jpoweredcart.admin.model.setting.StoreAdminModel;
import org.jpoweredcart.common.BaseModel;
import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.localisation.Country;
import org.jpoweredcart.common.entity.localisation.Currency;
import org.jpoweredcart.common.entity.localisation.Zone;
import org.jpoweredcart.common.entity.sale.Order;
import org.jpoweredcart.common.entity.sale.OrderDownload;
import org.jpoweredcart.common.entity.sale.OrderHistory;
import org.jpoweredcart.common.entity.sale.OrderOption;
import org.jpoweredcart.common.entity.sale.OrderProduct;
import org.jpoweredcart.common.entity.sale.OrderTotal;
import org.jpoweredcart.common.entity.sale.OrderVoucher;
import org.jpoweredcart.common.entity.setting.Store;
import org.jpoweredcart.common.service.SettingGroup;
import org.jpoweredcart.common.service.SettingKey;
import org.jpoweredcart.common.service.SettingService;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.transaction.annotation.Transactional;

public class OrderAdminModelImpl extends BaseModel implements OrderAdminModel{
	
	@Inject
	private StoreAdminModel storeAdminModel;
	
	@Inject
	private SettingAdminModel settingAdminModel;
	
	@Inject
	private CountryAdminModel countryAdminModel;
	
	@Inject
	private ZoneAdminModel zoneAdminModel;
	
	@Inject
	private CurrencyAdminModel currencyAdminModel;
	
	
	public OrderAdminModelImpl(SettingService settingService, JdbcOperations jdbcOperations){
		super(settingService, jdbcOperations);
	}
	
	public void setStoreModel(StoreAdminModel storeModel){
		this.storeAdminModel = storeModel;
	}
	
	public void setSettingModel(SettingAdminModel settingModel){
		this.settingAdminModel = settingModel;
	}
	
	public void setCountryModel(CountryAdminModel countryModel) {
		this.countryAdminModel = countryModel;
	}

	public void setZoneModel(ZoneAdminModel zoneModel) {
		this.zoneAdminModel = zoneModel;
	}

	@Transactional
	@Override
	public void create(Order order) {
		
		Store store = storeAdminModel.get(order.getStoreId());
		String storeName = store.getName();
		String storeUrl = store.getUrl();
		
		String invoicePrefix = null;
		Map<String, Object> settings = settingAdminModel.getSettings(SettingGroup.CONFIG, order.getStoreId());
		if(settings.containsKey(SettingKey.INVOICE_PREFIX)){
			invoicePrefix = ObjectUtils.toString(settings.get(SettingKey.INVOICE_PREFIX));
		}else{
			invoicePrefix = getSettingService().getConfig(SettingKey.CFG_INVOICE_PREFIX);
		}
		
		Country country = countryAdminModel.get(order.getPaymentCountryId());
		String shippingCountry = country.getName();
		String shippingAddrFmt = country.getAddressFormat();
		
		Zone zone = zoneAdminModel.get(order.getShippingZoneId());
		String shippingZone = zone.getName();
		
		country = countryAdminModel.get(order.getPaymentCountryId());
		String paymentCountry = country.getName();
		String paymentAddrFmt = country.getAddressFormat();
		
		zone = zoneAdminModel.get(order.getPaymentZoneId());
		String paymentZone = zone.getName();
		
		String currencyCode = getSettingService().getConfig(SettingKey.CFG_CURRENCY);
		Currency currency = currencyAdminModel.getOneByCode(currencyCode);
		Integer currencyId = currency.getId();
		BigDecimal currencyValue = currency.getValue();
		
		
	}

	@Override
	public void update(Integer orderId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer orderId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Order get(Integer orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getList(PageParam pageParam) {
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
	public int getTotal(TotalOrdersFilter filter) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalByStoreId(Integer storeId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalByOrderStatusId(Integer orderStatusId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalByLanguageId(Integer languageId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalByCurrencyId(Integer currencyId) {
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
