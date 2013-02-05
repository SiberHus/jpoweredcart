package org.jpoweredcart.admin.model.localisation.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.jpoweredcart.admin.model.localisation.TaxClassAdminModel;
import org.jpoweredcart.common.BaseModel;
import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.QueryBean;
import org.jpoweredcart.common.entity.localisation.TaxClass;
import org.jpoweredcart.common.entity.localisation.TaxRule;
import org.jpoweredcart.common.service.SettingService;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

public class TaxClassAdminModelImpl extends BaseModel implements TaxClassAdminModel {

	public TaxClassAdminModelImpl(SettingService configService, JdbcOperations jdbcOperations){
		super(configService, jdbcOperations);
	}
	
	@Transactional
	@Override
	public void addTaxClass(final TaxClass taxClass) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcOperations().update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				String sql = "INSERT INTO "+quoteTable("tax_class")+"(title, description, date_added) VALUES(?,?,?)";
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, taxClass.getTitle());
				ps.setString(2, taxClass.getDescription());
				ps.setTimestamp(3, new Timestamp(new Date().getTime()));
				return ps;
			}
		}, keyHolder);
		
		int taxClassId = keyHolder.getKey().intValue();
		
		addTaxRules(taxClassId, taxClass.getTaxRules());
	}
	
	@Transactional
	@Override
	public void updateTaxClass(TaxClass taxClass) {
		String sql = "UPDATE " +quoteTable("tax_class")+ " SET title = ?, description = ?, " +
				"date_modified = ? WHERE tax_class_id = ?";
		getJdbcOperations().update(sql, taxClass.getTitle(), taxClass.getDescription(),
				new Date(), taxClass.getId());
		
		sql = "DELETE FROM " +quoteTable("tax_rule")+ " WHERE tax_class_id = ?";
		
		getJdbcOperations().update(sql, taxClass.getId());
		
		addTaxRules(taxClass.getId(), taxClass.getTaxRules());
	}
	
	private void addTaxRules(Integer taxClassId, List<TaxRule> taxRules){
		if(taxRules!=null){
			String sql = "INSERT INTO "+quoteTable("tax_rule")
					+"(tax_class_id, tax_rate_id, based, priority) VALUES(?,?,?,?)";
			for(TaxRule taxRule: taxRules){
				getJdbcOperations().update(sql, taxClassId, taxRule.getTaxRateId(),
						taxRule.getBased(), taxRule.getPriority());
			}
		}
	}
	
	@Override
	public void saveTaxClass(TaxClass taxClass){
		if(taxClass.getId()!=null){
			updateTaxClass(taxClass);
		}else{
			addTaxClass(taxClass);
		}
	}
	
	@Transactional
	@Override
	public void deleteTaxClass(Integer taxClassId) {
		
		String sql = "DELETE FROM " +quoteTable("tax_class")+ " WHERE tax_class_id = ?";
		getJdbcOperations().update(sql, taxClassId);
		
		sql = "DELETE FROM " +quoteTable("tax_rule")+ " WHERE tax_class_id = ?";
		getJdbcOperations().update(sql, taxClassId);
		
	}

	@Override
	public TaxClass getTaxClass(Integer taxClassId) {
		String sql = "SELECT * FROM " +quoteTable("tax_class")+ " WHERE tax_class_id = ?";
		TaxClass taxClass = getJdbcOperations().queryForObject(sql, 
				new Object[]{taxClassId}, new TaxClassRowMapper());
		sql = "SELECT * FROM "+quoteTable("tax_rule")+ " WHERE tax_class_id =?";
		List<TaxRule> taxRules = getJdbcOperations()
				.query(sql, new Object[]{taxClassId}, new TaxRuleRowMapper());
		taxClass.setTaxRules(taxRules);
		return taxClass;
	}
	
	@Override
	public List<TaxClass> getTaxClasses(PageParam pageParam) {
		QueryBean query = createPaginationQuery("tax_class", pageParam, 
				new String[]{"tax_class_id"});
		List<TaxClass> taxClassList = getJdbcOperations()
				.query(query.getSql(), query.getParameters(), new TaxClassRowMapper());
		return taxClassList;
	}

	@Override
	public int getTotalTaxClasses() {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("tax_class");
		return getJdbcOperations().queryForInt(sql);
	}
	
	
}
