package org.jpoweredcart.admin.model.design;

import java.util.List;

import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.design.Banner;
import org.jpoweredcart.common.entity.design.BannerImage;

public interface BannerAdminModel {

	public void addBanner(Banner banner);
	
	public void updateBanner(Banner banner);
	
	public void deleteBanner(Integer bannerId);
	
	public Banner getBanner(Integer bannerId);
	
	public List<Banner> getBanners(PageParam pageParam);
	
	public List<BannerImage> getBannerImages(Integer bannerId);
	
	public int getTotalBanners();
	
}
