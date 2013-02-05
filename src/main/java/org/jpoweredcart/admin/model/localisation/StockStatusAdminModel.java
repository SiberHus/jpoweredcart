package org.jpoweredcart.admin.model.localisation;

import java.util.List;

import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.localisation.StockStatuses;
import org.jpoweredcart.common.entity.localisation.StockStatuses.StockStatus;

public interface StockStatusAdminModel {

	public void addStockStatus(StockStatus... stockStatuses);
	
	public void updateStockStatus(StockStatus... stockStatuses);
	
	public void deleteStockStatus(Integer stockStatusId);
	
	public StockStatus getStockStatus(Integer stockStatusId);
	
	public List<StockStatus> getStockStatuses(PageParam pageParam);
	
	public StockStatuses getStockStatuses(Integer stockStatusId);
	
	public int getTotalStockStatuses();
	
}
