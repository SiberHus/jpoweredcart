package org.jpoweredcart.admin.model.localisation;

import java.util.List;

import org.jpoweredcart.admin.entity.localisation.ReturnActions;
import org.jpoweredcart.admin.entity.localisation.ReturnActions.ReturnAction;
import org.jpoweredcart.common.PageParam;

public interface ReturnActionAdminModel {

	public void addReturnAction(ReturnAction... returnActions);
	
	public void updateReturnAction(ReturnAction... returnActions);
	
	public void deleteReturnAction(Integer returnActionId);
	
	public ReturnAction getReturnAction(Integer returnActionId);
	
	public List<ReturnAction> getReturnActions(PageParam pageParam);
	
	public ReturnActions getReturnActions(Integer returnActionId);
	
	public int getTotalReturnActions();
	
}
