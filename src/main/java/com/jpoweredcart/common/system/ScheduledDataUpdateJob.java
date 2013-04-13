package com.jpoweredcart.common.system;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ScheduledDataUpdateJob implements ApplicationContextAware {

	private Logger logger = LoggerFactory.getLogger(ScheduledDataUpdateJob.class);
	
	private ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	public void updateData(){
		Map<String, ScheduledDataUpdate> beans = applicationContext
				.getBeansOfType(ScheduledDataUpdate.class);
		for(Map.Entry<String, ScheduledDataUpdate> entry: beans.entrySet()){
			logger.info("Start updating data for: {}", entry.getKey());
			entry.getValue().updateData();
			logger.info("{} is now updated", entry.getKey());
		}
		
		System.gc();
	}
	
}
