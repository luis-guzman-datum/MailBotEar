package com.confia.mailbot.dto;

import java.util.Date;

public class HistorialDto {
	private String idTail;
	private String idProceso;
	private String estado;
	private String msg;
	private Date fecha;
	
	public String getIdTail() {
		return idTail;
	}
	public String getIdProceso() {
		return idProceso;
	}
	public String getEstado() {
		return estado;
	}
	public String getMsg() {
		return msg;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setIdTail(String idTail) {
		this.idTail = idTail;
	}
	public void setIdProceso(String idProceso) {
		this.idProceso = idProceso;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	
}
