package com.confia.mailbot.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;


public class MailTailDto {
	
	    private BigDecimal idTail;
	    private String tipoEnvio;
	    private BigInteger cantidadEnvio;
	    private BigInteger tiempoEspera;
	    private Date ultFechaEjecucion;
	    private String estado;
	    private String nombre;
	    private Date fechaHoraInicio;
	    private String horaInicio;
	    private String horaFin;
	    
	    public MailTailDto() {
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
	    
	    
}
