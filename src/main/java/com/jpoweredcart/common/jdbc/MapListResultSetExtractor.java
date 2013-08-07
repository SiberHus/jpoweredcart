package com.jpoweredcart.common.jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class MapListResultSetExtractor implements ResultSetExtractor<List<Map<String, Object>>>{
	
	@Override
	public List<Map<String, Object>> extractData(ResultSet rs)
			throws SQLException, DataAccessException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int colCount = rsmd.getColumnCount();
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		while(rs.next()){
			Map<String, Object> rowMap = new HashMap<String, Object>();
			for(int i=1; i<=colCount; i++){
				String colName = rsmd.getColumnLabel(i);
				rowMap.put(colName, rs.getObject(i));
			}
			resultList.add(rowMap);
		}
		return resultList;
	}
	
	
}
