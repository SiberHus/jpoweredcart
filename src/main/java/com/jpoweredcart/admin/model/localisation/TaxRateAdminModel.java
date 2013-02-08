package com.jpoweredcart.admin.model.localisation;

import java.util.List;

import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.localisation.TaxRate;

public interface TaxRateAdminModel {
	
	public void create(TaxRate taxRate);
	
	public void update(TaxRate taxRate);
	
	public void delete(Integer taxRateId);
	
	public TaxRate get(Integer taxRateId);
	
	public List<TaxRate> getList(PageParam pageParam);
	
	public int getTotal();
	
}
