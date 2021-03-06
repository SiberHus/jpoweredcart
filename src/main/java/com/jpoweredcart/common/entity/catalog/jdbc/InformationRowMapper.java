package com.jpoweredcart.common.entity.catalog.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.common.entity.catalog.Information;
import com.jpoweredcart.common.entity.catalog.InformationDesc;
import com.jpoweredcart.common.entity.catalog.InformationToLayout;
import com.jpoweredcart.common.entity.catalog.InformationToStore;
import com.jpoweredcart.common.jdbc.ObjectFactoryRowMapper;

public class InformationRowMapper extends ObjectFactoryRowMapper<Information>{
	
	@Override
	public Information mapRow(ResultSet rs, Information object) throws SQLException {
		object.setId(rs.getInt("information_id"));
		object.setBottom(rs.getInt("bottom"));
		object.setSortOrder(rs.getInt("sort_order"));
		object.setStatus(rs.getShort("status"));
		return object;
	}
	
	public static class Desc implements RowMapper<InformationDesc>{

		@Override
		public InformationDesc mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			InformationDesc desc = new InformationDesc();
			desc.setInformationId(rs.getInt("information_id"));
			desc.setLanguageId(rs.getInt("language_id"));
			desc.setLanguageName(rs.getString("language_name"));
			desc.setLanguageImage(rs.getString("language_image"));
			desc.setTitle(rs.getString("title"));
			desc.setDescription(rs.getString("description"));
			return desc;
		}
	}
	
	public static class Store implements RowMapper<InformationToStore>{

		@Override
		public InformationToStore mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			InformationToStore infoStore = new InformationToStore();
			infoStore.setInformationId(rs.getInt("information_id"));
			infoStore.setStoreId(rs.getInt("store_id"));
			return infoStore;
		}
	}
	
	public static class Layout implements RowMapper<InformationToLayout>{

		@Override
		public InformationToLayout mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			InformationToLayout infoLayout = new InformationToLayout();
			infoLayout.setInformationId(rs.getInt("information_id"));
			infoLayout.setStoreId(rs.getInt("store_id"));
			infoLayout.setLayoutId(rs.getInt("layout_id"));
			return infoLayout;
		}
		
	}
}
