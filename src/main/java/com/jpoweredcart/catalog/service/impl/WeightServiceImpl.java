package com.jpoweredcart.catalog.service.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.jpoweredcart.catalog.service.WeightService;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.entity.localisation.WeightClass;
import com.jpoweredcart.common.system.ScheduledDataUpdate;

public class WeightServiceImpl extends BaseModel implements WeightService, ScheduledDataUpdate, InitializingBean {
	
	private Map<String, WeightClass> weightClassMap = new HashMap<String, WeightClass>();
	
	@Override
	public void updateData() {
		String sql = "SELECT * FROM "+quoteTable("weight_class")+" wc LEFT JOIN "+quoteTable("weight_class_description")+
			" wcd ON (wc.weight_class_id = wcd.weight_class_id)";
		final Map<String, WeightClass> dataMap = new HashMap<String, WeightClass>();
		getJdbcOperations().query(sql, new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				WeightClass lc = new WeightClass();
				lc.setId(rs.getInt("weight_class_id"));
				lc.setTitle(rs.getString("title"));
				lc.setUnit(rs.getString("unit"));
				lc.setValue(rs.getBigDecimal("value"));
				String key = rs.getInt("language_id")+"_"+lc.getId();
				dataMap.put(key, lc);
			}
		});
		this.weightClassMap = dataMap;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		updateData();
	}
	
	@Override
	public BigDecimal convert(BigDecimal value, Integer languageId, Integer fromClassId,
			Integer toClassId) {
		
		if(ObjectUtils.equals(fromClassId, toClassId)){
			return value;
		}
		
		BigDecimal from = BigDecimal.ZERO;
		WeightClass fromWeightClass = weightClassMap.get(languageId+"_"+fromClassId);
		if(fromWeightClass!=null){ 
			from = fromWeightClass.getValue(); 
		}
		BigDecimal to = BigDecimal.ZERO;
		WeightClass toWeightClass = weightClassMap.get(languageId+"_"+toClassId);
		if(toWeightClass!=null){ 
			to = toWeightClass.getValue(); 
		}
		
		return value.multiply(to.divide(from));
	}
	
	@Override
	public String format(BigDecimal value, Integer languageId, Integer classId, Locale locale) {
		
		return NumberFormat.getInstance(locale).format(value)
			+" "+getUnit(languageId, classId);
	}
	
	@Override
	public String getUnit(Integer languageId, Integer classId) {
		WeightClass lengthClass = weightClassMap.get(languageId+"_"+classId);
		if(lengthClass!=null){
			return lengthClass.getUnit();
		}
		return "";
	}
	
}
