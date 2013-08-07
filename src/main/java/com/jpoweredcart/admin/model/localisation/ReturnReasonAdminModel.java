package com.jpoweredcart.admin.model.localisation;

import java.util.List;

import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.localisation.ReturnReasons;
import com.jpoweredcart.common.entity.localisation.ReturnReasons.ReturnReason;

public interface ReturnReasonAdminModel {

	public void create(ReturnReason... returnReasons);
	
	public void update(ReturnReason... returnReasons);
	
	public void delete(Integer returnReasonId);
	
	public ReturnReason get(Integer returnReasonId);
	
	public List<ReturnReason> getList(PageParam pageParam);
	
	public ReturnReasons getReturnReasons(Integer returnReasonId);
	
	public int getTotal();
	
}
