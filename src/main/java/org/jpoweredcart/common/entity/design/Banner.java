package org.jpoweredcart.common.entity.design;

import java.io.Serializable;
import java.util.List;

import org.springframework.util.AutoPopulatingList;

public class Banner implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	protected String name;

	protected int status;
	
	protected List<BannerImage> images = new AutoPopulatingList<BannerImage>(BannerImage.class);
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<BannerImage> getImages() {
		return images;
	}

	public void setImages(List<BannerImage> images) {
		this.images = new AutoPopulatingList<BannerImage>(images, BannerImage.class);
	}
	
}
