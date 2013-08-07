package com.jpoweredcart.admin.model.sale.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.jpoweredcart.admin.form.sale.CustomerForm;
import com.jpoweredcart.admin.form.sale.filter.CustomerFilter;
import com.jpoweredcart.admin.model.sale.CustomerAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.sale.Address;
import com.jpoweredcart.common.entity.sale.Customer;
import com.jpoweredcart.common.entity.sale.CustomerHistory;
import com.jpoweredcart.common.entity.sale.CustomerIp;
import com.jpoweredcart.common.entity.sale.CustomerReward;
import com.jpoweredcart.common.entity.sale.CustomerTransaction;

public class CustomerAdminModelImpl extends BaseModel implements CustomerAdminModel {

	@Transactional
	@Override
	public void create(CustomerForm custForm) {
		// TODO Auto-generated method stub
		
	}

	@Transactional
	@Override
	public void update(CustomerForm custForm) {
		// TODO Auto-generated method stub
		
	}

	@Transactional
	@Override
	public void delete(Integer custId) {
		String tables[] = new String[]{
			"customer", "customer_reward", "customer_transaction",
			"customer_ip", "address"
		};
		String sql = null;
		for(String table: tables){
			sql = "DELETE FROM "+quoteTable(table)+" WHERE customer_id=?";
			getJdbcOperations().update(sql, custId);
		}
	}

	@Override
	public CustomerForm newForm() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public CustomerForm getForm(Integer custId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer get(Integer custId, Class<? extends Customer> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer getOneByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> getList(CustomerFilter filter, PageParam pageParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public void approve(Integer custId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Address getAddress(Integer addrId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Address> getAddresses(Integer custId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotal(CustomerFilter filter) {
		
		return 0;
	}

	@Override
	public int getTotalAwaitingApproval() {
		String sql = "SELECT COUNT(*) AS total FROM "+quoteTable("customer")+" WHERE status = '0' OR approved = '0'";
		return getJdbcOperations().queryForObject(sql, null, Integer.class);
	}

	@Override
	public int getTotalAddressesByCustomerId(Integer custId) {
		String sql = "SELECT COUNT(*) AS total FROM "+quoteTable("address")+" WHERE customer_id = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{custId}, Integer.class);
	}

	@Override
	public int getTotalAddressesByCountryId(Integer countryId) {
		String sql = "SELECT COUNT(*) AS total FROM "+quoteTable("address")+" WHERE country_id = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{countryId}, Integer.class);
	}

	@Override
	public int getTotalAddressesByZoneId(Integer zoneId) {
		String sql = "SELECT COUNT(*) AS total FROM "+quoteTable("address")+" WHERE zone_id = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{zoneId}, Integer.class);
	}

	@Override
	public int getTotalByCustomerGroupId(Integer custGrpId){
		String sql = "SELECT COUNT(*) AS total FROM "+quoteTable("customer")+" WHERE customer_group_id = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{custGrpId}, Integer.class);
	}
	
	@Transactional
	@Override
	public void addHistory(CustomerHistory history) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CustomerHistory> getHistories(Integer custId,
			PageParam pageParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalHistories(Integer custId) {
		String sql = "SELECT COUNT(*) AS total FROM "+quoteTable("customer_history")+" WHERE customer_id = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{custId}, Integer.class);
	}
	
	@Transactional
	@Override
	public void addTransaction(CustomerTransaction trans) {
		// TODO Auto-generated method stub
		
	}

	@Transactional
	@Override
	public void deleteTransaction(Integer orderId) {
		String sql = "DELETE FROM "+quoteTable("customer_transaction")+" WHERE order_id = ?";
		getJdbcOperations().update(sql, orderId);
	}

	@Override
	public List<CustomerTransaction> getTransactions(Integer custId,
			PageParam pageParam) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public BigDecimal getTransactionTotal(Integer custId) {
		String sql = "SELECT SUM(amount) AS total FROM "+quoteTable("customer_transaction")+" WHERE customer_id = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{custId}, BigDecimal.class);
	}
	
	@Override
	public int getTotalTransactions(Integer custId) {
		String sql = "SELECT COUNT(*) AS total FROM "+quoteTable("customer_transaction")+" WHERE order_id = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{custId}, Integer.class);
	}

	@Override
	public int getTotalTransactionsByOrderId(Integer orderId) {
		String sql = "SELECT COUNT(*) AS total FROM "+quoteTable("customer_transaction")+" WHERE order_id = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{orderId}, Integer.class);
	}

	@Transactional
	@Override
	public void addReward(CustomerReward reward) {
		// TODO Auto-generated method stub		
	}

	@Transactional
	@Override
	public void deleteReward(Integer orderId) {
		String sql = "DELETE FROM "+quoteTable("customer_reward")+" WHERE order_id =?"; 
		getJdbcOperations().update(sql, orderId);
	}
	
	@Override
	public List<CustomerReward> getRewards(Integer custId, PageParam pageParam) {
		
		String sql = "SELECT * FROM "+quoteTable("customer_reward")+" WHERE customer_id = ? ORDER BY date_added DESC LIMIT ?,?";
		return null; //TODO: add paging
	}

	@Override
	public int getRewardTotal(Integer custId) {
		String sql = "SELECT SUM(points) AS total FROM "+quoteTable("customer_reward")+" WHERE customer_id = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{custId}, Integer.class);
	}

	@Override
	public int getTotalRewards(Integer custId) {
		String sql = "SELECT COUNT(*) AS total FROM "+quoteTable("customer_reward")+" WHERE customer_id = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{custId}, Integer.class);
	}

	@Override
	public int getTotalRewardsByOrderId(Integer orderId) {
		String sql = "SELECT COUNT(*) AS total FROM "+quoteTable("customer_reward")+" WHERE order_id = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{orderId}, Integer.class);
	}
	
	@Override
	public List<CustomerIp> getIpsByCustomerId(Integer custId) {
		String sql = "SELECT * FROM "+quoteTable("customer_ip")+" WHERE customer_id = ?";
		return null;//TODO: add jdbc op here
	}
	
	@Override
	public int getTotalByIp(String ip) {
		String sql = "SELECT COUNT(*) AS total FROM "+quoteTable("customer_ban_ip")+
				" SET "+quoteName("ip")+"=?";
		return getJdbcOperations().queryForObject(sql, new Object[]{ip}, Integer.class);
	}

	@Transactional
	@Override
	public void addBanIp(String ip) {
		String sql = "INSERT INTO "+quoteTable("customer_ban_ip")+" SET "+quoteName("ip")+"=?";
		getJdbcOperations().update(sql, ip);
	}
	
	@Transactional
	@Override
	public void removeBanIp(String ip) {
		String sql = "DELETE FROM "+quoteTable("customer_ban_ip")+" WHERE "+quoteName("ip")+"=?";
		getJdbcOperations().update(sql, ip);
	}

	@Override
	public int getTotalBanIpsByIp(String ip) {
		String sql = "SELECT COUNT(*) AS total FROM "+quoteTable("customer_ban_ip")+
				" WHERE "+quoteName("ip")+" = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{ip}, Integer.class);
	}
	
	
}
