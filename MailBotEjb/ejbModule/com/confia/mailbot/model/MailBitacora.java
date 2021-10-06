/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.confia.mailbot.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lumelen
 */
@Entity
@Table(name = "MAIL_BITACORA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MailBitacora.findAll", query = "SELECT m FROM MailBitacora m"),
    @NamedQuery(name = "MailBitacora.findByIdBitacora", query = "SELECT m FROM MailBitacora m WHERE m.idBitacora = :idBitacora"),
    @NamedQuery(name = "MailBitacora.findByDescripcion", query = "SELECT m FROM MailBitacora m WHERE m.descripcion = :descripcion"),
    @NamedQuery(name = "MailBitacora.findByIdTail", query = "SELECT m FROM MailBitacora m WHERE m.idTail = :idTail"),
    @NamedQuery(name = "MailBitacora.findByFechaIngreso", query = "SELECT m FROM MailBitacora m WHERE m.fechaIngreso = :fechaIngreso")})
public class MailBitacora implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_BITACORA")
    private Long idBitacora;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "ID_TAIL")
    private Long idTail;
    @Column(name = "FECHA_INGRESO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngreso;
    @JoinColumn(name = "ID_MAIL", referencedColumnName = "ID_MAIL")
    @ManyToOne(optional = false)
    private MailBox idMail;

    public MailBitacora() {
    }

    public MailBitacora(Long idBitacora) {
        this.idBitacora = idBitacora;
    }

    public Long getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(Long idBitacora) {
        this.idBitacora = idBitacora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getIdTail() {
        return idTail;
    }

    public void setIdTail(Long idTail) {
        this.idTail = idTail;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public MailBox getIdMail() {
        return idMail;
    }

    public void setIdMail(MailBox idMail) {
        this.idMail = idMail;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBitacora != null ? idBitacora.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MailBitacora)) {
            return false;
        }
        MailBitacora other = (MailBitacora) object;
        if ((this.idBitacora == null && other.idBitacora != null) || (this.idBitacora != null && !this.idBitacora.equals(other.idBitacora))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.datum.confia.mailbot.model.MailBitacora[ idBitacora=" + idBitacora + " ]";
    }
    
}
