/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.confia.mailbot.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author lumelen
 */
@Entity
@Table(name = "MAIL_TAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MailTail.findAll", query = "SELECT m FROM MailTail m"),
    @NamedQuery(name = "MailTail.findByIdTail", query = "SELECT m FROM MailTail m WHERE m.idTail = :idTail"),
    @NamedQuery(name = "MailTail.findByTipoEnvio", query = "SELECT m FROM MailTail m WHERE m.tipoEnvio = :tipoEnvio"),
    @NamedQuery(name = "MailTail.findByCantidadEnvio", query = "SELECT m FROM MailTail m WHERE m.cantidadEnvio = :cantidadEnvio"),
    @NamedQuery(name = "MailTail.findByTiempoEspera", query = "SELECT m FROM MailTail m WHERE m.tiempoEspera = :tiempoEspera"),
    @NamedQuery(name = "MailTail.findByUltFechaEjecucion", query = "SELECT m FROM MailTail m WHERE m.ultFechaEjecucion = :ultFechaEjecucion"),
    @NamedQuery(name = "MailTail.findByEstado", query = "SELECT m FROM MailTail m WHERE m.estado = :estado"),
    @NamedQuery(name = "MailTail.findByNombre", query = "SELECT m FROM MailTail m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "MailTail.findByFechaHoraInicio", query = "SELECT m FROM MailTail m WHERE m.fechaHoraInicio = :fechaHoraInicio"),
    @NamedQuery(name = "MailTail.findByHoraInicio", query = "SELECT m FROM MailTail m WHERE m.horaInicio = :horaInicio"),
    @NamedQuery(name = "MailTail.findByHoraFin", query = "SELECT m FROM MailTail m WHERE m.horaFin = :horaFin")})
public class MailTail implements Serializable {

    @OneToMany(mappedBy = "idTail")
    private List<MailBox> mailBoxList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "tail_seq",strategy=GenerationType.SEQUENCE)
    @SequenceGenerator(name = "tail_seq", sequenceName = "tail_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "ID_TAIL")
    private BigDecimal idTail;
    @Column(name = "TIPO_ENVIO")
    private String tipoEnvio;
    @Column(name = "CANTIDAD_ENVIO")
    private BigInteger cantidadEnvio;
    @Column(name = "TIEMPO_ESPERA")
    private BigInteger tiempoEspera;
    @Column(name = "ULT_FECHA_EJECUCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultFechaEjecucion;
    @Column(name = "ESTADO")
    private String estado;
    @Transient
    private String estadoLabel;
    @Transient
    private String tipoEnvioLabel;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "FECHA_HORA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraInicio;
    @Column(name = "HORA_INICIO")
    private String horaInicio;
    @Column(name = "HORA_FIN")
    private String horaFin;
    @Transient
    private String pendientes;
    @Transient
    private String ultFechaEjFormat;

    public MailTail() {
    }

    public MailTail(BigDecimal idTail) {
        this.idTail = idTail;
    }

    public BigDecimal getIdTail() {
        return idTail;
    }

    public void setIdTail(BigDecimal idTail) {
        this.idTail = idTail;
    }

    public String getTipoEnvio() {
        return tipoEnvio;
    }

    public void setTipoEnvio(String tipoEnvio) {
        this.tipoEnvio = tipoEnvio;
    }

    public BigInteger getCantidadEnvio() {
        return cantidadEnvio;
    }

    public void setCantidadEnvio(BigInteger cantidadEnvio) {
        this.cantidadEnvio = cantidadEnvio;
    }

    public BigInteger getTiempoEspera() {
        return tiempoEspera;
    }

    public void setTiempoEspera(BigInteger tiempoEspera) {
        this.tiempoEspera = tiempoEspera;
    }

    public Date getUltFechaEjecucion() {
        return ultFechaEjecucion;
    }

    public void setUltFechaEjecucion(Date ultFechaEjecucion) {
        this.ultFechaEjecucion = ultFechaEjecucion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(Date fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTail != null ? idTail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MailTail)) {
            return false;
        }
        MailTail other = (MailTail) object;
        if ((this.idTail == null && other.idTail != null) || (this.idTail != null && !this.idTail.equals(other.idTail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.confia.mailbot.model.MailTail[ name="+ nombre +" idTail=" + idTail + " estado=" + estado+"]";
    }

    @XmlTransient
    public List<MailBox> getMailBoxList() {
        return mailBoxList;
    }

    public void setMailBoxList(List<MailBox> mailBoxList) {
        this.mailBoxList = mailBoxList;
    }

	public String getEstadoLabel() {
		return estadoLabel;
	}

	public void setEstadoLabel(String estadoLabel) {
		this.estadoLabel = estadoLabel;
	}

	public String getTipoEnvioLabel() {
		return tipoEnvioLabel;
	}

	public void setTipoEnvioLabel(String tipoEnvioLabel) {
		this.tipoEnvioLabel = tipoEnvioLabel;
	}

	public String getPendientes() {
		return pendientes;
	}

	public void setPendientes(String pendientes) {
		this.pendientes = pendientes;
	}

	public String getUltFechaEjFormat() {
		return ultFechaEjFormat;
	}

	public void setUltFechaEjFormat(String ultFechaEjFormat) {
		this.ultFechaEjFormat = ultFechaEjFormat;
	}
	
    
}
