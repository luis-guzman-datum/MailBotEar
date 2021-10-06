package com.confia.mailbot.exceptions;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class UpdateGestionException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UpdateGestionException(String errorMessage) {
		super(errorMessage);
	}
	
	public UpdateGestionException(String errorMessage, Throwable err) {
		super(errorMessage,err);
	}
	
}