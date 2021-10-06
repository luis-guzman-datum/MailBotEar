package com.confia.mailbot.mail.builder;

import java.util.Calendar;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.codec.binary.Base64;

import com.confia.mailbot.utils.MailBotUtils;
import com.confia.mailbot.utils.MailValidator;

public class Tester {

	private static final String mailRegexPattern = 
			"^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";                  
	private static final String RFC2822RegexPattern = 
			"[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
	private static final String RFC2822RegexPatternV2 = 
			"[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
	
	private static final String ForbiddenChars = "'º°öª»•\\¡!|&\"#$~%€¬/()=¿?^`+*[]Ç¨{}:,;><ñÑÁáÉéÍíÓóÚú";
	
	private static Pattern pattern;
	private static Matcher matcher;
	
	public static void main(String[] args) {

		pattern = Pattern.compile(RFC2822RegexPattern,Pattern.CASE_INSENSITIVE);
		
		String firstString = "correprueba@hotmail.com";
		
		System.out.println("**"+firstString+"**");
		String str = firstString.trim().replaceAll("\\s+", "");
		System.out.println(customValidate(str));
	}


	
	public static boolean validateMails(String mailTo) throws AddressException{
		String[] mailsArray = mailTo.split(",");

		for(String mailStr:mailsArray) {
			if(!customValidate(mailStr)) {
				throw new AddressException("MSG Personalizado: Formato erróneo del correo destino. Revisar correo: "+mailStr);
			}
		}
		
		return true;
		
	}
	
	public static boolean validate(String email) {
		System.out.println("MAIL: "+ email);
		matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	public static boolean hasForbiddenChars(String email) {
		char[] charArray = ForbiddenChars.toCharArray();
		for(char ch: charArray) {
			int index = email.indexOf(ch);
			if(index >= 0) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean customValidate(String email) {
		if(email == null || email.isEmpty())
			return false;
		
		if(hasForbiddenChars(email)) {
			return false;
		}
		
		matcher = pattern.matcher(email);
		if(matcher.matches()) {
			try {
				InternetAddress emailAddr = new InternetAddress(email);
				return true;
			} catch (AddressException e) {
				e.printStackTrace();
				return false;
			}
		}else {
			return false;
		}
	}
	
	
	public void errorLookup(String email) {
		System.out.println("MAIL: "+ email);
		matcher = pattern.matcher(email);
		int count =0;

		while(matcher.find()) {
			count++;
			System.out.println("Match Number "+ count);
			System.out.println("LOOKING AT " + matcher.group());
			System.out.println("Start " + matcher.start());
			System.out.println("End " + matcher.end());
		}

	}
	/*public static void main(String[] args) {

		System.out.println("ejecutando test" + Calendar.getInstance().getTimeInMillis());
		boolean debug = true;
		Properties props = new Properties();

		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.host", "172.16.0.13");
		props.setProperty("mail.host", "172.16.0.13");
		props.setProperty("mail.debug", "true");
		props.setProperty("mail.from", "servicioalcliente@confia.com");

		Session se = Session.getDefaultInstance(props);
		System.out.println("TEST - DESPUES DE SESSION  " + Calendar.getInstance().getTimeInMillis());
		se.setDebug(debug);

		Transport tr = null;
		try {
			tr = se.getTransport();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		MimeMessage mm = new MimeMessage(se);
		try {
			mm.setSubject("Test standalone debug");
			mm.setFrom(new InternetAddress("servicioalcliente@confia.com"));
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BodyPart mbp = new MimeBodyPart();

		try {
			mbp.setContent("<h1>hola mundo EMAIL TEST</h1>", "text/html");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		MimeMultipart mp = new MimeMultipart();

		try {
			mp.addBodyPart(mbp);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			mm.setContent(mp);
			mm.addRecipient(Message.RecipientType.TO, new InternetAddress("datumtesting@outlook.com"));

			tr.connect();
			System.out.println("TEST -DESPUES DE CONNECT  " + Calendar.getInstance().getTimeInMillis());
			tr.send(mm, mm.getRecipients(Message.RecipientType.TO));
			System.out.println("TEST -DESPUES DE SEND  " + Calendar.getInstance().getTimeInMillis());
			tr.close();

		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
*/
	/*
	 * public static void main(String[] args) { String result =
	 * convertPath("\\\\confiamssal02\\WebMailFiles$\\plantillas\\ENEMOD00020.html",
	 * "/test/"); System.out.println(result); }
	 * 
	 * //Determina si está desplegado en Windows o Unix public static boolean
	 * isWindows() { String os = System.getProperty("os.name").toLowerCase(); if (os
	 * != null) { if (os.indexOf("windows") != -1 || os.indexOf("nt") != -1 ||
	 * os.equals("windows 95") || os.equals("windows 98")) {
	 * System.out.println("Sistema Operativo es Windows => " + os); return true; }
	 * else if (os.indexOf("mac") != -1) {
	 * System.out.println("Sistema Operativo es Mac => " + os); return false; } else
	 * if (os.indexOf("linux") != -1) {
	 * System.out.println("Sistema Operativo es Linux => " + os); return false; }
	 * else { System.out.println("Sistema Operativo es Unix => " + os); return
	 * false; } } else {
	 * System.out.println("Sistema Operativo no fue posible identificarlo => null "
	 * ); return false; } }
	 */

	// Modifica el path del directorio Windows a Unix
	/*
	 * public static String convertPath(String ruta, String serverUnix) { String
	 * rutaNueva = ""; int niveles = 2; //Nümero de niveles del árbol jerárquico de
	 * directorios de windows al que se desea accesar int nivelDir = niveles + 2;
	 * //Número total de backslash de windows. Se agregan 2 más que son los que
	 * enlazan al pc remoto ej. \\lacslvcfwdes268... if (isWindows()) { //Ruta
	 * Windows => \\LACSLVCFWDES021\webmailfiles$\contenidos\1234.html String
	 * backslash = "\\"; String rutaUnix = ruta.replace(backslash, "/"); char[]
	 * cadena = rutaUnix.toCharArray(); int found = 0; int count = 0; for (int i =
	 * 0; i < cadena.length; i++) { if (cadena[i] == '/') { found++; } count++; if
	 * (found == nivelDir) { break; } } if (found == nivelDir) { rutaNueva =
	 * rutaUnix.replace(rutaUnix.substring(0, count), serverUnix);
	 * System.out.println("Ruta Windows  => " + ruta);
	 * System.out.println("Ruta Unix AIX => " + rutaNueva); } else { rutaNueva =
	 * ruta; System.out.
	 * println("Ruta inválida! No fue posible determinar unidad montaje de Unix AIX"
	 * ); } } else { rutaNueva = ruta; } return rutaNueva; }
	 */

}
