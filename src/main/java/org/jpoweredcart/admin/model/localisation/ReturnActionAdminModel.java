package org.jpoweredcart.admin.model.localisation;

import java.util.List;

import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.localisation.ReturnActions;
import org.jpoweredcart.common.entity.localisation.ReturnActions.ReturnAction;

public interface ReturnActionAdminModel {

	public void addReturnAction(ReturnAction... returnActions);
	
	public void updateReturnAction(ReturnAction... returnActions);
	
	public void deleteReturnAction(Integer returnActionId);
	
	public ReturnAction getReturnAction(Integer returnActionId);
	
	public List<ReturnAction> getReturnActions(PageParam pageParam);
	
	public ReturnActions getReturnActions(Integer returnActionId);
	
	public int getTotalReturnActions();
	
}
