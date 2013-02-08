package com.jpoweredcart.config;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

import net.sf.ehcache.Ehcache;

import org.apache.activemq.Service;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.activemq.store.PersistenceAdapter;
import org.apache.activemq.store.kahadb.KahaDBPersistenceAdapter;
import org.apache.commons.lang3.StringUtils;
import com.jpoweredcart.common.service.EmailService;
import com.jpoweredcart.common.service.ImageService;
import com.jpoweredcart.common.service.SettingService;
import com.jpoweredcart.common.service.impl.AbstractImageService;
import com.jpoweredcart.common.service.impl.DefaultEmailService;
import com.jpoweredcart.common.service.impl.DefaultSettingService;
import com.jpoweredcart.common.service.impl.FileSystemImageService;
import com.jpoweredcart.common.service.impl.JmsEmailService;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.AbstractMessageListenerContainer;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jolbox.bonecp.BoneCPDataSource;

/**
 * Main configuration class for the application.
 * Turns on @Component scanning, loads external application.properties, and sets up the database.
 * @author Hussachai Puripunpinyo
 */
@Configuration
@ComponentScan(
	basePackages = {
		"com.jpoweredcart.admin.controller"
	}, 
	excludeFilters = { @Filter(Configuration.class) })
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class MainConfig {
	
	@Inject
	private ServletContext servletContext;
	
	@Inject
	private Environment env;
	
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		
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
		
		return new DataSourceTransactionManager(dataSource());
	}
	
	@Bean
	public JdbcOperations jdbcOperations() {
		
		return new JdbcTemplate(dataSource());
	}
	
	@Bean(initMethod="start", destroyMethod="stop")
	public Service embeddedBrokerService(){
		if(env.getProperty("jms.broker.local", Boolean.class, Boolean.FALSE)){
			BrokerService brokerService = new BrokerService();
			brokerService.setBrokerName("embeddedBroker");
			brokerService.setTransportConnectorURIs(new String[]{env.getProperty("jms.broker.url")});
			String dataDir = env.getProperty("jms.broker.dataDir");
			brokerService.setDataDirectory(dataDir);
			PersistenceAdapter persistenceAdapter = new KahaDBPersistenceAdapter();
			persistenceAdapter.setDirectory(new File(dataDir+File.separator+"kahadb"));
			try {
				brokerService.setPersistenceAdapter(persistenceAdapter);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return brokerService;
		}
		return null;
	}
	
	@Bean
	public ConnectionFactory jmsConnectionFactory(){
		
		ActiveMQConnectionFactory jmsConnectionFactory = new ActiveMQConnectionFactory();
		jmsConnectionFactory.setBrokerURL(env.getProperty("jms.broker.url"));
		
		return jmsConnectionFactory;
	}
	
	@Bean 
	public JmsTemplate jmsTemplate(){
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(jmsConnectionFactory());
		return jmsTemplate;
	}
	
	@Bean
	public AbstractMessageListenerContainer emailMessageListener(){
		
		DefaultMessageListenerContainer listenerContainer = null;
		String queueName = env.getProperty("jms.queue.email");
		if(StringUtils.isNotBlank(queueName)){
			listenerContainer = new DefaultMessageListenerContainer();
			listenerContainer.setConnectionFactory(jmsConnectionFactory());
			listenerContainer.setDestinationName(queueName);
			listenerContainer.setMessageListener(emailService());
		}
		
		return listenerContainer;
	}
	
	//================================= SERVICES ==========================================//
	
	@Bean
	public SettingService settingService(){
		
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
		
		DefaultEmailService emailService = null;
		String queueName = env.getProperty("jms.queue.email");
		if(StringUtils.isNotBlank(queueName)){
			JmsEmailService jmsEmailService = new JmsEmailService();
			jmsEmailService.setQueueName(queueName);
			jmsEmailService.setJmsTemplate(jmsTemplate());
			emailService = jmsEmailService;
		}else{
			emailService = new DefaultEmailService();
		}
		emailService.setSettingService(settingService());
		
		return emailService;
	}
	
	@Bean
	public ImageService imageService(){
		
		AbstractImageService imageService = null;
		String imgBaseDir = env.getProperty("image.baseDir");
		String imgBaseUrl = env.getProperty("image.baseUrl");
		
		FileSystemImageService fsImageService = new FileSystemImageService();
		if(StringUtils.isBlank(imgBaseUrl)){
			throw new IllegalArgumentException("image.baseUrl cannot be blank");
		}else if(imgBaseUrl.startsWith("/")){
			if(StringUtils.isBlank(imgBaseDir)){
				fsImageService.setBaseDir(servletContext.getRealPath(imgBaseUrl));
			}
			fsImageService.setBaseUrl(servletContext.getContextPath()+imgBaseUrl);
		}else{
			fsImageService.setBaseDir(imgBaseDir);
			fsImageService.setBaseUrl(imgBaseUrl);
		}
		imageService = fsImageService;
		
		return imageService;
	}
}
