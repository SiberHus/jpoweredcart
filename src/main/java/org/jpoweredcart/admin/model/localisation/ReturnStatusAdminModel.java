package org.jpoweredcart.admin.model.localisation;

import java.util.List;

import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.localisation.ReturnStatuses;
import org.jpoweredcart.common.entity.localisation.ReturnStatuses.ReturnStatus;

public interface ReturnStatusAdminModel {

	public void create(ReturnStatus... returnStatuses);
	
	public void update(ReturnStatus... returnStatuses);
	
	public void delete(Integer returnStatusId);
	
	public ReturnStatus get(Integer returnStatusId);
	
	public List<ReturnStatus> getList(PageParam pageParam);
	
	public ReturnStatuses getReturnStatuses(Integer returnStatusId);
	
	public int getTotal();
	
}
