package org.jpoweredcart.admin.model.localisation;

import java.util.List;

import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.localisation.TaxRate;

public interface TaxRateAdminModel {
	
	public void create(TaxRate taxRate);
	
	public void update(TaxRate taxRate);
	
	public void delete(Integer taxRateId);
	
	public TaxRate get(Integer taxRateId);
	
	public List<TaxRate> getList(PageParam pageParam);
	
	public int getTotal();
	
}
