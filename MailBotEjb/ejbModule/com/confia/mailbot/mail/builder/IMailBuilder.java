package com.confia.mailbot.mail.builder;

import java.io.FileNotFoundException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.confia.mailbot.model.MailBox;

public interface IMailBuilder {

	public EMail generateMailDto(MailBox box) throws AddressException, MessagingException, FileNotFoundException;  
}
