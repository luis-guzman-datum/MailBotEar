package com.confia.mailbot.mail.builder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.UnknownHostException;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.mail.MessagingException;

import com.confia.mailbot.model.MailBox;
import com.confia.mailbot.model.MailTail;

public interface MailSenderService {
	void buildAndSend(String channelParam, ObjectMessage obj);
	public void buildAndSend(String channelParam, MailBox mBox, String defaultMailServer, List<String> usedAddresses);
	public MailBox persistErrorMessages(String errorMessage, String estado, BigDecimal idMail);
	public void queueInErrorQueue(MailBox mailBox, String chanel, List<String> failedAddresses);

}
