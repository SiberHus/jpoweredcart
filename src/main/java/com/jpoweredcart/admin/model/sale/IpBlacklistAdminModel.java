package com.jpoweredcart.admin.model.sale;

import java.util.List;

import com.jpoweredcart.admin.form.sale.IpBlacklistForm;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.sale.IpBlacklist;

public interface IpBlacklistAdminModel {
	
	public void create(IpBlacklistForm blacklistForm);
	
	public void update(IpBlacklistForm blacklistForm);
	
	public void delete(Integer blacklistId);
	
	public IpBlacklistForm newForm();
	
	public IpBlacklistForm getForm(Integer blacklistId);
	
	public IpBlacklist get(Integer blacklistId, Class<? extends IpBlacklist> clazz);
	
	public List<IpBlacklist> getList(PageParam pageParam);
	
	public int getTotal();
	
}
