package com.jpoweredcart.common;

public class PageParam {
	
	private String sortKey;
	
	private String orderDir;
	
	private int page;
	
	private int limit;
	
	private int start;
	
	public static PageParam list(int limit){
		PageParam pageParam = new PageParam();
		pageParam.setLimit(limit);
		return pageParam;
	}
	
	public static PageParam list(){
		PageParam pageParam = new PageParam();
		pageParam.setLimit(-1);//set default limit
		return pageParam;
	}
	
	public String getSortKey() {
		return sortKey;
	}

	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}

	public String getOrderDir() {
		return orderDir;
	}

	public void setOrderDir(String orderDir) {
		this.orderDir = orderDir;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}
	
}
