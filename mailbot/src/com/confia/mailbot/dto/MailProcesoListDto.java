package com.confia.mailbot.dto;

import java.util.List;

import com.confia.mailbot.model.MailProceso;


public class MailProcesoListDto {
	private List<MailProceso> lista;

	public List<MailProceso> getLista() {
		return lista;
	}

	public void setLista(List<MailProceso> lista) {
		this.lista = lista;
	}
	
}
