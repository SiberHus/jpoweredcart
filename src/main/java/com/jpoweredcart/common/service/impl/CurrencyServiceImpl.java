package com.jpoweredcart.common.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.entity.Status;
import com.jpoweredcart.common.entity.localisation.Currency;
import com.jpoweredcart.common.entity.localisation.Language;
import com.jpoweredcart.common.service.CurrencyService;
import com.jpoweredcart.common.service.LanguageService;
import com.jpoweredcart.common.system.ScheduledDataUpdate;

public class CurrencyServiceImpl extends BaseModel implements CurrencyService, ScheduledDataUpdate, InitializingBean {
	
	public static final MessageFormat XR_URL = new MessageFormat("http://download.finance.yahoo.com/d/quotes.csv?s={0}&f=sl1&e=.csv");
	
	@Inject
	private LanguageService languageService;
	
	private Map<String, Currency> currencyMap = new HashMap<String, Currency>();
	
	private String defaultCurrencyCode;
	
	@Override
	public String getDefaultCurrencyCode(){
		return defaultCurrencyCode;
	}
	
	@Override
	public void updateCurrencyDatabase() throws Exception {
		
		if(defaultCurrencyCode==null){
			logger.warn("Something wrong with defaultCurrencyCode because it's null");
		}
		Map<String, Integer> updateCodeMap = new HashMap<String, Integer>();
		StringBuilder str = new StringBuilder();
		for(Entry<String, Currency> entry: currencyMap.entrySet()){
			if(defaultCurrencyCode.equals(entry.getKey())) continue;
			str.append(defaultCurrencyCode).append(entry.getKey()).append("=X,");
			updateCodeMap.put("\""+defaultCurrencyCode+entry.getKey()+
					"=X\"", entry.getValue().getId());
		}
		String xrUrl = XR_URL.format(new Object[]{str.toString()});
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		
		HttpGet httpget = new HttpGet(xrUrl);
		
		HttpResponse response = null;
		response = httpClient.execute(httpget);
		
		HttpEntity entity = response.getEntity();
		logger.debug("Response status: {}", response.getStatusLine());
		if (entity == null) {
			logger.warn("Currency update cannot proceed.");
			httpClient.getConnectionManager().shutdown();
			return;
		}
		
		String sql = "UPDATE "+quoteTable("currency")+" SET "+quoteName("value")+
				"=?, date_modified=? WHERE currency_id=?";
		
		BufferedReader bufferedReader = null;
		try {
			InputStream inputStream = entity.getContent();
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			BigDecimal currencyValue = null;
			Integer currencyId = null;
			while ((line = bufferedReader.readLine()) != null) {
				logger.debug("Response data: {}", line);
				String data[] = line.split(",");
				currencyId = updateCodeMap.get(data[0]);
				currencyValue = new BigDecimal(data[1]);
				currencyValue.setScale(4, RoundingMode.HALF_UP);
				getJdbcOperations().update(sql, currencyValue, 
					new Date(), currencyId);
			}
		}catch (IOException e) {
			throw new RuntimeException(e);
		}finally{
			IOUtils.closeQuietly(bufferedReader);
			httpClient.getConnectionManager().shutdown();
		}
		
		logger.info("Currency database has been updated");
	}
	
	@Override
	public void updateData() {
		
		final boolean autoUpdate = getEnvironment()
				.getProperty("currency.update.auto", Boolean.class);
		final long whenDiff = getEnvironment().getProperty(
				"currency.update.whenDiff", Long.class);
		final boolean willUpdate[] = new boolean[]{false};
		String sql = "SELECT * FROM "+quoteTable("currency")+" WHERE status=?";
		final Map<String, Currency> dataMap = new HashMap<String, Currency>();
		getJdbcOperations().query(sql, new Object[]{Status.ENABLED}, new RowCallbackHandler(){
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				Currency currency = new Currency();
				currency.setId(rs.getInt("currency_id"));
				currency.setCode(rs.getString("code"));
				currency.setTitle(rs.getString("title"));
				currency.setSymbolLeft(rs.getString("symbol_left"));
				currency.setSymbolRight(rs.getString("symbol_right"));
				try{
					currency.setDecimalPlace(rs.getInt("decimal_place"));
				}catch(Exception e){
					e.printStackTrace();
				}
				currency.setValue(rs.getBigDecimal("value"));
				if(currency.getValue().intValue()==1){
					CurrencyServiceImpl.this.defaultCurrencyCode = currency.getCode();
				}
				dataMap.put(currency.getCode(), currency);
				if(autoUpdate){
					long lastModified = rs.getTimestamp("date_modified").getTime();
					long current = new Date().getTime();
					long diff = ((current-lastModified)/(3600*1000));
					if(diff>whenDiff){
						willUpdate[0] = true;
					}
				}
			}
		});
		
		this.currencyMap = dataMap;
		
		if(willUpdate[0]){
			try{
				updateCurrencyDatabase();
			}catch(Exception e){
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		updateData();
	}
	
	
	@Override
	public Integer getId(String code) {
		Currency currency = currencyMap.get(code);
		if(currency!=null) return currency.getId();
		return null;
	}
	
	@Override
	public BigDecimal getValue(String code) {
		Currency currency = currencyMap.get(code);
		if(currency!=null) return currency.getValue();
		return null;
	}
	
	@Override
	public boolean has(String code) {
		return currencyMap.containsKey(code);
	}
	
	@Override
	public String format(BigDecimal number, String code, BigDecimal overrideRate, Integer languageId) {
		Language language = languageService.getById(languageId);
		return format(number, code, overrideRate, language.getLocale());
	}
	
	@Override
	public String format(BigDecimal number, String code, BigDecimal overrideRate, Locale locale) {
		
		Currency currency = currencyMap.get(code);
		if(currency==null){
			throw new IllegalArgumentException("Undefined currency code: "+code);
		}
		
		String result = "";
		if(currency.getSymbolLeft()!=null) result = currency.getSymbolLeft();
		
		NumberFormat numberFormat = locale==null?NumberFormat.getInstance():
			NumberFormat.getInstance(locale);
		
		BigDecimal value = null;
		if(overrideRate!=null){
			value = number.multiply(overrideRate);
		}else{
			value = number.multiply(currency.getValue());
		}
		
		result += numberFormat.format(value);
		
		if(currency.getSymbolRight()!=null) result += currency.getSymbolRight();
		
		return result;
	}

	@Override
	public BigDecimal convert(BigDecimal number, String fromCode, String toCode) {
		if(number==null){
			return BigDecimal.ZERO;
		}
		Currency fromCurrency = currencyMap.get(fromCode);
		BigDecimal from = null;
		if(fromCurrency!=null){
			from = fromCurrency.getValue();
			if(from==null||from.equals(BigDecimal.ZERO)){ 
				throw new RuntimeException("Invalid currency value for code: "+
						fromCode+", value: "+from); 
			}
		}else{
			throw new IllegalArgumentException("Undefined currency code: "+fromCode);
		}
		Currency toCurrency = currencyMap.get(toCode);
		BigDecimal to = BigDecimal.ZERO;
		if(toCurrency!=null){
			to = toCurrency.getValue();
		}
		
		return number.multiply(to.divide(from));
	}
	
	@Override
	public String getSymbolLeft(String code) {
		Currency currency = currencyMap.get(code);
		if(currency!=null) return currency.getSymbolLeft();
		return null;
	}

	@Override
	public String getSymbolRight(String code) {
		Currency currency = currencyMap.get(code);
		if(currency!=null) return currency.getSymbolRight();
		return null;
	}

	@Override
	public Integer getDecimalPlace(String code) {
		Currency currency = currencyMap.get(code);
		if(currency!=null) return currency.getDecimalPlace();
		return null;
	}
	
}
