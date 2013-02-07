package org.jpoweredcart.admin.model.localisation;

import java.util.List;

import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.localisation.StockStatuses;
import org.jpoweredcart.common.entity.localisation.StockStatuses.StockStatus;

public interface StockStatusAdminModel {

	public void create(StockStatus... stockStatuses);
	
	public void update(StockStatus... stockStatuses);
	
	public void delete(Integer stockStatusId);
	
	public StockStatus get(Integer stockStatusId);
	
	public List<StockStatus> getStockStatuses(PageParam pageParam);
	
	public StockStatuses getStockStatuses(Integer stockStatusId);
	
	public int getTotal();
	
}
