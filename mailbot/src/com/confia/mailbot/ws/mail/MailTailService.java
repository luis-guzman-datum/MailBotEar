/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.confia.mailbot.ws.mail;

import com.confia.mailbot.facades.MailTailFacadeLocal;
import com.confia.mailbot.model.MailTail;

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
@WebService(serviceName = "MailTailService")
public class MailTailService {

    @EJB
    private MailTailFacadeLocal ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    @WebMethod(operationName = "create")
    @Oneway
    public void createMailTail(@WebParam(name = "mailTail") MailTail mailTail) {
        ejbRef.create(mailTail);
    }

    @WebMethod(operationName = "edit")
    @Oneway
    public void editMailTail(@WebParam(name = "mailTail") MailTail mailTail) {
        ejbRef.edit(mailTail);
    }

//    @WebMethod(operationName = "remove")
//    @Oneway
//    public void remove(@WebParam(name = "mailTail") MailTail mailTail) {
//        ejbRef.remove(mailTail);
//    }

    @WebMethod(operationName = "find")
    public MailTail findMailTail(@WebParam(name = "id") String id) {
        return ejbRef.find(new BigDecimal(id));
    }

    @WebMethod(operationName = "findAll")
    public List<MailTail> findAllMailTail() {
        return ejbRef.findAll();
    }

    @WebMethod(operationName = "findRange")
    public List<MailTail> findRangeMailTail(@WebParam(name = "range") int[] range) {
        return ejbRef.findRange(range);
    }

    @WebMethod(operationName = "count")
    public int countMailTail() {
        return ejbRef.count();
    }
    
}
