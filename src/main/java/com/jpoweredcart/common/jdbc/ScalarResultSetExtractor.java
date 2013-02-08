package org.jpoweredcart.common.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 * This implementation of ResultSetExtractor is different from JdbcTemplate.queryForObject
 * because this one will not throw Exception when the result has more than one record or
 * the result is empty. The scalar result will not be converted by any converters as well.
 * 
 *  
 * @author hussachai
 *
 * @param <T>
 */
public class ScalarResultSetExtractor<T> implements ResultSetExtractor<T>{
	
	@SuppressWarnings("unchecked")
	@Override
	public T extractData(ResultSet rs)
			throws SQLException, DataAccessException {
		if(rs.next()){
			return (T)rs.getObject(1);
		}
		return null;
	}
	
}
