package com.jpoweredcart.common.web;

import org.apache.commons.lang3.StringUtils;

public class Link {
	
	private String rel;
	private String type;
	private String href;
	private String media;
	
	public Link(){}
	
	public Link(String rel, String type, String href, String media){
		this.rel = rel;
		this.type = type;
		this.href = href;
		this.media = media;
	}
	
	public static Link newCss(String href, String... media){
		
		return new Link("stylesheet", "text/css", href, 
				StringUtils.join(media, " "));
	}
	
	public String getRel() {
		return rel;
	}
	public void setRel(String rel) {
		this.rel = rel;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getMedia() {
		return media;
	}
	public void setMedia(String media) {
		this.media = media;
	}
	
}
