package com.jpoweredcart.common.service.email;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;

import com.jpoweredcart.common.service.setting.SettingService;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class DefaultEmailService implements EmailService {
	
	private Map<Integer, JavaMailSender> mailSenders = new ConcurrentHashMap<Integer, JavaMailSender>();
	
	private SettingService settingService;
	
	@Override
	public void send(EmailMessage message) {
		
		String to = message.getTo();
		String from = message.getFrom();
		String senderName = message.getSenderName();
		String subject = message.getSubject();
		String bodyHtml = message.getBodyHtml();
		String bodyText = message.getBodyText();
		
		Map<String, String> variables = message.getVariables();
		if(variables!=null){
			StrSubstitutor substitutor = new StrSubstitutor(variables, "${", "}");
			to = substitutor.replace(to);
			from = substitutor.replace(from);
			senderName = substitutor.replace(senderName);
			subject = substitutor.replace(subject);
			bodyHtml = substitutor.replace(bodyHtml);
			bodyText = substitutor.replace(bodyText);
		}
		
		if(senderName!=null){
			from = senderName+" <"+from+">";
		}
		
		JavaMailSender emailSender = createJavaMailSender(message.getStoreId());
		
		MimeMessage mimeMessage = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		try{
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			if(bodyHtml!=null){
				helper.setText(bodyHtml, true);
			}
			if(bodyText!=null){
				helper.setText(bodyText, false);
			}
			
			emailSender.send(mimeMessage);
			
		}catch(MessagingException e){
			//TODO: do something
			e.printStackTrace();
		}
	}
	
	protected JavaMailSender createJavaMailSender(Integer storeId){
		
		JavaMailSender mailSender = mailSenders.get(storeId);
		if(mailSender!=null){
			return mailSender;
		}
		
		JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
		String host = settingService.getConfig(storeId, "config_smtp_host");
		int port = settingService.getConfig(storeId, "config_smtp_port", Integer.class);
		String username = settingService.getConfig(storeId, "config_smtp_username");
		String password = settingService.getConfig(storeId, "config_smtp_password");
		String parameters = settingService.getConfig(storeId, "config_mail_parameter");
		Properties props = null;
		if(StringUtils.isNotBlank(parameters)){
			props = new Properties();
			try {
				props.load(new StringReader(parameters));
			} catch (IOException e) {
				e.printStackTrace();
			}
			mailSenderImpl.setJavaMailProperties(props);
		}
//		sender.setProtocol("smtp");
		mailSenderImpl.setHost(host);
		mailSenderImpl.setPort(port);
		mailSenderImpl.setUsername(username);
		mailSenderImpl.setPassword(password);
		
		mailSenders.put(storeId, mailSenderImpl);
		
		return mailSenderImpl;
	}
	
	public JavaMailSender getJavaMailSender(Integer storeId){
		return mailSenders.get(storeId);
	}
	
	public boolean removeJavaMailSender(Integer storeId){
		if(mailSenders.containsKey(storeId)){
			mailSenders.remove(storeId);
			return true;
		}
		return false;
	}
	
	public SettingService getSettingService() {
		return settingService;
	}

	public void setSettingService(SettingService settingService) {
		this.settingService = settingService;
	}
	
	
}
