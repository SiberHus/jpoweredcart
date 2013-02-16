package com.jpoweredcart.admin.model.sale;

import java.math.BigDecimal;
import java.util.List;

import com.jpoweredcart.admin.bean.sale.AffiliateForm;
import com.jpoweredcart.admin.bean.sale.filter.AffiliateFilter;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.sale.Affiliate;
import com.jpoweredcart.common.entity.sale.AffiliateTransaction;

public interface AffiliateAdminModel {

	public void create(AffiliateForm affForm);
	
	public void update(AffiliateForm affForm);
	
	public void delete(Integer affId);
	
	public AffiliateForm getForm(Integer affId);
	
	public Affiliate get(Integer affId);
	
	public Affiliate getOneByEmail(String email);
	
	public List<Affiliate> getList(AffiliateFilter filter, PageParam pageParam);
	
	public void approve(Integer affId);
	
	public List<Affiliate> getAllByNewsletter();
	
	public int getTotal(AffiliateFilter filter);
	
	public int getTotalAwaitingApproval();
	
	public int getTotalByCountryId(Integer countryId);
	
	public int getTotalByZoneId(Integer zoneId);
	
	public void addTransaction(AffiliateTransaction trans);
	
	public void deleteTransaction(Integer orderId);
	
	public List<AffiliateTransaction> getTransactions(Integer affId, int start, int limit);
	
	public int getTotalTransactions(Integer affId);
	
	public BigDecimal getTransactionTotal(Integer affId);
	
	public int getTotalTransactionsByOrderId(Integer orderId);
	
}






