/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.confia.mailbot.facades;

import java.util.List;
import javax.ejb.Local;

import com.confia.mailbot.model.ParametroWeb;

/**
 *
 * @author lumelen
 */
@Local
public interface ParametroWebFacadeLocal {

    void create(ParametroWeb parametroWeb);

    void edit(ParametroWeb parametroWeb);

    void remove(ParametroWeb parametroWeb);

    ParametroWeb find(Object id);

    List<ParametroWeb> findAll();

    List<ParametroWeb> findRange(int[] range);

    int count();
    
}
