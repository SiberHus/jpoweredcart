package com.jpoweredcart.admin.model.localisation;

import java.util.List;

import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.localisation.ReturnStatuses;
import com.jpoweredcart.common.entity.localisation.ReturnStatuses.ReturnStatus;

public interface ReturnStatusAdminModel {

	public void create(ReturnStatus... returnStatuses);
	
	public void update(ReturnStatus... returnStatuses);
	
	public void delete(Integer returnStatusId);
	
	public ReturnStatus get(Integer returnStatusId);
	
	public List<ReturnStatus> getList(PageParam pageParam);
	
	public ReturnStatuses getReturnStatuses(Integer returnStatusId);
	
	public int getTotal();
	
}
