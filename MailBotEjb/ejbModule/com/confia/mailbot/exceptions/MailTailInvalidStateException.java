package com.confia.mailbot.exceptions;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class MailTailInvalidStateException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MailTailInvalidStateException(String errorMessage) {
		super(errorMessage);
	}
	
	public MailTailInvalidStateException(String errorMessage, Throwable err) {
		super(errorMessage,err);
	}
	
}
