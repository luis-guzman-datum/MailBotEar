package com.confia.mailbot.mdbs;

import java.util.List;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.mail.internet.AddressException;
import javax.naming.NamingException;

import com.confia.mailbot.exceptions.UpdateGestionException;
import com.confia.mailbot.facades.MailBoxFacadeLocal;
import com.confia.mailbot.facades.MailTailFacadeLocal;
import com.confia.mailbot.jms.QueueSend;
import com.confia.mailbot.mail.builder.IMailBuilder;
import com.confia.mailbot.model.MailBox;
import com.confia.mailbot.model.MailTail;
import com.confia.mailbot.utils.MailBotUtils;
import com.confia.mailbot.utils.MailValidator;

/**
 * Message-Driven Bean implementation class for: MailTailMdb
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "connectionFactoryJndiName", propertyValue = "MailBotConnFactory"),
		@ActivationConfigProperty(propertyName = "destinationJndiName", propertyValue = "MailTailQueue") })
public class MailTailMdb implements MessageListener {

	@EJB
	private MailBoxFacadeLocal mailBoxFacade;
	@EJB
	private MailTailFacadeLocal tailFacade;
	@EJB
	private IMailBuilder mailBuilder;

	/**
	 * Default constructor.
	 */
	public MailTailMdb() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see MessageListener#onMessage(Message)
	 */
	/***
	 * Listener de Mensajes de Mail Tail, las colas son enviadas en mensaje JMS por Job de Quartz
	 ***/
	public void onMessage(Message message) {

		try {
			//System.out.println("MAILTAIL MDB - Reading message from Jms Queue");
			ObjectMessage obj = (ObjectMessage) message;
			MailValidator mailValidator = new MailValidator();

			MailTail mTail = null;

			mTail = (MailTail) obj.getObject();
			
			/********Se valida que objeto obtenido de Cola, sea de tipo MailTail y no sea nulo**********/
			if (mTail != null && mTail.getIdTail() != null) {

				//System.out.println("MAILTAIL MDB - Invoking from mdb mailTail queue " + mTail);

				/*****Se obtienen correos asociados a cola respectiva y en estado P*****/
				List<MailBox> mailboxes = mailBoxFacade.obtenerPor(mTail.getIdTail(), "P",
						mTail.getCantidadEnvio().intValue());

				for (MailBox mBox : mailboxes) {

					String exceptionMessage = "";
					boolean hasException = false;

					/*** se evalua si NO esta activa la cola se sale del bucle ***/
					/***
					 * Validacion No necesaria, ya que hilos en quartz funcionan correctamente
					 ***/
					if (!tailFacade.find(mTail.getIdTail()).getEstado().equals("A"))
						break;

					/***
					 * De ocurrir excepcion al actualizar Gestion no culmina el proceso, envio de
					 * correos continua
					 ******/
					try {
						/*** Verificacion de Actualizacion de Gestiones a nivel de Tabla MailBox ******/
						if (mBox.getActualizaGestion().equalsIgnoreCase("S")) {
							String periodo = "";
							String numeroGestion = "";
							int actualizados = 0;
							periodo = MailBotUtils.obtenerPeriodo(mBox.getReferencia());

							numeroGestion = MailBotUtils.obtenerNumeroGestion(mBox.getReferencia());

							if (MailBotUtils.valPeriodoCotiza(periodo)) {
								actualizados = mailBoxFacade.updateGestion("CER", periodo, Integer.valueOf(numeroGestion));
							} else {
								mBox.setActualizaGestion("X");
								mBox.setMsg("No se actualiza la gestion porque el periodo no es valido");
								mBox = mailBoxFacade.editAndGetEntity(mBox);
							}

							if (actualizados == 0) {
								mBox.setActualizaGestion("X");
								mBox.setMsg("No se actualiza la gestion porque el numero de gestion no se encontro");
								mBox = mailBoxFacade.editAndGetEntity(mBox);
							}

						}
					} catch (UpdateGestionException ex) {
						ex.printStackTrace();
						mBox.setActualizaGestion("X");
						mBox.setMsg(ex.getMessage());
						mBox = mailBoxFacade.editAndGetEntity(mBox);
					}

					/*****Segundo bloque, filtrado de correos en diferentes canales*****/
					QueueSend qSender = null;

					try {
						String chanel = "";

						/*** Verificacion de direcciones TO, incluyendo CCO Y CC ***********/
						/***
						 * Lanza Excepciones AddressException en caso alguna de las tres validaciones
						 * falle
						 ******/
						boolean validCC = true;
						boolean validCCO = true;
						
						if(mBox.getTo()!=null && !mBox.getTo().isEmpty()) {
							mBox.setTo(mBox.getTo().trim().replaceAll("\\s+", ""));
						}
						
						boolean validTO = mailValidator.validateMails(mBox.getTo());

						if (mBox.getCc() != null && !mBox.getCc().isEmpty()) {
							mBox.setCc(mBox.getCc().trim().replaceAll("\\s+", ""));
							validCC = mailValidator.validateMails(mBox.getCc());
						}

						if (mBox.getCco() != null && !mBox.getCco().isEmpty()) {
							mBox.setCco(mBox.getCco().trim().replaceAll("\\s+", ""));
							validCCO = mailValidator.validateMails(mBox.getCco());
						}

						if (validTO && validCC && validCCO) {
							if (mBox.getTo().contains("gmail")) {
								chanel = "googleChanel";							
							} else if (mBox.getTo().contains("outlook") || mBox.getTo().contains("hotmail")
									|| mBox.getTo().contains("live")) {
								chanel = "microsoftChanel";
							} else if (mBox.getTo().contains("yahoo")) {
								chanel = "yahooChanel";
							} else if (mBox.getTo().contains("confia")) {
								chanel = "confiaChanel";
								
							} else {
								chanel = "defaultChanel";
							}
							//System.out.println("Channel Used: " + chanel);
							
							if (!chanel.isEmpty()) {
								qSender = new QueueSend(chanel);
								qSender.sendMailMessage(mBox);
								qSender.close();

								/*** Se cambia estado a C para indicar que esta siendo procesado **/
								mBox.setEstado("C");
								mBox = mailBoxFacade.editAndGetEntity(mBox);

							}
							
						}

					} catch (NamingException e) {
						e.printStackTrace();
						exceptionMessage = e.getMessage();
						hasException = true;

					} catch (JMSException e) {
						e.printStackTrace();
						exceptionMessage = e.getMessage();
						hasException = true;
					} catch (AddressException e) {
						e.printStackTrace();
						exceptionMessage = e.getMessage();
						hasException = true;
					} catch (Exception e) {
						e.printStackTrace();
						exceptionMessage = e.getMessage();
						hasException = true;
					}finally {
						if (hasException) {
							mBox.setEstado("X");
							mBox.setMsg(exceptionMessage);
							mBox = mailBoxFacade.editAndGetEntity(mBox);
						}
					}

				}

			} else {
				/*****En caso de obtener objeto MailTail como null*****/
				System.out.println("MAILTAIL MDB - Error MailTail - NOT QUEUEING MAILS");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("se ha producido un error en mail tail mdb");
			e.printStackTrace();
		}
	}

}
