package com.confia.mailbot.mail.builder;

import java.io.FileNotFoundException;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;

public class FileMailPart extends MimeBodyPart {
	  
	  /** Creates a new instance of FileMailPart */
	  public FileMailPart(String source, String cid, String fileName) throws FileNotFoundException,
	MessagingException {
	    super();
	    DataSource fds = new FileDataSource(source);
	    this.setDataHandler(new DataHandler(fds));
	    this.setHeader("Content-ID",cid);
	    this.setFileName(fileName);
	  }
	}
