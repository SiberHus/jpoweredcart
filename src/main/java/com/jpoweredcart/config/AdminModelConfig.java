package com.jpoweredcart.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;

import com.jpoweredcart.admin.model.catalog.AttributeAdminModel;
import com.jpoweredcart.admin.model.catalog.AttributeGroupAdminModel;
import com.jpoweredcart.admin.model.catalog.CategoryAdminModel;
import com.jpoweredcart.admin.model.catalog.InformationAdminModel;
import com.jpoweredcart.admin.model.catalog.ProductAdminModel;
import com.jpoweredcart.admin.model.catalog.ReviewAdminModel;
import com.jpoweredcart.admin.model.catalog.jdbc.AttributeAdminModelImpl;
import com.jpoweredcart.admin.model.catalog.jdbc.AttributeGroupAdminModelImpl;
import com.jpoweredcart.admin.model.catalog.jdbc.CategoryAdminModelImpl;
import com.jpoweredcart.admin.model.catalog.jdbc.InformationAdminModelImpl;
import com.jpoweredcart.admin.model.catalog.jdbc.ProductAdminModelImpl;
import com.jpoweredcart.admin.model.catalog.jdbc.ReviewAdminModelImpl;
import com.jpoweredcart.admin.model.design.BannerAdminModel;
import com.jpoweredcart.admin.model.design.LayoutAdminModel;
import com.jpoweredcart.admin.model.design.jdbc.BannerAdminModelImpl;
import com.jpoweredcart.admin.model.design.jdbc.LayoutAdminModelImpl;
import com.jpoweredcart.admin.model.localisation.CountryAdminModel;
import com.jpoweredcart.admin.model.localisation.CurrencyAdminModel;
import com.jpoweredcart.admin.model.localisation.GeoZoneAdminModel;
import com.jpoweredcart.admin.model.localisation.LanguageAdminModel;
import com.jpoweredcart.admin.model.localisation.LengthClassAdminModel;
import com.jpoweredcart.admin.model.localisation.OrderStatusAdminModel;
import com.jpoweredcart.admin.model.localisation.ReturnActionAdminModel;
import com.jpoweredcart.admin.model.localisation.ReturnReasonAdminModel;
import com.jpoweredcart.admin.model.localisation.ReturnStatusAdminModel;
import com.jpoweredcart.admin.model.localisation.StockStatusAdminModel;
import com.jpoweredcart.admin.model.localisation.TaxClassAdminModel;
import com.jpoweredcart.admin.model.localisation.TaxRateAdminModel;
import com.jpoweredcart.admin.model.localisation.WeightClassAdminModel;
import com.jpoweredcart.admin.model.localisation.ZoneAdminModel;
import com.jpoweredcart.admin.model.localisation.jdbc.CountryAdminModelImpl;
import com.jpoweredcart.admin.model.localisation.jdbc.CurrencyAdminModelImpl;
import com.jpoweredcart.admin.model.localisation.jdbc.GeoZoneAdminModelImpl;
import com.jpoweredcart.admin.model.localisation.jdbc.LanguageAdminModelImpl;
import com.jpoweredcart.admin.model.localisation.jdbc.LengthClassAdminModelImpl;
import com.jpoweredcart.admin.model.localisation.jdbc.OrderStatusAdminModelImpl;
import com.jpoweredcart.admin.model.localisation.jdbc.ReturnActionAdminModelImpl;
import com.jpoweredcart.admin.model.localisation.jdbc.ReturnReasonAdminModelImpl;
import com.jpoweredcart.admin.model.localisation.jdbc.ReturnStatusAdminModelImpl;
import com.jpoweredcart.admin.model.localisation.jdbc.StockStatusAdminModelImpl;
import com.jpoweredcart.admin.model.localisation.jdbc.TaxClassAdminModelImpl;
import com.jpoweredcart.admin.model.localisation.jdbc.TaxRateAdminModelImpl;
import com.jpoweredcart.admin.model.localisation.jdbc.WeightClassAdminModelImpl;
import com.jpoweredcart.admin.model.localisation.jdbc.ZoneAdminModelImpl;
import com.jpoweredcart.admin.model.report.CustomerReportAdminModel;
import com.jpoweredcart.admin.model.report.ProductReportAdminModel;
import com.jpoweredcart.admin.model.report.SaleReportAdminModel;
import com.jpoweredcart.admin.model.report.jdbc.CustomerReportAdminModelImpl;
import com.jpoweredcart.admin.model.report.jdbc.ProductReportAdminModelImpl;
import com.jpoweredcart.admin.model.report.jdbc.SaleReportAdminModelImpl;
import com.jpoweredcart.admin.model.sale.CustomerGroupAdminModel;
import com.jpoweredcart.admin.model.sale.IpBlacklistAdminModel;
import com.jpoweredcart.admin.model.sale.OrderAdminModel;
import com.jpoweredcart.admin.model.sale.VoucherAdminModel;
import com.jpoweredcart.admin.model.sale.jdbc.CustomerGroupAdminModelImpl;
import com.jpoweredcart.admin.model.sale.jdbc.IpBlacklistAdminModelImpl;
import com.jpoweredcart.admin.model.sale.jdbc.OrderAdminModelImpl;
import com.jpoweredcart.admin.model.sale.jdbc.VoucherAdminModelImpl;
import com.jpoweredcart.admin.model.setting.SettingAdminModel;
import com.jpoweredcart.admin.model.setting.StoreAdminModel;
import com.jpoweredcart.admin.model.setting.jdbc.SettingAdminModelImpl;
import com.jpoweredcart.admin.model.setting.jdbc.StoreAdminModelImpl;
import com.jpoweredcart.admin.model.user.UserAdminModel;
import com.jpoweredcart.admin.model.user.UserGroupAdminModel;
import com.jpoweredcart.admin.model.user.jdbc.UserAdminModelImpl;
import com.jpoweredcart.admin.model.user.jdbc.UserGroupAdminModelImpl;
import com.jpoweredcart.common.service.EmailService;
import com.jpoweredcart.common.service.SettingService;


@Configuration
public class AdminModelConfig {
	
	
	@Inject
	private SettingService settingService;
	
	@Inject
	private JdbcOperations jdbcOperations;

	@Inject
	private EmailService emailService;
	
	//================= Catalog ========================//
	@Bean
	public AttributeAdminModel attributeAdminModel(){ return new AttributeAdminModelImpl(); }
	@Bean
	public AttributeGroupAdminModel attributeGroupAdminModel(){ return new AttributeGroupAdminModelImpl(); }
	@Bean
	public CategoryAdminModel categoryAdminModel(){ return new CategoryAdminModelImpl(); }
	@Bean
	public ProductAdminModel productAdminModel(){ return new ProductAdminModelImpl(); }
	@Bean
	public InformationAdminModel informationAdminModel(){ return new InformationAdminModelImpl(); }
	@Bean
	public ReviewAdminModel reviewAdminModel(){ return new ReviewAdminModelImpl(); }
	
	//================= Design ========================//
	@Bean
	public BannerAdminModel bannerAdminModel(){ return new BannerAdminModelImpl(); }
	@Bean
	public LayoutAdminModel layoutAdminModel(){ return new LayoutAdminModelImpl(); }
	
	//================= Locaisation ========================//
	@Bean
	public LanguageAdminModel languageAdminModel(){ return new LanguageAdminModelImpl();}
	@Bean
	public CurrencyAdminModel currencyAdminModel(){ return new CurrencyAdminModelImpl();}
	@Bean
	public CountryAdminModel countryAdminModel() { return new CountryAdminModelImpl(); }
	@Bean
	public ZoneAdminModel zoneAdminModel() { return new ZoneAdminModelImpl(); }
	@Bean
	public GeoZoneAdminModel geoZoneAdminModel(){ return new GeoZoneAdminModelImpl(); }
	@Bean
	public StockStatusAdminModel stockStatusAdminModel(){ return new StockStatusAdminModelImpl(); }
	@Bean
	public OrderStatusAdminModel orderStatusAdminModel(){ return new OrderStatusAdminModelImpl(); }
	@Bean
	public ReturnStatusAdminModel returnStatusAdminModel(){ return new ReturnStatusAdminModelImpl(); }
	@Bean
	public ReturnActionAdminModel returnActionAdminModel(){ return new ReturnActionAdminModelImpl(); }
	@Bean
	public ReturnReasonAdminModel returnReasonAdminModel(){ return new ReturnReasonAdminModelImpl(); }
	@Bean
	public TaxClassAdminModel taxClassAdminModel(){ return new TaxClassAdminModelImpl(); }
	@Bean
	public TaxRateAdminModel taxRateAdminModel(){ return new TaxRateAdminModelImpl(); }
	@Bean
	public WeightClassAdminModel weightClassAdminModel(){ return new WeightClassAdminModelImpl(); }
	@Bean
	public LengthClassAdminModel lengthClassAdminModel(){ return new LengthClassAdminModelImpl(); }
	
	//================= Sale ========================//
	@Bean
	public OrderAdminModel orderAdminModel(){ return new OrderAdminModelImpl(); }
	@Bean
	public CustomerGroupAdminModel customerGroupAdminModel(){ return new CustomerGroupAdminModelImpl(); }
	@Bean
	public IpBlacklistAdminModel ipBlacklistAdminModel(){ return new IpBlacklistAdminModelImpl(); }
	@Bean
	public VoucherAdminModel voucherAdminModel(){ return new VoucherAdminModelImpl(); }
	
	//================= User ========================//
	@Bean
	public UserAdminModel userAdminModel(){ return new UserAdminModelImpl();}
	@Bean
	public UserGroupAdminModel userGroupAdminModel(){ return new UserGroupAdminModelImpl();}
	
	
	//================= Setting ========================//
	@Bean
	public SettingAdminModel settingAdminModel(){ return new SettingAdminModelImpl(); }
	@Bean
	public StoreAdminModel storeAdminModel(){ return new StoreAdminModelImpl(); }
	
	//================= Report ========================//
	@Bean
	public SaleReportAdminModel saleReportAdminModel(){ return new SaleReportAdminModelImpl(); }
	@Bean
	public ProductReportAdminModel productReportAdminModel(){ return new ProductReportAdminModelImpl(); }
	@Bean
	public CustomerReportAdminModel customerReportAdminModel(){ return new CustomerReportAdminModelImpl(); }
	
	/*
	 * We can switch to 
	 */
//	return newModel(UserGroupModel.class, "com.siberhus.jpoweredcart.admin.model.user."+databaseName()+".UserGroupModelImpl");
	
	
}





