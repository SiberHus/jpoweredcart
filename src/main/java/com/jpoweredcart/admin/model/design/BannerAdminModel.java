package org.jpoweredcart.admin.model.design;

import java.util.List;

import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.design.Banner;
import org.jpoweredcart.common.entity.design.BannerImage;

public interface BannerAdminModel {

	public void create(Banner banner);
	
	public void update(Banner banner);
	
	public void delete(Integer bannerId);
	
	public Banner get(Integer bannerId);
	
	public List<Banner> getList(PageParam pageParam);
	
	public List<BannerImage> getBannerImages(Integer bannerId);
	
	public int getTotal();
	
}
