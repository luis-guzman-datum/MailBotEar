/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.confia.mailbot.facades;

import java.util.List;
import javax.ejb.Local;

import com.confia.mailbot.model.MailAttachment;
import com.confia.mailbot.model.MailTail;

/**
 *
 * @author lumelen
 */
@Local
public interface MailAttachmentFacadeLocal {

    void create(MailAttachment mailAttachment);

    void edit(MailAttachment mailAttachment);

    void remove(MailAttachment mailAttachment);

    MailAttachment find(Object id);
    
    MailAttachment editAndGetEntity(MailAttachment mailAttachment);

    List<MailAttachment> findAll();

    List<MailAttachment> findRange(int[] range);

    int count();
    
}
