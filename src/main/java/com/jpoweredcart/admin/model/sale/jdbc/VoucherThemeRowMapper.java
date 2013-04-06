package com.jpoweredcart.admin.model.sale.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.admin.bean.sale.VoucherThemeForm;
import com.jpoweredcart.common.entity.sale.VoucherTheme;
import com.jpoweredcart.common.entity.sale.VoucherThemeDesc;
import org.springframework.jdbc.core.RowMapper;

public class VoucherThemeRowMapper implements RowMapper<VoucherTheme>{
	
	@Override
	public VoucherTheme mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		VoucherTheme vt = new VoucherTheme();
		vt.setId(rs.getInt("voucher_theme_id"));
		vt.setImage(rs.getString("image"));
		return vt;
	}
	
	public static class Form implements RowMapper<VoucherThemeForm>{

		@Override
		public VoucherThemeForm mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			VoucherThemeForm vtForm = new VoucherThemeForm();
			vtForm.setId(rs.getInt("voucher_theme_id"));
			vtForm.setImage(rs.getString("image"));
			return vtForm;
		}
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
