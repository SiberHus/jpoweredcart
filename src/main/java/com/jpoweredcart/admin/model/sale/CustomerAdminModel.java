package com.jpoweredcart.admin.model.sale;

import java.math.BigDecimal;
import java.util.List;

import com.jpoweredcart.admin.form.sale.CustomerForm;
import com.jpoweredcart.admin.form.sale.filter.CustomerFilter;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.sale.Address;
import com.jpoweredcart.common.entity.sale.Customer;
import com.jpoweredcart.common.entity.sale.CustomerHistory;
import com.jpoweredcart.common.entity.sale.CustomerIp;
import com.jpoweredcart.common.entity.sale.CustomerReward;
import com.jpoweredcart.common.entity.sale.CustomerTransaction;

public interface CustomerAdminModel {
	
	public void create(CustomerForm custForm);
	
	public void update(CustomerForm custForm);
	
	public void delete(Integer custId);
	
	public CustomerForm newForm();
	
	public CustomerForm getForm(Integer custId);
	
	public Customer get(Integer custId, Class<? extends Customer> clazz);
	
	public Customer getOneByEmail(String email);
	
	public List<Customer> getList(CustomerFilter filter, PageParam pageParam);
	
	public void approve(Integer custId);
	
	public Address getAddress(Integer addrId);
	
	public List<Address> getAddresses(Integer custId);
	
	public int getTotal(CustomerFilter filter);
	
	public int getTotalAwaitingApproval();
	
	public int getTotalAddressesByCustomerId(Integer custId);
	
	public int getTotalAddressesByCountryId(Integer countryId);
	
	public int getTotalAddressesByZoneId(Integer zoneId);
	
	public int getTotalByCustomerGroupId(Integer custGrpId);
	
	public void addHistory(CustomerHistory history);
	
	public List<CustomerHistory> getHistories(Integer custId, PageParam pageParam);
	
	public int getTotalHistories(Integer custId);
	
	public void addTransaction(CustomerTransaction trans);
	
	public void deleteTransaction(Integer orderId);
	
	public List<CustomerTransaction> getTransactions(Integer custId, PageParam pageParam);
	
	public BigDecimal getTransactionTotal(Integer custId);
	
	public int getTotalTransactions(Integer custId);
	
	public int getTotalTransactionsByOrderId(Integer orderId);
	
	public void addReward(CustomerReward reward);
	
	public void deleteReward(Integer orderId);
	
	public List<CustomerReward> getRewards(Integer custId, PageParam pageParam);
	
	public int getRewardTotal(Integer custId);
	
	public int getTotalRewards(Integer custId);
	
	public int getTotalRewardsByOrderId(Integer orderId);
	
	public List<CustomerIp> getIpsByCustomerId(Integer custId);
	
	public int getTotalByIp(String ip);
	
	public void addBanIp(String ip);
	
	public void removeBanIp(String ip);
	
	public int getTotalBanIpsByIp(String ip);
	
	
}
