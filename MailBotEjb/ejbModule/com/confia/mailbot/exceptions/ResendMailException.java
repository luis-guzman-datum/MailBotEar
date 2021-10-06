package com.confia.mailbot.exceptions;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class ResendMailException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResendMailException(String errorMessage) {
		super(errorMessage);
	}
	
	public ResendMailException(String errorMessage, Throwable err) {
		super(errorMessage,err);
	}
	
}
