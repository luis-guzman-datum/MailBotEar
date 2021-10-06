package com.confia.mailbot.dto;

public enum SmtpErrorEnum {
	
	SMTP_432("421","4.3.2","Service not available"),
	SMTP_571("550","5.7.1","Unable to relay");
	
	private String statusCode;
	private String errorCode;
	private String errorString;
	
	
	private SmtpErrorEnum(String statusCode, String errorCode2, String errorString) {
		this.statusCode = statusCode;
		this.errorCode = errorCode2;
		this.errorString = errorString;
	}


	public static SmtpErrorEnum getEnumFromErrorCodes(String errorCode) {
		if("4.3.2".equals(errorCode)) {
			return SMTP_432;
		}else if("5.7.1".equals(errorCode)) {
			return SMTP_571;
		}
		return null;
	}

	public static SmtpErrorEnum getSMTP_432() {
		return SMTP_432;
	}
	
	public static SmtpErrorEnum getSMTP_571() {
		return SMTP_571;
	}


	public String getStatusCode() {
		return statusCode;
	}


	public String getErrorCode() {
		return errorCode;
	}


	public String getErrorString() {
		return errorString;
	}


}
