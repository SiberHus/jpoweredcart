package com.jpoweredcart.common.entity.catalog.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.common.entity.catalog.Category;
import com.jpoweredcart.common.entity.catalog.CategoryDesc;
import com.jpoweredcart.common.entity.catalog.CategoryToLayout;
import com.jpoweredcart.common.entity.catalog.CategoryToStore;
import com.jpoweredcart.common.jdbc.ObjectFactoryRowMapper;

public class CategoryRowMapper extends ObjectFactoryRowMapper<Category>{
	
	@Override
	public Category mapRow(ResultSet rs, Category object) throws SQLException {
		object.setId(rs.getInt("category_id"));
		object.setImage(rs.getString("image"));
		object.setParentId(rs.getInt("parent_id"));
		object.setTop(rs.getBoolean("top"));
		object.setColumn(rs.getInt("column"));
		object.setSortOrder(rs.getInt("sort_order"));
		object.setStatus(rs.getShort("status"));
		object.setDateAdded(rs.getDate("date_added"));
		object.setDateModified(rs.getDate("date_modified"));
		return object;
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
