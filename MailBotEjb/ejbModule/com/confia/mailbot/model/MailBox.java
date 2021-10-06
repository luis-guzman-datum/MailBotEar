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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author lumelen
 */
@Entity
@Table(name = "MAIL_BOX")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MailBox.findAll", query = "SELECT m FROM MailBox m"),
    @NamedQuery(name = "MailBox.findByIdMail", query = "SELECT m FROM MailBox m WHERE m.idMail = :idMail"),
    @NamedQuery(name = "MailBox.findByFrom", query = "SELECT m FROM MailBox m WHERE m.from = :from"),
    @NamedQuery(name = "MailBox.findByTo", query = "SELECT m FROM MailBox m WHERE m.to = :to"),
    @NamedQuery(name = "MailBox.findByCc", query = "SELECT m FROM MailBox m WHERE m.cc = :cc"),
    @NamedQuery(name = "MailBox.findByCco", query = "SELECT m FROM MailBox m WHERE m.cco = :cco"),
    @NamedQuery(name = "MailBox.findBySubject", query = "SELECT m FROM MailBox m WHERE m.subject = :subject"),
    @NamedQuery(name = "MailBox.findByHtmlContent", query = "SELECT m FROM MailBox m WHERE m.htmlContent = :htmlContent"),
    @NamedQuery(name = "MailBox.findByPrioridad", query = "SELECT m FROM MailBox m WHERE m.prioridad = :prioridad"),
    @NamedQuery(name = "MailBox.findByFechaAdicion", query = "SELECT m FROM MailBox m WHERE m.fechaAdicion = :fechaAdicion"),
    @NamedQuery(name = "MailBox.findByEstado", query = "SELECT m FROM MailBox m WHERE m.estado = :estado"),
    @NamedQuery(name = "MailBox.findByEstructura", query = "SELECT m FROM MailBox m WHERE m.estructura = :estructura"),
    @NamedQuery(name = "MailBox.findByBlobQuery", query = "SELECT m FROM MailBox m WHERE m.blobQuery = :blobQuery"),
    @NamedQuery(name = "MailBox.findByReferencia", query = "SELECT m FROM MailBox m WHERE m.referencia = :referencia"),
    @NamedQuery(name = "MailBox.findByMsg", query = "SELECT m FROM MailBox m WHERE m.msg = :msg"),
    @NamedQuery(name = "MailBox.findByCodCliente", query = "SELECT m FROM MailBox m WHERE m.codCliente = :codCliente"),
    @NamedQuery(name = "MailBox.findByCanContactos", query = "SELECT m FROM MailBox m WHERE m.canContactos = :canContactos"),
    @NamedQuery(name = "MailBox.findByIdEnvioMasivo", query = "SELECT m FROM MailBox m WHERE m.idEnvioMasivo = :idEnvioMasivo"),
    @NamedQuery(name = "MailBox.findByCanEnvios", query = "SELECT m FROM MailBox m WHERE m.canEnvios = :canEnvios"),
    @NamedQuery(name = "MailBox.findByFechaEnvio", query = "SELECT m FROM MailBox m WHERE m.fechaEnvio = :fechaEnvio"),
    @NamedQuery(name = "MailBox.findByActualizaGestion", query = "SELECT m FROM MailBox m WHERE m.actualizaGestion = :actualizaGestion"),
    @NamedQuery(name = "MailBox.findByFechaExpiracion", query = "SELECT m FROM MailBox m WHERE m.fechaExpiracion = :fechaExpiracion"),
    @NamedQuery(name = "MailBox.findByAdicionadoPor", query = "SELECT m FROM MailBox m WHERE m.adicionadoPor = :adicionadoPor"),
    @NamedQuery(name = "MailBox.findByCorrelativoPlanilla", query = "SELECT m FROM MailBox m WHERE m.correlativoPlanilla = :correlativoPlanilla")})
public class MailBox implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(generator = "mail_seq",strategy=GenerationType.SEQUENCE)
    @SequenceGenerator(name = "mail_seq", sequenceName = "mail_seq", allocationSize = 1)
    @Column(name = "ID_MAIL")
    private BigDecimal idMail;
    @Column(name = "FROM_")
    private String from;
    @Column(name = "TO_")
    private String to;
    @Column(name = "CC")
    private String cc;
    @Column(name = "CCO")
    private String cco;
    @Column(name = "SUBJECT")
    private String subject;
    @Column(name = "HTML_CONTENT")
    private String htmlContent;
    @Column(name = "PRIORIDAD")
    private String prioridad;
    @Column(name = "FECHA_ADICION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAdicion;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "ESTRUCTURA")
    private String estructura;
    @Column(name = "BLOB_QUERY")
    private String blobQuery;
    @Column(name = "REFERENCIA")
    private String referencia;
    @Column(name = "MSG")
    private String msg;
    @Column(name = "COD_CLIENTE")
    private String codCliente;
    @Column(name = "CAN_CONTACTOS")
    private BigInteger canContactos;
    @Column(name = "ID_ENVIO_MASIVO")
    private BigInteger idEnvioMasivo;
    @Column(name = "CAN_ENVIOS")
    private BigInteger canEnvios;
    @Column(name = "FECHA_ENVIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEnvio;
    @Column(name = "ACTUALIZA_GESTION")
    private String actualizaGestion;
    @Column(name = "FECHA_EXPIRACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaExpiracion;
    @Column(name = "ADICIONADO_POR")
    private String adicionadoPor;
    @Column(name = "CORRELATIVO_PLANILLA")
    private Long correlativoPlanilla;

    @OneToMany(mappedBy = "idMail")
    private List<MailAttachment> mailAttachmentList;

    
    @JoinColumn(name = "ID_PROCESO", referencedColumnName = "ID_PROCESO")
    @ManyToOne
    private MailProceso idProceso;
    
    @JoinColumn(name = "ID_TAIL", referencedColumnName = "ID_TAIL")
    @ManyToOne
    private MailTail idTail;

    public MailBox() {
    }

    public MailBox(BigDecimal idMail) {
        this.idMail = idMail;
    }

    public BigDecimal getIdMail() {
        return idMail;
    }

    public void setIdMail(BigDecimal idMail) {
        this.idMail = idMail;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getCco() {
        return cco;
    }

    public void setCco(String cco) {
        this.cco = cco;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public Date getFechaAdicion() {
        return fechaAdicion;
    }

    public void setFechaAdicion(Date fechaAdicion) {
        this.fechaAdicion = fechaAdicion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstructura() {
        return estructura;
    }

    public void setEstructura(String estructura) {
        this.estructura = estructura;
    }

    public String getBlobQuery() {
        return blobQuery;
    }

    public void setBlobQuery(String blobQuery) {
        this.blobQuery = blobQuery;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public BigInteger getCanContactos() {
        return canContactos;
    }

    public void setCanContactos(BigInteger canContactos) {
        this.canContactos = canContactos;
    }

    public BigInteger getIdEnvioMasivo() {
        return idEnvioMasivo;
    }

    public void setIdEnvioMasivo(BigInteger idEnvioMasivo) {
        this.idEnvioMasivo = idEnvioMasivo;
    }

    public BigInteger getCanEnvios() {
        return canEnvios;
    }

    public void setCanEnvios(BigInteger canEnvios) {
        this.canEnvios = canEnvios;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getActualizaGestion() {
        return actualizaGestion;
    }

    public void setActualizaGestion(String actualizaGestion) {
        this.actualizaGestion = actualizaGestion;
    }

    public Date getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(Date fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getAdicionadoPor() {
        return adicionadoPor;
    }

    public void setAdicionadoPor(String adicionadoPor) {
        this.adicionadoPor = adicionadoPor;
    }

    public Long getCorrelativoPlanilla() {
        return correlativoPlanilla;
    }

    public void setCorrelativoPlanilla(Long correlativoPlanilla) {
        this.correlativoPlanilla = correlativoPlanilla;
    }

    @XmlTransient
    public List<MailAttachment> getMailAttachmentList() {
        return mailAttachmentList;
    }

    public void setMailAttachmentList(List<MailAttachment> mailAttachmentList) {
        this.mailAttachmentList = mailAttachmentList;
    }


	public MailProceso getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(MailProceso idProceso) {
		this.idProceso = idProceso;
	}

	public MailTail getIdTail() {
        return idTail;
    }

    public void setIdTail(MailTail idTail) {
        this.idTail = idTail;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMail != null ? idMail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MailBox)) {
            return false;
        }
        MailBox other = (MailBox) object;
        if ((this.idMail == null && other.idMail != null) || (this.idMail != null && !this.idMail.equals(other.idMail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.datum.confia.mailbot.model.MailBox[ idMail=" + idMail + " ]";
    }
    
}
