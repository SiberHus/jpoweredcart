package com.jpoweredcart.common.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class ColumnListResultSetExtractor<T> implements ResultSetExtractor<List<T>>{
	
	private int column = 1;
	
	public ColumnListResultSetExtractor(){}
	
	/**
	 * Column number start with 1
	 * @param col
	 */
	public ColumnListResultSetExtractor(int column){
		this.column = column;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> extractData(ResultSet rs)
			throws SQLException, DataAccessException {
		List<T> resultList = new ArrayList<T>();
		while(rs.next()){
			resultList.add((T)rs.getObject(column));
		}
		return resultList;
	}
	
	
}
