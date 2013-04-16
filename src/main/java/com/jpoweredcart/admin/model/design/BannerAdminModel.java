package com.jpoweredcart.admin.model.design;

import java.util.List;

import com.jpoweredcart.admin.form.design.BannerForm;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.design.Banner;
import com.jpoweredcart.common.entity.design.BannerImage;

public interface BannerAdminModel {

	public void create(BannerForm bannerForm);
	
	public void update(BannerForm bannerForm);
	
	public void delete(Integer bannerId);
	
	public BannerForm newForm();
	
	public BannerForm getForm(Integer layoutId);
	
	public Banner get(Integer bannerId, Class<? extends Banner> clazz);
	
	public List<Banner> getList(PageParam pageParam);
	
	public List<BannerImage> getBannerImages(Integer bannerId);
	
	public int getTotal();
	
}
