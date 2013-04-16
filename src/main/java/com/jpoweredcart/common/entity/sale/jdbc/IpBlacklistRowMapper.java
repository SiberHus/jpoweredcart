package com.jpoweredcart.common.entity.sale.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.sale.IpBlacklist;
import com.jpoweredcart.common.jdbc.ObjectFactoryRowMapper;

public class IpBlacklistRowMapper extends ObjectFactoryRowMapper<IpBlacklist>{
	
	@Override
	public IpBlacklist mapRow(ResultSet rs, IpBlacklist object) throws SQLException {
		object.setId(rs.getInt("customer_ip_blacklist_id"));
		object.setIp(rs.getString("ip"));
		return object;
	}

}
