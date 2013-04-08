package com.jpoweredcart.admin.model.localisation.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.localisation.TaxRule;
import org.springframework.jdbc.core.RowMapper;

public class TaxRuleRowMapper implements RowMapper<TaxRule>{

	@Override
	public TaxRule mapRow(ResultSet rs, int rowNum) throws SQLException {
		TaxRule taxRule = new TaxRule();
		taxRule.setId(rs.getInt("tax_rule_id"));
		taxRule.setTaxClassId(rs.getInt("tax_class_id"));
		taxRule.setTaxRateId(rs.getInt("tax_rate_id"));
		taxRule.setBased(rs.getString("based"));
		taxRule.setPriority(rs.getInt("priority"));
		return taxRule;
	}
	
	
}
