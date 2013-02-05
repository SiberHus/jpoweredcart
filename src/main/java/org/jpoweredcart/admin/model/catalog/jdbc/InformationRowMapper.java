package org.jpoweredcart.admin.model.catalog.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jpoweredcart.common.entity.catalog.Information;
import org.jpoweredcart.common.entity.catalog.InformationDesc;
import org.jpoweredcart.common.entity.catalog.InformationToLayout;
import org.jpoweredcart.common.entity.catalog.InformationToStore;
import org.springframework.jdbc.core.RowMapper;

public class InformationRowMapper implements RowMapper<Information>{

	@Override
	public Information mapRow(ResultSet rs, int rowNum) throws SQLException {
		Information info = new Information();
		info.setId(rs.getInt("information_id"));
		info.setBottom(rs.getInt("bottom"));
		info.setSortOrder(rs.getInt("sort_order"));
		info.setStatus(rs.getShort("status"));
		return info;
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
			InformationToStore store = new InformationToStore();
			store.setInformationId(rs.getInt("information_id"));
			store.setStoreId(rs.getInt("store_id"));
			return store;
		}
	}
	
	public static class Layout implements RowMapper<InformationToLayout>{

		@Override
		public InformationToLayout mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			InformationToLayout layout = new InformationToLayout();
			layout.setInformationId(rs.getInt("information_id"));
			layout.setStoreId(rs.getInt("store_id"));
			layout.setLayoutId(rs.getInt("layout_id"));
			return layout;
		}
		
	}
}
