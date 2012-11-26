package org.jpoweredcart.admin.model.localisation.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jpoweredcart.admin.entity.localisation.TaxClass;
import org.springframework.jdbc.core.RowMapper;

public class TaxClassRowMapper implements RowMapper<TaxClass>{
	
	@Override
	public TaxClass mapRow(ResultSet rs, int rowNum) throws SQLException {
		TaxClass taxClass = new TaxClass();
		taxClass.setId(rs.getInt("tax_class_id"));
		taxClass.setTitle(rs.getString("title"));
		taxClass.setDescription(rs.getString("description"));
		taxClass.setDateAdded(rs.getDate("date_added"));
		taxClass.setDateModified(rs.getDate("date_modified"));
		return taxClass;
	}
	
	
}
