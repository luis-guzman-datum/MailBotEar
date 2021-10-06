/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.confia.mailbot.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "GE_TIPO_CORREO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GeTipoCorreo.findAll", query = "SELECT g FROM GeTipoCorreo g"),
    @NamedQuery(name = "GeTipoCorreo.findByCodEmpresa", query = "SELECT g FROM GeTipoCorreo g WHERE g.geTipoCorreoPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "GeTipoCorreo.findByTipoCorreo", query = "SELECT g FROM GeTipoCorreo g WHERE g.geTipoCorreoPK.tipoCorreo = :tipoCorreo"),
    @NamedQuery(name = "GeTipoCorreo.findByDescripcion", query = "SELECT g FROM GeTipoCorreo g WHERE g.descripcion = :descripcion"),
    @NamedQuery(name = "GeTipoCorreo.findByEncabezadoCorreo", query = "SELECT g FROM GeTipoCorreo g WHERE g.encabezadoCorreo = :encabezadoCorreo"),
    @NamedQuery(name = "GeTipoCorreo.findByDetalleCorreo", query = "SELECT g FROM GeTipoCorreo g WHERE g.detalleCorreo = :detalleCorreo"),
    @NamedQuery(name = "GeTipoCorreo.findByPeriodico", query = "SELECT g FROM GeTipoCorreo g WHERE g.periodico = :periodico"),
    @NamedQuery(name = "GeTipoCorreo.findByGenerico", query = "SELECT g FROM GeTipoCorreo g WHERE g.generico = :generico"),
    @NamedQuery(name = "GeTipoCorreo.findByTipoEnvio", query = "SELECT g FROM GeTipoCorreo g WHERE g.tipoEnvio = :tipoEnvio"),
    @NamedQuery(name = "GeTipoCorreo.findByPrimerEnvio", query = "SELECT g FROM GeTipoCorreo g WHERE g.primerEnvio = :primerEnvio"),
    @NamedQuery(name = "GeTipoCorreo.findBySiguienteEnvio", query = "SELECT g FROM GeTipoCorreo g WHERE g.siguienteEnvio = :siguienteEnvio"),
    @NamedQuery(name = "GeTipoCorreo.findByUltimoEnvio", query = "SELECT g FROM GeTipoCorreo g WHERE g.ultimoEnvio = :ultimoEnvio"),
    @NamedQuery(name = "GeTipoCorreo.findByOcurrencia", query = "SELECT g FROM GeTipoCorreo g WHERE g.ocurrencia = :ocurrencia"),
    @NamedQuery(name = "GeTipoCorreo.findByNumeroDias", query = "SELECT g FROM GeTipoCorreo g WHERE g.numeroDias = :numeroDias"),
    @NamedQuery(name = "GeTipoCorreo.findByArchivoAnexo", query = "SELECT g FROM GeTipoCorreo g WHERE g.archivoAnexo = :archivoAnexo"),
    @NamedQuery(name = "GeTipoCorreo.findByEstado", query = "SELECT g FROM GeTipoCorreo g WHERE g.estado = :estado"),
    @NamedQuery(name = "GeTipoCorreo.findByComentarios", query = "SELECT g FROM GeTipoCorreo g WHERE g.comentarios = :comentarios"),
    @NamedQuery(name = "GeTipoCorreo.findByVigencia", query = "SELECT g FROM GeTipoCorreo g WHERE g.vigencia = :vigencia"),
    @NamedQuery(name = "GeTipoCorreo.findByAdicionadoPor", query = "SELECT g FROM GeTipoCorreo g WHERE g.adicionadoPor = :adicionadoPor"),
    @NamedQuery(name = "GeTipoCorreo.findByFechaAdicion", query = "SELECT g FROM GeTipoCorreo g WHERE g.fechaAdicion = :fechaAdicion"),
    @NamedQuery(name = "GeTipoCorreo.findByModificadoPor", query = "SELECT g FROM GeTipoCorreo g WHERE g.modificadoPor = :modificadoPor"),
    @NamedQuery(name = "GeTipoCorreo.findByFechaModificacion", query = "SELECT g FROM GeTipoCorreo g WHERE g.fechaModificacion = :fechaModificacion"),
    @NamedQuery(name = "GeTipoCorreo.findByPieCorreo", query = "SELECT g FROM GeTipoCorreo g WHERE g.pieCorreo = :pieCorreo"),
    @NamedQuery(name = "GeTipoCorreo.findBySubject", query = "SELECT g FROM GeTipoCorreo g WHERE g.subject = :subject"),
    @NamedQuery(name = "GeTipoCorreo.findByTipoAttachment", query = "SELECT g FROM GeTipoCorreo g WHERE g.tipoAttachment = :tipoAttachment"),
    @NamedQuery(name = "GeTipoCorreo.findByCorreoPrueba", query = "SELECT g FROM GeTipoCorreo g WHERE g.correoPrueba = :correoPrueba"),
    @NamedQuery(name = "GeTipoCorreo.findByPlantilla", query = "SELECT g FROM GeTipoCorreo g WHERE g.plantilla = :plantilla"),
    @NamedQuery(name = "GeTipoCorreo.findByTiempoVigenciaInfo", query = "SELECT g FROM GeTipoCorreo g WHERE g.tiempoVigenciaInfo = :tiempoVigenciaInfo")})
public class GeTipoCorreo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GeTipoCorreoPK geTipoCorreoPK;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "ENCABEZADO_CORREO")
    private String encabezadoCorreo;
    @Column(name = "DETALLE_CORREO")
    private String detalleCorreo;
    @Column(name = "PERIODICO")
    private String periodico;
    @Column(name = "GENERICO")
    private String generico;
    @Column(name = "TIPO_ENVIO")
    private String tipoEnvio;
    @Column(name = "PRIMER_ENVIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date primerEnvio;
    @Column(name = "SIGUIENTE_ENVIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date siguienteEnvio;
    @Column(name = "ULTIMO_ENVIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimoEnvio;
    @Column(name = "OCURRENCIA")
    private Integer ocurrencia;
    @Column(name = "NUMERO_DIAS")
    private Short numeroDias;
    @Column(name = "ARCHIVO_ANEXO")
    private String archivoAnexo;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "COMENTARIOS")
    private String comentarios;
    @Column(name = "VIGENCIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vigencia;
    @Column(name = "ADICIONADO_POR")
    private String adicionadoPor;
    @Column(name = "FECHA_ADICION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAdicion;
    @Column(name = "MODIFICADO_POR")
    private String modificadoPor;
    @Column(name = "FECHA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Column(name = "PIE_CORREO")
    private String pieCorreo;
    @Column(name = "SUBJECT")
    private String subject;
    @Column(name = "TIPO_ATTACHMENT")
    private String tipoAttachment;
    @Column(name = "CORREO_PRUEBA")
    private String correoPrueba;
    @Column(name = "PLANTILLA")
    private String plantilla;
    @Column(name = "TIEMPO_VIGENCIA_INFO")
    private BigInteger tiempoVigenciaInfo;

    public GeTipoCorreo() {
    }

    public GeTipoCorreo(GeTipoCorreoPK geTipoCorreoPK) {
        this.geTipoCorreoPK = geTipoCorreoPK;
    }

    public GeTipoCorreo(String codEmpresa, int tipoCorreo) {
        this.geTipoCorreoPK = new GeTipoCorreoPK(codEmpresa, tipoCorreo);
    }

    public GeTipoCorreoPK getGeTipoCorreoPK() {
        return geTipoCorreoPK;
    }

    public void setGeTipoCorreoPK(GeTipoCorreoPK geTipoCorreoPK) {
        this.geTipoCorreoPK = geTipoCorreoPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEncabezadoCorreo() {
        return encabezadoCorreo;
    }

    public void setEncabezadoCorreo(String encabezadoCorreo) {
        this.encabezadoCorreo = encabezadoCorreo;
    }

    public String getDetalleCorreo() {
        return detalleCorreo;
    }

    public void setDetalleCorreo(String detalleCorreo) {
        this.detalleCorreo = detalleCorreo;
    }

    public String getPeriodico() {
        return periodico;
    }

    public void setPeriodico(String periodico) {
        this.periodico = periodico;
    }

    public String getGenerico() {
        return generico;
    }

    public void setGenerico(String generico) {
        this.generico = generico;
    }

    public String getTipoEnvio() {
        return tipoEnvio;
    }

    public void setTipoEnvio(String tipoEnvio) {
        this.tipoEnvio = tipoEnvio;
    }

    public Date getPrimerEnvio() {
        return primerEnvio;
    }

    public void setPrimerEnvio(Date primerEnvio) {
        this.primerEnvio = primerEnvio;
    }

    public Date getSiguienteEnvio() {
        return siguienteEnvio;
    }

    public void setSiguienteEnvio(Date siguienteEnvio) {
        this.siguienteEnvio = siguienteEnvio;
    }

    public Date getUltimoEnvio() {
        return ultimoEnvio;
    }

    public void setUltimoEnvio(Date ultimoEnvio) {
        this.ultimoEnvio = ultimoEnvio;
    }

    public Integer getOcurrencia() {
        return ocurrencia;
    }

    public void setOcurrencia(Integer ocurrencia) {
        this.ocurrencia = ocurrencia;
    }

    public Short getNumeroDias() {
        return numeroDias;
    }

    public void setNumeroDias(Short numeroDias) {
        this.numeroDias = numeroDias;
    }

    public String getArchivoAnexo() {
        return archivoAnexo;
    }

    public void setArchivoAnexo(String archivoAnexo) {
        this.archivoAnexo = archivoAnexo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Date getVigencia() {
        return vigencia;
    }

    public void setVigencia(Date vigencia) {
        this.vigencia = vigencia;
    }

    public String getAdicionadoPor() {
        return adicionadoPor;
    }

    public void setAdicionadoPor(String adicionadoPor) {
        this.adicionadoPor = adicionadoPor;
    }

    public Date getFechaAdicion() {
        return fechaAdicion;
    }

    public void setFechaAdicion(Date fechaAdicion) {
        this.fechaAdicion = fechaAdicion;
    }

    public String getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(String modificadoPor) {
        this.modificadoPor = modificadoPor;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getPieCorreo() {
        return pieCorreo;
    }

    public void setPieCorreo(String pieCorreo) {
        this.pieCorreo = pieCorreo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTipoAttachment() {
        return tipoAttachment;
    }

    public void setTipoAttachment(String tipoAttachment) {
        this.tipoAttachment = tipoAttachment;
    }

    public String getCorreoPrueba() {
        return correoPrueba;
    }

    public void setCorreoPrueba(String correoPrueba) {
        this.correoPrueba = correoPrueba;
    }

    public String getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(String plantilla) {
        this.plantilla = plantilla;
    }

    public BigInteger getTiempoVigenciaInfo() {
        return tiempoVigenciaInfo;
    }

    public void setTiempoVigenciaInfo(BigInteger tiempoVigenciaInfo) {
        this.tiempoVigenciaInfo = tiempoVigenciaInfo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (geTipoCorreoPK != null ? geTipoCorreoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GeTipoCorreo)) {
            return false;
        }
        GeTipoCorreo other = (GeTipoCorreo) object;
        if ((this.geTipoCorreoPK == null && other.geTipoCorreoPK != null) || (this.geTipoCorreoPK != null && !this.geTipoCorreoPK.equals(other.geTipoCorreoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.datum.confia.mailbot.model.GeTipoCorreo[ geTipoCorreoPK=" + geTipoCorreoPK + " ]";
    }
    
}
