package org.jpoweredcart.admin.model.sale.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.jpoweredcart.admin.model.sale.IpBlacklistAdminModel;
import org.jpoweredcart.common.BaseModel;
import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.QueryBean;
import org.jpoweredcart.common.entity.sale.IpBlacklist;
import org.jpoweredcart.common.service.SettingService;
import org.springframework.jdbc.core.JdbcOperations;

public class IpBlacklistAdminModelImpl extends BaseModel implements IpBlacklistAdminModel {

	
	public IpBlacklistAdminModelImpl(SettingService settingService,
			JdbcOperations jdbcOperations) {
		super(settingService, jdbcOperations);
	}

	@Override
	public void create(IpBlacklist blacklist) {
		String sql = "INSERT INTO " +quoteTable("customer_ip_blacklist")+ "(ip) VALUES (?)";
		getJdbcOperations().update(sql, blacklist.getIp());
	}

	@Override
	public void update(IpBlacklist blacklist) {
		String sql = "UPDATE " +quoteTable("customer_ip_blacklist")+ " SET ip=? WHERE customer_ip_blacklist_id=?";
		getJdbcOperations().update(sql, blacklist.getIp(), blacklist.getId());
	}
	
	
	@Override
	public void delete(Integer blacklistId) {
		String sql = "DELETE FROM "+quoteTable("customer_ip_blacklist")+" WHERE customer_ip_blacklist_id=?";
		getJdbcOperations().update(sql, blacklistId);
	}
	
	@Override
	public IpBlacklist get(Integer blacklistId) {
		String sql = "SELECT * FROM " +quoteTable("customer_ip_blacklist")+ " WHERE customer_ip_blacklist_id = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{blacklistId}, new IpBlacklistRowMapper());
	}
	
	@Override
	public List<IpBlacklist> getList(PageParam pageParam) {
		String sql = "SELECT *, (SELECT COUNT(DISTINCT customer_id) FROM " +quoteTable("customer_ip")
				+ " ci WHERE ci.ip = cib.ip) AS total FROM " +quoteTable("customer_ip_blacklist")+" cib";
		QueryBean query = createPaginationQueryFromSql(sql, pageParam, new String[]{"ip"});
		List<IpBlacklist> blacklists = getJdbcOperations().query(query.getSql(), 
				query.getParameters(), new IpBlacklistRowMapper(){
					@Override
					public IpBlacklist mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						IpBlacklist blacklist = super.mapRow(rs, rowNum);
						blacklist.setTotalCustomers(rs.getInt("total"));
						return blacklist;
					}
		});
		return blacklists;
	}

	@Override
	public int getTotal() {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("customer_ip_blacklist");
		return getJdbcOperations().queryForInt(sql);
	}

}
