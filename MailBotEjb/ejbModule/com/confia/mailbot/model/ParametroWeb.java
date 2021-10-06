/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.confia.mailbot.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lumelen
 */
@Entity
@Table(name = "PARAMETRO_WEB")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParametroWeb.findAll", query = "SELECT p FROM ParametroWeb p"),
    @NamedQuery(name = "ParametroWeb.findByNbrParametro", query = "SELECT p FROM ParametroWeb p WHERE p.nbrParametro = :nbrParametro"),
    @NamedQuery(name = "ParametroWeb.findByValor", query = "SELECT p FROM ParametroWeb p WHERE p.valor = :valor")})
public class ParametroWeb implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NBR_PARAMETRO")
    private String nbrParametro;
    @Column(name = "VALOR")
    private String valor;

    public ParametroWeb() {
    }

    public ParametroWeb(String nbrParametro) {
        this.nbrParametro = nbrParametro;
    }

    public String getNbrParametro() {
        return nbrParametro;
    }

    public void setNbrParametro(String nbrParametro) {
        this.nbrParametro = nbrParametro;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nbrParametro != null ? nbrParametro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParametroWeb)) {
            return false;
        }
        ParametroWeb other = (ParametroWeb) object;
        if ((this.nbrParametro == null && other.nbrParametro != null) || (this.nbrParametro != null && !this.nbrParametro.equals(other.nbrParametro))) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "ParametroWeb [nbrParametro=" + nbrParametro + ", valor=" + valor + "]";
	}

   
    
}
