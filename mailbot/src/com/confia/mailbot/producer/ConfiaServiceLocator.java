package com.confia.mailbot.producer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.confia.mailbot.facades.GeTipoCorreoFacadeRemote;
import com.confia.mailbot.facades.MailBoxFacadeRemote;
import com.confia.mailbot.facades.MailProcesoFacadeRemote;
import com.confia.mailbot.facades.MailTailFacadeRemote;
import com.confia.mailbot.scheduler.SchedulerServiceRemote;

public class ConfiaServiceLocator {
	
	private MailTailFacadeRemote tailFacade = null;
	private MailProcesoFacadeRemote procesoFacade = null;
	private SchedulerServiceRemote schedulerService = null;
	private MailBoxFacadeRemote mailFacade = null;
	private GeTipoCorreoFacadeRemote tipoCorreoFacade = null;
	private Context env= null;
	
	public ConfiaServiceLocator(String tipo) {
		if(tipo != null && tipo.equals("MailTail")) {
			obtenerTailService();
		}else if(tipo != null && tipo.equals("MailProceso")) {
			obtenerProcesoService();
		}else if(tipo != null && tipo.equals("SchedulerService")) {
			obtenerSchedulerService();
		}else if(tipo != null && tipo.equals("MailBox")) {
			obtenerMailService();
		}else if(tipo != null && tipo.equals("GeTipoCorreo")) {
			obtenerGeTipoCorreoService();
		}
	}
	
	public void obtenerGeTipoCorreoService() {
		
		
		try {
			env =  new InitialContext();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		
		try {
			setTipoCorreoFacade((GeTipoCorreoFacadeRemote) env.lookup("ejb.GeTipoCorreoFacade#com.confia.mailbot.facades.GeTipoCorreoFacadeRemote"));
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void obtenerTailService() {
		
		
		try {
			env =  new InitialContext();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		
		try {
			setTailFacade((MailTailFacadeRemote) env.lookup("ejb.MailTailFacade#com.confia.mailbot.facades.MailTailFacadeRemote"));
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void obtenerProcesoService() {
		
		
		try {
			env =  new InitialContext();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		
		try {
			setProcesoFacade((MailProcesoFacadeRemote) env.lookup("ejb.MailProcesoFacade#com.confia.mailbot.facades.MailProcesoFacadeRemote"));
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void obtenerSchedulerService() {
		
		
		try {
			env =  new InitialContext();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		
		try {
			setSchedulerService((SchedulerServiceRemote) env.lookup("ejb.SchedulerService#com.confia.mailbot.scheduler.SchedulerServiceRemote"));
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
  public void obtenerMailService() {
		
		
		try {
			env =  new InitialContext();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		
		try {
			setMailFacade((MailBoxFacadeRemote) env.lookup("ejb.MailBoxFacade#com.confia.mailbot.facades.MailBoxFacadeRemote"));
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public MailTailFacadeRemote getTailFacade() {
		return tailFacade;
	}

	public void setTailFacade(MailTailFacadeRemote tailFacade) {
		this.tailFacade = tailFacade;
	}

	public MailProcesoFacadeRemote getProcesoFacade() {
		return procesoFacade;
	}

	public void setProcesoFacade(MailProcesoFacadeRemote procesoFacade) {
		this.procesoFacade = procesoFacade;
	}

	public SchedulerServiceRemote getSchedulerService() {
		return schedulerService;
	}

	public void setSchedulerService(SchedulerServiceRemote schedulerService) {
		this.schedulerService = schedulerService;
	}

	public MailBoxFacadeRemote getMailFacade() {
		return mailFacade;
	}

	public void setMailFacade(MailBoxFacadeRemote mailFacade) {
		this.mailFacade = mailFacade;
	}

	public GeTipoCorreoFacadeRemote getTipoCorreoFacade() {
		return tipoCorreoFacade;
	}

	public void setTipoCorreoFacade(GeTipoCorreoFacadeRemote tipoCorreoFacade) {
		this.tipoCorreoFacade = tipoCorreoFacade;
	}

}
