package com.jpoweredcart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jpoweredcart.admin.model.catalog.AttributeAdminModel;
import com.jpoweredcart.admin.model.catalog.AttributeGroupAdminModel;
import com.jpoweredcart.admin.model.catalog.CategoryAdminModel;
import com.jpoweredcart.admin.model.catalog.DownloadAdminModel;
import com.jpoweredcart.admin.model.catalog.InformationAdminModel;
import com.jpoweredcart.admin.model.catalog.ProductAdminModel;
import com.jpoweredcart.admin.model.catalog.ReviewAdminModel;
import com.jpoweredcart.admin.model.catalog.impl.AttributeAdminModelImpl;
import com.jpoweredcart.admin.model.catalog.impl.AttributeGroupAdminModelImpl;
import com.jpoweredcart.admin.model.catalog.impl.CategoryAdminModelImpl;
import com.jpoweredcart.admin.model.catalog.impl.DownloadAdminModelImpl;
import com.jpoweredcart.admin.model.catalog.impl.InformationAdminModelImpl;
import com.jpoweredcart.admin.model.catalog.impl.ProductAdminModelImpl;
import com.jpoweredcart.admin.model.catalog.impl.ReviewAdminModelImpl;
import com.jpoweredcart.admin.model.design.BannerAdminModel;
import com.jpoweredcart.admin.model.design.LayoutAdminModel;
import com.jpoweredcart.admin.model.design.impl.BannerAdminModelImpl;
import com.jpoweredcart.admin.model.design.impl.LayoutAdminModelImpl;
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
import com.jpoweredcart.admin.model.localisation.impl.CountryAdminModelImpl;
import com.jpoweredcart.admin.model.localisation.impl.CurrencyAdminModelImpl;
import com.jpoweredcart.admin.model.localisation.impl.GeoZoneAdminModelImpl;
import com.jpoweredcart.admin.model.localisation.impl.LanguageAdminModelImpl;
import com.jpoweredcart.admin.model.localisation.impl.LengthClassAdminModelImpl;
import com.jpoweredcart.admin.model.localisation.impl.OrderStatusAdminModelImpl;
import com.jpoweredcart.admin.model.localisation.impl.ReturnActionAdminModelImpl;
import com.jpoweredcart.admin.model.localisation.impl.ReturnReasonAdminModelImpl;
import com.jpoweredcart.admin.model.localisation.impl.ReturnStatusAdminModelImpl;
import com.jpoweredcart.admin.model.localisation.impl.StockStatusAdminModelImpl;
import com.jpoweredcart.admin.model.localisation.impl.TaxClassAdminModelImpl;
import com.jpoweredcart.admin.model.localisation.impl.TaxRateAdminModelImpl;
import com.jpoweredcart.admin.model.localisation.impl.WeightClassAdminModelImpl;
import com.jpoweredcart.admin.model.localisation.impl.ZoneAdminModelImpl;
import com.jpoweredcart.admin.model.report.AffiliateReportAdminModel;
import com.jpoweredcart.admin.model.report.CustomerReportAdminModel;
import com.jpoweredcart.admin.model.report.ProductReportAdminModel;
import com.jpoweredcart.admin.model.report.SaleReportAdminModel;
import com.jpoweredcart.admin.model.report.impl.AffiliateReportAdminModelImpl;
import com.jpoweredcart.admin.model.report.impl.CustomerReportAdminModelImpl;
import com.jpoweredcart.admin.model.report.impl.ProductReportAdminModelImpl;
import com.jpoweredcart.admin.model.report.impl.SaleReportAdminModelImpl;
import com.jpoweredcart.admin.model.sale.AffiliateAdminModel;
import com.jpoweredcart.admin.model.sale.ContactAdminModel;
import com.jpoweredcart.admin.model.sale.CustomerAdminModel;
import com.jpoweredcart.admin.model.sale.CustomerGroupAdminModel;
import com.jpoweredcart.admin.model.sale.IpBlacklistAdminModel;
import com.jpoweredcart.admin.model.sale.OrderAdminModel;
import com.jpoweredcart.admin.model.sale.VoucherAdminModel;
import com.jpoweredcart.admin.model.sale.VoucherThemeAdminModel;
import com.jpoweredcart.admin.model.sale.impl.AffiliateAdminModelImpl;
import com.jpoweredcart.admin.model.sale.impl.ContactAdminModelImpl;
import com.jpoweredcart.admin.model.sale.impl.CustomerAdminModelImpl;
import com.jpoweredcart.admin.model.sale.impl.CustomerGroupAdminModelImpl;
import com.jpoweredcart.admin.model.sale.impl.IpBlacklistAdminModelImpl;
import com.jpoweredcart.admin.model.sale.impl.OrderAdminModelImpl;
import com.jpoweredcart.admin.model.sale.impl.VoucherAdminModelImpl;
import com.jpoweredcart.admin.model.sale.impl.VoucherThemeAdminModelImpl;
import com.jpoweredcart.admin.model.setting.SettingAdminModel;
import com.jpoweredcart.admin.model.setting.StoreAdminModel;
import com.jpoweredcart.admin.model.setting.impl.SettingAdminModelImpl;
import com.jpoweredcart.admin.model.setting.impl.StoreAdminModelImpl;
import com.jpoweredcart.admin.model.user.UserAdminModel;
import com.jpoweredcart.admin.model.user.UserGroupAdminModel;
import com.jpoweredcart.admin.model.user.impl.UserAdminModelImpl;
import com.jpoweredcart.admin.model.user.impl.UserGroupAdminModelImpl;


@Configuration
public class AdminModelConfig {
	
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
	public DownloadAdminModel downloadAdminModel(){ return new DownloadAdminModelImpl(); }
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
	public CustomerAdminModel customerAdminModel(){ return new CustomerAdminModelImpl(); }
	@Bean
	public CustomerGroupAdminModel customerGroupAdminModel(){ return new CustomerGroupAdminModelImpl(); }
	@Bean
	public IpBlacklistAdminModel ipBlacklistAdminModel(){ return new IpBlacklistAdminModelImpl(); }
	@Bean
	public AffiliateAdminModel affiliateAdminModel(){ return new AffiliateAdminModelImpl(); }
	@Bean
	public VoucherAdminModel voucherAdminModel(){ return new VoucherAdminModelImpl(); }
	@Bean
	public VoucherThemeAdminModel voucherThemeAdminModel(){ return new VoucherThemeAdminModelImpl(); }
	@Bean
	public ContactAdminModel contactAdminModel(){ return new ContactAdminModelImpl(); }
	
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
	@Bean
	public AffiliateReportAdminModel affiliateReportAdminModel(){ return new AffiliateReportAdminModelImpl(); }
	
	/*
	 * We can switch to 
	 */
//	return newModel(UserGroupModel.class, "com.siberhus.jpoweredcart.admin.model.user."+databaseName()+".UserGroupModelImpl");
	
	
}





