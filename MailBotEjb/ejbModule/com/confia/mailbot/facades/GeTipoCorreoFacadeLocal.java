/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.confia.mailbot.facades;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.confia.mailbot.model.GeTipoCorreo;

/**
 *
 * @author lumelen
 */
@Local
public interface GeTipoCorreoFacadeLocal {

    void create(GeTipoCorreo geTipoCorreo);

    void edit(GeTipoCorreo geTipoCorreo);

    void remove(GeTipoCorreo geTipoCorreo);

    GeTipoCorreo find(Object id);

    List<GeTipoCorreo> findAll();

    List<GeTipoCorreo> findRange(int[] range);

    int count();
    
    Map<Integer,String> getTipoCorreos(List<Integer> listaIds);
}
