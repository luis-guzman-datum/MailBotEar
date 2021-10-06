package com.confia.mailbot.scheduler;

import java.util.Date;
import java.util.logging.Logger;

import javax.ejb.Stateless;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

import com.confia.mailbot.job.GestionJob;

@Stateless
public class UpdateServiceImpl implements UpdateService {
   
	private SchedulerFactory schedFactory = null;
	private Scheduler scheduler = null;		
	
	@Override
	public void startJob() {	
		
		// SE INICIA EL SERVICIO DE CRON SCHEDULER
		///System.out.println("GESTION - STARTING SERVICE");
		
		schedFactory = new StdSchedulerFactory();		
		try {
			scheduler = schedFactory.getScheduler();
	        scheduler.start();		
	        
	        runJob();
	        
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("GESTION - ha ocurrido un error iniciando servicio de actualizacion de gestion");
		}
		
	}

	@Override
	public void stopJob() {
		try {
			scheduler.deleteJob("Gestion",Scheduler.DEFAULT_GROUP);
		} catch (SchedulerException e) {
			System.out.println("GESTION - ha ocurrido un error deteniendo job Actualizacion Gestion ");
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void stopScheduler() {
		// SE PARAN TODOS LOS JOBS EN EJECUCION
		try {
			scheduler.shutdown();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void runJob() {
		
		JobDetail jobDetail = new JobDetail("Gestion", Scheduler.DEFAULT_GROUP, GestionJob.class);
		
		SimpleTrigger trigger =null;		
		
		//se transforma a milisegundos
		long offset = 0L;
		
	    trigger = new SimpleTrigger("Gestion", Scheduler.DEFAULT_GROUP,
                new Date(), null, SimpleTrigger.REPEAT_INDEFINITELY,
                15 * 60 * 1000L);   
		
		try {
			scheduler.scheduleJob(jobDetail, trigger);						
			
		} catch (SchedulerException e) {
			System.out.println("GESTION - ha ocurrido un error iniciando Job Actualizacion Gestion ");
			e.printStackTrace();
			
		}
	}

}
