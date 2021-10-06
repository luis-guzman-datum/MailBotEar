package com.confia.mailbot.job;

import java.math.BigDecimal;
import java.util.Date;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.quartz.InterruptableJob;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.quartz.UnableToInterruptJobException;

import com.confia.mailbot.facades.MailTailFacadeRemote;
import com.confia.mailbot.model.MailTail;

public class MailJob implements StatefulJob, InterruptableJob {

	private JobDetail jobDetail;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		jobDetail = context.getJobDetail();
		//System.out.println("MAILBOT - EXECUTING QUEUE  ID " + jobDetail.getKey());
		//System.out.println("MAILBOT - EXECUTING QUEUE  NAME " + jobDetail.getName() );
		Context env = null;
		MailTailFacadeRemote tailFacade = null;

		try {
			env = new InitialContext();
			/**
			 * Se busca interface remota de MailTail, Job Quartz no es EJB, no puede ser inyectado
			 **/
			tailFacade = (MailTailFacadeRemote) env
					.lookup("ejb.MailTailFacade#com.confia.mailbot.facades.MailTailFacadeRemote");
			if (tailFacade != null) {
				Integer idMailTail = (Integer) context.getJobDetail().getJobDataMap().get("IdMailTail");

				MailTail tail = tailFacade.find(new BigDecimal(idMailTail));
				//System.out.println("MAILBOT - EXECUTING QUEUE  NAME " + tail);
				tailFacade.sendToJmsQueue(tail);

				/*****Se setea �ltima fecha ejecuci�n en tabla MailTail*******/
				tail.setUltFechaEjecucion(new Date());
				tailFacade.edit(tail);
			}
		} catch (NamingException e) {
			e.printStackTrace();
			System.out.println("MAILBOT - EXCEPTION WHILE LOOKING FOR REMOTE INTERFACE" + jobDetail.getDescription());
		}

	}

	@Override
	public void interrupt() throws UnableToInterruptJobException {
		System.out.println("MAILBOT - INTERRUPTING QUEUE " + jobDetail.getName() + " ID " + jobDetail.getDescription());
	}

}
