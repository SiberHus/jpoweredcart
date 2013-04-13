package com.jpoweredcart.common.entity.sale.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpoweredcart.common.entity.sale.Customer;

public class CustomerRowMapper implements RowMapper<Customer>{

	public Customer newObject(){
		return new Customer();
	}
	
	@Override
	public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Customer cust = newObject();
		cust.setId(rs.getInt("customer_id"));
		cust.setStoreId(rs.getInt("store_id"));
		cust.setFirstName(rs.getString("firstname"));
		cust.setLastName(rs.getString("lastname"));
		cust.setEmail(rs.getString("email"));
		cust.setTelephone(rs.getString("telephone"));
		cust.setFax(rs.getString("fax"));
		cust.setPassword(rs.getString("password"));
		cust.setSalt(rs.getString("salt"));
		cust.setCart(rs.getString("cart"));
		cust.setWishlist(rs.getString("wishlist"));
		cust.setNewsletter(rs.getShort("newsletter"));
		cust.setAddressId(rs.getInt("address_id"));
		cust.setCustomerGroupId(rs.getInt("customer_group_id"));
		cust.setIp(rs.getString(rs.getString("ip")));
		cust.setStatus(rs.getShort("status"));
		cust.setApproved(rs.getShort("approved"));
		cust.setToken(rs.getString("token"));
		cust.setDateAdded(rs.getDate("date_added"));
		return cust;
	}

	
}
