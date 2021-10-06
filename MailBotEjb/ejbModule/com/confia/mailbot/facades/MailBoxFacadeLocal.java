/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.confia.mailbot.facades;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

import com.confia.mailbot.dto.MailBoxDto;
import com.confia.mailbot.model.MailBox;
import com.confia.mailbot.model.MailTail;

/**
 *
 * @author lumelen
 */
@Local
public interface MailBoxFacadeLocal {

    void create(MailBox mailBox);

    void edit(MailBox mailBox);

    void remove(MailBox mailBox);

    MailBox find(Object id);

    List<MailBox> findAll();

    List<MailBox> findRange(int[] range);

    int count();
    
    List<MailBox> obtenerPor(BigDecimal pTail, String pEstado, Integer pCantidad );
    
    List<MailBox> obtenerMailBoxPorGestion(String paramGestion, String paramEstado);
    
    List<Object[]> obtenerMailBoxPorGestionNative();
    
    int updateGestion(String estado, String periodo, int numeroGestion);
    
    MailBox editAndGetEntity(MailBox mailBox);
    
    List<MailBox> findByDatesAndStatusOpt(Date initDate, Date endDate, List<String> statusList);

    List<MailBox> findByDatesAndStatus(Date initDate, Date endDate, List<String> statusList);

	List<MailBoxDto> findByDatesAndStatusOptNQ(Date initDate, Date endDate, List<String> statusList);

}
