package com.jpoweredcart.common.entity.sale.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.common.entity.sale.VoucherTheme;
import com.jpoweredcart.common.entity.sale.VoucherThemeDesc;

public class VoucherThemeRowMapper implements RowMapper<VoucherTheme>{
	
	public VoucherTheme newObject(){
		return new VoucherTheme();
	}
	
	@Override
	public VoucherTheme mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		VoucherTheme vt = newObject();
		vt.setId(rs.getInt("voucher_theme_id"));
		vt.setImage(rs.getString("image"));
		return vt;
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
