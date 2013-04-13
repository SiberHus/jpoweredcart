package com.jpoweredcart.common.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.service.StoreResolver;
import com.jpoweredcart.common.system.ScheduledDataUpdate;
import com.jpoweredcart.common.system.setting.DefaultSettings;

public class StoreResolverImpl extends BaseModel implements StoreResolver,
	ScheduledDataUpdate, InitializingBean {
	
	private Map<String, Integer> storeMap = new HashMap<String, Integer>();
	
	@Override
	public void updateData() {
		final Map<String, Integer> dataMap = new HashMap<String, Integer>();
		String http = getEnvironment().getProperty("app.http");
		String https = getEnvironment().getProperty("app.https");
		dataMap.put(http, 0);
		dataMap.put(https, 0);
		
		String sql = "SELECT store_id, url, "+quoteName("ssl")+" FROM "+quoteTable("store");
		
		getJdbcOperations().query(sql, new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				Integer storeId = rs.getInt("store_id");
				dataMap.put(rs.getString("url"), storeId);
				dataMap.put(rs.getString("ssl"), storeId);
			}
		});
		this.storeMap = dataMap;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		updateData();
	}
	
	@Override
	public Integer resolve(String url) {
		for(Entry<String, Integer> entry: storeMap.entrySet()){
			if(url.startsWith(entry.getKey())){
				return entry.getValue();
			}
		}
		return DefaultSettings.STORE_ID;
	}
	
	
}
