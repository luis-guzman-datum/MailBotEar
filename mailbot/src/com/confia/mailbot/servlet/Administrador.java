package com.confia.mailbot.servlet;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.naming.NamingException;

import javax.servlet.*;
import javax.servlet.http.*;
import org.quartz.SchedulerException;

import com.confia.mailbot.model.MailTail;
import com.confia.mailbot.scheduler.SchedulerService;
import com.confia.mailbot.scheduler.UpdateService;

/**
 *
 * @author acruz
 * @version
 */
public class Administrador extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Logger logger= Logger.getLogger(this.getClass().getCanonicalName());      
	
	@EJB
	private UpdateService updateService;

	
	@Override
	public void init() throws ServletException {
		/*// TODO Auto-generated method stub
		super.init();		
		try {
			updateService.startJob();
	        System.out.println("GESTION - SERVICE STARTED");	        
	        

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("MAILBOT - ERROR STARTING SCHEDULER SERVICE");
		}*/

	} 

	@Override
	public void destroy() {
		// PARANDO SERVICIO CRON
		/*System.out.println("GESTION - STOPING SERVICE...");
		updateService.stopScheduler();
		super.destroy();
		System.out.println("GESTION - SERVICE STOPPED");
		*/
	}

	

}
