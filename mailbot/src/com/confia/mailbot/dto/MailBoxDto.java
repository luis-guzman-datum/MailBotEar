package com.confia.mailbot.dto;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class MailBoxDto {

	private String from;
	private String to;
	private String cc;
	private String cco;
	private String subject;
	private String plantillaHtml;
	private String prioridad;
	private Date fechaAdicion;
	private String referencia;
	private String estructura;
	private String codCliente;
	private BigInteger canContactos;
	private BigInteger idEnvioMasivo;
	private BigInteger canEnvios;
	private String actualizaGestion;
	private Date fechaExpiracion;
	private String adicionadoPor;
	private Long correlativoPlanilla;
	private String idTail;
	private String procesoId;
	private List<AttachmentDto> adjuntos;
	
	public String getProcesoId() {
		return procesoId;
	}

	public void setProcesoId(String procesoId) {
		this.procesoId = procesoId;
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


	public String getPlantillaHtml() {
		return plantillaHtml;
	}

	public void setPlantillaHtml(String plantillaHtml) {
		this.plantillaHtml = plantillaHtml;
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

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getEstructura() {
		return estructura;
	}

	public void setEstructura(String estructura) {
		this.estructura = estructura;
	}

	public String getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(String codCliente) {
		this.codCliente = codCliente;
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

	public String getIdTail() {
		return idTail;
	}

	public void setIdTail(String idTail) {
		this.idTail = idTail;
	}

	public BigInteger getCanContactos() {
		return canContactos;
	}

	public void setCanContactos(BigInteger canContactos) {
		this.canContactos = canContactos;
	}

	public List<AttachmentDto> getAdjuntos() {
		return adjuntos;
	}

	public void setAdjuntos(List<AttachmentDto> adjuntos) {
		this.adjuntos = adjuntos;
	}

}
