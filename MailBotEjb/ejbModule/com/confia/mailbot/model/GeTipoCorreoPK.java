/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.confia.mailbot.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author lumelen
 */
@Embeddable
public class GeTipoCorreoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;
    @Basic(optional = false)
    @Column(name = "TIPO_CORREO")
    private int tipoCorreo;

    public GeTipoCorreoPK() {
    }

    public GeTipoCorreoPK(String codEmpresa, int tipoCorreo) {
        this.codEmpresa = codEmpresa;
        this.tipoCorreo = tipoCorreo;
    }

    public String getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public int getTipoCorreo() {
        return tipoCorreo;
    }

    public void setTipoCorreo(int tipoCorreo) {
        this.tipoCorreo = tipoCorreo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmpresa != null ? codEmpresa.hashCode() : 0);
        hash += (int) tipoCorreo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof GeTipoCorreoPK)) {
            return false;
        }
        GeTipoCorreoPK other = (GeTipoCorreoPK) object;
        if ((this.codEmpresa == null && other.codEmpresa != null) || (this.codEmpresa != null && !this.codEmpresa.equals(other.codEmpresa))) {
            return false;
        }
        if (this.tipoCorreo != other.tipoCorreo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.datum.confia.mailbot.model.GeTipoCorreoPK[ codEmpresa=" + codEmpresa + ", tipoCorreo=" + tipoCorreo + " ]";
    }
    
}
