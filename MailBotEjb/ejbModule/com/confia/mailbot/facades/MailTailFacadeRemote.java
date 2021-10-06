package com.confia.mailbot.facades;

import com.confia.mailbot.model.MailTail;

public interface MailTailFacadeRemote extends MailTailFacadeLocal {
	
	public void sendToJmsQueue(MailTail mailTail);

}
