package com.jpoweredcart.common;

import java.util.ArrayList;
import java.util.List;

public class PageParam {
	
	public static class OrderPair {
		private String key;
		private String dir;
		public OrderPair(String key, String dir){
			this.key = key;
			this.dir = dir==null?"":dir;
		}
		public String getKey() {
			return key;
		}
		public String getDir() {
			return dir;
		}
	}
	
	private List<OrderPair> orders = null;
	
	private int page;
	
	private int limit;
	
	private int start;
	
	public static PageParam list(int start, int limit){
		PageParam pageParam = new PageParam();
		pageParam.setStart(start);
		pageParam.setLimit(limit);
		return pageParam;
	}
	
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
	
	public void addOrder(String key, String dir){
		if(orders==null){
			orders = new ArrayList<OrderPair>();
		}
		orders.add(new OrderPair(key, dir));
	}
	
	public List<OrderPair> getOrders(){
		return this.orders;
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
