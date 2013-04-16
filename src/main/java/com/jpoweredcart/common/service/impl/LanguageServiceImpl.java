package com.jpoweredcart.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.entity.Status;
import com.jpoweredcart.common.entity.localisation.Language;
import com.jpoweredcart.common.entity.localisation.jdbc.LanguageRowMapper;
import com.jpoweredcart.common.service.LanguageService;
import com.jpoweredcart.common.system.ScheduledDataUpdate;

public class LanguageServiceImpl extends BaseModel implements LanguageService, ScheduledDataUpdate, InitializingBean {
	
	private Map<Integer, Language> languageMap; 
	
	@Override
	public void afterPropertiesSet() throws Exception {
		updateData();
	}
	
	@Override
	public void updateData() {
		String sql = "SELECT * FROM "+quoteTable("language")+" WHERE status=?";
		Map<Integer, Language> dataMap = new HashMap<Integer, Language>();
		List<Language> languageList = getJdbcOperations().query(sql, new Object[]{Status.ENABLED}, new LanguageRowMapper());
		for(Language language: languageList){
			dataMap.put(language.getId(), language);
		}
		this.languageMap = dataMap;
	}
	
	@Override
	public Language getByCode(String code) {
		for(Language lang: languageMap.values()){
			if(StringUtils.equals(lang.getCode(), code)){
				return lang;
			}
		}
		return null;
	}
	
	@Override
	public Language getById(Integer id) {
		return languageMap.get(id);
	}

	
	
}
