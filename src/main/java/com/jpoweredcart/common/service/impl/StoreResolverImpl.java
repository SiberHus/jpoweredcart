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
	
	private Map<Integer, String> storeMap = new HashMap<Integer, String>();
	
	@Override
	public void updateData() {
		final Map<Integer, String> dataMap = new HashMap<Integer, String>();
		String http = getEnvironment().getProperty("app.http");
		String https = getEnvironment().getProperty("app.https");
		dataMap.put(0, http);
		dataMap.put(0, https);
		
		String sql = "SELECT store_id, url FROM "+quoteTable("store");
		
		getJdbcOperations().query(sql, new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				Integer storeId = rs.getInt("store_id");
				String url = rs.getString("url");
				int pos = url.indexOf("://");
				if(pos!=-1){
					url = url.substring(pos+3);
				}
				dataMap.put(storeId, url);
			}
		});
		this.storeMap = dataMap;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		updateData();
	}
	
	@Override
	public Integer getStoreId(String url) {
		int pos = url.indexOf("://");
		if(pos!=-1){
			url = url.substring(pos+3);
		}
		for(Entry<Integer, String> entry: storeMap.entrySet()){
			if(url.startsWith(entry.getValue())){
				return entry.getKey();
			}
		}
		return DefaultSettings.STORE_ID;
	}

	@Override
	public String getStoreUrl(Integer storeId, boolean secured) {
		String url = storeMap.get(storeId);
		if(url!=null){
			String protocol = secured?"https://":"http://";
			url = protocol + url;
		}
		return url;
	}
	
	
}
