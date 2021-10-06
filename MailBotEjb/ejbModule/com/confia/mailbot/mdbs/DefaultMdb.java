package com.confia.mailbot.mdbs;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.confia.mailbot.mail.builder.MailSenderService;

/**
 * Message-Driven Bean implementation class for: MailTailMdb
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "connectionFactoryJndiName", propertyValue = "MailBotConnFactory"),
		@ActivationConfigProperty(propertyName = "destinationJndiName", propertyValue = "defaultChanel") })
public class DefaultMdb implements MessageListener {

	@EJB
	private MailSenderService mailSenderService;

	/**
	 * Default constructor.
	 */
	public DefaultMdb() {
	}

	/**
	 * @see MessageListener#onMessage(Message)
	 */
	public void onMessage(Message message) {

		/******
		 * Se obtiene mensaje enviado a cola JMS, se procesa en servicio MailSender
		 ************************/
		try {
			ObjectMessage obj = (ObjectMessage) message;
			mailSenderService.buildAndSend("DEFAULT_CHANNEL_MPM", obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("se ha producido un error al enviar por canal default");
			e.printStackTrace();
		}

	}

}
