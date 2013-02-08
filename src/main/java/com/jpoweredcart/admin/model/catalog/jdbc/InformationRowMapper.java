package com.jpoweredcart.admin.model.catalog.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.admin.bean.catalog.InformationForm;
import com.jpoweredcart.common.entity.catalog.Information;
import com.jpoweredcart.common.entity.catalog.InformationDesc;
import com.jpoweredcart.common.entity.catalog.InformationToLayout;
import com.jpoweredcart.common.entity.catalog.InformationToStore;
import org.springframework.jdbc.core.RowMapper;

public class InformationRowMapper implements RowMapper<Information>{
	
	private static void setProperties(ResultSet rs, Information info) throws SQLException{
		info.setId(rs.getInt("information_id"));
		info.setBottom(rs.getInt("bottom"));
		info.setSortOrder(rs.getInt("sort_order"));
		info.setStatus(rs.getShort("status"));
	}
	
	@Override
	public Information mapRow(ResultSet rs, int rowNum) throws SQLException {
		Information info = new Information();
		setProperties(rs, info);
		return info;
	}
	
	public static class Form implements RowMapper<InformationForm>{
		
		@Override
		public InformationForm mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			InformationForm infoForm = new InformationForm();
			setProperties(rs, infoForm);
			infoForm.setKeyword(rs.getString("keyword"));
			return infoForm;
		}
	}
	
	public static class Desc implements RowMapper<InformationDesc>{

		@Override
		public InformationDesc mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			InformationDesc infoDesc = new InformationDesc();
			infoDesc.setInformationId(rs.getInt("information_id"));
			infoDesc.setLanguageId(rs.getInt("language_id"));
			infoDesc.setLanguageName(rs.getString("language_name"));
			infoDesc.setLanguageImage(rs.getString("language_image"));
			infoDesc.setTitle(rs.getString("title"));
			infoDesc.setDescription(rs.getString("description"));
			return infoDesc;
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
