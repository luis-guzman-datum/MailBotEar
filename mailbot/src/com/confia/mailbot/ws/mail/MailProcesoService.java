/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.confia.mailbot.ws.mail;

import com.confia.mailbot.facades.MailProcesoFacadeLocal;
import com.confia.mailbot.model.MailProceso;

import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author lumelen
 */
@WebService(serviceName = "MailProcesoService")
public class MailProcesoService {

    @EJB
    private MailProcesoFacadeLocal ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    @WebMethod(operationName = "create")
    @Oneway
    public void createMailProceso(@WebParam(name = "mailProceso") MailProceso mailProceso) {
        ejbRef.create(mailProceso);
    }

    @WebMethod(operationName = "edit")
    @Oneway
    public void editMailProceso(@WebParam(name = "mailProceso") MailProceso mailProceso) {
        ejbRef.edit(mailProceso);
    }

    @WebMethod(operationName = "remove")
    @Oneway
    public void removeMailProceso(@WebParam(name = "mailProceso") MailProceso mailProceso) {
        ejbRef.remove(mailProceso);
    }

    @WebMethod(operationName = "find")
    public MailProceso findMailProceso(@WebParam(name = "id") String id) {
        return ejbRef.find(new BigDecimal(id));
    }

    @WebMethod(operationName = "findAll")
    public List<MailProceso> findAllMailProceso() {
        return ejbRef.findAll();
    }

    @WebMethod(operationName = "findRange")
    public List<MailProceso> findRangeMailProceso(@WebParam(name = "range") int[] range) {
        return ejbRef.findRange(range);
    }

    @WebMethod(operationName = "count")
    public int countMailProceso() {
        return ejbRef.count();
    }
    
}
