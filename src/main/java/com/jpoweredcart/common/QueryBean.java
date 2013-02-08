package com.jpoweredcart.common;

import java.util.ArrayList;
import java.util.List;

public class QueryBean {

	private String sql;
	
	private List<Object> parameterList = new ArrayList<Object>();
	
	private int start;
	
	private int limit;
	
	public QueryBean(){}
	
	public QueryBean(String sql, Object[] parameters){
		this(sql, parameters, -1, -1);
	}
	
	public QueryBean(String sql, Object[] parameters, int start, int limit){
		this.sql = sql;
		if(parameters!=null){
			for(Object param : parameters){
				this.parameterList.add(param);
			}
		}
		this.start = start;
		this.limit = limit;
	}
	
	public String getSql() {
		return sql;
	}
	
	public QueryBean setSql(String sql) {
		this.sql = sql;
		return this;
	}
	
	public List<Object> getParameterList(){
		return parameterList;
	}
	
	public QueryBean addParameters(Object... params){
		if(params==null){
			this.parameterList.add(null);
		}else{
			for(Object param: params){
				this.parameterList.add(param);				
			}
		}
		return this;
	}
	
	public Object[] getParameters() {
		List<Object> parameters = this.parameterList;
		if(this.start!=-1 && this.limit!=-1){
			parameters = new ArrayList<Object>(this.parameterList);
			parameters.add(this.start);
			parameters.add(this.limit);
		}
		return parameters.toArray();
	}
	
}
