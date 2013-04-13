package com.jpoweredcart.admin.model.sale.impl;

import java.math.BigDecimal;
import java.util.List;

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

	@Override
	public void create(CustomerForm custForm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(CustomerForm custForm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer custId) {
		// TODO Auto-generated method stub
		
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
	public Customer get(Integer custId) {
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalAwaitingApproval() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalAddressesByCustomerId(Integer custId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalAddressesByCountryId(Integer countryId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalAddressesByZoneId(Integer zoneId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalAddressesByCustomerGroupId(Integer cgId) {
		// TODO Auto-generated method stub
		return 0;
	}

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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addTransaction(CustomerTransaction trans) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteTransaction(Integer orderId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CustomerTransaction> getTransactions(Integer custId,
			PageParam pageParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getTransactionTotal(Integer custId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalTransactions(Integer custId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalTransactionsByOrderId(Integer orderId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addReward(CustomerReward reward) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteReward(Integer orderId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CustomerReward> getRewards(Integer custId, PageParam pageParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRewardTotal(Integer custId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalRewards(Integer custId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalRewardsByOrderId(Integer orderId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<CustomerIp> getIpsByCustomerId(Integer custId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalByIp(String ip) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addBanIp(String ip) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeBanIp(String ip) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getTotalBanIpsByIp(String ip) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
