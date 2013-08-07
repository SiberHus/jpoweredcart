package com.jpoweredcart.common.entity.sale.jdbc;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.sale.Affiliate;
import com.jpoweredcart.common.jdbc.ObjectFactoryRowMapper;

public class AffiliateRowMapper extends ObjectFactoryRowMapper<Affiliate>{
	
	@Override
	public Affiliate mapRow(ResultSet rs, Affiliate object) throws SQLException {
		
		object.setId(rs.getInt("affiliate_id"));
		object.setFirstName(rs.getString("firstname"));
		object.setLastName(rs.getString("lastname"));
		object.setEmail(rs.getString("email"));
		object.setTelephone(rs.getString("telephone"));
		object.setFax(rs.getString("fax"));
		object.setPassword(rs.getString("password"));
		object.setSalt(rs.getString("salt"));
		object.setCompany(rs.getString("company"));
		object.setWebsite(rs.getString("website"));
		object.setAddress1(rs.getString("address_1"));
		object.setAddress2(rs.getString("address_2"));
		object.setCity(rs.getString("city"));
		object.setPostcode(rs.getString("postcode"));
		object.setCountryId(rs.getInt("country_id"));
		object.setZoneId(rs.getInt("zone_id"));
		object.setCode (rs.getString("code"));
		BigDecimal commission = rs.getBigDecimal("commission");
		if(commission==null) commission = BigDecimal.ZERO;
		object.setCommission(commission);
		object.setTax(rs.getString("tax"));
		object.setPayment(rs.getString("payment"));
		object.setCheque(rs.getString("cheque"));
		object.setPaypal(rs.getString("paypal"));
		object.setBankName(rs.getString("bank_name"));
		object.setBankBranchNumber(rs.getString("bank_branch_number"));
		object.setBankSwiftCode(rs.getString("bank_swift_code"));
		object.setBankAccountName(rs.getString("bank_account_name"));
		object.setBankAccountNumber(rs.getString("bank_account_number"));
		object.setIp(rs.getString("ip"));
		object.setStatus(rs.getShort("status"));
		object.setApproved(rs.getBoolean("approved"));
		object.setDateAdded(rs.getTimestamp("date_added"));
		return object;
	}
	
}
