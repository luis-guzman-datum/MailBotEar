package com.confia.mailbot.mdbs;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.confia.mailbot.facades.MailBoxFacadeLocal;
import com.confia.mailbot.facades.MailTailFacadeLocal;
import com.confia.mailbot.facades.ParametroWebFacadeLocal;
import com.confia.mailbot.mail.builder.EMail;
import com.confia.mailbot.mail.builder.IMailBuilder;
import com.confia.mailbot.mail.builder.MailClient;
import com.confia.mailbot.mail.builder.MailSenderService;
import com.confia.mailbot.model.MailBox;
import com.confia.mailbot.model.ParametroWeb;

/**
 * Message-Driven Bean implementation class for: MailTailMdb
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "connectionFactoryJndiName", propertyValue = "MailBotConnFactory"),
		@ActivationConfigProperty(propertyName = "destinationJndiName", propertyValue = "yahooChanel") })
public class YahooMdb implements MessageListener {

	@EJB
	private MailSenderService mailSenderService;

	/******
	 * Se obtiene mensaje enviado a cola JMS, se procesa en servicio MailSender
	 ************************/
	public void onMessage(Message message) {
		try {
			ObjectMessage obj = (ObjectMessage) message;
			mailSenderService.buildAndSend("YAHOO_CHANNEL_MPM", obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("se ha producido un error al enviar por canal yahoo");
			e.printStackTrace();
		}
	}

}
