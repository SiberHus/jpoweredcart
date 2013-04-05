package com.jpoweredcart.admin.model.catalog.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.bean.catalog.DownloadForm;
import com.jpoweredcart.common.entity.catalog.Download;
import com.jpoweredcart.common.entity.catalog.DownloadDesc;

public class DownloadRowMapper implements RowMapper<Download>{
	
	private static void setProperties(ResultSet rs, Download dl) throws SQLException {
		dl.setId(rs.getInt("download_id"));
		dl.setFileName(rs.getString("filename"));
		dl.setMask(rs.getString("mask"));
		dl.setRemaining(rs.getInt("remaining"));
		dl.setDateAdded(rs.getDate("date_added"));
	}
	
	@Override
	public Download mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Download dl = new Download();
		setProperties(rs, dl);
		return dl;
	}
	
	public static class Form implements RowMapper<DownloadForm>{

		@Override
		public DownloadForm mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			DownloadForm dlForm = new DownloadForm();
			setProperties(rs, dlForm);
			return dlForm;
		}
		
	}
	
	public static class Desc implements RowMapper<DownloadDesc>{
		
		@Override
		public DownloadDesc mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			DownloadDesc desc = new DownloadDesc();
			desc.setDownloadId(rs.getInt("download_id"));
			desc.setLanguageId(rs.getInt("language_id"));
			desc.setLanguageName(rs.getString("language_name"));
			desc.setLanguageImage(rs.getString("language_image"));
			desc.setName(rs.getString("name"));
			return desc;
		}
	}
}
