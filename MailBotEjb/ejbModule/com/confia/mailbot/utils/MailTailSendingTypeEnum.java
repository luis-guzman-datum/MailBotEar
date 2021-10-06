package com.confia.mailbot.utils;


public enum MailTailSendingTypeEnum {
	TYPE_P("P","Parcial"),
	TYPE_T("T","Total");

	
	private String type;
	private String typeLabel;
	

	private MailTailSendingTypeEnum(String type, String typeLabel) {
		this.type = type;
		this.typeLabel = typeLabel;
	}

	public static MailTailSendingTypeEnum getType(String typeCode) {
		if("P".equals(typeCode)) {
			return TYPE_P;
		}else if("T".equals(typeCode)) {
			return TYPE_T;
		}
		return null;
	}

	public String getType() {
		return type;
	}

	public String getTypeLabel() {
		return typeLabel;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setTypeLabel(String typeLabel) {
		this.typeLabel = typeLabel;
	}

	
}
