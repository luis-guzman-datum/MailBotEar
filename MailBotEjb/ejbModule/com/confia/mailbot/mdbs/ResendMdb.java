package com.confia.mailbot.mdbs;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.confia.mailbot.dto.MessageWrapper;
import com.confia.mailbot.exceptions.ResendMailException;
import com.confia.mailbot.facades.ParametroWebFacadeLocal;
import com.confia.mailbot.mail.builder.MailSenderService;
import com.confia.mailbot.model.ParametroWeb;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "connectionFactoryJndiName", propertyValue = "MailBotConnFactory"),
		@ActivationConfigProperty(propertyName = "destinationJndiName", propertyValue = "resendQueue") })
public class ResendMdb implements MessageListener {

	@EJB
	private MailSenderService mailSenderService;

	@EJB
	private ParametroWebFacadeLocal paramFacade;

	public void onMessage(Message message) {

		/************************
		 * Se obtiene mensaje cuyo envio falló, para intentar nuevamente, con un
		 * servidor diferente
		 ************************/

		String defaultChanel = "defaultChanel";
		String nextSelectedAddress = "";
		ObjectMessage obj = (ObjectMessage) message;

		try {
			ParametroWeb mailsPerMinutePar = paramFacade.find("AVAILABLE_SMTP_ADDRESSES");
			String parametro = mailsPerMinutePar.getValor();
			String[] addresses = parametro.trim().split(",");
			MessageWrapper wrapper = (MessageWrapper) obj.getObject();

			if (addresses!=null && addresses.length > 0 && wrapper != null && wrapper.getMailBox() != null) {
				if (wrapper.getUsedServers()==null || wrapper.getUsedServers().isEmpty() || addresses.length == wrapper.getUsedServers().size()) {
					throw new ResendMailException(
							"Couldn't Queue again. Mail has already been sent to all available addresses or used addresses list is empty");
				}
				
				
				for(String addr: addresses) {
					if(addr!=null && !addr.isEmpty() && !wrapper.getUsedServers().contains(addr)) {
						nextSelectedAddress = addr;					
					}
				}
				
				if(!nextSelectedAddress.isEmpty()) {
				mailSenderService.buildAndSend(
						wrapper.getChanel() != null && !wrapper.getChanel().isEmpty() ? wrapper.getChanel()
								: defaultChanel,
								wrapper.getMailBox(), nextSelectedAddress, wrapper.getUsedServers());
				}
				
			} else {
				throw new ResendMailException("Couldn't Queue again. Message Wrapper or Mail Box Object is null");
			}

		} catch (JMSException e) {
			e.printStackTrace();
		} catch (ResendMailException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
