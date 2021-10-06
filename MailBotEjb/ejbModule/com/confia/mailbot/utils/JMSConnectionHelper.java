package com.confia.mailbot.utils;

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
import com.confia.mailbot.model.MailBox;
import com.confia.mailbot.model.MailTail;

public class JMSConnectionHelper {

	public final static String JNDI_FACTORY= "weblogic.jndi.WLInitialContextFactory";
	public final static String JMS_FACTORY= "MailBotConnFactory";
	public final static String QUEUE = "testQueue";
	
	private static QueueConnectionFactory qConnFactory;
	private static QueueConnection qConn;
	private static QueueSession qSession;
	private static QueueSender qSender;
	private static Queue queue;
	
	private static ObjectMessage objMsg;

	private static InitialContext context;
	
	public JMSConnectionHelper() throws NamingException, JMSException {

	}
	
	public static void init() throws NamingException, JMSException {
		System.out.println("Creating JMS Session. Should do this only once");
		context = new InitialContext();
		qConnFactory = (QueueConnectionFactory) context.lookup(JMS_FACTORY);
		qConn = qConnFactory.createQueueConnection();
		qSession = qConn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		qConn.start();
		
		
	}
	
	public static void sendTailMessage(MailTail message, String pQueue) throws JMSException, NamingException {
		if(context == null || qSession == null) {
			init();
		}
		queue = (Queue) context.lookup(pQueue);
		qSender = qSession.createSender(queue);
		objMsg = qSession.createObjectMessage(message);	
		qSender.send(objMsg);
		qSender.close();
	}
	

	public static void sendMailMessage(MailBox message, String pQueue) throws JMSException, NamingException {
		if(context == null || qSession == null) {
			init();
		}
		queue = (Queue) context.lookup(pQueue);
		qSender = qSession.createSender(queue);
		objMsg = qSession.createObjectMessage(message);		
		qSender.send(objMsg);
		qSender.close();
	}
	
	public static void sendMessageWrapper(MessageWrapper message, String pQueue) throws JMSException, NamingException {
		if(context == null || qSession == null) {
			init();
		}
		queue = (Queue) context.lookup(pQueue);
		qSender = qSession.createSender(queue);
		objMsg = qSession.createObjectMessage(message);	
		qSender.send(objMsg);
		qSender.close();
	}
	
	
	public static void close() throws JMSException {
		qSender.close();
		qSession.close();
		qConn.close();
	}
}
