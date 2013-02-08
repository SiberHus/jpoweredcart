package com.jpoweredcart.admin.model.localisation.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import com.jpoweredcart.admin.model.localisation.LanguageAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.QueryBean;
import com.jpoweredcart.common.entity.Description;
import com.jpoweredcart.common.entity.localisation.Language;
import com.jpoweredcart.common.jdbc.ArrayListResultSetExtractor;
import com.jpoweredcart.common.service.SettingService;


public class LanguageAdminModelImpl extends BaseModel implements LanguageAdminModel{
	
	public LanguageAdminModelImpl(SettingService settingService, JdbcOperations jdbcOperations){
		super(settingService, jdbcOperations);
	}
	
	@Transactional
	@Override
	public void create(final Language lang) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcOperations().update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				String sql = "INSERT INTO "+quoteTable("language")+"(name, code, locale, " +
						"directory, filename, image, sort_order, status) VALUES(?,?,?,?,?,?,?,?)";
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, lang.getName());
				ps.setString(2, lang.getCode());
				ps.setString(3, lang.getLocale());
				ps.setString(4, lang.getDirectory());
				ps.setString(5, lang.getFilename());
				ps.setString(6, lang.getImage());
				ps.setInt(7, lang.getSortOrder());
				ps.setInt(8, lang.getStatus());
				return ps;
			}
		}, keyHolder);
		
		int langId = keyHolder.getKey().intValue();
		
		//find default language. We're going to copy text from default language to the new one.
		String langCode = getJdbcOperations().queryForObject("SELECT value FROM "+quoteTable("setting")
				+" WHERE "+quoteName("group")+" = 'config' AND "+quoteName("key")
				+" = 'config_language' AND store_id=0", String.class);
		//Use language code to find default language ID 
		int defaultLangId = getJdbcOperations().queryForInt("SELECT language_id FROM "+quoteTable("language")
				+" WHERE status=1 AND code=?", langCode);
		
		//Attribute
		String table = quoteTable("attribute_description");
		String sql = "SELECT attribute_id, name FROM " +table+ " WHERE language_id = ?";
		List<Object[]> rows = getJdbcOperations().query(sql, new Object[]{defaultLangId}, new ArrayListResultSetExtractor());
		sql = "INSERT INTO " +table + " SET attribute_id = ?, language_id = ?, name = ?";
		for(Object[] row: rows){
			getJdbcOperations().update(sql, row[0], langId, row[1]);
		}
		//AttributeGroup
		table = quoteTable("attribute_group_description");
		sql = "SELECT attribute_group_id, name FROM " +table+ " WHERE language_id = ?";
		rows = getJdbcOperations().query(sql, new Object[]{defaultLangId}, new ArrayListResultSetExtractor());
		sql = "INSERT INTO " +table + " SET attribute_group_id = ?, language_id = ?, name = ?";
		for(Object[] row: rows){
			getJdbcOperations().update(sql, row[0], langId, row[1]);
		}
		//Banner
		table = quoteTable("banner_image_description");
		sql = "SELECT banner_image_id,banner_id,title name FROM " +table+ " WHERE language_id = ?";
		rows = getJdbcOperations().query(sql, new Object[]{defaultLangId}, new ArrayListResultSetExtractor());
		sql = "INSERT INTO " +table + " SET banner_image_id = ?, banner_id=?, language_id = ?, title = ?";
		for(Object[] row: rows){
			getJdbcOperations().update(sql, row[0], row[1], langId, row[2]);
		}
		//Category
		table = quoteTable("category_description");
		sql = "SELECT category_id, name, meta_description, meta_keyword, description FROM " +table+ " WHERE language_id = ?";
		rows = getJdbcOperations().query(sql, new Object[]{defaultLangId}, new ArrayListResultSetExtractor());
		sql = "INSERT INTO " +table + " SET category_id = ?, language_id = ?, name = ?, meta_description=?, meta_keyword = ?, description = ?";
		for(Object[] row: rows){
			getJdbcOperations().update(sql, row[0], langId, row[1], row[2], row[3], row[4]);
		}
		//CustomerGroup
		table = quoteTable("customer_group_description");
		sql = "SELECT customer_group_id, name, description FROM " +table+ " WHERE language_id = ?";
		rows = getJdbcOperations().query(sql, new Object[]{defaultLangId}, new ArrayListResultSetExtractor());
		sql = "INSERT INTO " +table + " SET customer_group_id = ?, language_id = ?, name = ?, description = ?";
		for(Object[] row: rows){
			getJdbcOperations().update(sql, row[0], langId, row[1], row[2]);
		}
		//Download
		table = quoteTable("download_description");
		sql = "SELECT download_id, name FROM " +table+ " WHERE language_id = ?";
		rows = getJdbcOperations().query(sql, new Object[]{defaultLangId}, new ArrayListResultSetExtractor());
		sql = "INSERT INTO " +table + " SET download_id = ?, language_id = ?, name = ?";
		for(Object[] row: rows){
			getJdbcOperations().update(sql, row[0], langId, row[1]);
		}
		//Filter
		table = quoteTable("filter_description");
		sql = "SELECT filter_id, name FROM " +table+ " WHERE language_id = ?";
		rows = getJdbcOperations().query(sql, new Object[]{defaultLangId}, new ArrayListResultSetExtractor());
		sql = "INSERT INTO " +table + " SET filter_id = ?, language_id = ?, name = ?";
		for(Object[] row: rows){
			getJdbcOperations().update(sql, row[0], langId, row[1]);
		}
		//Information
		table = quoteTable("information_description");
		sql = "SELECT information_id, title, description FROM " +table+ " WHERE language_id = ?";
		rows = getJdbcOperations().query(sql, new Object[]{defaultLangId}, new ArrayListResultSetExtractor());
		sql = "INSERT INTO " +table + " SET information_id = ?, language_id = ?, title = ?, description=?";
		for(Object[] row: rows){
			getJdbcOperations().update(sql, row[0], langId, row[1], row[2]);
		}
		//Length
		table = quoteTable("length_class_description");
		sql = "SELECT length_class_id, title, unit FROM " +table+ " WHERE language_id = ?";
		rows = getJdbcOperations().query(sql, new Object[]{defaultLangId}, new ArrayListResultSetExtractor());
		sql = "INSERT INTO " +table + " SET length_class_id = ?, language_id = ?, title = ?, unit=?";
		for(Object[] row: rows){
			getJdbcOperations().update(sql, row[0], langId, row[1], row[2]);
		}
		//Option
		table = quoteTable("option_description");
		sql = "SELECT option_id, name FROM " +table+ " WHERE language_id = ?";
		rows = getJdbcOperations().query(sql, new Object[]{defaultLangId}, new ArrayListResultSetExtractor());
		sql = "INSERT INTO " +table + " SET option_id = ?, language_id = ?, name = ?";
		for(Object[] row: rows){
			getJdbcOperations().update(sql, row[0], langId, row[1]);
		}
		//Option value
		table = quoteTable("option_value_description");
		sql = "SELECT option_value_id, option_id, name FROM " +table+ " WHERE language_id = ?";
		rows = getJdbcOperations().query(sql, new Object[]{defaultLangId}, new ArrayListResultSetExtractor());
		sql = "INSERT INTO " +table + " SET option_value_id = ?, language_id = ?, option_id = ?, name = ?";
		for(Object[] row: rows){
			getJdbcOperations().update(sql, row[0], langId, row[1], row[2]);
		}
		//Order Status
		table = quoteTable("order_status");
		sql = "SELECT order_status_id, name FROM " +table+ " WHERE language_id = ?";
		rows = getJdbcOperations().query(sql, new Object[]{defaultLangId}, new ArrayListResultSetExtractor());
		sql = "INSERT INTO " +table + " SET order_status_id = ?, language_id = ?, name = ?";
		for(Object[] row: rows){
			getJdbcOperations().update(sql, row[0], langId, row[1]);
		}
		//Product
		table = quoteTable("product_description");
		sql = "SELECT product_id, name, meta_description, meta_keyword, description, tag FROM " +table+ " WHERE language_id = ?";
		rows = getJdbcOperations().query(sql, new Object[]{defaultLangId}, new ArrayListResultSetExtractor());
		sql = "INSERT INTO " +table + " SET product_id = ?, language_id = ?, name = ?, meta_description = ?, meta_keyword = ?, description = ?, tag = ?";
		for(Object[] row: rows){
			getJdbcOperations().update(sql, row[0], langId, row[1], row[2], row[3], row[4], row[5]);
		}
		//Product Attribute 
		table = quoteTable("product_attribute");
		sql = "SELECT product_id, attribute_id, text FROM " +table+ " WHERE language_id = ?";
		rows = getJdbcOperations().query(sql, new Object[]{defaultLangId}, new ArrayListResultSetExtractor());
		sql = "INSERT INTO " +table + " SET product_id = ?, language_id = ?, attribute_id = ?, text = ?";
		for(Object[] row: rows){
			getJdbcOperations().update(sql, row[0], langId, row[1], row[2]);
		}
		//Return Action
		table = quoteTable("return_action");
		sql = "SELECT return_action_id, name FROM " +table+ " WHERE language_id = ?";
		rows = getJdbcOperations().query(sql, new Object[]{defaultLangId}, new ArrayListResultSetExtractor());
		sql = "INSERT INTO " +table + " SET return_action_id = ?, language_id = ?, name = ?";
		for(Object[] row: rows){
			getJdbcOperations().update(sql, row[0], langId, row[1]);
		}
		//Return Reason
		table = quoteTable("return_reason");
		sql = "SELECT return_reason_id, name FROM " +table+ " WHERE language_id = ?";
		rows = getJdbcOperations().query(sql, new Object[]{defaultLangId}, new ArrayListResultSetExtractor());
		sql = "INSERT INTO " +table + " SET return_reason_id = ?, language_id = ?, name = ?";
		for(Object[] row: rows){
			getJdbcOperations().update(sql, row[0], langId, row[1]);
		}
		//Return Status
		table = quoteTable("return_status");
		sql = "SELECT return_status_id, name FROM " +table+ " WHERE language_id = ?";
		rows = getJdbcOperations().query(sql, new Object[]{defaultLangId}, new ArrayListResultSetExtractor());
		sql = "INSERT INTO " +table + " SET return_status_id = ?, language_id = ?, name = ?";
		for(Object[] row: rows){
			getJdbcOperations().update(sql, row[0], langId, row[1]);
		}
		//Stock Status
		table = quoteTable("stock_status");
		sql = "SELECT stock_status_id, name FROM " +table+ " WHERE language_id = ?";
		rows = getJdbcOperations().query(sql, new Object[]{defaultLangId}, new ArrayListResultSetExtractor());
		sql = "INSERT INTO " +table + " SET stock_status_id = ?, language_id = ?, name = ?";
		for(Object[] row: rows){
			getJdbcOperations().update(sql, row[0], langId, row[1]);
		}
		//Voucher Theme
		table = quoteTable("voucher_theme_description");
		sql = "SELECT voucher_theme_id, name FROM " +table+ " WHERE language_id = ?";
		rows = getJdbcOperations().query(sql, new Object[]{defaultLangId}, new ArrayListResultSetExtractor());
		sql = "INSERT INTO " +table + " SET voucher_theme_id = ?, language_id = ?, name = ?";
		for(Object[] row: rows){
			getJdbcOperations().update(sql, row[0], langId, row[1]);
		}
		//Weight Class
		table = quoteTable("weight_class_description");
		sql = "SELECT weight_class_id, title, unit FROM " +table+ " WHERE language_id = ?";
		rows = getJdbcOperations().query(sql, new Object[]{defaultLangId}, new ArrayListResultSetExtractor());
		sql = "INSERT INTO " +table + " SET weight_class_id = ?, language_id = ?, title = ?, unit = ?";
		for(Object[] row: rows){
			getJdbcOperations().update(sql, row[0], langId, row[1], row[2]);
		}
	}
	
	
	@Override
	public void update(Language lang) {
		
		String sql = "UPDATE " +quoteTable("language")+ " SET name = ?, code = ?, locale = ?, " +
				"directory = ?, filename = ?, image = ?, sort_order = ?, status = ? WHERE language_id = ?";
		getJdbcOperations().update(sql, lang.getName(), lang.getCode(), lang.getLocale(), 
				lang.getDirectory(), lang.getFilename(), lang.getImage(), lang.getSortOrder(),
				lang.getStatus(), lang.getId());
	}
	
	@Transactional
	@Override
	public void delete(Integer langId) {
		
		String tables[] = new String[]{
			"language", "attribute_description", "attribute_group_description",
			"banner_image_description", "category_description", "customer_group_description",
			"download_description", "filter_description", "filter_value_description",
			"information_description", "length_class_description", "option_description",
			"option_value_description", "order_status", "product_attribute",
			"product_description", "return_action", "return_reason", "return_status",
			"stock_status", "voucher_theme_description", "weight_class_description"
		};
		
		for(String table: tables){
			String sql = "DELETE FROM " +quoteTable(table)+ " WHERE language_id = ?";
			getJdbcOperations().update(sql, langId);
		}
		
	}

	@Override
	public Language get(Integer langId) {
		String sql = "SELECT * FROM " +quoteTable("language")+ " WHERE language_id = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{langId}, new LanguageRowMapper());
	}
	
	@Override
	public List<Language> getList(PageParam pageParam) {
		QueryBean query = createPaginationQuery("language", pageParam, 
				new String[]{"name", "code", "sort_order"});
		List<Language> languageList = getJdbcOperations()
				.query(query.getSql(), query.getParameters(), new LanguageRowMapper());
		return languageList;
	}
	
	@Override
	public int getTotal() {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("language");
		return getJdbcOperations().queryForInt(sql);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T>List<T> createDescriptionList(Class<T> descClass){
		if(Description.class.isAssignableFrom(descClass)){
			try{
				List<T> descs = new ArrayList<T>();
				for(Language language: getList(null)){
					Description desc = (Description)descClass.newInstance();
					desc.setLanguageId(language.getId());
					desc.setLanguageName(language.getName());
					desc.setLanguageImage(language.getImage());
					descs.add((T)desc);
				}
				return descs;
			}catch(Exception e){
				throw new RuntimeException(e);
			}
		}else{
			throw new IllegalArgumentException("Class must extend Description");
		}
		
	}
}
