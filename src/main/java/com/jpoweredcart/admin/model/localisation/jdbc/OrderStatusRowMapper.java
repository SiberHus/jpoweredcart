package org.jpoweredcart.admin.model.localisation.jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.jpoweredcart.common.entity.localisation.OrderStatuses.OrderStatus;
import org.springframework.jdbc.core.RowMapper;

public class OrderStatusRowMapper implements RowMapper<OrderStatus> {
	
	private boolean init = false;
	
	private boolean hasExtraColumns = false;
	
	@Override
	public OrderStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		if(!this.init){
			this.init = true;
			ResultSetMetaData rsmd = rs.getMetaData();
			for(int i=1;i<=rsmd.getColumnCount();i++){
				String columnName = rsmd.getColumnLabel(i);
				if("language_name".equals(columnName)){
					hasExtraColumns = true;
					break;
				}
			}
		}
		
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setId(rs.getInt("order_status_id"));
		orderStatus.setLanguageId(rs.getInt("language_id"));
		orderStatus.setName(rs.getString("name"));
		
		if(hasExtraColumns){
			orderStatus.setLanguageName(rs.getString("language_name"));
			orderStatus.setLanguageImage(rs.getString("language_image"));
		}
		
		return orderStatus;
	}
	
	
}