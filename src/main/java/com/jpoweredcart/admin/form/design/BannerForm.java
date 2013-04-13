package com.jpoweredcart.admin.form.design;

import java.util.List;

import org.springframework.util.AutoPopulatingList;

import com.jpoweredcart.common.entity.design.Banner;
import com.jpoweredcart.common.entity.design.BannerImage;

public class BannerForm extends Banner{
	
	private static final long serialVersionUID = 1L;
	
	protected List<BannerImage> images = new AutoPopulatingList<BannerImage>(BannerImage.class);
	
	public List<BannerImage> getImages() {
		return images;
	}

	public void setImages(List<BannerImage> images) {
		this.images = new AutoPopulatingList<BannerImage>(images, BannerImage.class);
	}
	
}
