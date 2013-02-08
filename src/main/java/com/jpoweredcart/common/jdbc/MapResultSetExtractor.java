package com.jpoweredcart.common.jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class MapResultSetExtractor implements ResultSetExtractor<Map<String, Object>>{
	
	@Override
	public Map<String, Object> extractData(ResultSet rs)
			throws SQLException, DataAccessException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int colCount = rsmd.getColumnCount();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(rs.next()){
			for(int i=1; i<=colCount; i++){
				String colName = rsmd.getColumnLabel(i);
				resultMap.put(colName, rs.getObject(i));
			}
			if(rs.next()){
				throw new IncorrectResultSizeDataAccessException(1, -1);
			}
		}
		return resultMap;
	}
	
	
}
