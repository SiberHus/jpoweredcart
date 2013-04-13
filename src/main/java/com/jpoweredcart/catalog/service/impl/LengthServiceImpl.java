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

import com.jpoweredcart.catalog.service.LengthService;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.entity.localisation.LengthClass;
import com.jpoweredcart.common.system.ScheduledDataUpdate;

public class LengthServiceImpl extends BaseModel implements LengthService, ScheduledDataUpdate, InitializingBean {
	
	private Map<String, LengthClass> lengthClassMap = new HashMap<String, LengthClass>();
	
	@Override
	public void updateData() {
		String sql = "SELECT * FROM "+quoteTable("length_class")+" mc LEFT JOIN "+quoteTable("length_class_description")+
			" mcd ON (mc.length_class_id = mcd.length_class_id)";
		final Map<String, LengthClass> dataMap = new HashMap<String, LengthClass>();
		getJdbcOperations().query(sql, new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				LengthClass lc = new LengthClass();
				lc.setId(rs.getInt("length_class_id"));
				lc.setTitle(rs.getString("title"));
				lc.setUnit(rs.getString("unit"));
				lc.setValue(rs.getBigDecimal("value"));
				String key = rs.getInt("language_id")+"_"+lc.getId();
				dataMap.put(key, lc);
			}
		});
		this.lengthClassMap = dataMap;
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
		LengthClass fromLengthClass = lengthClassMap.get(languageId+"_"+fromClassId);
		if(fromLengthClass!=null){ 
			from = fromLengthClass.getValue(); 
		}
		BigDecimal to = BigDecimal.ZERO;
		LengthClass toLengthClass = lengthClassMap.get(languageId+"_"+toClassId);
		if(toLengthClass!=null){ 
			to = toLengthClass.getValue(); 
		}
		
		return value.multiply(to.divide(from));
	}
	
	@Override
	public String format(Number value, Integer languageId, Integer classId, Locale locale) {
		
		return NumberFormat.getInstance(locale).format(value)
			+" "+getUnit(languageId, classId);
	}

	@Override
	public String getUnit(Integer languageId, Integer classId) {
		LengthClass lengthClass = lengthClassMap.get(languageId+"_"+classId);
		if(lengthClass!=null){
			return lengthClass.getUnit();
		}
		return "";
	}
	
}
