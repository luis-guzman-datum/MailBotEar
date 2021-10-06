package com.confia.mailbot.dto;

import java.io.Serializable;
import java.util.Date;

public class MailBoxDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String count;
	private String idMail;
	private String idProceso;
	private String idTail;
	private String estado;
	private String fecha;
	private String msg;
	
	
	public MailBoxDto() {
		super();
	}

	public MailBoxDto(String idMail, String idProceso, String idTail, String estado, String fecha, String msg) {
		super();
		this.idMail	= idMail;
		this.idProceso = idProceso;
		this.idTail = idTail;
		this.estado = estado;
		this.fecha = fecha;
		this.msg = msg;
	}
	
	public String getIdProceso() {
		return idProceso;
	}
	public String getIdTail() {
		return idTail;
	}
	public String getEstado() {
		return estado;
	}
	public String getFecha() {
		return fecha;
	}
	public String getMsg() {
		return msg;
	}
	public void setIdProceso(String idProceso) {
		this.idProceso = idProceso;
	}
	public void setIdTail(String idTail) {
		this.idTail = idTail;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getIdMail() {
		return idMail;
	}

	public void setIdMail(String idMail) {
		this.idMail = idMail;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
	
	
}
