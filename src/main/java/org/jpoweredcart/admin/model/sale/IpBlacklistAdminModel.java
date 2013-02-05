package org.jpoweredcart.admin.model.sale;

import java.util.List;

import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.sale.IpBlacklist;

public interface IpBlacklistAdminModel {
	
	public void addIpBlacklist(IpBlacklist blacklist);
	
	public void updateIpBlacklist(IpBlacklist blacklist);

	public void saveIpBlacklist(IpBlacklist blacklist);
	
	public void deleteIpBlacklist(Integer blacklistId);
	
	public IpBlacklist getIpBlacklist(Integer blacklistId);
	
	public List<IpBlacklist> getIpBlacklists(PageParam pageParam);
	
	public int getTotalIpBlacklists();
	
}
