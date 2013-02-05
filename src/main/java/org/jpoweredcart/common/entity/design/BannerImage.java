package org.jpoweredcart.common.entity.design;

import java.io.Serializable;
import java.util.List;

import org.springframework.util.AutoPopulatingList;

public class BannerImage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	protected Integer bannerId;
	
	protected String link;
	
	protected String image;

	protected List<BannerImageDesc> descs = new AutoPopulatingList<BannerImageDesc>(BannerImageDesc.class);
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBannerId() {
		return bannerId;
	}

	public void setBannerId(Integer bannerId) {
		this.bannerId = bannerId;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<BannerImageDesc> getDescs() {
		return descs;
	}

	public void setDescs(List<BannerImageDesc> descs) {
		this.descs = new AutoPopulatingList<BannerImageDesc>(descs, BannerImageDesc.class);
	}
	
	
}
