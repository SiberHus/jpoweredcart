package org.jpoweredcart.admin.model.localisation;

import java.util.List;

import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.localisation.ReturnStatuses;
import org.jpoweredcart.common.entity.localisation.ReturnStatuses.ReturnStatus;

public interface ReturnStatusAdminModel {

	public void addReturnStatus(ReturnStatus... returnStatuses);
	
	public void updateReturnStatus(ReturnStatus... returnStatuses);
	
	public void deleteReturnStatus(Integer returnStatusId);
	
	public ReturnStatus getReturnStatus(Integer returnStatusId);
	
	public List<ReturnStatus> getReturnStatuses(PageParam pageParam);
	
	public ReturnStatuses getReturnStatuses(Integer returnStatusId);
	
	public int getTotalReturnStatuses();
	
}
