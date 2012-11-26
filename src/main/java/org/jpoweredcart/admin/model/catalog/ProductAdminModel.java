package org.jpoweredcart.admin.model.catalog;

import java.util.List;

import org.jpoweredcart.admin.entity.catalog.Product;
import org.jpoweredcart.admin.entity.catalog.ProductAttribute;
import org.jpoweredcart.admin.entity.catalog.ProductDescription;
import org.jpoweredcart.admin.entity.catalog.ProductDiscount;
import org.jpoweredcart.admin.entity.catalog.ProductFilter;
import org.jpoweredcart.admin.entity.catalog.ProductImage;
import org.jpoweredcart.admin.entity.catalog.ProductOption;
import org.jpoweredcart.admin.entity.catalog.ProductRelated;
import org.jpoweredcart.admin.entity.catalog.ProductReward;
import org.jpoweredcart.admin.entity.catalog.ProductSpecial;
import org.jpoweredcart.admin.entity.catalog.ProductToCategory;
import org.jpoweredcart.admin.entity.catalog.ProductToDownload;
import org.jpoweredcart.admin.entity.catalog.ProductToLayout;
import org.jpoweredcart.admin.entity.catalog.ProductToStore;
import org.jpoweredcart.common.PageParam;

public interface ProductAdminModel {
	
	public void addProduct(Product product);
	
	public void updateProduct(Product product);
	
	public void copyProduct(Integer productId);
	
	public void deleteProduct(Integer productId);
	
	public Product getProduct(Integer productId);
	
	public List<Product> getProducts(PageParam pageParam);
	
	public List<Product> getProductsByCategoryId(Integer categoryId);
	
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
	
	public int getTotalProducts(TotalProductsFilter filter);
	
	public int getTotalProductsByTaxClassId(Integer taxClassId);
	
	public int getTotalProductsByStockStatusId(Integer stockStatusId);
	
	public int getTotalProductsByWeightClassId(Integer weightClassId);
	
	public int getTotalProductsByLengthClassId(Integer lengthClassId);
	
	public int getTotalProductsByDownloadId(Integer downloadId);
	
	public int getTotalProductsByManufacturerId(Integer manufacturerId);
	
	public int getTotalProductsByAttributeId(Integer attributeId);
	
	public int getTotalProductsByOptionId(Integer optionId);
	
	public int getTotalProductsByLayoutId(Integer layoutId);
	
}
