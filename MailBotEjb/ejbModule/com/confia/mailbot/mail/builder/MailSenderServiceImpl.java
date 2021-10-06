package com.confia.mailbot.mail.builder;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.AddressException;
import javax.naming.NamingException;

import com.confia.mailbot.dto.MessageWrapper;
import com.confia.mailbot.facades.MailBoxFacadeLocal;
import com.confia.mailbot.facades.MailTailFacadeLocal;
import com.confia.mailbot.facades.ParametroWebFacadeLocal;
import com.confia.mailbot.jms.QueueSend;
import com.confia.mailbot.model.MailBox;
import com.confia.mailbot.model.MailTail;
import com.confia.mailbot.model.ParametroWeb;
import com.confia.mailbot.utils.MailBotUtils;
import com.confia.mailbot.utils.MailValidator;

@Stateless
@Local(MailSenderService.class)
public class MailSenderServiceImpl implements MailSenderService {

	@EJB
	private MailBoxFacadeLocal mailBoxFacade;
	@EJB
	private MailTailFacadeLocal tailFacade;
	@EJB
	private IMailBuilder mailBuilder;
	@EJB
	private ParametroWebFacadeLocal paramFacade;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void buildAndSend(String channelParam, ObjectMessage obj) {
		MailBox mBox;
		try {
			mBox = (MailBox) obj.getObject();
			buildAndSend(channelParam, mBox, "", new ArrayList<String>());
		} catch (JMSException e) {
			e.printStackTrace();
			System.out.println("Exception in JMS");
		}

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void buildAndSend(String channelParam, MailBox mBox, String defaultMailServer, List<String> usedAddresses) {
		// MailBox mBox = null;
		String smtpHost = "";
		// System.out.println("Entering mail sender unified service");
		// System.out.println("Using CHANNEL: " + channelParam);
		try {
			ParametroWeb mailsPerMinutePar = paramFacade.find(channelParam);
			double mpm = Double.valueOf(mailsPerMinutePar.getValor());
			
			if(mpm <= 0d) {
				mpm = 25d;
				System.out.println("NOT GETTING PARAMETER FROM DATABASE. WARNING");
			}
			/**
			 * Valor establecido segun desempeño en ambiente en desface por construcción y
			 * envio de correo
			 **/
			double gap = 15;
			double ratio = ((60 / mpm) * 1000) - gap;

			/*** Construcción de correo a enviar ***/
			EMail email = mailBuilder.generateMailDto(mBox);

			smtpHost = defaultMailServer != null && !defaultMailServer.isEmpty() ? defaultMailServer
					: mBox.getIdProceso().getIpSalida();

			/**** Se realiza envio de correo ***/
			MailClient mClient = new MailClient(smtpHost);
			mClient.sendMail(email);

			// System.out.println("MAIL SENT USING ADDRESS "+smtpHost);
			/**** Actualizando entidad MailBox: estado del envio de correo ***/
			mBox = mailBoxFacade.find(mBox.getIdMail());
			mBox.setEstado("E");
			mBox.setFechaEnvio(new Date());
			mailBoxFacade.edit(mBox);

			long finalRatio = (long)(Math.abs(ratio));
			Thread.sleep(finalRatio);

		} catch (UnknownHostException e) {
			e.printStackTrace();
			mBox = persistErrorMessages(e.getMessage(), "R", mBox.getIdMail());
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("Exception in Thread Sleep");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			mBox = persistErrorMessages(e.getMessage(), "X", mBox.getIdMail());
		} catch (SendFailedException e) {
			String flag = "X";
			String errorMessage = "";
			e.printStackTrace();
			String exceptionString = e.getMessage();
			if (e.getNextException() != null) {
				exceptionString = exceptionString + "\n" + e.getNextException().getMessage();
			}

			errorMessage = mBox.getMsg();
			errorMessage = errorMessage != null && !errorMessage.isEmpty() ? errorMessage + ";" : errorMessage;

			if (exceptionString != null && !exceptionString.isEmpty()
					&& MailBotUtils.validateErrorString(exceptionString)) {
				if (usedAddresses != null && !usedAddresses.contains(smtpHost)) {
					flag = "C";
					usedAddresses.add(smtpHost);
					mBox = persistErrorMessages(errorMessage + smtpHost + ":" + exceptionString, flag,
							mBox.getIdMail());
					queueInErrorQueue(mBox, channelParam, usedAddresses);
					System.out.println("MAIL QUEUED USING ADDRESS " + smtpHost);
				}
			}

			if (flag.equals("X")) {
				mBox = persistErrorMessages(errorMessage + smtpHost + ":" + exceptionString, flag, mBox.getIdMail());
			}

		} catch (MessagingException e) {
			e.printStackTrace();
			if (e.getNextException() != null) {
				String error = e.getNextException().toString();

				if (error.indexOf("UnknownHostException", 0) > -1) {
					e.printStackTrace();
					mBox = persistErrorMessages(error, "R", mBox.getIdMail());
				} else {
					int principio = error.indexOf("nested exception is:", 0);
					e.printStackTrace();
					mBox = persistErrorMessages(error.substring(principio + 20, error.length()), "R", mBox.getIdMail());
				}
			} else {
				e.printStackTrace();
				mBox = persistErrorMessages(e.getMessage(), "R", mBox.getIdMail());
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (mBox.getIdMail() != null) {
				persistErrorMessages(e.getMessage(), "X", mBox.getIdMail());
			}
		}

	}

	@Override
	public MailBox persistErrorMessages(String errorMessage, String estado, BigDecimal idMail) {

		try {
			MailBox mBox = mailBoxFacade.find(idMail);
			mBox.setEstado(estado);
			mBox.setMsg(errorMessage);
			return mailBoxFacade.editAndGetEntity(mBox);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public void queueInErrorQueue(MailBox mailBox, String chanel, List<String> failedAddresses) {

		MessageWrapper wrapper = new MessageWrapper();
		wrapper.setChanel(chanel);
		wrapper.setMailBox(mailBox);
		wrapper.setUsedServers(failedAddresses);
		try {
			QueueSend qSender = null;
			if (!chanel.isEmpty()) {
				qSender = new QueueSend("resendQueue");
				qSender.sendMessageWrapper(wrapper);
				qSender.close();
			}
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}


}
