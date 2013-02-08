package com.jpoweredcart.admin.model.sale;

import java.util.List;

import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.sale.IpBlacklist;

public interface IpBlacklistAdminModel {
	
	public void create(IpBlacklist blacklist);
	
	public void update(IpBlacklist blacklist);
	
	public void delete(Integer blacklistId);
	
	public IpBlacklist get(Integer blacklistId);
	
	public List<IpBlacklist> getList(PageParam pageParam);
	
	public int getTotal();
	
}