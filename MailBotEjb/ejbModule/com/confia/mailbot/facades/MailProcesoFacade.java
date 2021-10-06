/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.confia.mailbot.facades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.confia.mailbot.model.GeTipoCorreo;
import com.confia.mailbot.model.MailProceso;
import com.confia.mailbot.model.MailTail;

/**
 *
 * @author lumelen
 */
@Remote(MailProcesoFacadeRemote.class)
@Local(MailProcesoFacadeLocal.class)
@Stateless(mappedName="ejb/MailProcesoFacade")
public class MailProcesoFacade extends AbstractFacade<MailProceso> implements MailProcesoFacadeLocal {

	@EJB
	private GeTipoCorreoFacadeLocal tipoCorreoFacade;
	
    @PersistenceContext(unitName = "MailBotEap-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MailProcesoFacade() {
        super(MailProceso.class);
    }
    
	@Override
	public List<MailProceso> findAll() {
		Query q = getEntityManager().createQuery("select t from MailProceso t order by t.idProceso ASC");
		List<MailProceso> listaProcesos = q.getResultList();
		
		List<Integer> ids = new ArrayList<Integer>();
		
		for(MailProceso mailProceso: listaProcesos) {
			if(!ids.contains(mailProceso.getTipoCorreo())) {
				ids.add(mailProceso.getTipoCorreo());
			}
			
		}
		
		Map<Integer,String> hmp = tipoCorreoFacade.getTipoCorreos(ids);
		for(MailProceso mailProceso: listaProcesos) {
			mailProceso.setTipoCorreoLabel(hmp.get(mailProceso.getTipoCorreo()));
		}

		return listaProcesos;
	}

    
}
