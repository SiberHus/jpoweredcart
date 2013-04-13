package com.jpoweredcart.common;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcOperations;

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
	
	protected QueryBean createPaginationQuery(String table, PageParam pageParam, String... sortFields){
		
		String sql = "SELECT * FROM "+quoteTable(table);
		
		return createPaginationQueryFromSql(sql, pageParam, sortFields);
	}
	
	protected QueryBean createPaginationQueryFromSql(String sql, PageParam pageParam, String... sortFields){
		
		//TODO: Add database specific pagination query
		if(sortFields!=null && sortFields.length>0){
			List<String> sortKeys = Arrays.asList(sortFields);
			if(pageParam==null){
				pageParam = PageParam.list(-1);//use the default limit
			}
			if(pageParam.getSortKey()!=null && sortKeys.contains(pageParam.getSortKey())){
				sql += " ORDER BY "+pageParam.getSortKey();
			}else{
				sql += " ORDER BY "+sortFields[0];
			}
			if(pageParam.getOrderDir()!=null && "desc".equals(pageParam.getOrderDir())){
				sql += " DESC";
			}else{
				sql += " ASC";
			}
		}
		
		int start = pageParam.getStart()>0?pageParam.getStart():0;
		int limit = pageParam.getLimit()>1?pageParam.getLimit():DefaultSettings.SQL_RESULT_LIMIT;
		sql += " LIMIT  ?, ? ";
		
		return new QueryBean(sql, null, start, limit);
	}
	
}
