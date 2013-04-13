package com.jpoweredcart.common.config;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.spring3.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import com.jpoweredcart.common.exception.TemplateMappingExceptionResolver;
import com.jpoweredcart.common.i18n.CustomMessageResolver;
import com.jpoweredcart.common.i18n.MessageResolver;
import com.jpoweredcart.common.service.StoreResolver;
import com.jpoweredcart.common.system.UserDataInterceptor;


@Configuration
@ImportResource({
	"classpath:security.xml"
})
public class WebMvcConfig extends WebMvcConfigurationSupport {
	
	@Inject
	private ServletContext servletContext;
	
	@Inject
	private Environment env;
	
	@Inject
	private JdbcOperations jdbcOperations;
	
	@Inject
	private StoreResolver storeResolver;
	
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		UserDataInterceptor userDataInterceptor = new UserDataInterceptor();
		userDataInterceptor.setJdbcOperations(jdbcOperations);
		userDataInterceptor.setStoreResolver(storeResolver);
		userDataInterceptor.setLanguageParam(env.getProperty("language.param", "language_code"));
		userDataInterceptor.setCurrencyParam(env.getProperty("currency.param", "currency_code"));
		
		registry.addInterceptor(userDataInterceptor);
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/admin/error/permission").setViewName("/admin/error/permission");
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("resources/");
		registry.addResourceHandler("/files/**").addResourceLocations("files/");
	}
	
	@Override
	public FormattingConversionService mvcConversionService() {
		FormattingConversionService formattingConversionService = super.mvcConversionService();
		if(env.getProperty("input.trimToNull", Boolean.class, Boolean.TRUE)){
			formattingConversionService.addConverter(new Converter<String, String>() {
				public String convert(String source) { return StringUtils.trimToNull(source); }});
		}
		
		return formattingConversionService;
	}
	
	@Bean
	public LocaleResolver localeResolver(){
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		Locale defaultLocale = Locale.ENGLISH;
		String defaultLanguage = env.getProperty("language.default", "");
		if(!"".equals(defaultLanguage)){
			defaultLocale = new Locale(defaultLanguage);
		}
		localeResolver.setDefaultLocale(defaultLocale);
		return localeResolver;
	}
	
	@Bean
	public HandlerExceptionResolver mappingExceptionResolver(){
		TemplateMappingExceptionResolver exceptionResolver = new TemplateMappingExceptionResolver();
		Properties props = new Properties();//exception name = view name
		props.setProperty("com.jpoweredcart.common.exception.admin.UnauthorizedAdminException", "/admin/error/permission");
		
		props.setProperty("org.springframework.web.servlet.PageNotFound", "/error/pageNotFound");
		props.setProperty("org.springframework.dao.DataAccessException", "/error/dataAccessFailure");
		props.setProperty("org.springframework.transaction.TransactionException", "/error/dataAccessFailure");
		exceptionResolver.setExceptionMappings(props);
		exceptionResolver.setDefaultErrorView("uncaughtException");
		return exceptionResolver;
	}
	
	@Bean
	public ViewResolver viewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		resolver.setCharacterEncoding(env.getProperty("language.encoding", "UTF-8"));
		return resolver;
	}
	
	@Bean
	public MultipartResolver multipartResolver() throws IOException{
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		String tmpDir = env.getProperty("fileUpload.tmpDir", "");
		if(!tmpDir.equals("")){
			multipartResolver.setUploadTempDir(new FileSystemResource(tmpDir));
		}
		long maxSize = env.getProperty("fileUpload.maxSize", Long.class);
		if(maxSize>0) multipartResolver.setMaxUploadSize(maxSize);
		
		return multipartResolver;
	}
	
	@Bean
	public SpringTemplateEngine templateEngine() {
		
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.addMessageResolver(messageResolver());
		engine.setTemplateResolver(templateResolver());
		engine.addDialect(new SpringSecurityDialect());
		return engine;
	}
	
	@Bean
	public TemplateResolver templateResolver() {
		ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
		resolver.setPrefix(env.getProperty("template.baseDir"));
		resolver.setSuffix(env.getProperty("template.suffix", ".html"));
		resolver.setTemplateMode(env.getProperty("template.mode", "HTML5"));
		resolver.setCharacterEncoding(env.getProperty("template.encoding", "UTF-8"));
		resolver.setCacheable(env.getProperty("template.cacheable", Boolean.class, Boolean.TRUE));
		return resolver;
	}
	
	@Bean 
	public MessageResolver messageResolver(){
		
		CustomMessageResolver messageResolver = new CustomMessageResolver();
		messageResolver.setEnvironment(env);
		String baseDir = env.getProperty("language.baseDir", "/WEB-INF/languages");
		if(baseDir.startsWith("/")){
			baseDir = servletContext.getRealPath(baseDir);
		}
		messageResolver.setBaseDir(baseDir);
		messageResolver.setJdbcTemplate(jdbcOperations);
		messageResolver.setLocaleResolver(localeResolver());
		
		boolean cacheable = env.getProperty("language.cacheable", Boolean.class, Boolean.TRUE);
		if(cacheable){
			EhCacheFactoryBean cacheFactory = new EhCacheFactoryBean();
			//use default cache manager CacheManager.getInstance();
			cacheFactory.setCacheName("messageCache");
			try {
				cacheFactory.afterPropertiesSet();
			} catch (Exception e) {
				throw new IllegalStateException(e.getMessage(), e);
			}
			messageResolver.setCacheable(true);
			messageResolver.setCache(cacheFactory.getObject());
		}else{
			messageResolver.setCacheable(false);
		}
		
		return messageResolver;
	}
	
}
