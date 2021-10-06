package com.confia.mailbot.mail.builder;

import java.io.Serializable;
import java.util.Date;
import javax.mail.BodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.AddressException;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.MessagingException;

public class EMail extends MimeMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//MimeMultipart mmpMultipartType;
	//MimeMultipart mmpRelatedType;
	MimeMultipart mpMixed;
	MimeMultipart mpMixedAlternative; 

	public EMail(Session session) throws MessagingException {
		super(session);
		mpMixed = new MimeMultipart("mixed");
		mpMixedAlternative = new MimeMultipart("mixed");
		//mmpMultipartType = new MimeMultipart("alternative");
		//mmpRelatedType = new MimeMultipart("related");
		BodyPart bp = new MimeBodyPart();
		bp.setContent(mpMixedAlternative);
		mpMixed.addBodyPart(bp);
		/*bp.setContent(mmpRelatedType);
		mmpMultipartType.addBodyPart(bp);*/
		this.setSentDate(new Date());

	}

	public void setFrom(String fromAccount) throws MessagingException, AddressException {
		this.setFrom(new InternetAddress(fromAccount));
	}

	public void setTO(String toRecipients) throws MessagingException, AddressException {
		if (toRecipients != null && toRecipients.length() > 0)
			this.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toRecipients, false));
	}

	public void setCC(String toRecipients) throws MessagingException, AddressException {
		if (toRecipients != null && toRecipients.length() > 0)
			this.setRecipients(Message.RecipientType.CC, InternetAddress.parse(toRecipients, false));
	}

	public void setBCC(String toRecipients) throws MessagingException, AddressException {
		if (toRecipients != null && toRecipients.length() > 0)
			this.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(toRecipients, false));
	}

	public void setSubject(String subject) throws MessagingException {
		super.setSubject(subject == null ? "(No Subject)" : subject);
	}

	public void addMailPart(MimeBodyPart mbp, String tipo) throws MessagingException {
		if ("M".equals(tipo))
			//mmpMultipartType.addBodyPart(mbp);
			mpMixed.addBodyPart(mbp);
		else
			//mmpRelatedType.addBodyPart(mbp);
			mpMixedAlternative.addBodyPart(mbp);
	}

	public void prepareEMail() throws MessagingException {
		//this.setContent(mmpMultipartType);
		this.setContent(mpMixedAlternative);
		this.setContent(mpMixed);
	}
}
