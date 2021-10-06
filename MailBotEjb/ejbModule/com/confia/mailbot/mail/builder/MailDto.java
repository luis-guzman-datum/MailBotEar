package com.confia.mailbot.mail.builder;

import java.io.Serializable;

import com.confia.mailbot.model.MailBox;

public class MailDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MailBox mailBox;

	public MailBox getMailBox() {
		return mailBox;
	}

	public void setMailBox(MailBox mailBox) {
		this.mailBox = mailBox;
	}
	

}
