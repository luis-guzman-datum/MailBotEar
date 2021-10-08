package com.confia.mailbot.ws.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.confia.mailbot.dto.MailProcesoListDto;
import com.confia.mailbot.dto.ResponseDto;
import com.confia.mailbot.dto.SelectDto;
import com.confia.mailbot.model.GeTipoCorreo;
import com.confia.mailbot.model.MailProceso;
import com.confia.mailbot.model.MailTail;
import com.confia.mailbot.producer.ConfiaServiceLocator;

@Path("mailProceso")
public class MailProcesoRest {

	@GET
	@Path("obtenerMailProceso")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public MailProceso obtenerMailTail(@QueryParam(value = "idProceso") String idProceso) {
		ConfiaServiceLocator csl = new ConfiaServiceLocator("MailProceso");
		MailProceso mailProceso = null;
		try {
			 mailProceso = csl.getProcesoFacade().find(new BigDecimal(idProceso));
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return mailProceso;
	}

	@GET
	@Path("listaMailProceso")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public List<MailProceso> obtenerListaMailProceso() {
		ConfiaServiceLocator csl = new ConfiaServiceLocator("MailProceso");
		List<MailProceso> procesos = null;
		try {
			 procesos = csl.getProcesoFacade().findAll();
			 return procesos;
		}catch(Exception ex) {
			ex.printStackTrace();
			return new ArrayList<MailProceso>();
		}
		
	}

	@POST
	@Path("editProceso")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON)
	public ResponseDto editTail(MailProceso mailProceso) {
		
		ResponseDto res = new ResponseDto();
		try {
			ConfiaServiceLocator csl = new ConfiaServiceLocator("MailProceso");
			ConfiaServiceLocator csl2 = new ConfiaServiceLocator("MailTail");

			if (mailProceso == null || mailProceso.getIdProceso() == null) {
				res.setResultado("error");
				res.setMessage("El id proceso de Mail Proceso no debe estar vacío");
				return res;
			}

			MailProceso objectCheck = csl.getProcesoFacade().find(mailProceso.getIdProceso());

			if (objectCheck != null && objectCheck.getIdProceso() != null
					&& objectCheck.getIdProceso().equals(mailProceso.getIdProceso())) {
				if (mailProceso.getDescripcion() != null && !mailProceso.getDescripcion().isEmpty()) {
					if (mailProceso.getDescripcion().length() > 100) {
						res.setResultado("error");
						res.setMessage("La descripción debe tener una longitud máxima de 100 carácteres");
						return res;
					}
					objectCheck.setDescripcion(mailProceso.getDescripcion());
				} else {
					res.setResultado("error");
					res.setMessage("La descripción no debe estar vacía");
					return res;
				}

				if (mailProceso.getIdTail() != null) {
					BigDecimal bigdec = new BigDecimal(mailProceso.getIdTail());
					MailTail tailCheck = csl2.getTailFacade().find(bigdec);
					if (tailCheck != null && tailCheck.getIdTail() != null) {
						objectCheck.setIdTail(tailCheck.getIdTail().toBigInteger());
					} else {
						res.setResultado("error");
						res.setMessage("El ID de Mail Tail no es válido...");
						return res;
					}
				}

				if (mailProceso.getTipoCorreo() != null) {
					objectCheck.setTipoCorreo(mailProceso.getTipoCorreo());
				} else {
					res.setResultado("error");
					res.setMessage("El tipo de correo no debe estar vacío");
					return res;
				}

				if (mailProceso.getIpSalida() != null) {
					objectCheck.setIpSalida(mailProceso.getIpSalida());
				} else {
					res.setResultado("error");
					res.setMessage("La IP de salida no debe estar vacía");
					return res;
				}

				MailProceso result = csl.getProcesoFacade().editAndGetEntity(objectCheck);
				res.setResultado("ok");
				res.setMessage("Se editó la entidad mailProceso ID: " + result.getIdProceso());
			} else {
				res.setResultado("error");
				res.setMessage("No se encontró la entidad mailProceso ID: " + mailProceso.getIdProceso());
			}
		} catch (Exception e) {
			res.setResultado("error");
			res.setMessage(e.getMessage());
			e.printStackTrace();
		} finally {

		}
		return res;

	}

	@POST
	@Path("crearProceso")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Consumes(MediaType.APPLICATION_JSON)
	public ResponseDto createProcess(MailProceso mailProceso) {
		ResponseDto res = new ResponseDto();
		
		try {
			ConfiaServiceLocator csl = new ConfiaServiceLocator("MailProceso");
			ConfiaServiceLocator csl2 = new ConfiaServiceLocator("MailTail");

			if (mailProceso == null) {
				res.setResultado("error");
				res.setMessage("Mail Proceso no debe ser nulo");
				return res;
			} else {

				if (mailProceso.getDescripcion() == null || mailProceso.getDescripcion().isEmpty()) {
					res.setResultado("error");
					res.setMessage("La descripción no debe estar vacía");
					return res;
				}

				if (mailProceso.getIdTail() != null) {		
					BigDecimal bigdec = new BigDecimal(mailProceso.getIdTail());
					MailTail tailCheck = csl2.getTailFacade().find(bigdec);
					if (tailCheck != null && tailCheck.getIdTail() != null) {
						mailProceso.setIdTail(tailCheck.getIdTail().toBigInteger());
					} else {
						res.setResultado("error");
						res.setMessage("El ID de Mail Tail no es válido...");
						return res;
					}
				}

				if (mailProceso.getTipoCorreo() == null) {
					res.setResultado("error");
					res.setMessage("El tipo de correo no debe estar vacío");
					return res;
				}

				if (mailProceso.getIpSalida() == null || mailProceso.getIpSalida().isEmpty()) {
					res.setResultado("error");
					res.setMessage("La IP de salida no debe estar vacía");
					return res;
				}

				MailProceso result = csl.getProcesoFacade().editAndGetEntity(mailProceso);
				res.setResultado("ok");
				res.setMessage("Se editó la entidad mailProceso ID: " + result.getIdProceso());
			}
		} catch (Exception e) {
			res.setResultado("error");
			res.setMessage(e.getMessage());
			e.printStackTrace();
		} finally {

		}
		return res;

	}

	@GET
	@Path("tipoCorreos")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public List<SelectDto> obtenerValoresTipoCorreo() {

		try {
			ConfiaServiceLocator csl = new ConfiaServiceLocator("GeTipoCorreo");
			List<SelectDto> tipos = new ArrayList<SelectDto>();
			List<GeTipoCorreo> tiposEntities = csl.getTipoCorreoFacade().findAll();

			for (GeTipoCorreo ge : tiposEntities) {
				SelectDto dto = new SelectDto();
				dto.setKey(String.valueOf(ge.getGeTipoCorreoPK().getTipoCorreo()));
				dto.setValue(ge.getDescripcion());
				tipos.add(dto);
			}
			return tipos;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ArrayList<SelectDto>();
	}
}
