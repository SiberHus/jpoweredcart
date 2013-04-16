package com.jpoweredcart.admin.model.sale.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.jpoweredcart.admin.form.sale.AffiliateForm;
import com.jpoweredcart.admin.form.sale.filter.AffiliateFilter;
import com.jpoweredcart.admin.model.sale.AffiliateAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.QueryBean;
import com.jpoweredcart.common.entity.sale.Affiliate;
import com.jpoweredcart.common.entity.sale.AffiliateTransaction;
import com.jpoweredcart.common.entity.sale.jdbc.AffiliateRowMapper;
import com.jpoweredcart.common.security.Password;

public class AffiliateAdminModelImpl extends BaseModel implements AffiliateAdminModel {
	
	
	@Transactional
	@Override
	public void create(AffiliateForm affForm) {
		
		String sql = "INSERT INTO "+quoteTable("affiliate")+"(firstname, lastname, email, telephone, fax, " +
				"salt, password, company, address_1, address_2, city, postcode, country_id, zone_id, code, " +
				"commission, tax, payment, cheque, paypal, bank_name, bank_branch_number, bank_swift_code, " +
				"bank_account_name, bank_account_number, status, date_added) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String salt = Password.generateSalt();
		String password = Password.encode(salt, affForm.getPassword());
		getJdbcOperations().update(sql, affForm.getFirstName(), affForm.getLastName(), affForm.getEmail(),
			affForm.getTelephone(), affForm.getFax(), salt, password, affForm.getCompany(), 
			affForm.getAddress1(), affForm.getAddress2(), affForm.getCity(), affForm.getPostcode(),
			affForm.getCountryId(), affForm.getZoneId(), affForm.getCode(), affForm.getCommission(),
			affForm.getTax(), affForm.getPayment(), affForm.getCheque(), affForm.getPaypal(),
			affForm.getBankName(), affForm.getBankBranchNumber(), affForm.getBankSwiftCode(),
			affForm.getBankAccountName(), affForm.getBankAccountNumber(), affForm.getStatus(), new Date());
	}

	@Transactional
	@Override
	public void update(AffiliateForm affForm) {
		
		String sql = "UPDATE "+quoteTable("affiliate")+" SET firstname=?, lastname=?, email=?, telephone=?, fax=?, " +
			"company=?, address_1=?, address_2=?, city=?, postcode=?, country_id=?, zone_id=?, code=?, " +
			"commission=?, tax=?, payment=?, cheque=?, paypal=?, bank_name=?, bank_branch_number=?, bank_swift_code=?, " +
			"bank_account_name=?, bank_account_number=?, status=? WHERE affiliate_id=?";
		getJdbcOperations().update(sql, affForm.getFirstName(), affForm.getLastName(), affForm.getEmail(),
			affForm.getTelephone(), affForm.getFax(), affForm.getCompany(), 
			affForm.getAddress1(), affForm.getAddress2(), affForm.getCity(), affForm.getPostcode(),
			affForm.getCountryId(), affForm.getZoneId(), affForm.getCode(), affForm.getCommission(),
			affForm.getTax(), affForm.getPayment(), affForm.getCheque(), affForm.getPaypal(),
			affForm.getBankName(), affForm.getBankBranchNumber(), affForm.getBankSwiftCode(),
			affForm.getBankAccountName(), affForm.getBankAccountNumber(), affForm.getStatus(), affForm.getId());
		if(StringUtils.isNotBlank(affForm.getPassword())){
			String salt = Password.generateSalt();
			String password = Password.encode(salt, affForm.getPassword());
			sql = "UPDATE "+quoteTable("affiliate")+" SET salt=?, password=? WHERE affiliate_id=?";
			getJdbcOperations().update(sql, salt, password, affForm.getId());
		}
	}

	@Transactional
	@Override
	public void delete(Integer affId) {
		
		String sql = "DELETE FROM "+quoteTable("affiliate")+" WHERE affiliate_id =?";
		getJdbcOperations().update(sql, affId);
		
		sql = "DELETE FROM "+quoteTable("affiliate_transaction")+" WHERE affiliate_id =?";
		getJdbcOperations().update(sql, affId);
	}
	
	@Override
	public AffiliateForm newForm() {
		
		String uniqueCode = null;
		int count = 0;
		
		do{
			uniqueCode = RandomStringUtils.randomAlphanumeric(13);
			String sql = "SELECT COUNT(*) FROM "+quoteTable("affiliate")+" WHERE code=?";
			count = getJdbcOperations().queryForObject(sql, Integer.class, uniqueCode);
		}while(count>0);
		
		AffiliateForm form = new AffiliateForm();
		form.setCode(uniqueCode);
		
		return form;
	}
	
	@Override
	public AffiliateForm getForm(Integer affId) {
		
		return (AffiliateForm)get(affId, AffiliateForm.class);
	}
	
	@Override
	public Affiliate get(Integer affId, Class<? extends Affiliate> clazz) {
		String sql = "SELECT * FROM "+quoteTable("affiliate")+" WHERE affiliate_id=?";
		return getJdbcOperations().queryForObject(sql, new Object[]{affId}, 
				new AffiliateRowMapper().setTargetClass(clazz));
	}

	@Override
	public Affiliate getOneByEmail(String email) {
		String sql = "SELECT DISTINCT * FROM "+quoteTable("affiliate")+" WHERE LCASE(email) =?";
		return getJdbcOperations().queryForObject(sql, new Object[]{email}, 
				new AffiliateRowMapper());
	}
	
	@Override
	public List<Affiliate> getList(AffiliateFilter filter, PageParam pageParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT *, CONCAT(a.firstname, ' ', a.lastname) AS name, (SELECT SUM(at.amount) FROM "+quoteTable("affiliate_transaction")+
				" at WHERE at.affiliate_id = a.affiliate_id GROUP BY at.affiliate_id) AS balance FROM "+
				quoteTable("affiliate")+" a WHERE 1=1 ";
		if(StringUtils.isNotBlank(filter.getAffiliateName())){
			sql += "AND CONCAT(a.firstname, ' ', a.lastname) LIKE ? ";
			params.add("%"+filter.getAffiliateName()+"%");
		}
		if(StringUtils.isNotBlank(filter.getEmail())){
			sql += "AND LCASE(a.email) = ? ";
			params.add(filter.getEmail().toLowerCase());
		}
		if(StringUtils.isNotBlank(filter.getCode())){
			sql += "AND a.code = ? ";
			params.add(filter.getCode());
		}
		if(filter.getStatus()!=null){
			sql += "AND a.status = ? ";
			params.add(filter.getStatus());
		}
		if(filter.getApproved()!=null){
			sql += "AND a.approved = ? ";
			params.add(filter.getApproved());
		}
		if(filter.getDateAdded()!=null){
			sql += "AND a.date_added >= ? ";
			params.add(filter.getDateAdded().getTime());
		}
		
		//sortedKeys = {"name","a.email","a.code","a.status","a.approved","a.date_added"}
		QueryBean query = createPaginationQuery(sql, pageParam);
		query.addParameters(params.toArray());
		System.out.println(query.getSql());
		System.out.println(query.getParameterList());
		return getJdbcOperations().query(query.getSql(), 
				query.getParameters(), new AffiliateRowMapper(){
					@Override
					public Affiliate mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Affiliate aff = super.mapRow(rs, rowNum);
						aff.setBalance(rs.getBigDecimal("balance"));
						return aff;
					}
		});
		
	}

	@Transactional
	@Override
	public void approve(Integer affId) {
		
		String sql = "UPDATE " +quoteTable("affiliate")+ " SET approved = ? WHERE affiliate_id=?";
		getJdbcOperations().update(sql, Boolean.TRUE, affId);
	}
	
	@Override
	public List<Affiliate> getAllByNewsletter() {
		
		String sql = "SELECT * FROM "+quoteTable("affiliate")+ " WHERE newsletter =? ORDER BY firstname, lastname, email";
		return getJdbcOperations().query(sql, new Object[]{Boolean.TRUE}, new AffiliateRowMapper());
	}
	
	@Override
	public int getTotal(AffiliateFilter filter) {
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT COUNT(*) AS total FROM "+quoteTable("affiliate")+" WHERE 1=1 ";
		if(StringUtils.isNotBlank(filter.getAffiliateName())){
			sql += "AND CONCAT(firstname, ' ', lastname) LIKE ? ";
			params.add("%"+filter.getAffiliateName()+"%");
		}
		if(StringUtils.isNotBlank(filter.getEmail())){
			sql += "AND LCASE(email) = ? ";
			params.add(filter.getEmail().toLowerCase());
		}
		if(StringUtils.isNotBlank(filter.getCode())){
			sql += "AND code = ? ";
			params.add(filter.getCode());
		}
		if(filter.getStatus()!=null){
			sql += "AND status = ? ";
			params.add(filter.getStatus());
		}
		if(filter.getApproved()!=null){
			sql += "AND approved = ? ";
			params.add(filter.getApproved());
		}
		if(filter.getDateAdded()!=null){
			sql += "AND date_added >= ? ";
			params.add(filter.getDateAdded().getTime());
		}
		
		return getJdbcOperations().queryForObject(sql, Integer.class, params.toArray());
		
	}
	
	@Override
	public int getTotalAwaitingApproval() {
		
		String sql = "SELECT COUNT(*) AS total FROM "+quoteTable("affiliate")+
				" WHERE status=? OR approved =?";
		
		return getJdbcOperations().queryForObject(sql, Integer.class, 0, Boolean.FALSE);
	}
	
	@Override
	public int getTotalByCountryId(Integer countryId) {
		
		String sql = "SELECT COUNT(*) AS total FROM "+quoteTable("affiliate")+" WHERE country_id = ?";
		return getJdbcOperations().queryForObject(sql, Integer.class, countryId);
	}
	
	@Override
	public int getTotalByZoneId(Integer zoneId) {
		
		String sql = "SELECT COUNT(*) AS total FROM "+quoteTable("affiliate")+" WHERE zone_id=?";
		return getJdbcOperations().queryForObject(sql, Integer.class, zoneId);
	}

	@Transactional
	@Override
	public void addTransaction(AffiliateTransaction trans) {
		
		String sql = "INSERT INTO "+quoteTable("affiliate_transaction")+"(affiliate_id, order_id, description, amount, date_added) VALUES(?,?,?,?,?)";
		getJdbcOperations().update(sql, trans.getAffiliateId(), trans.getOrderId(), 
				trans.getDescription(), trans.getAmount(), new Date());
	}
	
	@Transactional
	@Override
	public void deleteTransaction(Integer orderId) {
		String sql = "DELETE FROM "+quoteTable("affiliate_transaction")+" WHERE order_id =?";
		getJdbcOperations().update(sql, orderId);
	}

	@Override
	public List<AffiliateTransaction> getTransactions(Integer affId, int start,
			int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalTransactions(Integer affId) {
		
		String sql = "SELECT COUNT(*) AS total  FROM "+quoteTable("affiliate_transaction")+" WHERE affiliate_id = ?";
		return getJdbcOperations().queryForObject(sql, Integer.class, affId);
	}

	@Override
	public BigDecimal getTransactionTotal(Integer affId) {
		
		String sql = "SELECT SUM(amount) AS total FROM "+quoteTable("affiliate_transaction")+ " WHERE affiliate_id = ?";
		return getJdbcOperations().queryForObject(sql, BigDecimal.class, affId);
	}
	
	@Override
	public int getTotalTransactionsByOrderId(Integer orderId) {
		
		String sql = "SELECT COUNT(*) AS total FROM "+quoteTable("affiliate_transaction")+" WHERE order_id = ?";
		return getJdbcOperations().queryForObject(sql, Integer.class, orderId);
	}
	
}
