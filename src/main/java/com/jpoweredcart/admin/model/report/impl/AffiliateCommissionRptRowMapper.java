package com.jpoweredcart.admin.model.report.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.admin.bean.report.AffiliateCommissionRpt;

public class AffiliateCommissionRptRowMapper implements RowMapper<AffiliateCommissionRpt>{
	
	@Override
	public AffiliateCommissionRpt mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		
		AffiliateCommissionRpt rpt = new AffiliateCommissionRpt();
		rpt.setAffiliateId(rs.getInt("affiliate_id"));
		String affiliateName = rs.getString("firstname")+" "+rs.getString("lastname");
		rpt.setAffiliateName(affiliateName);
		rpt.setEmail(rs.getString("email"));
		rpt.setStatus(rs.getShort("status"));
		rpt.setCommission(rs.getBigDecimal("commission"));
		rpt.setOrders(rs.getInt("orders"));
		rpt.setTotal(rs.getBigDecimal("total"));
		
		return rpt;
	}
	
}
