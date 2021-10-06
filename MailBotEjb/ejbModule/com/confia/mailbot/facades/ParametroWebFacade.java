/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.confia.mailbot.facades;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.confia.mailbot.model.ParametroWeb;

/**
 *
 * @author lumelen
 */
@Stateless
public class ParametroWebFacade extends AbstractFacade<ParametroWeb> implements ParametroWebFacadeLocal {

    @PersistenceContext(unitName = "MailBotEap-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParametroWebFacade() {
        super(ParametroWeb.class);
    }
    

}
