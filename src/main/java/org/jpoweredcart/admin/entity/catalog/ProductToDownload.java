package org.jpoweredcart.admin.entity.catalog;

import java.io.Serializable;

public class ProductToDownload implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Integer productId;
	
	protected Integer downloadId;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getDownloadId() {
		return downloadId;
	}

	public void setDownloadId(Integer downloadId) {
		this.downloadId = downloadId;
	}
	
}
