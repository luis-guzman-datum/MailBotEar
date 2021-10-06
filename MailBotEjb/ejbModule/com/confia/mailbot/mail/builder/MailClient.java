package com.confia.mailbot.mail.builder;

import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

import javax.ejb.EJB;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.confia.mailbot.facades.ParametroWebFacade;
import com.confia.mailbot.facades.ParametroWebFacadeLocal;
import com.confia.mailbot.model.ParametroWeb;
import com.confia.mailbot.utils.MailAddressesEnum;

public class MailClient {
	private Properties currProperties;
	private String smtpHost;
	private Session session;
	@EJB
	private ParametroWebFacadeLocal paramFacade;

	public MailClient(String smtpHost) {
		currProperties = new Properties();
		this.smtpHost = smtpHost;

		// initial context
		InitialContext context = null;
		try {
			context = new InitialContext();
		} catch (NamingException e1) {
			e1.printStackTrace();
		}

		try {

			// Agregar Parametro Web en Enum
			MailAddressesEnum mailAddressEnum = MailAddressesEnum.getMailSession(smtpHost);

			if (mailAddressEnum != null) {
				session = (Session) context.lookup(mailAddressEnum.getMailSessionJndi());
				// System.out.println("USING SESSION: "+mailAddressEnum.getMailSessionJndi());
			} else {
				currProperties.put("mail.smtp.host", this.smtpHost);
				session = Session.getInstance(currProperties, null);
			}

			session.setDebug(false);
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}

	public Session getSession() {
		return this.session;
	}

	public int sendMail(EMail email) throws UnknownHostException, MessagingException, SendFailedException {
		ParametroWeb env = paramFacade.find("ENV");
		System.out.println("********************  Ambiente seleccionado para envio de correos      **********************");
		System.out.println(env.toString());
		System.out.println("*********************************************************************************************");
		email.prepareEMail();	
		if (!env.getValor().equals("PRD")) {
			ParametroWeb parSmtpServer = paramFacade.find("CORREO_REMITENTE");
			email.setFrom(parSmtpServer.getValor());
			send0(email, email.getAllRecipients());
		} else {
			send0(email, email.getAllRecipients());
		}
		return 0;
	}

	private void send0(EMail msg, Address[] addresses) throws MessagingException, SendFailedException {

		/****/
		// for(Address addr: addresses) {
		// System.out.println("Address: "+addr.toString());
		// System.out.println("TYPE ADDRESS: "+addr.getType());
		// }
		if (addresses == null || addresses.length == 0)
			throw new SendFailedException("No recipient addresses");

		/*
		 * protocols is a hashtable containing the addresses indexed by address type
		 */
		Hashtable protocols = new Hashtable();

		// Vectors of addresses
		Vector invalid = new Vector();
		Vector validSent = new Vector();
		Vector validUnsent = new Vector();

		for (int i = 0; i < addresses.length; i++) {
			// is this address type already in the hashtable?

			if (protocols.containsKey(addresses[i].getType())) {
				Vector v = (Vector) protocols.get(addresses[i].getType());
				v.addElement(addresses[i]);
			} else {
				// need to add a new protocol
				Vector w = new Vector();
				w.addElement(addresses[i]);
				protocols.put(addresses[i].getType(), w);
			}
		}

		int dsize = protocols.size();

		/****/
		// System.out.println("protocol size: " + dsize);

		if (dsize == 0)
			throw new SendFailedException("No recipient addresses");

		Transport transport = null;

		/*
		 * Optimize the case of a single protocol.
		 */

		if (dsize == 1) {

			msg.saveChanges();
			transport = session.getTransport(addresses[0]);

			try {

				// System.out.println("CHANNEL MDB - ANTES DE CONNECT " +
				// Calendar.getInstance().getTimeInMillis());

				// Properties props = new Properties();
				//
				// props.setProperty("mail.transport.protocol", "smtp");
				// props.setProperty("mail.smtp.host", smtpHost);
				// props.setProperty("mail.host", smtpHost);
				// props.setProperty("mail.debug", "false");
				// props.setProperty("mail.from", "servicioalcliente@confia.com");

				// Session se = Session.getDefaultInstance(props);
				// System.out.println("TEST - DESPUES DE SESSION " +
				// Calendar.getInstance().getTimeInMillis());
				// transport = se.getTransport();

				transport.connect();
				// System.out.println("CHANNEL MDB - DESPUES DE CONNECT " +
				// Calendar.getInstance().getTimeInMillis());
				msg.saveChanges();
				transport.sendMessage(msg, addresses);
				// System.out.println("CHANNEL MDB - DESPUES DE SEND " +
				// Calendar.getInstance().getTimeInMillis());

			} finally {
				transport.close();
			}

			return;
		}

		/*
		 * More than one protocol. Have to do them one at a time and collect addresses
		 * and chain exceptions.
		 */
		MessagingException chainedEx = null;
		boolean sendFailed = false;

		Enumeration e = protocols.elements();
		while (e.hasMoreElements()) {
			Vector v = (Vector) e.nextElement();
			Address[] protaddresses = new Address[v.size()];
			v.copyInto(protaddresses);

			// Get a Transport that can handle this address type.
			if ((transport = session.getTransport(protaddresses[0])) == null) {
				// Could not find an appropriate Transport ..
				// Mark these addresses invalid.
				for (int j = 0; j < protaddresses.length; j++)
					invalid.addElement(protaddresses[j]);
				continue;
			}
			try {
				transport.connect();
				transport.sendMessage(msg, protaddresses);
			} catch (SendFailedException sex) {
				sex.printStackTrace();
				sendFailed = true;
				// chain the exception we're catching to any previous ones
				if (chainedEx == null)
					chainedEx = sex;
				else
					chainedEx.setNextException(sex);

				// retrieve invalid addresses
				Address[] a = sex.getInvalidAddresses();
				if (a != null)
					for (int j = 0; j < a.length; j++)
						invalid.addElement(a[j]);

				// retrieve validSent addresses
				a = sex.getValidSentAddresses();
				if (a != null)
					for (int k = 0; k < a.length; k++)
						validSent.addElement(a[k]);

				// retrieve validUnsent addresses
				Address[] c = sex.getValidUnsentAddresses();
				if (c != null)
					for (int l = 0; l < c.length; l++)
						validUnsent.addElement(c[l]);
			} catch (MessagingException mex) {
				mex.printStackTrace();

				sendFailed = true;
				// chain the exception we're catching to any previous ones
				if (chainedEx == null)
					chainedEx = mex;
				else
					chainedEx.setNextException(mex);
			} finally {
				transport.close();
			}
		}

		// done with all protocols. throw exception if something failed
		if (sendFailed || invalid.size() != 0 || validUnsent.size() != 0) {
			Address[] a = null, b = null, c = null;

			// copy address vectors into arrays
			if (validSent.size() > 0) {
				a = new Address[validSent.size()];
				validSent.copyInto(a);
			}
			if (validUnsent.size() > 0) {
				b = new Address[validUnsent.size()];
				validUnsent.copyInto(b);
			}
			if (invalid.size() > 0) {
				c = new Address[invalid.size()];
				invalid.copyInto(c);
			}
			throw new SendFailedException("Sending failed", chainedEx, a, b, c);

		}
	}

}