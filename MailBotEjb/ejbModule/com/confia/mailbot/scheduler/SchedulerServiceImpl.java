package com.confia.mailbot.scheduler;

import java.util.Calendar;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.utils.TriggerStatus;

import com.confia.mailbot.exceptions.MailTailInvalidStateException;
import com.confia.mailbot.facades.MailTailFacadeLocal;
import com.confia.mailbot.job.MailJob;
import com.confia.mailbot.model.MailTail;
import com.confia.mailbot.utils.MailBotUtils;

@Remote(SchedulerServiceRemote.class)
@Local(SchedulerService.class)
@Stateless(mappedName = "ejb/SchedulerService")
public class SchedulerServiceImpl implements SchedulerService {

	private SchedulerFactory schedFactory = null;
	private Scheduler scheduler = null;

	@EJB
	private MailTailFacadeLocal mailTailFacade;

	@Override
	public void startScheduler() {
		// SE INICIA EL SERVICIO DE CRON SCHEDULER
		//System.out.println("MAILBOT - STARTING QUEUE SERVICE");

		schedFactory = new StdSchedulerFactory();
		try {
			scheduler = schedFactory.getScheduler();
			scheduler.start();

		} catch (SchedulerException e) {
			e.printStackTrace();
			System.out.println("MAILBOT - ha ocurrido un error iniciando servicio de colas de correo");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void stopScheduler() {
		// SE PARAN TODOS LOS JOBS EN EJECUCION
		try {
			scheduler.shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addQueue(MailTail tail) throws SchedulerException, MailTailInvalidStateException {
		// AGREGANDO TAIL= QUEUE
		
		String[] names = scheduler.getJobNames(Scheduler.DEFAULT_GROUP);
		boolean alreadyInQueue = MailBotUtils.checkCurrentJobs(names, tail.getNombre()+ "-" + tail.getIdTail().intValue());
		//System.out.println("Already in Queue: "+alreadyInQueue);
		/**** alreadyInQueue: ya se encuentra agregado job en cola ****/
		
		/** Validación antes de inicio de Cola para verificar el estado **/
		if (tail.getEstado().equals("A") && alreadyInQueue) {
			throw new MailTailInvalidStateException(
					"Estado inválido. No puede iniciar la cola por que esta ya se encuentra activa. Estado Actual de Cola: "
							+ tail.getEstado());
		}
		/*****************************************************************/

		JobDetail jobDet = new JobDetail(tail.getNombre() + "-" + tail.getIdTail(), Scheduler.DEFAULT_GROUP,
				MailJob.class);
		jobDet.getJobDataMap().put("IdMailTail", Integer.valueOf(tail.getIdTail().intValue()));

		SimpleTrigger trigger = null;

		// se transforma a milisegundos
		long offset = 0L;

		if (tail.getTiempoEspera() != null && tail.getTiempoEspera().longValue() != 0L)
			offset = tail.getTiempoEspera().longValue() * 60 * 1000L;

		Date scheduledDate = null;
		if(tail.getFechaHoraInicio().compareTo(new Date()) <= 0 ) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MINUTE, 2);
			calendar.set(Calendar.SECOND, 0);
			scheduledDate = calendar.getTime();
		}else {
			scheduledDate = tail.getFechaHoraInicio();
		}	
		
		trigger = new SimpleTrigger(String.valueOf(tail.getIdTail().intValue()),
				String.valueOf(tail.getIdTail().intValue()), scheduledDate, null,
				SimpleTrigger.REPEAT_INDEFINITELY, offset);

		//System.out.println("MAILBOT - ADDING QUEUE " + tail.getNombre() + "-" + tail.getIdTail().intValue());
		Date date  = scheduler.scheduleJob(jobDet, trigger);
		//System.out.println("SCHEDULE AT :" + date);

		int state = scheduler.getTriggerState(String.valueOf(tail.getIdTail().intValue()),
				String.valueOf(tail.getIdTail().intValue()));
		//System.out.println("STATE :" + state);
		
		if(state == 1) {
			scheduler.resumeTriggerGroup(String.valueOf(tail.getIdTail().intValue()));
		}
		
		tail.setEstado("A");
		mailTailFacade.edit(tail);


	}

	@Override
	public void deleteQueue(MailTail tail) throws SchedulerException, MailTailInvalidStateException {
		// borrando cola de scheduler
		
		/** Validación antes de inicio de Cola para verificar el estado **/
		if (tail.getEstado().equals("X")) {
			throw new MailTailInvalidStateException(
					"Estado inválido. No puede desactivar cola por que esta ya se encuentra desactivada. Estado Actual de Cola: "
							+ tail.getEstado());
		} else if (tail.getEstado().equals("A")) {
			throw new MailTailInvalidStateException(
					"Estado inválido. No puede desactivar cola por que esta se encuentra activa. Debe detener la cola, antes de desactivarla. Estado Actual de Cola: "
							+ tail.getEstado());
		}
		/***************************************************************/
		
		//System.out.println("MAILBOT - REMOVING QUEUE " + tail.getNombre() + "-" + tail.getIdTail().intValue());

		boolean wasUnscheduled = scheduler.unscheduleJob(String.valueOf(tail.getIdTail().intValue()),
				String.valueOf(tail.getIdTail().intValue()));

		//System.out.println("MAILBOT - QUEUE " + tail.getIdTail().intValue() + " - UNSCHEDULED " + wasUnscheduled);
		
		tail.setEstado("X");
		mailTailFacade.edit(tail);
		//System.out.println("MAILBOT -  QUEUE REMOVED " + tail.getNombre());

	}

	@Override
	public void addJobToQueue() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pauseQueue(MailTail tail) throws SchedulerException, MailTailInvalidStateException {
		// pausing queue

		/** Validación antes de inicio de Cola para verificar el estado **/
		if (tail.getEstado().equals("X")) {
			throw new MailTailInvalidStateException(
					"Estado inválido. No puede pausar la cola por que esta se encuentra desactivada. Estado Actual de Cola: "
							+ tail.getEstado());
		} else if (tail.getEstado().equals("I")) {
			throw new MailTailInvalidStateException(
					"Estado inválido. No puede pausar la cola por que esta ya se encuentra detenida. Estado Actual de Cola: "
							+ tail.getEstado());
		}
		/***************************************************************/
		
		String[] names = scheduler.getJobNames(Scheduler.DEFAULT_GROUP);

		//for (String name : names) {
			//System.out.println("JOB: " + name);
		//}

		//System.out.println("MAILBOT - STOPPING QUEUE " + tail.getNombre() + "-" + tail.getIdTail().intValue());
		scheduler.pauseTriggerGroup(String.valueOf(tail.getIdTail().intValue()));

		names = scheduler.getJobNames(Scheduler.DEFAULT_GROUP);

		//for (String name : names) {
			//System.out.println("JOB: " + name);
		//}

		tail.setEstado("I");
		mailTailFacade.edit(tail);
		//System.out.println("MAILBOT -  QUEUE STOPPED " + tail.getNombre());

	}

	@Override
	public void resumeQueue(MailTail tail) throws SchedulerException, MailTailInvalidStateException {
		// RESUMING QUEUE

		/** Validación antes de inicio de Cola para verificar el estado **/
		if (tail.getEstado().equals("X")) {
			throw new MailTailInvalidStateException(
					"Estado inválido. No puede reanudar la cola por que esta se encuentra desactivada. Estado Actual de Cola: "
							+ tail.getEstado());
		} else if (tail.getEstado().equals("A")) {
			throw new MailTailInvalidStateException(
					"Estado inválido. No puede reanudar la cola por que esta ya se encuentra en estado Activo. Estado Actual de Cola: "
							+ tail.getEstado());
		}
		/***************************************************************/
		

		
		String[] names = scheduler.getJobNames(Scheduler.DEFAULT_GROUP);
		boolean flag = MailBotUtils.checkCurrentJobs(names, tail.getNombre() + "-" + tail.getIdTail().intValue());

		if (flag) {
			//System.out.println("MAILBOT - RESUMING QUEUE " + tail.getNombre() + "-" + tail.getIdTail().intValue());
			scheduler.resumeTriggerGroup(String.valueOf(tail.getIdTail().intValue()));
		} else {
			//System.out.println("MAILBOT - RESUMING QUEUE " + tail.getNombre() + "-" + tail.getIdTail().intValue() + " NOT ADDED - ADDING NOW");
			addQueue(tail);
		}

		names = scheduler.getJobNames(Scheduler.DEFAULT_GROUP);

		//for (String name : names) {
			//System.out.println("JOB: " + name);
		//}

		tail.setEstado("A");
		mailTailFacade.edit(tail);
		//System.out.println("MAILBOT - QUEUE RESUMED " + tail.getNombre());

	}

}
