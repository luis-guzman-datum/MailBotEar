package com.confia.mailbot.mail.builder;

import java.io.FileNotFoundException;
import java.util.Calendar;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.commons.codec.binary.Base64;

import com.confia.mailbot.facades.MailBoxFacadeLocal;
import com.confia.mailbot.facades.ParametroWebFacadeLocal;
import com.confia.mailbot.model.MailAttachment;
import com.confia.mailbot.model.MailBox;
import com.confia.mailbot.model.ParametroWeb;
import com.confia.mailbot.utils.MailBotUtils;

//import weblogic.uddi.client.structures.datatypes.Email;

/**
 * Session Bean implementation class MailBuilderImpl
 */
@Stateless
@Local(IMailBuilder.class)
public class MailBuilderImpl implements IMailBuilder {

	@EJB
	private ParametroWebFacadeLocal paramFacade;
	@EJB
	private MailBoxFacadeLocal mailBoxFacade;

	/**
	 * Default constructor.
	 */
	public MailBuilderImpl() {
	}

	/**
	 * @throws MessagingException
	 * @throws AddressException
	 * @throws FileNotFoundException
	 * @see IMailBuilder#generateMailDto()
	 */
	public EMail generateMailDto(MailBox mailBox) throws AddressException, MessagingException, FileNotFoundException {
		MailDto mDto = null;
		EMail email = null;
		// definir servidor smtp
		String smtpHost = "";
		boolean isWindows = MailBotUtils.isWindows();
		ParametroWeb paramMountUnix = paramFacade.find("MOUNT_UNIX");
		ParametroWeb parSmtpServer = paramFacade.find("SMTP_SERVER");

		// si no viene nulo se toma sino utiliza la IP del parametro SMTP_SERVER
		if (mailBox.getIdProceso() != null && mailBox.getIdProceso().getIpSalida() != null && !mailBox.getIdProceso().getIpSalida().isEmpty())
			smtpHost = mailBox.getIdProceso().getIpSalida();
		else
			smtpHost = parSmtpServer.getValor();

		// SE INSTANCIA SESSION
		//System.out.println("CHANNEL MDB - ANTES DE MAIL SESSION " + Calendar.getInstance().getTimeInMillis());
		MailClient mClient = new MailClient(smtpHost);
		//System.out.println("CHANNEL MDB - DESPUES DE MAIL SESSION " + Calendar.getInstance().getTimeInMillis());

		// se vuelve a buscar por relacion 1 - N
		mailBox = mailBoxFacade.find(mailBox.getIdMail());

		// DATOS GENERALES DE CORREO
		email = new EMail(mClient.getSession());

		email.setFrom(mailBox.getFrom());
		email.setTO(mailBox.getTo());
		email.setCC(mailBox.getCc());
		email.setBCC(mailBox.getCco());
		email.setSubject(mailBox.getSubject());

		String convertHtmlContent = MailBotUtils.convertPath( mailBox.getHtmlContent(), paramMountUnix.getValor(),isWindows);
		HTMLMailPart htmlMP = new HTMLMailPart(convertHtmlContent);

		email.addMailPart(htmlMP, "R");

		// ADJUNTOS
		for (MailAttachment attach : mailBox.getMailAttachmentList()) {

			/*****
			 * Se verifica URL de attachment según entidad MailAttachment, sin embargo
			 * WebLogic si se ejecuta en un servidor linux, el formato a obtener en todas
			 * las ejecuciones siempre será con simbolo -> '/'
			 *****/
			
			String attachmentPath = "";
			if (!isWindows) {
				attachmentPath = MailBotUtils.convertPath(attach.getAttachmentPath(), paramMountUnix.getValor(),
						isWindows);
				//System.out.println("NOT WINDOWS");
			} else {
				attachmentPath = attach.getAttachmentPath();
				//System.out.println("WINDOWS");
			}

			//System.out.println("PATH: " + attachmentPath);

			FileMailPart fileMP = new FileMailPart(attachmentPath, attach.getContentId(), attach.getAttachmentName());
			
			email.addMailPart(fileMP, attach.getAttachmentType());
		}
		return email;

	}

}
