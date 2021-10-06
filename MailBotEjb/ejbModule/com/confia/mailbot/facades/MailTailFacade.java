/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.confia.mailbot.facades;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.confia.mailbot.jms.QueueSend;
import com.confia.mailbot.model.MailTail;
import com.confia.mailbot.utils.MailTailSendingTypeEnum;
import com.confia.mailbot.utils.MailTailStatusEnum;

/**
 *
 * @author lumelen
 */
@Remote(MailTailFacadeRemote.class)
@Local(MailTailFacadeLocal.class)
@Stateless(mappedName="ejb/MailTailFacade")
public class MailTailFacade extends AbstractFacade<MailTail> implements MailTailFacadeLocal, MailTailFacadeRemote  {

    @PersistenceContext(unitName = "MailBotEap-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MailTailFacade() {
        super(MailTail.class);
    }

       
	@Override
	public List<MailTail> findByStatus(String pStatus) {
		List<MailTail> lista = null;
		Query q = getEntityManager().createNamedQuery("MailTail.findByEstado");
		q.setParameter("estado", pStatus);
		
		lista =  q.getResultList();
		
		return lista;
	}

	@Override
	public void sendToJmsQueue(MailTail mailTail) {
		try {
			QueueSend sender = new QueueSend("MailTailQueue");
			sender.sendTailMessage(mailTail);
			sender.close();
		} catch (NamingException | JMSException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public List<MailTail> findAll() {
		Query q = getEntityManager().createQuery("Select T from MailTail T Order by T.idTail ASC");
		List<MailTail> listMailTail = q.getResultList();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String dateString = "";
		for(MailTail mailTail: listMailTail) {
			//System.out.println("Estado: "+mailTail.getEstado()); 
			MailTailStatusEnum status = MailTailStatusEnum.getStatus(mailTail.getEstado());
			
			if(status!=null) {
				mailTail.setEstadoLabel(status.getStatusLabel());
			}
			
			MailTailSendingTypeEnum type = MailTailSendingTypeEnum.getType(mailTail.getTipoEnvio());
			if(type!=null) {
				mailTail.setTipoEnvioLabel(type.getTypeLabel());
			}
			
			mailTail.setPendientes(obtenerPendientes(mailTail));
			
			try {
				mailTail.setUltFechaEjFormat("");
				if(mailTail.getUltFechaEjecucion()!=null) {
					dateString = format.format(mailTail.getUltFechaEjecucion());
					if(dateString!=null) {
						mailTail.setUltFechaEjFormat(dateString);
					}
				}
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
		
		return listMailTail;
	}
	
	public String obtenerPendientes(MailTail pTail) {
		String val ="0";
		
		try {
			Query q = getEntityManager().createQuery("SELECT count(m) FROM MailBox m WHERE m.estado = 'P' and m.idTail.idTail= :pTail");
			q.setParameter("pTail", pTail.getIdTail());
			val = ((Long) q.getSingleResult()).toString() ;
		} catch (NoResultException e) {
			e.printStackTrace();			
		}		

		return val;
	}
	
	
    
}
