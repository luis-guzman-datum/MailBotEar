package com.confia.mailbot.scheduler;

public interface UpdateService {
	public void startJob();
	public void stopJob();
	public void runJob();
	public void stopScheduler();
}
