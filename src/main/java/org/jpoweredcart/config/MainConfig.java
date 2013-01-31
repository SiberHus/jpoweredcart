package org.jpoweredcart.config;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.sql.DataSource;

import net.sf.ehcache.Ehcache;

import org.apache.activemq.Service;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.activemq.store.PersistenceAdapter;
import org.apache.activemq.store.kahadb.KahaDBPersistenceAdapter;
import org.apache.commons.lang3.StringUtils;
import org.jpoweredcart.common.service.ConfigService;
import org.jpoweredcart.common.service.EmailService;
import org.jpoweredcart.common.service.impl.DefaultConfigService;
import org.jpoweredcart.common.service.impl.DefaultEmailService;
import org.jpoweredcart.common.service.impl.JmsEmailService;
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
		"org.jpoweredcart.admin.controller"
	}, 
	excludeFilters = { @Filter(Configuration.class) })
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class MainConfig {
	
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
	public ConfigService configService(){
		
		DefaultConfigService configService = new DefaultConfigService();
		configService.setJdbcOperations(jdbcOperations());
		configService.setEnvironment(env);
		
		if(env.getProperty("config.cacheable", Boolean.class, Boolean.FALSE)){
			EhCacheFactoryBean cacheFactory = new EhCacheFactoryBean();
			//use default cache manager CacheManager.getInstance();
			cacheFactory.setCacheName("configCache");
			try{
				cacheFactory.afterPropertiesSet();
			}catch(Exception e){
				//TODO: do something
				e.printStackTrace();
			}
			Ehcache configCache = cacheFactory.getObject();
			
			configService.setConfigCache(configCache);
		}
		
		return configService;
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
		emailService.setConfigService(configService());
		
		return emailService;
	}
}

