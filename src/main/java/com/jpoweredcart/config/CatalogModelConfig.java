package com.jpoweredcart.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;

import com.jpoweredcart.catalog.model.catalog.ProductModel;
import com.jpoweredcart.catalog.model.catalog.jdbc.ProductModelImpl;
import com.jpoweredcart.common.service.SettingService;

@Configuration
public class CatalogModelConfig {
	
	@Inject
	private SettingService settingService;
	
	@Inject
	private JdbcOperations jdbcOperations;
	
	//================= Catalog ========================//
	
	@Bean
	public ProductModel productModel(){ return new ProductModelImpl(); }
	
	
}
