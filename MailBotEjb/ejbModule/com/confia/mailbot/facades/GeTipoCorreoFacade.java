/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.confia.mailbot.facades;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.confia.mailbot.model.GeTipoCorreo;
import com.confia.mailbot.model.MailProceso;
import com.confia.mailbot.model.MailTail;
import com.confia.mailbot.utils.MailTailStatusEnum;

/**
 *
 * @author lumelen
 */

@Remote(GeTipoCorreoFacadeRemote.class)
@Local(GeTipoCorreoFacadeLocal.class)
@Stateless(mappedName = "ejb/GeTipoCorreoFacade")
public class GeTipoCorreoFacade extends AbstractFacade<GeTipoCorreo> implements GeTipoCorreoFacadeLocal {

    @PersistenceContext(unitName = "MailBotEap-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GeTipoCorreoFacade() {
        super(GeTipoCorreo.class);
    }
    
	
    public Map<Integer,String> getTipoCorreos(List<Integer> listaIds){
    	
    	String va = listaIds.toString().replaceAll("\\[","").replaceAll("\\]","");
    	Query q = getEntityManager().createQuery("select G from GeTipoCorreo G where G.geTipoCorreoPK.tipoCorreo in ("+va+")");
		//q.setParameter("listaIds", listaIds);
		
    	List<GeTipoCorreo> listaTipos = q.getResultList();
    	
		Map<Integer,String> hmp = new HashMap<Integer,String>();
		for(GeTipoCorreo ge : listaTipos) {
			//System.out.println("ID: "+ge.getGeTipoCorreoPK().getTipoCorreo());
			//System.out.println("DESCRIPCION: "+ge.getDescripcion());

			if(!hmp.containsKey(ge.getGeTipoCorreoPK().getTipoCorreo())) {
				hmp.put(ge.getGeTipoCorreoPK().getTipoCorreo(), ge.getDescripcion());
			}
		}
    	
		return hmp;
    }
    
	@Override
	public List<GeTipoCorreo> findAll() {
		Query q = getEntityManager().createQuery("select G from GeTipoCorreo G");
		List<GeTipoCorreo> tipoCorreos = q.getResultList();
		return tipoCorreos;
	}
    
}
