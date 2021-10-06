package com.confia.mailbot.utils;

public enum MailTailStatusEnum {
	STATUS_A("A","Activa"),
	STATUS_X("X","Detenida"),
	STATUS_I("I","Pausada");
	
	private String status;
	private String statusLabel;
	
	private MailTailStatusEnum(String status, String statusLabel) {
		this.status = status;
		this.statusLabel = statusLabel;
	}
	
	public static MailTailStatusEnum getStatus(String statusCode) {
		if(statusCode.equals("A")) {
			return STATUS_A;
		}else if(statusCode.equals("X")) {
			return STATUS_X;
		}else if(statusCode.equals("I")) {
			return STATUS_I;
		}
		return null;
	}
	
	public String getStatus() {
		return status;
	}
	public String getStatusLabel() {
		return statusLabel;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setStatusLabel(String statusLabel) {
		this.statusLabel = statusLabel;
	}
	
	
}
