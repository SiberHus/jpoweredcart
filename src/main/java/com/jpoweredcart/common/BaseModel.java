package com.jpoweredcart.common;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcOperations;

import com.jpoweredcart.common.PageParam.OrderPair;
import com.jpoweredcart.common.system.setting.DefaultSettings;
import com.jpoweredcart.common.system.setting.SettingService;


public class BaseModel {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private SettingService settingService;
	
	@Inject
	private JdbcOperations jdbcOperations;
	
	public BaseModel(){}
	
	public Environment getEnvironment(){
		return this.settingService.getEnvironment();
	}
	
	public SettingService getSettingService() {
		return settingService;
	}
	
	public JdbcOperations getJdbcOperations() {
		return jdbcOperations;
	}
	
	/**
	 * Quote the table name and add the prefix if it has been configured.
	 *   
	 * @param env
	 * @param tableName
	 * @return
	 */
	public static String quoteTable(Environment env, String tableName){
		return quoteName(env, env.getProperty("database.prefix", "")+tableName);
	}
	
	public static String quoteName(Environment env, String name){
		
		String db = env.getProperty("database.name");
		if("mysql".equals(db)){
			return "`"+name+"`";
		}else if("postgresql".equals(db)){
			return "\""+name+"\"";
		}else if("mssql".equals(db)){
			return "["+name+"]";
		}else if("oracle".equals(db)){
			return StringUtils.upperCase("\""+name+"\"");
		}
		return name;
	}
	
	protected String quoteTable(String tableName){
		
		return BaseModel.quoteTable(getEnvironment(), tableName);
	}
	
	protected String quoteName(String name){
		
		return BaseModel.quoteName(getEnvironment(), name);
	}
	
	protected QueryBean createPaginationQuery(String sql, PageParam pageParam){
		
		pageParam = pageParam==null? PageParam.list(): pageParam;
		
		if(pageParam.getOrders()!=null){
			sql += " ORDER BY ";
			int i=pageParam.getOrders().size();
			for(OrderPair pair: pageParam.getOrders()){
				i--;
				sql += quoteName(pair.getKey())+ " "+pair.getDir();
				if(i>0){
					sql += ",";
				}else{
					sql += " ";
				}
			}
		}
		
		int start = pageParam.getStart()>0?pageParam.getStart():0;
		int limit = pageParam.getLimit()>1?pageParam.getLimit():DefaultSettings.SQL_RESULT_LIMIT;
		sql += " LIMIT  ?, ? ";
		
		return new QueryBean(sql, null, start, limit);
	}
	
}
