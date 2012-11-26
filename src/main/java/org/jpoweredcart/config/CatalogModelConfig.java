package org.jpoweredcart.config;

import javax.inject.Inject;

import org.jpoweredcart.catalog.model.catalog.jdbc.ProductModelImpl;
import org.jpoweredcart.catalog.model.catalog.ProductModel;
import org.jpoweredcart.common.service.ConfigService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;

@Configuration
public class CatalogModelConfig {
	
	@Inject
	private ConfigService configService;
	
	@Inject
	private JdbcOperations jdbcOperations;
	
	//================= Catalog ========================//
	
	@Bean
	public ProductModel productModel(){ return new ProductModelImpl(configService, jdbcOperations); }
	
	
}
