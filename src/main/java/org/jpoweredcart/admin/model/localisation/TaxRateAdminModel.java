package org.jpoweredcart.admin.model.localisation;

import java.util.List;

import org.jpoweredcart.admin.entity.localisation.TaxRate;
import org.jpoweredcart.common.PageParam;

public interface TaxRateAdminModel {
	
	public void addTaxRate(TaxRate taxRate);
	
	public void updateTaxRate(TaxRate taxRate);
	
	public void saveTaxRate(TaxRate taxRate);
	
	public void deleteTaxRate(Integer taxRateId);
	
	public TaxRate getTaxRate(Integer taxRateId);
	
	public List<TaxRate> getTaxRatees(PageParam pageParam);
	
	public int getTotalTaxRatees();
	
}
