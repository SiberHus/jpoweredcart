package com.jpoweredcart.admin.model.catalog.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.bean.catalog.DownloadForm;
import com.jpoweredcart.common.entity.catalog.Download;

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
}
