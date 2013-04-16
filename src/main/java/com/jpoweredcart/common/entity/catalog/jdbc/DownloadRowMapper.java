package com.jpoweredcart.common.entity.catalog.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.common.entity.catalog.Download;
import com.jpoweredcart.common.entity.catalog.DownloadDesc;
import com.jpoweredcart.common.jdbc.ObjectFactoryRowMapper;

public class DownloadRowMapper extends ObjectFactoryRowMapper<Download>{
	
	@Override
	public Download mapRow(ResultSet rs, Download object) throws SQLException {
		
		object.setId(rs.getInt("download_id"));
		object.setFileName(rs.getString("filename"));
		object.setMask(rs.getString("mask"));
		object.setRemaining(rs.getInt("remaining"));
		object.setDateAdded(rs.getDate("date_added"));
		return object;
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
