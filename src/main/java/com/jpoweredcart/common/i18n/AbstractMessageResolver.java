package com.jpoweredcart.common.i18n;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.jdbc.ArrayListResultSetExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;
import org.springframework.web.servlet.LocaleResolver;
import org.thymeleaf.Arguments;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.messageresolver.MessageResolution;


public abstract class AbstractMessageResolver implements MessageResolver {
	
	public static final String MSG_DIR_ATTR_NAME = AbstractMessageResolver.class.getName()+".LOCALE"; 
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private Environment env;
	
	private JdbcTemplate jdbcTemplate;
	
	private Ehcache cache;
	
	private LocaleResolver localeResolver;
	
	private String baseDir;
	
	private boolean cacheable = true;
	
	@Override
	public String getName() { return "SimpleMessageResolver"; }
	
	@Override
	public Integer getOrder() { return 0; }
	
	@Override
	public void initialize() {
		Assert.notNull(env, "environment is required");
		Assert.notNull(jdbcTemplate, "jdbcTemplate is required");
		if(cacheable){
			Assert.notNull(cache, "cache is required if the cacheable attribute is set to true");
		}
		Assert.notNull(localeResolver, "localeResolver is required");
		Assert.notNull(baseDir, "baseDir is required");
	}
	
	public void setEnvironment(Environment env){
		this.env = env;
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void setCache(Ehcache cache){
		this.cache = cache;
	}
	
	public void setLocaleResolver(LocaleResolver localeResolver){
		this.localeResolver = localeResolver;
	}
	
	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}
	
	public void setCacheable(boolean cacheable){
		this.cacheable = cacheable;
	}
	
	@Override
	public MessageResolution resolveMessage(Arguments arguments, String key,
			Object[] messageParameters) {
		
		WebContext webContext = (WebContext)arguments.getContext();
		
		String message = resolveMessage(webContext.getHttpServletRequest(), key, messageParameters);
		
		if(message!=null){
			return new MessageResolution(message);
		}
		
		return null;
	}
	
	@Override
	public String resolveMessage(HttpServletRequest httpRequest, String key,
			Object[] messageParameters) {
		
		Locale locale = localeResolver.resolveLocale(httpRequest);
		ServletContext servletContext = httpRequest.getSession().getServletContext();
		
		@SuppressWarnings("unchecked")
		Map<String, String> msgDirMap = (Map<String, String>)servletContext
				.getAttribute(MSG_DIR_ATTR_NAME);
		String langCode = locale.getLanguage();
		String langDir = null;
		if(msgDirMap==null || (langDir=msgDirMap.get(langCode))==null ){
			msgDirMap = new HashMap<String, String>();
			String sql = "SELECT code, directory FROM "+BaseModel.quoteTable(env, "language")
					+ " WHERE status=1";
			List<Object[]> result = jdbcTemplate.query(sql, new ArrayListResultSetExtractor());
			for(Object[] row: result){
				msgDirMap.put((String)row[0], (String)row[1]);
			}
			servletContext.setAttribute(MSG_DIR_ATTR_NAME, msgDirMap);
			langDir = msgDirMap.get(langCode);
		}
		if(langDir==null){
			langDir = "english";//TODO: remove this hardcode
		}
		
		String baseDir = servletContext.getRealPath(this.baseDir);
		String paths[] = getMessagePaths(httpRequest);
		Properties props = null;
		for(String path : paths){
			
			String resourceName = langDir+File.separator+path;
			Element element = null;
			
			if(cacheable){
				element = cache.get(resourceName);
			}
			if(element==null){
				File inputFile = new File(baseDir+File.separator+resourceName+".properties");
				if(inputFile.exists()){
					props = new Properties();
					InputStreamReader in = null;
					try{
						in = new InputStreamReader(new FileInputStream(inputFile), "UTF-8");
						props.load(in);
						element = new Element(resourceName, props);
						if(cacheable){
							cache.put(element);
						}
					}catch(IOException e){
						//TODO: add exception handler
					}finally{
						if(in!=null) try{ in.close(); }catch(Exception e){}
					}
				}
			}
			
			if(element==null) continue;
			
			props = (Properties)element.getValue();
			if(props==null) continue;
			String messageValue = props.getProperty(key);
			if(messageValue==null) continue;
			if (messageParameters == null || messageParameters.length == 0) {
				return messageValue;
			}
			MessageFormat messageFormat = new MessageFormat(messageValue, locale);
			String message =  messageFormat.format(messageParameters);
			return message;
		}
		
		return null;
	}
	
	
	public abstract String[] getMessagePaths(HttpServletRequest request);
	
}
