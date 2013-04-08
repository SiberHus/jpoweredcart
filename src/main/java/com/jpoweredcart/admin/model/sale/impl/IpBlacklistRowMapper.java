package com.jpoweredcart.admin.model.sale.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.sale.IpBlacklist;
import org.springframework.jdbc.core.RowMapper;

public class IpBlacklistRowMapper implements RowMapper<IpBlacklist>{

	@Override
	public IpBlacklist mapRow(ResultSet rs, int rowNum) throws SQLException {
		IpBlacklist blacklist = new IpBlacklist();
		blacklist.setId(rs.getInt("customer_ip_blacklist_id"));
		blacklist.setIp(rs.getString("ip"));
		return blacklist;
	}

}
