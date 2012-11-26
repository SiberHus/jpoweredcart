package org.jpoweredcart.config;

import javax.inject.Inject;
import javax.sql.DataSource;

import net.sf.ehcache.Ehcache;

import org.jpoweredcart.common.service.ConfigService;
import org.jpoweredcart.common.service.impl.DefaultConfigService;
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
	
	//================================= SERVICES ==========================================//
	
	@Bean
	public ConfigService configService() throws Exception{
		
		DefaultConfigService configService = new DefaultConfigService();
		configService.setJdbcOperations(jdbcOperations());
		configService.setEnvironment(env);
		
		if(env.getProperty("config.cacheable", Boolean.class, Boolean.FALSE)){
			EhCacheFactoryBean cacheFactory = new EhCacheFactoryBean();
			//use default cache manager CacheManager.getInstance();
			cacheFactory.setCacheName("configCache");
			cacheFactory.afterPropertiesSet();
			Ehcache configCache = cacheFactory.getObject();
			
			configService.setConfigCache(configCache);
		}
		
		return configService;
	}
	
}

