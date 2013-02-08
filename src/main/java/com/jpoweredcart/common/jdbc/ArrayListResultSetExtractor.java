package org.jpoweredcart.common.jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class ArrayListResultSetExtractor implements ResultSetExtractor<List<Object[]>>{
	
	@Override
	public List<Object[]> extractData(ResultSet rs)
			throws SQLException, DataAccessException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int colCount = rsmd.getColumnCount();
		List<Object[]> resultList = new ArrayList<Object[]>();
		while(rs.next()){
			Object[] row = new Object[colCount];
			for(int i=1; i<=colCount; i++){
				row[i-1] = rs.getObject(i);
			}
			resultList.add(row);
		}
		return resultList;
	}
	
	
}
