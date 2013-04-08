package com.jpoweredcart.admin.model.catalog.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.bean.catalog.CategoryForm;
import com.jpoweredcart.common.entity.catalog.Category;
import com.jpoweredcart.common.entity.catalog.CategoryDesc;
import com.jpoweredcart.common.entity.catalog.CategoryToLayout;
import com.jpoweredcart.common.entity.catalog.CategoryToStore;

public class CategoryRowMapper implements RowMapper<Category>{
	
	public static void setProperties(ResultSet rs, Category cat) throws SQLException{
		cat.setId(rs.getInt("category_id"));
		cat.setImage(rs.getString("image"));
		cat.setParentId(rs.getInt("parent_id"));
		cat.setTop(rs.getBoolean("top"));
		cat.setColumn(rs.getInt("column"));
		cat.setSortOrder(rs.getInt("sort_order"));
		cat.setStatus(rs.getShort("status"));
		cat.setDateAdded(rs.getDate("date_added"));
		cat.setDateModified(rs.getDate("date_modified"));
	}
	
	@Override
	public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
		Category cat = new Category();
		setProperties(rs, cat);
		return cat;
	}
	
	public static class Form implements RowMapper<CategoryForm>{
		
		@Override
		public CategoryForm mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			CategoryForm catForm = new CategoryForm();
			setProperties(rs, catForm);
			catForm.setKeyword(rs.getString("keyword"));
			return catForm;
		}
	}
	
	public static class Desc implements RowMapper<CategoryDesc>{

		@Override
		public CategoryDesc mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			CategoryDesc desc = new CategoryDesc();
			desc.setCategoryId(rs.getInt("category_id"));
			desc.setLanguageId(rs.getInt("language_id"));
			desc.setLanguageName(rs.getString("language_name"));
			desc.setLanguageImage(rs.getString("language_image"));
			desc.setName(rs.getString("name"));
			desc.setDescription(rs.getString("description"));
			desc.setMetaDescription(rs.getString("meta_description"));
			desc.setMetaKeyword(rs.getString("meta_keyword"));
			return desc;
		}
	}
	
	public static class Store implements RowMapper<CategoryToStore>{

		@Override
		public CategoryToStore mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			CategoryToStore catStore = new CategoryToStore();
			catStore.setCategoryId(rs.getInt("category_id"));
			catStore.setStoreId(rs.getInt("store_id"));
			return catStore;
		}
	}
	
	public static class Layout implements RowMapper<CategoryToLayout>{

		@Override
		public CategoryToLayout mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			CategoryToLayout catLayout = new CategoryToLayout();
			catLayout.setCategoryId(rs.getInt("category_id"));
			catLayout.setStoreId(rs.getInt("store_id"));
			catLayout.setLayoutId(rs.getInt("layout_id"));
			return catLayout;
		}
		
	}
}
