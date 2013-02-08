package org.jpoweredcart.common.service.impl;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.jpoweredcart.common.service.EmailMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class JmsEmailService extends DefaultEmailService implements MessageListener {
	
	private String queueName;
	
	private JmsTemplate jmsTemplate;
	
	@Override
	public void send(final EmailMessage message) {
		jmsTemplate.send(queueName, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage(message);
			}
		});
	}
	
	@Override
	public void onMessage(Message message) {
		ObjectMessage objectMessage = (ObjectMessage)message;
		try {
			EmailMessage emailMessage = (EmailMessage)objectMessage.getObject();
			super.send(emailMessage);
		} catch (JMSException e) {
			//TODO: do something
			e.printStackTrace();
		}
		
	}
	
	
	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
}
