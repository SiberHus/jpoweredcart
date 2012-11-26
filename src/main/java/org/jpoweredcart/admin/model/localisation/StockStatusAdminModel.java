package org.jpoweredcart.admin.model.localisation;

import java.util.List;

import org.jpoweredcart.admin.entity.localisation.StockStatuses;
import org.jpoweredcart.admin.entity.localisation.StockStatuses.StockStatus;
import org.jpoweredcart.common.PageParam;

public interface StockStatusAdminModel {

	public void addStockStatus(StockStatus... stockStatuses);
	
	public void updateStockStatus(StockStatus... stockStatuses);
	
	public void deleteStockStatus(Integer stockStatusId);
	
	public StockStatus getStockStatus(Integer stockStatusId);
	
	public List<StockStatus> getStockStatuses(PageParam pageParam);
	
	public StockStatuses getStockStatuses(Integer stockStatusId);
	
	public int getTotalStockStatuses();
	
}
