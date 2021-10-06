package com.confia.mailbot.dto;

import java.io.Serializable;
import java.util.List;

import com.confia.mailbot.model.MailBox;

public class MessageWrapper implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MailBox mailBox;
	private List<String> usedServers;
	private String chanel;
	
	public MailBox getMailBox() {
		return mailBox;
	}
	public void setMailBox(MailBox mailBox) {
		this.mailBox = mailBox;
	}
	
	public List<String> getUsedServers() {
		return usedServers;
	}
	public void setUsedServers(List<String> usedServers) {
		this.usedServers = usedServers;
	}
	public String getChanel() {
		return chanel;
	}
	public void setChanel(String chanel) {
		this.chanel = chanel;
	}
	
	
}
