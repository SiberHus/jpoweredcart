package org.jpoweredcart.admin.model.localisation;

import java.util.List;

import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.localisation.ReturnReasons;
import org.jpoweredcart.common.entity.localisation.ReturnReasons.ReturnReason;

public interface ReturnReasonAdminModel {

	public void addReturnReason(ReturnReason... returnReasons);
	
	public void updateReturnReason(ReturnReason... returnReasons);
	
	public void deleteReturnReason(Integer returnReasonId);
	
	public ReturnReason getReturnReason(Integer returnReasonId);
	
	public List<ReturnReason> getReturnReasons(PageParam pageParam);
	
	public ReturnReasons getReturnReasons(Integer returnReasonId);
	
	public int getTotalReturnReasons();
	
}
