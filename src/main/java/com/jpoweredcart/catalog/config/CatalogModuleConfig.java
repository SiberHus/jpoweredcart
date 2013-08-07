package com.jpoweredcart.catalog.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;

import com.jpoweredcart.catalog.model.catalog.ProductModel;
import com.jpoweredcart.catalog.model.catalog.jdbc.ProductModelImpl;
import com.jpoweredcart.catalog.service.LengthService;
import com.jpoweredcart.catalog.service.WeightService;
import com.jpoweredcart.catalog.service.impl.LengthServiceImpl;
import com.jpoweredcart.catalog.service.impl.WeightServiceImpl;
import com.jpoweredcart.common.system.setting.SettingService;

@Configuration
public class CatalogModuleConfig {
	
	@Inject
	private SettingService settingService;
	
	@Inject
	private JdbcOperations jdbcOperations;
	
	//================= Services ========================//
	@Bean
	public LengthService lengthService() { return new LengthServiceImpl(); }
	@Bean
	public WeightService weightService() { return new WeightServiceImpl(); }
	
	//================= Catalog ========================//
	
	@Bean
	public ProductModel productModel(){ return new ProductModelImpl(); }
	
	
}
