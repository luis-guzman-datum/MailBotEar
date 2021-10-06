package com.confia.mailbot.scheduler;

import javax.ejb.Local;

import org.quartz.SchedulerException;

import com.confia.mailbot.exceptions.MailTailInvalidStateException;
import com.confia.mailbot.model.MailTail;

@Local
public interface SchedulerService {

	public void startScheduler();
	public void stopScheduler();
	public void addJobToQueue();
	public void addQueue(MailTail tail) throws SchedulerException,MailTailInvalidStateException;
	public void deleteQueue(MailTail tail) throws SchedulerException,MailTailInvalidStateException;
	public void pauseQueue(MailTail tail) throws SchedulerException,MailTailInvalidStateException;
	public void resumeQueue(MailTail tail) throws SchedulerException,MailTailInvalidStateException;
	
}
