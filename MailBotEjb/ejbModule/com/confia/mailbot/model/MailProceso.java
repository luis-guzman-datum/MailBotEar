/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.confia.mailbot.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author lumelen
 */
@Entity
@Table(name = "MAIL_PROCESO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MailProceso.findAll", query = "SELECT m FROM MailProceso m"),
    @NamedQuery(name = "MailProceso.findByIdProceso", query = "SELECT m FROM MailProceso m WHERE m.idProceso = :idProceso"),
    @NamedQuery(name = "MailProceso.findByDescripcion", query = "SELECT m FROM MailProceso m WHERE m.descripcion = :descripcion"),
    @NamedQuery(name = "MailProceso.findByIdTail", query = "SELECT m FROM MailProceso m WHERE m.idTail = :idTail"),
    @NamedQuery(name = "MailProceso.findByTipoCorreo", query = "SELECT m FROM MailProceso m WHERE m.tipoCorreo = :tipoCorreo"),
    @NamedQuery(name = "MailProceso.findByIpSalida", query = "SELECT m FROM MailProceso m WHERE m.ipSalida = :ipSalida"),
    @NamedQuery(name = "MailProceso.findByUseDirMount", query = "SELECT m FROM MailProceso m WHERE m.useDirMount = :useDirMount"),
    @NamedQuery(name = "MailProceso.findByDirEnvioMail", query = "SELECT m FROM MailProceso m WHERE m.dirEnvioMail = :dirEnvioMail")})
public class MailProceso implements Serializable {

    private static final long serialVersionUID = 1L;    
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PROCESO")
    @GeneratedValue(generator = "proceso_seq",strategy=GenerationType.SEQUENCE)
    @SequenceGenerator(name = "proceso_seq", sequenceName = "proceso_seq", allocationSize = 1)
    private BigDecimal idProceso;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "ID_TAIL")
    private BigInteger idTail;
    @Column(name = "TIPO_CORREO")
    private Integer tipoCorreo;
    @Column(name = "IP_SALIDA")
    private String ipSalida;
    @Column(name = "USE_DIR_MOUNT")
    private String useDirMount;
    @Column(name = "DIR_ENVIO_MAIL")
    private String dirEnvioMail;
    @Transient
    private String tipoCorreoLabel;
    @OneToMany(mappedBy = "idProceso")
    private List<MailBox> mailBoxList;

    public MailProceso() {
    }

    public MailProceso(BigDecimal idProceso) {
        this.idProceso = idProceso;
    }

    public BigDecimal getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(BigDecimal idProceso) {
        this.idProceso = idProceso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigInteger getIdTail() {
        return idTail;
    }

    public void setIdTail(BigInteger idTail) {
        this.idTail = idTail;
    }

    public Integer getTipoCorreo() {
        return tipoCorreo;
    }

    public void setTipoCorreo(Integer tipoCorreo) {
        this.tipoCorreo = tipoCorreo;
    }

    public String getIpSalida() {
        return ipSalida;
    }

    public void setIpSalida(String ipSalida) {
        this.ipSalida = ipSalida;
    }

    public String getUseDirMount() {
        return useDirMount;
    }

    public void setUseDirMount(String useDirMount) {
        this.useDirMount = useDirMount;
    }

    public String getDirEnvioMail() {
        return dirEnvioMail;
    }

    public void setDirEnvioMail(String dirEnvioMail) {
        this.dirEnvioMail = dirEnvioMail;
    }

    @XmlTransient
    public List<MailBox> getMailBoxList() {
        return mailBoxList;
    }

    public void setMailBoxList(List<MailBox> mailBoxList) {
        this.mailBoxList = mailBoxList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProceso != null ? idProceso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MailProceso)) {
            return false;
        }
        MailProceso other = (MailProceso) object;
        if ((this.idProceso == null && other.idProceso != null) || (this.idProceso != null && !this.idProceso.equals(other.idProceso))) {
            return false;
        }
        return true;
    }

   
	@Override
	public String toString() {
		return "MailProceso [idProceso=" + idProceso + ", descripcion=" + descripcion + ", idTail=" + idTail
				+ ", tipoCorreo=" + tipoCorreo + ", ipSalida=" + ipSalida + ", useDirMount=" + useDirMount
				+ ", dirEnvioMail=" + dirEnvioMail + ", tipoCorreoLabel=" + tipoCorreoLabel + ", mailBoxList="
				+ mailBoxList + "]";
	}

	public String getTipoCorreoLabel() {
		return tipoCorreoLabel;
	}

	public void setTipoCorreoLabel(String tipoCorreoLabel) {
		this.tipoCorreoLabel = tipoCorreoLabel;
	}
    
    
}
