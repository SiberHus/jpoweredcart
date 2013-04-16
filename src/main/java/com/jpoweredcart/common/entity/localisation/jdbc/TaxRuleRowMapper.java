package com.jpoweredcart.common.entity.localisation.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.localisation.TaxRule;
import com.jpoweredcart.common.jdbc.ObjectFactoryRowMapper;

public class TaxRuleRowMapper extends ObjectFactoryRowMapper<TaxRule>{

	@Override
	public TaxRule mapRow(ResultSet rs, TaxRule object) throws SQLException {
		object.setId(rs.getInt("tax_rule_id"));
		object.setTaxClassId(rs.getInt("tax_class_id"));
		object.setTaxRateId(rs.getInt("tax_rate_id"));
		object.setBased(rs.getString("based"));
		object.setPriority(rs.getInt("priority"));
		return object;
	}
	
	
}
