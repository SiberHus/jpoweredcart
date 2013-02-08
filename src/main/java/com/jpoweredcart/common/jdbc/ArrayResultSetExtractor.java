package org.jpoweredcart.common.jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class ArrayResultSetExtractor implements ResultSetExtractor<Object[]>{
	
	@Override
	public Object[] extractData(ResultSet rs)
			throws SQLException, DataAccessException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int colCount = rsmd.getColumnCount();
		Object result[] = new Object[colCount];
		if(rs.next()){
			for(int i=1; i<=colCount; i++){
				result[i-1] = rs.getObject(i);
			}
			if(rs.next()){
				throw new IncorrectResultSizeDataAccessException(1, -1);
			}
		}
		return result;
	}
	
	
}
