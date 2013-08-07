package com.jpoweredcart.common.entity.sale.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jpoweredcart.common.entity.sale.Customer;
import com.jpoweredcart.common.jdbc.ObjectFactoryRowMapper;

public class CustomerRowMapper extends ObjectFactoryRowMapper<Customer>{
	
	
	@Override
	public Customer mapRow(ResultSet rs, Customer object) throws SQLException {
		object.setId(rs.getInt("customer_id"));
		object.setStoreId(rs.getInt("store_id"));
		object.setFirstName(rs.getString("firstname"));
		object.setLastName(rs.getString("lastname"));
		object.setEmail(rs.getString("email"));
		object.setTelephone(rs.getString("telephone"));
		object.setFax(rs.getString("fax"));
		object.setPassword(rs.getString("password"));
		object.setSalt(rs.getString("salt"));
		object.setCart(rs.getString("cart"));
		object.setWishlist(rs.getString("wishlist"));
		object.setNewsletter(rs.getShort("newsletter"));
		object.setAddressId(rs.getInt("address_id"));
		object.setCustomerGroupId(rs.getInt("customer_group_id"));
		object.setIp(rs.getString(rs.getString("ip")));
		object.setStatus(rs.getShort("status"));
		object.setApproved(rs.getShort("approved"));
		object.setToken(rs.getString("token"));
		object.setDateAdded(rs.getDate("date_added"));
		return object;
	}

	
}
