package com.jpoweredcart.admin.model.localisation;

import java.util.List;

import com.jpoweredcart.admin.bean.localisation.LanguageForm;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.localisation.Language;


public interface LanguageAdminModel {
	
	public void create(LanguageForm lang);
	
	public void update(LanguageForm lang);
	
	public void delete(Integer langId);
	
	public LanguageForm newForm();
	
	public LanguageForm getForm(Integer langId);
	
	public Language get(Integer langId);
	
	public List<Language> getList(PageParam pageParam);
	
	public int getTotal();
	
	public <T>List<T> createDescriptionList(Class<T> descClass);
	
}
