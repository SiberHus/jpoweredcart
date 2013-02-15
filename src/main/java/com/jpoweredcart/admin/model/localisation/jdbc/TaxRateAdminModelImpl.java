package com.jpoweredcart.admin.model.localisation.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import com.jpoweredcart.admin.model.localisation.TaxRateAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.QueryBean;
import com.jpoweredcart.common.entity.localisation.TaxRate;

public class TaxRateAdminModelImpl extends BaseModel implements TaxRateAdminModel {

	
	@Transactional
	@Override
	public void create(final TaxRate taxRate) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcOperations().update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				String sql = "INSERT INTO "+quoteTable("tax_rate")+"(name, rate, type, geo_zone_id, date_added, date_modified) VALUES(?,?,?,?,?,?)";
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, taxRate.getName());
				ps.setBigDecimal(2, taxRate.getRate());
				ps.setString(3, taxRate.getType());
				ps.setInt(4, taxRate.getGeoZoneId());
				Timestamp timestamp = new Timestamp(new Date().getTime());
				ps.setTimestamp(5, timestamp);
				ps.setTimestamp(6, timestamp);
				return ps;
			}
		}, keyHolder);
		
		int taxRateId = keyHolder.getKey().intValue();
		
		addCustomerGroupIds(taxRateId, taxRate.getCustomerGroupIds());
		
	}
	
	@Transactional
	@Override
	public void update(TaxRate taxRate) {
		String sql = "UPDATE " +quoteTable("tax_rate")+ " SET name = ?, rate = ?, type = ?, geo_zone_id = ?," +
				"date_modified = ? WHERE tax_rate_id = ?";
		getJdbcOperations().update(sql, taxRate.getName(), taxRate.getRate(), taxRate.getType(), 
				taxRate.getGeoZoneId(), new Timestamp(new Date().getTime()), taxRate.getId());
		
		sql = "DELETE FROM " +quoteTable("tax_rate_to_customer_group")+ " WHERE tax_rate_id = ?";
		
		getJdbcOperations().update(sql, taxRate.getId());
		
		addCustomerGroupIds(taxRate.getId(), taxRate.getCustomerGroupIds());
	}
	
	private void addCustomerGroupIds(Integer taxRateId, List<Integer> customerGroupIds){
		if(customerGroupIds==null){
			return;
		}
		
		String sql = "INSERT INTO "+quoteTable("tax_rate_to_customer_group")
				+"(tax_rate_id, customer_group_id) VALUES(?,?)";
		for(Integer groupId: customerGroupIds){
			getJdbcOperations().update(sql, taxRateId, groupId);
		}
	}

	@Transactional
	@Override
	public void delete(Integer taxRateId) {
		String sql = "DELETE FROM " +quoteTable("tax_rate")+ " WHERE tax_rate_id = ?";
		getJdbcOperations().update(sql, taxRateId);
		
		sql = "DELETE FROM " +quoteTable("tax_rate_to_customer_group")+ " WHERE tax_rate_id = ?";
		getJdbcOperations().update(sql, taxRateId);
	}
	
	
	@Override
	public TaxRate get(Integer taxRateId) {
		String sql = "SELECT * FROM " +quoteTable("tax_rate")+ " WHERE tax_rate_id = ?";
		TaxRate taxRate = getJdbcOperations().queryForObject(sql, 
				new Object[]{taxRateId}, new TaxRateRowMapper());
		
		sql = "SELECT customer_group_id FROM "+quoteTable("tax_rate_to_customer_group")+ " WHERE tax_rate_id =?";
		List<Integer> customerGroupIds = getJdbcOperations()
				.queryForList(sql, Integer.class, taxRateId);
		taxRate.setCustomerGroupIds(customerGroupIds);
		
		return taxRate;
	}

	@Override
	public List<TaxRate> getList(PageParam pageParam) {
		String sql = "SELECT tr.tax_rate_id, tr.name AS name, tr.rate, tr.type, tr.geo_zone_id, gz.name AS geo_zone, tr.date_added, tr.date_modified FROM " 
				+quoteTable("tax_rate")+ " tr LEFT JOIN " +quoteTable("geo_zone")+ " gz ON (tr.geo_zone_id = gz.geo_zone_id)";
		QueryBean query = createPaginationQueryFromSql(sql, pageParam, 
				new String[]{"tr.name", "tr.rate", "tr.type", "gz.name", "tr.date_added", "tr.date_modified"});
		List<TaxRate> taxRateList = getJdbcOperations()
				.query(query.getSql(), query.getParameters(), new TaxRateRowMapper(){
					@Override
					public TaxRate mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						TaxRate taxRate = super.mapRow(rs, rowNum);
						taxRate.setGeoZone(rs.getString("geo_zone"));
						return taxRate;
					}
				});
		return taxRateList;
	}
	
	@Override
	public int getTotal() {
		String sql = "SELECT COUNT(*) AS total FROM "+quoteTable("tax_rate");
		return getJdbcOperations().queryForInt(sql);
	}

	
}
