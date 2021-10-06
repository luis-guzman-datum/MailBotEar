package com.confia.mailbot.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.confia.mailbot.facades.MailTailFacadeLocal;
import com.confia.mailbot.model.MailTail;
import com.confia.mailbot.scheduler.SchedulerService;

/**
 * Servlet implementation class QueueEngineStart
 */
public class QueueEngineStart extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger= Logger.getLogger(this.getClass().getCanonicalName());      
	
	@EJB
	private SchedulerService schedulerService;
	
	@EJB
	private MailTailFacadeLocal tailFacade;
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueueEngineStart() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
	   @Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();		
		try {

	        
	        schedulerService.startScheduler();
	        //System.out.println("MAILBOT - QUEUE SERVICE STARTED");	        
	        //System.out.println("MAILBOT - ADDING QUEUES TO SCHEDULER...");
	        // SE OBTIENEN TODAS LAS QUEUES ACTIVAS
	        List<MailTail> lista = tailFacade.findByStatus("A");  
	        
	        for(MailTail tail: lista) {
	        	schedulerService.addQueue(tail);
	        }
	        
	        

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("MAILBOT - ERROR STARTING SCHEDULER SERVICE");
		}

	} 

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String operacion =request.getParameter("operacion");
		String idTail ="";
		idTail = request.getParameter("tail");
		
		try {
			if(operacion != null && operacion.equals("PAUSE")) {
				if(!idTail.isEmpty())
					schedulerService.pauseQueue(tailFacade.find(new BigDecimal(idTail)));
			}
				
			if(operacion != null && operacion.equals("RESUME")) {
				if(!idTail.isEmpty())
					schedulerService.resumeQueue(tailFacade.find(new BigDecimal(idTail)));
			}
			
			if(operacion != null && operacion.equals("DELETE")) {
				if(!idTail.isEmpty())
					schedulerService.deleteQueue(tailFacade.find(new BigDecimal(idTail)));
			}
			
			
			if(operacion != null && operacion.equals("MAILTEST")) {
				

				//System.out.println("ejecutando test" + Calendar.getInstance().getTimeInMillis());
				boolean debug = true;
				Properties props = new Properties();

				props.setProperty("mail.transport.protocol", "smtp");
				props.setProperty("mail.smtp.host", "172.16.0.19");
				props.setProperty("mail.host", "172.16.0.19");
				props.setProperty("mail.debug", "true");
				props.setProperty("mail.from", "servicioalcliente@confia.com");

				Session se = Session.getDefaultInstance(props);
				//System.out.println("TEST - DESPUES DE SESSION  " + Calendar.getInstance().getTimeInMillis());
				se.setDebug(debug);

				Transport tr = null;
				try {
					tr = se.getTransport();
				} catch (NoSuchProviderException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				MimeMessage mm = new MimeMessage(se);
				try {
					mm.setSubject("Test standalone debug");
					mm.setFrom(new InternetAddress("servicioalcliente@confia.com"));
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				BodyPart mbp = new MimeBodyPart();

				try {
					mbp.setContent("<h1>hola mundo EMAIL TEST</h1>", "text/html");
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				MimeMultipart mp = new MimeMultipart();

				try {
					mp.addBodyPart(mbp);
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					mm.setContent(mp);
					mm.addRecipient(Message.RecipientType.TO, new InternetAddress("emerson.guevara@confia.com"));

					tr.connect();
					//System.out.println("TEST -DESPUES DE CONNECT  " + Calendar.getInstance().getTimeInMillis());
					tr.send(mm, mm.getRecipients(Message.RecipientType.TO));
					//System.out.println("TEST -DESPUES DE SEND  " + Calendar.getInstance().getTimeInMillis());
					tr.close();

				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				
				
			}
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
	@Override
	public void destroy() {
		// PARANDO SERVICIO CRON
		//System.out.println("MAILBOT - STOPING QUEUE SERVICE...");
		schedulerService.stopScheduler();
		super.destroy();
		//System.out.println("MAILBOT - QUEUE SERVICE STOPPED");
		
	}

}
