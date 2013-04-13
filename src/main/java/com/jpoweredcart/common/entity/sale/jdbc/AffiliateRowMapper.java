package com.jpoweredcart.common.entity.sale.jdbc;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.common.entity.sale.Affiliate;

public class AffiliateRowMapper implements RowMapper<Affiliate>{

	public Affiliate newObject(){
		return new Affiliate();
	}
	
	@Override
	public Affiliate mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Affiliate aff = newObject();
		aff.setId(rs.getInt("affiliate_id"));
		aff.setFirstName(rs.getString("firstname"));
		aff.setLastName(rs.getString("lastname"));
		aff.setEmail(rs.getString("email"));
		aff.setTelephone(rs.getString("telephone"));
		aff.setFax(rs.getString("fax"));
		aff.setPassword(rs.getString("password"));
		aff.setSalt(rs.getString("salt"));
		aff.setCompany(rs.getString("company"));
		aff.setWebsite(rs.getString("website"));
		aff.setAddress1(rs.getString("address_1"));
		aff.setAddress2(rs.getString("address_2"));
		aff.setCity(rs.getString("city"));
		aff.setPostcode(rs.getString("postcode"));
		aff.setCountryId(rs.getInt("country_id"));
		aff.setZoneId(rs.getInt("zone_id"));
		aff.setCode (rs.getString("code"));
		BigDecimal commission = rs.getBigDecimal("commission");
		if(commission==null) commission = BigDecimal.ZERO;
		aff.setCommission(commission);
		aff.setTax(rs.getString("tax"));
		aff.setPayment(rs.getString("payment"));
		aff.setCheque(rs.getString("cheque"));
		aff.setPaypal(rs.getString("paypal"));
		aff.setBankName(rs.getString("bank_name"));
		aff.setBankBranchNumber(rs.getString("bank_branch_number"));
		aff.setBankSwiftCode(rs.getString("bank_swift_code"));
		aff.setBankAccountName(rs.getString("bank_account_name"));
		aff.setBankAccountNumber(rs.getString("bank_account_number"));
		aff.setIp(rs.getString("ip"));
		aff.setStatus(rs.getShort("status"));
		aff.setApproved(rs.getBoolean("approved"));
		aff.setDateAdded(rs.getTimestamp("date_added"));
		return aff;
	}
	
}
