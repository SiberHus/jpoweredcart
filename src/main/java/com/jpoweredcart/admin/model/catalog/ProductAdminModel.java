package com.jpoweredcart.admin.model.catalog;

import java.util.List;

import com.jpoweredcart.admin.form.catalog.filter.TotalProductsFilter;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.catalog.Product;
import com.jpoweredcart.common.entity.catalog.ProductAttribute;
import com.jpoweredcart.common.entity.catalog.ProductDescription;
import com.jpoweredcart.common.entity.catalog.ProductDiscount;
import com.jpoweredcart.common.entity.catalog.ProductFilter;
import com.jpoweredcart.common.entity.catalog.ProductImage;
import com.jpoweredcart.common.entity.catalog.ProductOption;
import com.jpoweredcart.common.entity.catalog.ProductRelated;
import com.jpoweredcart.common.entity.catalog.ProductReward;
import com.jpoweredcart.common.entity.catalog.ProductSpecial;
import com.jpoweredcart.common.entity.catalog.ProductToCategory;
import com.jpoweredcart.common.entity.catalog.ProductToDownload;
import com.jpoweredcart.common.entity.catalog.ProductToLayout;
import com.jpoweredcart.common.entity.catalog.ProductToStore;

public interface ProductAdminModel {
	
	public void create(Product product);
	
	public void update(Product product);
	
	public void copy(Integer productId);
	
	public void delete(Integer productId);
	
	public Product get(Integer productId, Class<? extends Product> clazz);
	
	public List<Product> getList(PageParam pageParam);
	
	public List<Product> getAllByCategoryId(Integer categoryId);
	
	public List<ProductDescription> getProductDescriptions(Integer productId);
	
	public List<ProductAttribute> getProductAttributes(Integer productId);
	
	public List<ProductOption> getProductOptions(Integer productId);
	
	public List<ProductFilter> getProductFilters(Integer productId);
	
	public List<ProductImage> getProductImages(Integer productId);
	
	public List<ProductDiscount> getProductDiscounts(Integer productId);
	
	public List<ProductSpecial> getProductSpecials(Integer productId);
	
	public List<ProductReward> getProductRewards(Integer productId);
	
	public List<ProductToDownload> getProductDownloads(Integer productId);
	
	public List<ProductToStore> getProductStores(Integer productId);
	
	public List<ProductToLayout> getProductLayouts(Integer productId);
	
	public List<ProductToCategory> getProductCategories(Integer productId);
	
	public List<ProductRelated> getProductRelated(Integer productId);
	
	public int getTotal(TotalProductsFilter filter);
	
	public int getTotalByTaxClassId(Integer taxClassId);
	
	public int getTotalByStockStatusId(Integer stockStatusId);
	
	public int getTotalByWeightClassId(Integer weightClassId);
	
	public int getTotalByLengthClassId(Integer lengthClassId);
	
	public int getTotalByDownloadId(Integer downloadId);
	
	public int getTotalByManufacturerId(Integer manufacturerId);
	
	public int getTotalByAttributeId(Integer attributeId);
	
	public int getTotalByOptionId(Integer optionId);
	
	public int getTotalByLayoutId(Integer layoutId);
	
}
