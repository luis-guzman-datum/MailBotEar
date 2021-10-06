package com.confia.mailbot.jms;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.confia.mailbot.dto.MessageWrapper;
import com.confia.mailbot.mail.builder.MailDto;
import com.confia.mailbot.model.MailBox;
import com.confia.mailbot.model.MailTail;


public class QueueSend {

	public final static String JNDI_FACTORY= "weblogic.jndi.WLInitialContextFactory";
	public final static String JMS_FACTORY= "MailBotConnFactory";
	public final static String QUEUE = "testQueue";
	
	private QueueConnectionFactory qConnFactory;
	private QueueConnection qConn;
	private QueueSession qSession;
	private QueueSender qSender;
	private Queue queue;
	
	private TextMessage textMsg;
	private ObjectMessage objMsg;

	private InitialContext context;
	
	public QueueSend(String pQueue) throws NamingException, JMSException {
		
		 init(pQueue);
	}
	
	public void init(String pQueue) throws NamingException, JMSException {
		context = new InitialContext();
		qConnFactory = (QueueConnectionFactory) context.lookup(JMS_FACTORY);
		qConn = qConnFactory.createQueueConnection();
		qSession = qConn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		queue = (Queue) context.lookup(pQueue);
		qSender = qSession.createSender(queue);
		qConn.start();
		
	}
	
	public void sendTailMessage(MailTail message) throws JMSException {
		objMsg = qSession.createObjectMessage(message);
		
		qSender.send(objMsg);
	}
	

	public void sendMailMessage(MailBox message) throws JMSException {
		objMsg = qSession.createObjectMessage(message);
		
		qSender.send(objMsg);
	}
	
	public void sendMessageWrapper(MessageWrapper message) throws JMSException {
		objMsg = qSession.createObjectMessage(message);	
		qSender.send(objMsg);
	}
	
	
	public void close() throws JMSException {
		qSender.close();
		qSession.close();
		qConn.close();
	}
	
}
