package com.confia.mailbot.mail.builder;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;

public class HTMLMailPart extends MimeBodyPart 
{ 
  public HTMLMailPart(String htmlPageSource) throws MessagingException 
  { 
    super(); 
    DataSource fds = new FileDataSource(htmlPageSource);
    this.setDataHandler(new DataHandler(fds));
  } 
}
