package org.jpoweredcart.admin.entity.design;

import java.io.Serializable;

public class BannerImageDesc implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Integer bannerImageId;
	
	protected Integer languageId;
	
	protected Integer bannerId;
	
	protected String title;

	public Integer getBannerImageId() {
		return bannerImageId;
	}

	public void setBannerImageId(Integer bannerImageId) {
		this.bannerImageId = bannerImageId;
	}

	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}

	public Integer getBannerId() {
		return bannerId;
	}

	public void setBannerId(Integer bannerId) {
		this.bannerId = bannerId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
