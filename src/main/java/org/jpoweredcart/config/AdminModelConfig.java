package org.jpoweredcart.config;

import javax.inject.Inject;

import org.jpoweredcart.admin.model.catalog.CategoryAdminModel;
import org.jpoweredcart.admin.model.catalog.InformationAdminModel;
import org.jpoweredcart.admin.model.catalog.ProductAdminModel;
import org.jpoweredcart.admin.model.catalog.jdbc.CategoryAdminModelImpl;
import org.jpoweredcart.admin.model.catalog.jdbc.InformationAdminModelImpl;
import org.jpoweredcart.admin.model.catalog.jdbc.ProductAdminModelImpl;
import org.jpoweredcart.admin.model.design.BannerAdminModel;
import org.jpoweredcart.admin.model.design.LayoutAdminModel;
import org.jpoweredcart.admin.model.design.jdbc.BannerAdminModelImpl;
import org.jpoweredcart.admin.model.design.jdbc.LayoutAdminModelImpl;
import org.jpoweredcart.admin.model.localisation.CountryAdminModel;
import org.jpoweredcart.admin.model.localisation.CurrencyAdminModel;
import org.jpoweredcart.admin.model.localisation.GeoZoneAdminModel;
import org.jpoweredcart.admin.model.localisation.LanguageAdminModel;
import org.jpoweredcart.admin.model.localisation.LengthClassAdminModel;
import org.jpoweredcart.admin.model.localisation.OrderStatusAdminModel;
import org.jpoweredcart.admin.model.localisation.ReturnActionAdminModel;
import org.jpoweredcart.admin.model.localisation.ReturnReasonAdminModel;
import org.jpoweredcart.admin.model.localisation.ReturnStatusAdminModel;
import org.jpoweredcart.admin.model.localisation.StockStatusAdminModel;
import org.jpoweredcart.admin.model.localisation.TaxClassAdminModel;
import org.jpoweredcart.admin.model.localisation.TaxRateAdminModel;
import org.jpoweredcart.admin.model.localisation.WeightClassAdminModel;
import org.jpoweredcart.admin.model.localisation.ZoneAdminModel;
import org.jpoweredcart.admin.model.localisation.jdbc.CountryAdminModelImpl;
import org.jpoweredcart.admin.model.localisation.jdbc.CurrencyAdminModelImpl;
import org.jpoweredcart.admin.model.localisation.jdbc.GeoZoneAdminModelImpl;
import org.jpoweredcart.admin.model.localisation.jdbc.LanguageAdminModelImpl;
import org.jpoweredcart.admin.model.localisation.jdbc.LengthClassAdminModelImpl;
import org.jpoweredcart.admin.model.localisation.jdbc.OrderStatusAdminModelImpl;
import org.jpoweredcart.admin.model.localisation.jdbc.ReturnActionAdminModelImpl;
import org.jpoweredcart.admin.model.localisation.jdbc.ReturnReasonAdminModelImpl;
import org.jpoweredcart.admin.model.localisation.jdbc.ReturnStatusAdminModelImpl;
import org.jpoweredcart.admin.model.localisation.jdbc.StockStatusAdminModelImpl;
import org.jpoweredcart.admin.model.localisation.jdbc.TaxClassAdminModelImpl;
import org.jpoweredcart.admin.model.localisation.jdbc.TaxRateAdminModelImpl;
import org.jpoweredcart.admin.model.localisation.jdbc.WeightClassAdminModelImpl;
import org.jpoweredcart.admin.model.localisation.jdbc.ZoneAdminModelImpl;
import org.jpoweredcart.admin.model.sale.CustomerGroupAdminModel;
import org.jpoweredcart.admin.model.sale.IpBlacklistAdminModel;
import org.jpoweredcart.admin.model.sale.OrderAdminModel;
import org.jpoweredcart.admin.model.sale.VoucherAdminModel;
import org.jpoweredcart.admin.model.sale.jdbc.CustomerGroupAdminModelImpl;
import org.jpoweredcart.admin.model.sale.jdbc.IpBlacklistAdminModelImpl;
import org.jpoweredcart.admin.model.sale.jdbc.OrderAdminModelImpl;
import org.jpoweredcart.admin.model.sale.jdbc.VoucherAdminModelImpl;
import org.jpoweredcart.admin.model.setting.SettingAdminModel;
import org.jpoweredcart.admin.model.setting.StoreAdminModel;
import org.jpoweredcart.admin.model.setting.jdbc.SettingAdminModelImpl;
import org.jpoweredcart.admin.model.setting.jdbc.StoreAdminModelImpl;
import org.jpoweredcart.admin.model.user.UserAdminModel;
import org.jpoweredcart.admin.model.user.UserGroupAdminModel;
import org.jpoweredcart.admin.model.user.jdbc.UserAdminModelImpl;
import org.jpoweredcart.admin.model.user.jdbc.UserGroupAdminModelImpl;
import org.jpoweredcart.common.service.EmailService;
import org.jpoweredcart.common.service.SettingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;


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
	public CategoryAdminModel categoryAdminModel(){ return new CategoryAdminModelImpl(settingService, jdbcOperations); }
	@Bean
	public ProductAdminModel productAdminModel(){ return new ProductAdminModelImpl(settingService, jdbcOperations); }
	@Bean
	public InformationAdminModel informationAdminModel(){ return new InformationAdminModelImpl(settingService, jdbcOperations); }
	
	//================= Design ========================//
	@Bean
	public BannerAdminModel bannerAdminModel(){ return new BannerAdminModelImpl(settingService, jdbcOperations); }
	@Bean
	public LayoutAdminModel layoutAdminModel(){ return new LayoutAdminModelImpl(settingService, jdbcOperations); }
	
	//================= Locaisation ========================//
	@Bean
	public LanguageAdminModel languageAdminModel(){ return new LanguageAdminModelImpl(settingService, jdbcOperations);}
	@Bean
	public CurrencyAdminModel currencyAdminModel(){ return new CurrencyAdminModelImpl(settingService, jdbcOperations);}
	@Bean
	public CountryAdminModel countryAdminModel() { return new CountryAdminModelImpl(settingService, jdbcOperations); }
	@Bean
	public ZoneAdminModel zoneAdminModel() { return new ZoneAdminModelImpl(settingService, jdbcOperations); }
	@Bean
	public GeoZoneAdminModel geoZoneAdminModel(){ return new GeoZoneAdminModelImpl(settingService, jdbcOperations); }
	@Bean
	public StockStatusAdminModel stockStatusAdminModel(){ return new StockStatusAdminModelImpl(settingService, jdbcOperations); }
	@Bean
	public OrderStatusAdminModel orderStatusAdminModel(){ return new OrderStatusAdminModelImpl(settingService, jdbcOperations); }
	@Bean
	public ReturnStatusAdminModel returnStatusAdminModel(){ return new ReturnStatusAdminModelImpl(settingService, jdbcOperations); }
	@Bean
	public ReturnActionAdminModel returnActionAdminModel(){ return new ReturnActionAdminModelImpl(settingService, jdbcOperations); }
	@Bean
	public ReturnReasonAdminModel returnReasonAdminModel(){ return new ReturnReasonAdminModelImpl(settingService, jdbcOperations); }
	@Bean
	public TaxClassAdminModel taxClassAdminModel(){ return new TaxClassAdminModelImpl(settingService, jdbcOperations); }
	@Bean
	public TaxRateAdminModel taxRateAdminModel(){ return new TaxRateAdminModelImpl(settingService, jdbcOperations); }
	@Bean
	public WeightClassAdminModel weightClassAdminModel(){ return new WeightClassAdminModelImpl(settingService, jdbcOperations); }
	@Bean
	public LengthClassAdminModel lengthClassAdminModel(){ return new LengthClassAdminModelImpl(settingService, jdbcOperations); }
	
	//================= Sale ========================//
	@Bean
	public OrderAdminModel orderAdminModel(){ return new OrderAdminModelImpl(settingService, jdbcOperations); }
	@Bean
	public CustomerGroupAdminModel customerGroupAdminModel(){ return new CustomerGroupAdminModelImpl(settingService, jdbcOperations); }
	@Bean
	public IpBlacklistAdminModel ipBlacklistAdminModel(){ return new IpBlacklistAdminModelImpl(settingService, jdbcOperations); }
	@Bean
	public VoucherAdminModel voucherAdminModel(){ 
		VoucherAdminModelImpl voucherAdminModel = new VoucherAdminModelImpl(settingService, jdbcOperations);
		voucherAdminModel.setEmailService(emailService);
		return voucherAdminModel;
	}
	//================= User ========================//
	@Bean
	public UserAdminModel userAdminModel(){ return new UserAdminModelImpl(settingService, jdbcOperations);}
	@Bean
	public UserGroupAdminModel userGroupAdminModel(){ return new UserGroupAdminModelImpl(settingService, jdbcOperations);}
	
	
	//================= Setting ========================//
	@Bean
	public SettingAdminModel settingAdminModel(){ return new SettingAdminModelImpl(settingService, jdbcOperations); }
	@Bean
	public StoreAdminModel storeAdminModel(){ return new StoreAdminModelImpl(settingService, jdbcOperations); }
	
	/*
	 * We can switch to 
	 */
//	return newModel(UserGroupModel.class, "com.siberhus.jpoweredcart.admin.model.user."+databaseName()+".UserGroupModelImpl");
	
	
}





