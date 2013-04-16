package com.jpoweredcart.common.service;

public interface StoreResolver {
	
	/**
	 * Get store ID from given URL
	 * @param url
	 * @return
	 */
	public Integer getStoreId(String url);
	
	/**
	 * Get store URL from given ID
	 * @param storeId
	 * @return
	 */
	public String getStoreUrl(Integer storeId, boolean secured);
	
}
