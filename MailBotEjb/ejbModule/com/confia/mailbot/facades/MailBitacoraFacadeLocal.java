/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.confia.mailbot.facades;

import java.util.List;
import javax.ejb.Local;

import com.confia.mailbot.model.MailBitacora;

/**
 *
 * @author lumelen
 */
@Local
public interface MailBitacoraFacadeLocal {

    void create(MailBitacora mailBitacora);

    void edit(MailBitacora mailBitacora);

    void remove(MailBitacora mailBitacora);

    MailBitacora find(Object id);

    List<MailBitacora> findAll();

    List<MailBitacora> findRange(int[] range);

    int count();
    
}
