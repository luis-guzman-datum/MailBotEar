package com.confia.mailbot.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


public class MailValidator {
	private static final String mailRegexPattern = 
			"^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";                  
	private static final String RFC2822RegexPattern = 
			"[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
	private static final String ForbiddenChars = "'º°öª»•\\¡!|&\"#$~%€¬/()=¿?^`+*[]Ç¨{}:,;><ñÑÁáÉéÍíÓóÚú";

	private Pattern pattern;
	private Matcher matcher;
	public MailValidator() {
		pattern = Pattern.compile(RFC2822RegexPattern,Pattern.CASE_INSENSITIVE);

	}
	
	public boolean validateMails(String mailTo) throws AddressException{
		String[] mailsArray = mailTo.split(",");

		for(String mailStr:mailsArray) {
			if(!customValidate(mailStr)) {
				throw new AddressException("MSG Personalizado: Formato erróneo del correo destino. Revisar correo: "+mailStr);
			}
		}
		
		return true;
		
	}
	
	public boolean hasForbiddenChars(String email) {
		char[] charArray = ForbiddenChars.toCharArray();
		for(char ch: charArray) {
			int index = email.indexOf(ch);
			if(index >= 0) {
				return true;
			}
		}
		return false;
	}
	
	
	public boolean validate(String email) {
		System.out.println("MAIL: "+ email);
		matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	public boolean customValidate(String email) {
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
}
