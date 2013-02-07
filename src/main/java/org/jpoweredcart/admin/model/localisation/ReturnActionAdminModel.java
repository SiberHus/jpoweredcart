package org.jpoweredcart.admin.model.localisation;

import java.util.List;

import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.localisation.ReturnActions;
import org.jpoweredcart.common.entity.localisation.ReturnActions.ReturnAction;

public interface ReturnActionAdminModel {

	public void create(ReturnAction... returnActions);
	
	public void update(ReturnAction... returnActions);
	
	public void delete(Integer returnActionId);
	
	public ReturnAction get(Integer returnActionId);
	
	public List<ReturnAction> getList(PageParam pageParam);
	
	public ReturnActions getReturnActions(Integer returnActionId);
	
	public int getTotal();
	
}
