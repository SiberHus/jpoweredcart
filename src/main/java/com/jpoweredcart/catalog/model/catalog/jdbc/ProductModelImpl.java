package com.jpoweredcart.catalog.model.catalog.jdbc;

import com.jpoweredcart.catalog.model.catalog.ProductModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.service.SettingService;
import org.springframework.jdbc.core.JdbcOperations;

public class ProductModelImpl extends BaseModel implements ProductModel {

	public ProductModelImpl(SettingService configService, JdbcOperations jdbcOperations){
		super(configService, jdbcOperations);
	}
	
	
}
