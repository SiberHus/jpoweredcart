package org.jpoweredcart.admin.model.catalog.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jpoweredcart.admin.model.catalog.ProductAdminModel;
import org.jpoweredcart.admin.model.catalog.TotalProductsFilter;
import org.jpoweredcart.common.BaseModel;
import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.catalog.Product;
import org.jpoweredcart.common.entity.catalog.ProductAttribute;
import org.jpoweredcart.common.entity.catalog.ProductDescription;
import org.jpoweredcart.common.entity.catalog.ProductDiscount;
import org.jpoweredcart.common.entity.catalog.ProductFilter;
import org.jpoweredcart.common.entity.catalog.ProductImage;
import org.jpoweredcart.common.entity.catalog.ProductOption;
import org.jpoweredcart.common.entity.catalog.ProductRelated;
import org.jpoweredcart.common.entity.catalog.ProductReward;
import org.jpoweredcart.common.entity.catalog.ProductSpecial;
import org.jpoweredcart.common.entity.catalog.ProductToCategory;
import org.jpoweredcart.common.entity.catalog.ProductToDownload;
import org.jpoweredcart.common.entity.catalog.ProductToLayout;
import org.jpoweredcart.common.entity.catalog.ProductToStore;
import org.jpoweredcart.common.service.SettingKey;
import org.jpoweredcart.common.service.SettingService;
import org.springframework.jdbc.core.JdbcOperations;

public class ProductAdminModelImpl extends BaseModel implements ProductAdminModel {

	
	public ProductAdminModelImpl(SettingService settingService, JdbcOperations jdbcOperations){
		super(settingService, jdbcOperations);
	}
	
	@Override
	public void addProduct(Product product) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateProduct(Product product) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void copyProduct(Integer productId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteProduct(Integer productId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Product getProduct(Integer productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getProducts(PageParam pageParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getProductsByCategoryId(Integer categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductDescription> getProductDescriptions(Integer productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductAttribute> getProductAttributes(Integer productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductOption> getProductOptions(Integer productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductFilter> getProductFilters(Integer productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductImage> getProductImages(Integer productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductDiscount> getProductDiscounts(Integer productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductSpecial> getProductSpecials(Integer productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductReward> getProductRewards(Integer productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductToDownload> getProductDownloads(Integer productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductToStore> getProductStores(Integer productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductToLayout> getProductLayouts(Integer productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductToCategory> getProductCategories(Integer productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductRelated> getProductRelated(Integer productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalProducts(TotalProductsFilter filter) {
		String sql = "SELECT COUNT(DISTINCT p.product_id) AS total FROM " +quoteTable("product")+ " p LEFT JOIN " 
				+quoteTable("product_description")+ " pd ON (p.product_id = pd.product_id)";
		if(filter.getCategoryId()!=null){
			sql += " LEFT JOIN " +quoteTable("product_to_category")+ " p2c ON (p.product_id = p2c.product_id)";
		}
		List<Object> params = new ArrayList<Object>();
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		params.add(languageId);
		sql += " WHERE pd.language_id = ?";
		
		if(StringUtils.isNotBlank(filter.getName())){
			params.add(filter.getName().toLowerCase()+"%");
			sql += " AND LOWER(pd.name) LIKE ?";
		}
		
		if(StringUtils.isNotBlank(filter.getModel())){
			params.add(filter.getModel().toLowerCase()+"%");
			sql += " AND LOWER(p.model) LIKE ?";
		}
		
		if(filter.getPrice()!=null){
			//TODO: This may cause performance problem
			params.add(filter.getPrice().toString()+"%");
			sql += " AND p.price LIKE ?"; 
		}
		
		if(filter.getQuantity()!=null){
			params.add(filter.getQuantity());
			sql += " AND p.quantity = ?";
		}
		
		if(filter.getStatus()!=null){
			params.add(filter.getStatus());
			sql += " AND p.status = ?";
		}
		
		if(filter.getCategoryId()!=null && StringUtils.isNotBlank(filter.getSubCategory())){
			
		}
		return 0;
	}

	@Override
	public int getTotalProductsByTaxClassId(Integer taxClassId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("product")+ " WHERE tax_class_id = ?";
		return getJdbcOperations().queryForInt(sql, taxClassId);
	}

	@Override
	public int getTotalProductsByStockStatusId(Integer stockStatusId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("product")+ " WHERE stock_status_id = ?";
		return getJdbcOperations().queryForInt(sql, stockStatusId);
	}
	
	@Override
	public int getTotalProductsByWeightClassId(Integer weightClassId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("product")+ " WHERE weight_class_id = ?";
		return getJdbcOperations().queryForInt(sql, weightClassId);
	}
	
	@Override
	public int getTotalProductsByLengthClassId(Integer lengthClassId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("product")+ " WHERE length_class_id = ?";
		return getJdbcOperations().queryForInt(sql, lengthClassId);
	}
	
	@Override
	public int getTotalProductsByDownloadId(Integer downloadId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("product_to_download")+ " WHERE download_id = ?";
		return getJdbcOperations().queryForInt(sql, downloadId);
	}
	
	@Override
	public int getTotalProductsByManufacturerId(Integer manufacturerId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("product")+ " WHERE manufacturer_id = ?";
		return getJdbcOperations().queryForInt(sql, manufacturerId);
	}

	@Override
	public int getTotalProductsByAttributeId(Integer attributeId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("product_attribute")+ " WHERE attribute_id = ?";
		return getJdbcOperations().queryForInt(sql, attributeId);
	}
	
	@Override
	public int getTotalProductsByOptionId(Integer optionId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("product_option")+ " WHERE option_id = ?";
		return getJdbcOperations().queryForInt(sql, optionId);
	}
	
	@Override
	public int getTotalProductsByLayoutId(Integer layoutId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("product_to_layout")+ " WHERE layout_id =?";
		return getJdbcOperations().queryForInt(sql, layoutId);
	}

	
}
