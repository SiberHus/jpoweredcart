package org.jpoweredcart.admin.model.localisation;

import java.util.List;

import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.localisation.TaxClass;

public interface TaxClassAdminModel {

	
	public void addTaxClass(TaxClass taxClass);
	
	public void updateTaxClass(TaxClass taxClass);
	
	public void saveTaxClass(TaxClass taxClass);
	
	public void deleteTaxClass(Integer taxClassId);
	
	public TaxClass getTaxClass(Integer taxClassId);
	
	public List<TaxClass> getTaxClasses(PageParam pageParam);
	
	public int getTotalTaxClasses();
	
}
