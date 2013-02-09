package com.jpoweredcart.admin.model.catalog.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import com.jpoweredcart.admin.bean.catalog.CategoryForm;
import com.jpoweredcart.admin.model.catalog.CategoryAdminModel;
import com.jpoweredcart.admin.model.localisation.LanguageAdminModel;
import com.jpoweredcart.admin.model.setting.StoreAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.entity.catalog.Category;
import com.jpoweredcart.common.entity.catalog.CategoryDesc;
import com.jpoweredcart.common.entity.catalog.CategoryToLayout;
import com.jpoweredcart.common.entity.catalog.CategoryToStore;
import com.jpoweredcart.common.entity.setting.Store;
import com.jpoweredcart.common.jdbc.MapResultSetExtractor;
import com.jpoweredcart.common.jdbc.ScalarResultSetExtractor;
import com.jpoweredcart.common.service.SettingKey;
import com.jpoweredcart.common.service.SettingService;

public class CategoryAdminModelImpl extends BaseModel implements CategoryAdminModel {
	
	@Inject
	private StoreAdminModel storeAdminModel;
	
	@Inject
	private LanguageAdminModel languageAdminModel;
	
	public CategoryAdminModelImpl(SettingService settingService, JdbcOperations jdbcOperations){
		super(settingService, jdbcOperations);
	}
	
	@Transactional
	@Override
	public void create(final CategoryForm catForm) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcOperations().update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				String sql = "INSERT INTO " +quoteTable("category")+ "(image, path, parent_id, top," 
					+quoteName("column")+",sort_order, status, date_added, date_modified) VALUES(?,?,?,?,?,?,?,?,?) ";
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, HtmlUtils.htmlEscape(catForm.getImage()));
				ps.setString(2, catForm.getPath());
				ps.setInt(3, catForm.getParentId());
				ps.setBoolean(4, catForm.isTop());
				ps.setInt(5, catForm.getColumn());
				ps.setInt(6, catForm.getSortOrder());
				ps.setShort(7, catForm.getStatus());
				Timestamp currentTime = new Timestamp(new Date().getTime());
				ps.setTimestamp(8, currentTime);
				ps.setTimestamp(9, currentTime);
				return ps;
			}
		}, keyHolder);
		Integer catId = keyHolder.getKey().intValue();
		catForm.setId(catId);
		setAdditionalFormValues(catForm);
	}
	
	@Transactional
	@Override
	public void update(CategoryForm catForm) {
		
		String sql = "UPDATE " +quoteTable("category")
				+ " SET image=?, parent_id=?, top=?, "+quoteName("column")
				+ "=?, sort_order=?, status=?, date_modified=? WHERE category_id=?";
		getJdbcOperations().update(sql, HtmlUtils.htmlEscape(catForm.getImage()), catForm.getParentId(), catForm.isTop(), 
				catForm.getColumn(), catForm.getSortOrder(), catForm.getStatus(), new Date(), catForm.getId());
		
		sql = "DELETE FROM " +quoteTable("category_description")+ " WHERE category_id=?";
		getJdbcOperations().update(sql, catForm.getId());

		sql = "DELETE FROM " +quoteTable("category_to_store")+ " WHERE category_id=?";
		getJdbcOperations().update(sql, catForm.getId());

		sql = "DELETE FROM " +quoteTable("category_to_layout")+ " WHERE category_id=?";
		getJdbcOperations().update(sql, catForm.getId());
		
		sql = "DELETE FROM " +quoteTable("url_alias")+ " WHERE query=?";
		getJdbcOperations().update(sql, "category_id="+catForm.getId());
		
		setAdditionalFormValues(catForm);
	}
	
	protected void setAdditionalFormValues(CategoryForm catForm){
		if(StringUtils.isNotBlank(catForm.getKeyword())){
			String sql = "INSERT INTO " +quoteTable("url_alias") + "(query, keyword) VALUES(?,?)";
			getJdbcOperations().update(sql, "category_id="+catForm.getId(), catForm.getKeyword());
		}
		for(CategoryDesc desc: catForm.getDescs()){
			String sql = "INSERT INTO " +quoteTable("category_description")
					+ "(category_id, language_id, name, description, meta_description, meta_keyword) VALUES(?,?,?,?,?,?)";
			getJdbcOperations().update(sql, catForm.getId(), desc.getLanguageId(),
					desc.getName(), desc.getDescription(), desc.getMetaDescription(), 
					desc.getMetaKeyword());
		}
		for(CategoryToStore its: catForm.getStores()){
			if(its.getStoreId()!=null){
				String sql = "INSERT INTO " +quoteTable("category_to_store")
					+ "(category_id, store_id) VALUES(?,?)";
				getJdbcOperations().update(sql, catForm.getId(), its.getStoreId());
			}
		}
		for(CategoryToLayout itl: catForm.getLayouts()){
			if(itl.getLayoutId()!=null){
				String sql = "INSERT INTO " +quoteTable("category_to_layout")
					+ "(category_id, store_id, layout_id) VALUES(?,?,?)";
				getJdbcOperations().update(sql, catForm.getId(), 
						itl.getStoreId(), itl.getLayoutId());
			}
		}
	}
	
	@Transactional
	@Override
	public void delete(Integer catId) {
		
		String sql = "DELETE FROM " +quoteTable("url_alias")+ " WHERE query=?";
		getJdbcOperations().update(sql, "category_id="+catId);
		
		String tables[] = new String[]{"category_description", "category_to_store", 
				"category_to_layout", "category"};
		for(String table: tables){
			sql = "DELETE FROM " +quoteTable(table)+ " WHERE category_id=?";
			getJdbcOperations().update(sql, catId);
		}
	}
	
	@Override
	public CategoryForm newForm(){
		
		CategoryForm catForm = new CategoryForm();
		List<CategoryDesc> descList = languageAdminModel
				.createDescriptionList(CategoryDesc.class);
		catForm.setDescs(descList);
		List<CategoryToLayout> ctlList = new ArrayList<CategoryToLayout>();
		for(Store store: storeAdminModel.getAll()){
			CategoryToLayout ctl = new CategoryToLayout();
			ctl.setStoreId(store.getId());
			ctl.setStoreName(store.getName());
			ctlList.add(ctl);
		}
		catForm.setLayouts(ctlList);
		
		return catForm;
	}
	
	@Override
	public CategoryForm getForm(Integer catId) {
		
		String sql = "SELECT DISTINCT *, (SELECT keyword FROM " +quoteTable("url_alias")
				+ " WHERE query = ?) AS keyword FROM " 
				+ quoteTable("category")+" WHERE category_id = ?";
		CategoryForm catForm = getJdbcOperations().queryForObject(sql, 
				new Object[]{"category_id="+catId, catId}, 
				new CategoryRowMapper.Form());
		catForm.setDescs(getDescriptions(catId));
		catForm.setStores(getCatStores(catId));
		catForm.setLayouts(getCatLayouts(catId));
		
		return catForm;
	}
	
	@Override
	public Category get(Integer catId) {
		
		String sql = "SELECT * FROM " + quoteTable("category")+" WHERE category_id = ?";
		Category cat = getJdbcOperations().queryForObject(sql, 
				new Object[]{catId}, new CategoryRowMapper());
		return cat;
	}
	
	@Override
	public List<Category> getList(final String separator) {
		
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		String sql = "SELECT * FROM " +quoteTable("category")+" c LEFT JOIN "+quoteTable("category_description")
			+" cd ON (c.category_id = cd.category_id) WHERE cd.language_id = ? ORDER BY c.sort_order, cd.name ASC";
		List<Category> catList = getJdbcOperations().query(sql, new Object[]{languageId}, new RowMapper<Category>(){
			@Override
			public Category mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				Category cat = new Category();
				CategoryRowMapper.setProperties(rs, cat);
				cat.setName(getPath(cat.getId(), separator));
				return cat;
			}
		});
		
		Collections.sort(catList, new Comparator<Category>() {
			@Override
			public int compare(Category o1, Category o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		
		return catList;
	}
	
	@Override
	public String getPath(Integer catId, String separator) {
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		String sql = "SELECT name, parent_id FROM " +quoteTable("category")+ " c LEFT JOIN " +quoteTable("category_description")
				+ " cd ON (c.category_id = cd.category_id) WHERE c.category_id = ? AND cd.language_id = ? ORDER BY c.sort_order, cd.name ASC";
//		System.out.println(sql+"("+catId+","+languageId+")");
		Map<String, Object> resultMap = getJdbcOperations().query(sql, 
				new Object[]{catId, languageId}, new MapResultSetExtractor());
		Integer parentId = (Integer)resultMap.get("parent_id");
		String name = (String)resultMap.get("name");
		if(parentId!=0){
			return getPath(parentId, separator)+separator+name;
		}else{
			return name;
		}
	}

	@Override
	public List<CategoryDesc> getDescriptions(Integer catId){
		String sql = "SELECT cd.category_id, cd.language_id, l.name AS language_name, l.image AS language_image, " +
				"cd.name, cd.description, cd.meta_description, cd.meta_keyword FROM " +
				quoteTable("category_description")+" cd INNER JOIN "
				+quoteTable("language")+" l ON cd.language_id=l.language_id WHERE cd.category_id=?";
		return getJdbcOperations().query(sql, new Object[]{catId}, 
				new CategoryRowMapper.Desc());
	}
	
	@Override
	public List<CategoryToStore> getCatStores(Integer catId) {
		String sql = "SELECT * FROM "+quoteTable("category_to_store")
				+" WHERE category_id =?"; 
		return getJdbcOperations().query(sql, new Object[]{catId}, 
				new CategoryRowMapper.Store());
	}

	@Override
	public List<CategoryToLayout> getCatLayouts(Integer catId) {
		List<CategoryToLayout> ctlList = new ArrayList<CategoryToLayout>();
		for(Store store: storeAdminModel.getAll()){
			CategoryToLayout ctl = new CategoryToLayout();
			ctl.setStoreId(store.getId());
			ctl.setStoreName(store.getName());
			String sql = "SELECT layout_id FROM "+quoteTable("category_to_layout")
					+" WHERE category_id=? AND store_id=?";
			Integer layoutId = getJdbcOperations().query(sql, 
					new Object[]{catId, store.getId()}, 
					new ScalarResultSetExtractor<Integer>());
			ctl.setLayoutId(layoutId);
			ctlList.add(ctl);
		}
		return ctlList;
	}
	
	@Override
	public int getTotal() {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("category");
		return getJdbcOperations().queryForInt(sql);
	}
	
	@Override
	public int getTotalByImageId(Integer imageId) {
		String sql = "SELECT COUNT(*) AS total FROM "+quoteTable("category")+" WHERE image_id =?";
		return getJdbcOperations().queryForInt(sql, imageId);
	}

	@Override
	public int getTotalByLayoutId(Integer layoutId) {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("category_to_layout")
				+" WHERE layout_id=?";
		return getJdbcOperations().queryForInt(sql, layoutId);
	}
	
	
}
