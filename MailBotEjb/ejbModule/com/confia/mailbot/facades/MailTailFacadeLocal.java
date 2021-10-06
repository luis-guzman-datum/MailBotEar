/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.confia.mailbot.facades;

import java.util.Date;
import java.util.List;

import javax.ejb.EJBLocalObject;
import javax.ejb.Local;

import com.confia.mailbot.model.MailTail;

/**
 *
 * @author lumelen
 */
@Local
public interface MailTailFacadeLocal  {

    void create(MailTail mailTail);
    
    MailTail editAndGetEntity(MailTail mailTail);

    void edit(MailTail mailTail);

    void remove(MailTail mailTail);

    MailTail find(Object id);

    List<MailTail> findAll();
    
    List<MailTail> findByStatus(String pStatus);

    List<MailTail> findRange(int[] range);

    int count();
    

    
}
