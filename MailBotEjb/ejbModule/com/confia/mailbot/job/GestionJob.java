package com.confia.mailbot.job;

import java.util.List;

import org.quartz.InterruptableJob;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.quartz.UnableToInterruptJobException;

import com.confia.mailbot.facades.MailBoxFacadeRemote;
import com.confia.mailbot.model.MailBox;
import com.confia.mailbot.utils.MailBotUtils;

public class GestionJob implements StatefulJob, InterruptableJob {

	private JobDetail jobDetail;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		jobDetail = context.getJobDetail();
		//System.out.println("GESTIONJOB - EXECUTING QUEUE  ID " + jobDetail.getKey());

		MailBoxFacadeRemote mailBoxFacade = null;
		mailBoxFacade = (MailBoxFacadeRemote) MailBotUtils
				.beanLookUp("ejb.MailBoxFacade#com.confia.mailbot.facades.MailBoxFacadeRemote");

		if (mailBoxFacade != null) {
			String periodo = "";
		    String numeroGestion = "";
		    
			List<Object[]> mails = mailBoxFacade.obtenerMailBoxPorGestionNative();
			//System.out.println("GESTIONJOB - EXECUTING QUEUE  ID " + jobDetail.getKey());
			//System.out.println("GESTIONJOB - LOOKING FOR MAILS CONT:" + mails.size());
			
			for (Object[] obj : mails) {
				try {
					String idMail = (String) obj[0];
					String referencia = (String) obj[1];
					
					//System.out.println("GESTIONJOB - MAIL:" + idMail);
					//System.out.println("GESTIONJOB - REFERENCIA:" + referencia);
					periodo = MailBotUtils.obtenerPeriodo(referencia);
					numeroGestion = MailBotUtils.obtenerNumeroGestion(referencia);

					mailBoxFacade.updateGestion("CER", periodo, Integer.valueOf(numeroGestion));
					//System.out.println("GESTIONJOB - ACTUALIZANDO:" + referencia);
					
					MailBox mb = mailBoxFacade.find(idMail);
					mb.setActualizaGestion("F");
					mailBoxFacade.edit(mb);
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			
		}

	}

	@Override
	public void interrupt() throws UnableToInterruptJobException {
		//System.out.println("GESTIONJOB - INTERRUPTING QUEUE " + jobDetail.getName() + " ID " + jobDetail.getKey());
		System.out.println("GESTIONJOB - INTERRUPTING QUEUE " + jobDetail.getName() + " ID " + jobDetail.getDescription());
	}

}
