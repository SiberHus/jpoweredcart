package org.jpoweredcart.config;

import javax.inject.Inject;

import org.jpoweredcart.catalog.model.catalog.jdbc.ProductModelImpl;
import org.jpoweredcart.catalog.model.catalog.ProductModel;
import org.jpoweredcart.common.service.SettingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;

@Configuration
public class CatalogModelConfig {
	
	@Inject
	private SettingService settingService;
	
	@Inject
	private JdbcOperations jdbcOperations;
	
	//================= Catalog ========================//
	
	@Bean
	public ProductModel productModel(){ return new ProductModelImpl(settingService, jdbcOperations); }
	
	
}
