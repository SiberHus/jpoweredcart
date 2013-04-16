package com.jpoweredcart.common.entity.sale.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.common.entity.sale.VoucherTheme;
import com.jpoweredcart.common.entity.sale.VoucherThemeDesc;
import com.jpoweredcart.common.jdbc.ObjectFactoryRowMapper;

public class VoucherThemeRowMapper extends ObjectFactoryRowMapper<VoucherTheme>{
	
	@Override
	public VoucherTheme mapRow(ResultSet rs, VoucherTheme object) throws SQLException {
		
		object.setId(rs.getInt("voucher_theme_id"));
		object.setImage(rs.getString("image"));
		return object;
	}
	
	public static class Desc implements RowMapper<VoucherThemeDesc>{

		@Override
		public VoucherThemeDesc mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			
			VoucherThemeDesc desc = new VoucherThemeDesc();
			desc.setVoucherThemeId(rs.getInt("voucher_theme_id"));
			desc.setLanguageId(rs.getInt("language_id"));
			desc.setLanguageName(rs.getString("language_name"));
			desc.setLanguageImage(rs.getString("language_image"));
			desc.setName(rs.getString("name"));
			return desc;
		}
		
		
	}
}
