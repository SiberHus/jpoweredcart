package com.jpoweredcart.common.config;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

import net.sf.ehcache.Ehcache;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;

import com.jolbox.bonecp.BoneCPDataSource;
import com.jpoweredcart.common.service.CurrencyService;
import com.jpoweredcart.common.service.StoreResolver;
import com.jpoweredcart.common.service.UserService;
import com.jpoweredcart.common.service.impl.CurrencyServiceImpl;
import com.jpoweredcart.common.service.impl.StoreResolverImpl;
import com.jpoweredcart.common.service.impl.UserServiceImpl;
import com.jpoweredcart.common.system.email.EmailService;
import com.jpoweredcart.common.system.email.SmtpEmailService;
import com.jpoweredcart.common.system.file.DefaultFileService;
import com.jpoweredcart.common.system.file.FileService;
import com.jpoweredcart.common.system.media.DefaultMediaService;
import com.jpoweredcart.common.system.media.MediaService;
import com.jpoweredcart.common.system.setting.DefaultSettingService;
import com.jpoweredcart.common.system.setting.SettingService;
import com.jpoweredcart.common.system.template.SpringWebTemplateService;
import com.jpoweredcart.common.system.template.TemplateService;
import com.jpoweredcart.common.utils.PathUtils;

/**
 * Main configuration class for the application.
 * Turns on @Component scanning, loads external application.properties, and sets up the database.
 * @author Hussachai Puripunpinyo
 */
@Configuration
@PropertySource("classpath:application.properties")
@ImportResource({
	"classpath:application.xml"
})
@ComponentScan(
	basePackages = {
		"com.jpoweredcart.admin.config",
		"com.jpoweredcart.admin.controller",
		"com.jpoweredcart.catalog.config",
		"com.jpoweredcart.catalog.controller",
	}
	//,excludeFilters = { @Filter(Configuration.class) }
)
@EnableTransactionManagement
//@EnableScheduling
public class MainModuleConfig implements ApplicationContextAware {
	
	private ApplicationContext applicationContext;
	
	@Inject
	private ServletContext servletContext;
	
	@Inject
	private Environment env;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		
		if(applicationContext.containsBean("custom_dataSource")){
			return (DataSource)applicationContext.getBean("custom_dataSource");
		}
		
		BoneCPDataSource dataSource = new BoneCPDataSource();
		dataSource.setDriverClass(env.getProperty("database.driverClass"));
		dataSource.setJdbcUrl(env.getProperty("database.url"));
		dataSource.setUsername(env.getProperty("database.username"));
		dataSource.setPassword(env.getProperty("database.password"));
		
		dataSource.setIdleConnectionTestPeriodInMinutes(env.getProperty("dataSource.idleConnectionTestPeriod", Long.class, 240L));
		dataSource.setIdleMaxAgeInMinutes(env.getProperty("dataSource.idleMaxAge", Long.class, 60L));
		dataSource.setMaxConnectionsPerPartition(env.getProperty("dataSource.maxConnectionsPerPartition", Integer.class));
		dataSource.setMinConnectionsPerPartition(env.getProperty("dataSource.minConnectionsPerPartition", Integer.class));
		dataSource.setPartitionCount(env.getProperty("dataSource.partitionCount", Integer.class));
		dataSource.setAcquireIncrement(env.getProperty("dataSource.acquireIncrement", Integer.class));
//		dataSource.setStatementCacheSize(env.getProperty("dataSource.statementsCacheSize", Integer.class));
		dataSource.setReleaseHelperThreads(env.getProperty("dataSource.releaseHelperThreads", Integer.class));
		return dataSource;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		
		if(applicationContext.containsBean("custom_transactionManager")){
			return (PlatformTransactionManager)applicationContext.getBean("custom_transactionManager");
		}
		
		return new DataSourceTransactionManager(dataSource());
	}
	
	@Bean
	public JdbcOperations jdbcOperations() {
		
		return new JdbcTemplate(dataSource());
	}
	
	//================================= SERVICES ==========================================//
	@Bean
	public UserService userService(){
		return new UserServiceImpl();
	}
	@Bean
	public CurrencyService currencyService(){
		return new CurrencyServiceImpl();
	}
	@Bean
	public StoreResolver storeResolver(){
		return new StoreResolverImpl();
	}
	//================================= SYSTEMS ==========================================//
	
	@Bean
	public TemplateService templateService(){
		if(applicationContext.containsBean("custom_templateService")){
			return (TemplateService)applicationContext.getBean("custom_templateService");
		}
		SpringWebTemplateService templateService = new SpringWebTemplateService();
		ViewResolver viewResolver = applicationContext.getBean("viewResolver", ViewResolver.class);
		templateService.setViewResolver(viewResolver);
		return templateService;
	}
	
	@Bean
	public SettingService settingService(){
		
		if(applicationContext.containsBean("custom_settingService")){
			return (SettingService)applicationContext.getBean("custom_settingService");
		}
		
		DefaultSettingService settingService = new DefaultSettingService();
		settingService.setJdbcOperations(jdbcOperations());
		settingService.setEnvironment(env);
		
		if(env.getProperty("setting.cacheable", Boolean.class, Boolean.FALSE)){
			EhCacheFactoryBean cacheFactory = new EhCacheFactoryBean();
			//use default cache manager CacheManager.getInstance();
			cacheFactory.setCacheName("settingCache");
			try{
				cacheFactory.afterPropertiesSet();
			}catch(Exception e){
				//TODO: do something
				e.printStackTrace();
			}
			Ehcache settingCache = cacheFactory.getObject();
			
			settingService.setSettingCache(settingCache);
		}
		
		return settingService;
	}
	
	
	@Bean
	public EmailService emailService() {
		
		if(applicationContext.containsBean("custom_emailService")){
			return (EmailService)applicationContext.getBean("custom_emailService");
		}
		
		SmtpEmailService emailService = new SmtpEmailService();
		emailService.setSettingService(settingService());
		
		return emailService;
	}
	
	@Bean
	public FileService downloadFileService(){
		if(applicationContext.containsBean("custom_downloadFileService")){
			return (FileService)applicationContext.getBean("custom_downloadFileService");
		}
		
		DefaultFileService defaultFileService = new DefaultFileService();
		
		String dlDir = env.getProperty("download.dir");
		String dlBaseUrl = env.getProperty("download.baseUrl", "/files/downloads");
		defaultFileService.setBaseDir(getDefaultFilePath(dlDir, dlBaseUrl));
		defaultFileService.setBaseUrl(getWebPath(dlBaseUrl));
		
		return defaultFileService;
	}
	
	@Bean
	public FileService mediaFileService(){
		
		if(applicationContext.containsBean("custom_mediaFileService")){
			return (FileService)applicationContext.getBean("custom_mediaFileService");
		}
		
		DefaultFileService defaultFileService = new DefaultFileService();
		
		String imgDir = env.getProperty("media.imageDir");
		String imgBaseUrl = env.getProperty("media.imageBaseUrl", "/files/images");
		
		imgDir = getDefaultFilePath(imgDir, imgBaseUrl);
		imgDir = PathUtils.ensureEndingFileSeparator(imgDir)+"data";
		defaultFileService.setBaseDir(imgDir);
		defaultFileService.setBaseUrl(getWebPath(imgBaseUrl));
		return defaultFileService;
	}
	
	@Bean
	public MediaService mediaService(){
		
		if(applicationContext.containsBean("custom_mediaService")){
			return (MediaService)applicationContext.getBean("custom_mediaService");
		}
		
		DefaultMediaService defaultMediaService = new DefaultMediaService();
		
		String imgDir = env.getProperty("media.imageDir");
		String imgBaseUrl = env.getProperty("media.imageBaseUrl", "/files/images");
		defaultMediaService.setImageDir(getDefaultFilePath(imgDir, imgBaseUrl));
		defaultMediaService.setImageBaseUrl(getWebPath(imgBaseUrl));
		
		String thumbDir = env.getProperty("media.thumbnailDir");
		String thumbBaseUrl = env.getProperty("media.thumbnailBaseUrl", "/files/images/thumbnails");
		defaultMediaService.setThumbnailDir(getDefaultFilePath(thumbDir, thumbBaseUrl));
		defaultMediaService.setThumbnailBaseUrl(getWebPath(thumbBaseUrl));
		
		return defaultMediaService;
	}
	
	private String getDefaultFilePath(String path, String uri){
		if(StringUtils.isBlank(path) && uri.startsWith("/")){
			return servletContext.getRealPath(uri);
		}
		return path;
	}
	
	private String getWebPath(String uri){
		if(uri!=null && uri.startsWith("/")){
			return servletContext.getContextPath()+uri;
		}
		return uri;
	}
	
	
}

