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

import javax.servlet.http.HttpServletRequest;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.util.Assert;
import org.springframework.web.servlet.LocaleResolver;
import org.thymeleaf.Arguments;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.messageresolver.MessageResolution;

import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.jdbc.ArrayListResultSetExtractor;
import com.jpoweredcart.common.utils.PathUtils;


public abstract class AbstractMessageResolver implements MessageResolver {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<String, String> messageDirMap = new HashMap<String, String>();
	
	private Environment env;
	
	private JdbcOperations jdbcOperations;
	
	private Ehcache cache;
	
	private LocaleResolver localeResolver;
	
	private String baseDir;
	
	private boolean cacheable = true;
	
	@Override
	public String getName() { return "AbstractMessageResolver"; }
	
	@Override
	public Integer getOrder() { return 0; }
	
	@Override
	public void initialize() {
		Assert.notNull(env, "environment is required");
		Assert.notNull(jdbcOperations, "jdbcTemplate is required");
		if(cacheable){
			Assert.notNull(cache, "cache is required if the cacheable attribute is set to true");
		}
		Assert.notNull(localeResolver, "localeResolver is required");
		Assert.notNull(baseDir, "baseDir is required");
	}
	
	public void setEnvironment(Environment env){
		this.env = env;
	}
	
	public void setJdbcTemplate(JdbcOperations jdbcOperations){
		this.jdbcOperations = jdbcOperations;
	}
	
	public void setCache(Ehcache cache){
		this.cache = cache;
	}
	
	public void setLocaleResolver(LocaleResolver localeResolver){
		this.localeResolver = localeResolver;
	}
	
	public void setBaseDir(String baseDir) {
		if(!new File(baseDir).exists()){
			throw new IllegalArgumentException("baseDir doesn't exist!");
		}
		this.baseDir = PathUtils.ensureEndingFileSeparator(baseDir);
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
			Object... messageParameters) {
		
		Locale locale = localeResolver.resolveLocale(httpRequest);
		String langCode = locale.getLanguage();
		String langDir = null;
		
		synchronized(this){
			if((langDir=messageDirMap.get(langCode))==null ){
				String sql = "SELECT code, directory FROM "+BaseModel.quoteTable(env, "language")
						+ " WHERE status=1";
				List<Object[]> result = jdbcOperations.query(sql, new ArrayListResultSetExtractor());
				for(Object[] row: result){
					messageDirMap.put((String)row[0], (String)row[1]);
				}
				langDir = messageDirMap.get(langCode);
			}
		}
		if(langDir==null){
			langDir = "english";/* default language dir */
		}
		
		String paths[] = getMessagePaths(httpRequest);
		
		Properties props = null;
		for(int i=paths.length-1; i>=0; i--){
			String path = paths[i];
			String resourceName = langDir+File.separatorChar+path;
			Element element = null;
			
			if(cacheable){
				element = cache.get(resourceName);
			}
			if(element==null){
				File inputFile = new File(baseDir+resourceName+".properties");
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
						logger.warn("Cannot read file: {}", inputFile);
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
			
		}//end for
		
		return null;
	}
	
	public abstract String[] getMessagePaths(HttpServletRequest request);
	
	
	/**
	 * This method will get the request URI from HttpServletRequest then
	 * remove the context path and parameters if they exist.
	 * The slash character at the beginning will also be removed.
	 * Example:
	 * Suppose that /jpoweredcart is context path
	 * /jpoweredcart/admin/ ==> admin
	 * /jpoweredcart/admin/common/login ==> admin/common/login
	 * /jpoweredcart/admin/test?param1=1&param2=2 ==> admin/test
	 * /jpoweredcart/admin/common/home;jsessionid=3344?test=1 ==> admin/common/home
	 * 
	 * @param request
	 * @return
	 */
	protected String extractRequestPath(HttpServletRequest request){
		String requestPath = request.getRequestURI();
		String contextPath = request.getContextPath();
		/* remove context path */
		if(!"".equals(contextPath)){
			requestPath = requestPath.substring(contextPath.length()+1);
		}else{
			requestPath = requestPath.substring(1);
		}
		
		/* remove parameters */
		int idx = 0;
		if((idx=requestPath.indexOf(";"))!=-1){
			requestPath = requestPath.substring(0, idx);
		}else if((idx=requestPath.indexOf("?"))!=-1){
			requestPath = requestPath.substring(0, idx);
		}
		return requestPath;
	}
	
}
