package com.jpoweredcart.admin.model.localisation;

import java.util.List;

import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.localisation.Language;


public interface LanguageAdminModel {
	
	public void create(Language lang);
	
	public void update(Language lang);
	
	public void delete(Integer langId);
	
	public Language get(Integer langId);
	
	public List<Language> getList(PageParam pageParam);
	
	public int getTotal();
	
	public <T>List<T> createDescriptionList(Class<T> descClass);
	
}
