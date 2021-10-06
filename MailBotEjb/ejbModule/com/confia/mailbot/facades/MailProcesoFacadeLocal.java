package com.confia.mailbot.facades;

import java.util.List;
import javax.ejb.Local;

import com.confia.mailbot.model.MailProceso;
import com.confia.mailbot.model.MailTail;

/**
 *
 * @author lumelen
 */
@Local
public interface MailProcesoFacadeLocal {

    void create(MailProceso mailProceso);

    void edit(MailProceso mailProceso);

    MailProceso editAndGetEntity(MailProceso mailProceso);
    
    void remove(MailProceso mailProceso);

    MailProceso find(Object id);

    List<MailProceso> findAll();

    List<MailProceso> findRange(int[] range);

    int count();
    
}
