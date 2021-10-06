package com.confia.mailbot.utils;

public enum MailAddressesEnum {	
	
	ADDRESS_1("172.16.0.13","Principal","MailSessionConfia02"),
	ADDRESS_2("172.16.0.19","Secondary","MailSessionConfia01");

	private String address;
	private String addressLabel;
	private String mailSessionJndi;

	private MailAddressesEnum(String address, String addressLabel, String mailSessionJndi) {
		this.address = address;
		this.addressLabel = addressLabel;
		this.mailSessionJndi = mailSessionJndi;
	}

	public static MailAddressesEnum getMailSession(String addressIp) {
		if("172.16.0.13".equals(addressIp)) {
			return ADDRESS_1;
		}else if("172.16.0.19".equals(addressIp)) {
			return ADDRESS_2;
		}
		return null;
	}

	public String getAddress() {
		return address;
	}

	public String getAddressLabel() {
		return addressLabel;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setAddressLabel(String addressLabel) {
		this.addressLabel = addressLabel;
	}

	public String getMailSessionJndi() {
		return mailSessionJndi;
	}

	public void setMailSessionJndi(String mailSessionJndi) {
		this.mailSessionJndi = mailSessionJndi;
	}
	
	
	
}
