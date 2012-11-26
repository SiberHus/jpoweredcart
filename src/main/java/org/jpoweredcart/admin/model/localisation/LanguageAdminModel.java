package org.jpoweredcart.admin.model.localisation;

import java.util.List;

import org.jpoweredcart.admin.entity.localisation.Language;
import org.jpoweredcart.common.PageParam;


public interface LanguageAdminModel {
	
	public void addLanguage(Language lang);
	
	public void updateLanguage(Language lang);
	
	public void saveLanguage(Language lang);
	
	public void deleteLanguage(Integer langId);
	
	public Language getLanguage(Integer langId);
	
	public List<Language> getLanguages(PageParam pageParam);
	
	public int getTotalLanguages();
	
}
