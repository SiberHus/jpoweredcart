package com.jpoweredcart.admin.model.catalog.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.jpoweredcart.admin.form.catalog.filter.TotalProductsFilter;
import com.jpoweredcart.admin.model.catalog.ProductAdminModel;
import com.jpoweredcart.common.BaseModel;
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
import com.jpoweredcart.common.system.setting.SettingKey;

public class ProductAdminModelImpl extends BaseModel implements ProductAdminModel {

	@Transactional
	@Override
	public void create(Product product) {
		// TODO Auto-generated method stub
		
	}

	@Transactional
	@Override
	public void update(Product product) {
		// TODO Auto-generated method stub
		
	}

	@Transactional
	@Override
	public void copy(Integer productId) {
		// TODO Auto-generated method stub
		
	}

	@Transactional
	@Override
	public void delete(Integer productId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Product get(Integer productId, Class<? extends Product> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getList(PageParam pageParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getAllByCategoryId(Integer categoryId) {
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
	public int getTotal(TotalProductsFilter filter) {
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
	public int getTotalByTaxClassId(Integer taxClassId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("product")+ " WHERE tax_class_id = ?";
		return getJdbcOperations().queryForObject(sql, Integer.class, taxClassId);
	}

	@Override
	public int getTotalByStockStatusId(Integer stockStatusId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("product")+ " WHERE stock_status_id = ?";
		return getJdbcOperations().queryForObject(sql, Integer.class, stockStatusId);
	}
	
	@Override
	public int getTotalByWeightClassId(Integer weightClassId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("product")+ " WHERE weight_class_id = ?";
		return getJdbcOperations().queryForObject(sql, Integer.class, weightClassId);
	}
	
	@Override
	public int getTotalByLengthClassId(Integer lengthClassId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("product")+ " WHERE length_class_id = ?";
		return getJdbcOperations().queryForObject(sql, Integer.class, lengthClassId);
	}
	
	@Override
	public int getTotalByDownloadId(Integer downloadId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("product_to_download")+ " WHERE download_id = ?";
		return getJdbcOperations().queryForObject(sql, Integer.class, downloadId);
	}
	
	@Override
	public int getTotalByManufacturerId(Integer manufacturerId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("product")+ " WHERE manufacturer_id = ?";
		return getJdbcOperations().queryForObject(sql, Integer.class, manufacturerId);
	}

	@Override
	public int getTotalByAttributeId(Integer attributeId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("product_attribute")+ " WHERE attribute_id = ?";
		return getJdbcOperations().queryForObject(sql, Integer.class, attributeId);
	}
	
	@Override
	public int getTotalByOptionId(Integer optionId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("product_option")+ " WHERE option_id = ?";
		return getJdbcOperations().queryForObject(sql, Integer.class, optionId);
	}
	
	@Override
	public int getTotalByLayoutId(Integer layoutId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("product_to_layout")+ " WHERE layout_id =?";
		return getJdbcOperations().queryForObject(sql, Integer.class, layoutId);
	}

	
}
