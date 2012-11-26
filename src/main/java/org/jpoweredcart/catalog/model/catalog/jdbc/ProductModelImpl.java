package org.jpoweredcart.catalog.model.catalog.jdbc;

import org.jpoweredcart.catalog.model.catalog.ProductModel;
import org.jpoweredcart.common.BaseModel;
import org.jpoweredcart.common.service.ConfigService;
import org.springframework.jdbc.core.JdbcOperations;

public class ProductModelImpl extends BaseModel implements ProductModel {

	public ProductModelImpl(ConfigService configService, JdbcOperations jdbcOperations){
		super(configService, jdbcOperations);
	}
	
	
}
