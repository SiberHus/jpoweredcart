package com.jpoweredcart.common.i18n;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.thymeleaf.Arguments;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.cache.ICache;
import org.thymeleaf.cache.ICacheManager;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.messageresolver.MessageResolution;
import org.thymeleaf.messageresolver.StandardMessageResolver;
import org.thymeleaf.resourceresolver.IResourceResolver;
import org.thymeleaf.util.MessageResolutionUtils;
import org.thymeleaf.util.Validate;

/**
 * 
 * @author hussachai
 * 
 */
public class ThymeleafMessageResolver extends StandardMessageResolver {

	public static final String MSG_PATH_ATTR = "_i18n.paths";
	
	private static final Logger logger = LoggerFactory.getLogger(ThymeleafMessageResolver.class);
	
	private static final String TEMPLATE_CACHE_PREFIX = "{template_msg}";
	
	private String baseDir;
	
	public static void setMessagePath(HttpServletRequest request, String msgPath){
		request.setAttribute(MSG_PATH_ATTR, msgPath);
	}
	
	public static void setMessagePath(Model model, String msgPath){
		model.addAttribute(MSG_PATH_ATTR, msgPath);
	}
	
	public static void setMessagePath(ModelMap model, String msgPath){
		model.addAttribute(MSG_PATH_ATTR, msgPath);
	}
	
	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}
	
	@Override
	public MessageResolution resolveMessage(Arguments arguments, String key,
			Object[] messageParameters) {

		checkInitialized();
		
		WebContext webContext = (WebContext)arguments.getContext();
		ServletContext servletContext = webContext.getServletContext();
		
		String baseDir = servletContext.getRealPath(this.baseDir);
		String baseName = getMessageBaseName(arguments);
		
		String message = ThymeleafMessageResolver
				.resolveMessageForTemplate(arguments, new FileSystemResourceResolver(baseDir, baseName), 
						key, messageParameters, unsafeGetDefaultMessages());
		
		if (message == null) {
			return null;
		}
		
		return new MessageResolution(message);
		
	}
	
	/**
	 * This method can be overridden by user.
	 * 
	 * @param webContext
	 * @return
	 */
	public String getMessageBaseName(Arguments arguments){
		WebContext webContext = (WebContext)arguments.getContext();
		HttpServletRequest request = webContext.getHttpServletRequest();
		String baseName = (String)request.getAttribute(MSG_PATH_ATTR);
		//if there is not explicit setting for message path, we'll try our best to figure it out.
		if(baseName==null){
			//Use URI convention to retrieve the baseName
			String uriParts[] = request.getRequestURI().split("/");
			if(uriParts.length>4){
				String lastPart = uriParts[4];
				int sepIdx = 0;
				if((sepIdx=lastPart.indexOf(";"))!=-1
						|| (sepIdx=lastPart.indexOf("?"))!=-1) 
					lastPart = lastPart.substring(0, sepIdx);
				baseName = "/"+uriParts[2]+"/"+uriParts[3]+"/"+lastPart;
			}else{
				//If URI convention doesn't work, use template name instead
				baseName = arguments.getTemplateResolution().getTemplateName();
				baseName = StringUtils.substringBeforeLast(baseName, ".");
			}
		}
		return baseName;
	}
	
	public static class FileSystemResourceResolver implements IResourceResolver{
		
		private String baseDir;
		private String baseName;
		
		public FileSystemResourceResolver(String baseDir, String baseName){
			this.baseDir = baseDir;
			this.baseName = baseName;
		}
		
		@Override
		public String getName() {
			return FileSystemResourceResolver.class.getName();
		}
		
		public String getBaseDir(){ return baseDir; }
		public String getBaseName(){ return baseName; }
		
		@Override
		public InputStream getResourceAsStream(
				TemplateProcessingParameters templateProcessingParameters,
				String resourceName) {
			File inputFile = new File(baseDir+File.separator+resourceName);
			logger.debug("Try to load language file: {}", inputFile.getPath());
			if(!inputFile.exists()) return null;
			try {
				return new FileInputStream(inputFile);
			} catch (FileNotFoundException e) {return null;}
		}
	}
	
	public static String resolveMessageForTemplate(final Arguments arguments, FileSystemResourceResolver resourceResolver,
			final String key, final Object[] messageParameters,
			final Properties defaultMessages) {
		
		Validate.notNull(arguments, "Arguments cannot be null");
		Validate.notNull(arguments.getContext().getLocale(),
				"Locale in context cannot be null");
		Validate.notNull(key, "Message key cannot be null");
		
		final Locale locale = arguments.getContext().getLocale();
		
		final String templateName = arguments.getTemplateResolution().getTemplateName();
		final String baseName = resourceResolver.getBaseName();
		final String cacheKey = TEMPLATE_CACHE_PREFIX + baseName + "_"+ locale.toString();
		
		Properties properties = null;
		ICache<String, Properties> messagesCache = null;
		
		final ICacheManager cacheManager = arguments.getConfiguration().getCacheManager();
		if (cacheManager != null) {
			messagesCache = cacheManager.getMessageCache();
			if (messagesCache != null) {
				properties = messagesCache.get(cacheKey);
			}
		}

		if (properties == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("[THYMELEAF][{}] Resolving uncached messages for template \"{}\" and locale \"{}\". Messages will be retrieved from files",
						new Object[] { TemplateEngine.threadIndex(),templateName, locale });
			}
			properties = loadMessagesForTemplate(arguments, resourceResolver, defaultMessages);
			if (messagesCache != null) {
				messagesCache.put(cacheKey, properties);
			}
		} else {
			if (logger.isTraceEnabled()) {
				logger.trace("[THYMELEAF][{}] Resolving messages for template \"{}\" and locale \"{}\". Messages are CACHED",
						new Object[] { TemplateEngine.threadIndex(),templateName, locale });
			}
		}
		
		final String messageValue = properties.getProperty(key);

		if (messageValue == null) {
			return null;
		}
		if (messageParameters == null || messageParameters.length == 0) {
			return messageValue;
		}

		final MessageFormat messageFormat = new MessageFormat(messageValue, locale);
		return messageFormat.format(messageParameters);

	}

	private static Properties loadMessagesForTemplate(
			final Arguments arguments, final FileSystemResourceResolver resourceResolver, final Properties defaultMessages) {

		Validate.notNull(arguments, "Arguments cannot be null");
		Validate.notNull(arguments.getContext().getLocale(),"Locale in context cannot be null");
		
		Locale locale = arguments.getContext().getLocale();
		
		return MessageResolutionUtils.loadCombinedMessagesFilesFromBaseName(
				arguments, resourceResolver, resourceResolver.getBaseName(), locale,
				defaultMessages);
	}
	
}

