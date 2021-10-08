package com.confia.mailbot.ws.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.quartz.SchedulerException;

import com.confia.mailbot.dto.HistorialDto;
import com.confia.mailbot.dto.MailBoxDto;
import com.confia.mailbot.dto.ResponseDto;
import com.confia.mailbot.dto.SelectDto;
import com.confia.mailbot.exceptions.MailTailInvalidStateException;
import com.confia.mailbot.model.GeTipoCorreo;
import com.confia.mailbot.model.MailBox;
import com.confia.mailbot.model.MailTail;
import com.confia.mailbot.producer.ConfiaServiceLocator;

import weblogic.ejbgen.Entity.DatabaseType;

@Path("mailTail")
public class MailTailRest {

	@GET
	@Path("obtenerMailTail")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public MailTail obtenerMailTail(@QueryParam(value = "idTail") String idTail) {
		ConfiaServiceLocator csl = new ConfiaServiceLocator("MailTail");
		MailTail mailTail = null;
		
		try {
			mailTail = csl.getTailFacade().find(new BigDecimal(idTail));
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return mailTail;
	}

	@GET
	@Path("listaMailTail")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public List<MailTail> obtenerListaMailTail() {
		ConfiaServiceLocator csl = new ConfiaServiceLocator("MailTail");
		List<MailTail> listaMailTail = null;
		try {
			listaMailTail = csl.getTailFacade().findAll();
			return listaMailTail;
		}catch(Exception ex) {
			ex.printStackTrace();
			return new ArrayList<MailTail>();
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("crearTail")
	@Consumes(MediaType.APPLICATION_JSON)
	public ResponseDto crearTail(MailTail mailTail) {
		ResponseDto res = new ResponseDto();
		try {
			ConfiaServiceLocator csl = new ConfiaServiceLocator("MailTail");
			mailTail.setEstado("I");
			MailTail result = csl.getTailFacade().editAndGetEntity(mailTail);
			res.setResultado("ok");
			res.setMessage("Se creó nueva entidad Mail Tail ID: " + result.getIdTail());
		} catch (Exception e) {
			res.setResultado("error");
			res.setMessage(e.getMessage());
		} finally {

		}

		return res;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Path("editTail")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editTail(MailTail mailTail) {
		Response.ResponseBuilder response = Response.ok();
		ResponseDto res = new ResponseDto();
		try {
			ConfiaServiceLocator csl = new ConfiaServiceLocator("MailTail");

			if (mailTail.getIdTail() == null) {
				res.setResultado("error");
				res.setMessage("El id tail de Mail Tail no debe estar vacío");
				response = Response.status(Response.Status.BAD_REQUEST);
				return response.entity(res).build();
			}

			MailTail objectCheck = csl.getTailFacade().find(mailTail.getIdTail());
			
			
			if (objectCheck != null && objectCheck.getIdTail() != null
					&& objectCheck.getIdTail().equals(mailTail.getIdTail())) {

				/***Validación antes de modificacion de Cola para verificar el estado***/
				if (!objectCheck.getEstado().equals("X")) {
					throw new MailTailInvalidStateException(
							"Estado inválido. No puede modificar la cola por que no se encuentra desactiva. Para modificarla, debe detener la cola. Estado Actual de la Cola: "
									+ objectCheck.getEstado());
				} 
				/*****************************************************************/
				
				/******* Seteando Cantidad desde servicio ********/
				if (mailTail.getCantidadEnvio() != null) {
					objectCheck.setCantidadEnvio(mailTail.getCantidadEnvio());
				}

				/******* Seteando Fecha Hora Inicio desde servicio ********/
				if (mailTail.getFechaHoraInicio() != null) {					
					objectCheck.setFechaHoraInicio(mailTail.getFechaHoraInicio());
				}

				/******* Seteando Hora Fin desde servicio ********/
				if (mailTail.getHoraFin() != null) {
					objectCheck.setHoraFin(mailTail.getHoraFin());
				}

				/******* Seteando Hora Inicio desde servicio ********/
				if (mailTail.getHoraInicio() != null) {
					objectCheck.setHoraInicio(mailTail.getHoraInicio());
				}

				/******* Seteando Nombre desde servicio ********/
				if (mailTail.getNombre() != null && !mailTail.getNombre().isEmpty()) {
					objectCheck.setNombre(mailTail.getNombre());
				}

				/******* Seteando Tiempo de espera desde servicio ********/
				if (mailTail.getTiempoEspera() != null) {
					objectCheck.setTiempoEspera(mailTail.getTiempoEspera());
				}

				/******* Seteando Tipo de Envio desde servicio ********/
				if (mailTail.getTipoEnvio() != null && !mailTail.getTipoEnvio().isEmpty()) {
					objectCheck.setTipoEnvio(mailTail.getTipoEnvio());
				}

				MailTail result = csl.getTailFacade().editAndGetEntity(objectCheck);
				res.setResultado("ok");
				res.setMessage("Se editó la entidad mail tail ID: " + result.getIdTail());
				response = Response.status(Response.Status.OK);
				return response.entity(res).build();
			} else {
				res.setResultado("error");
				res.setMessage("No se encontró la entidad con ID: " + mailTail.getIdTail());
				response = Response.status(Response.Status.OK);
				return response.entity(res).build();
			}

		}catch (MailTailInvalidStateException e) {
			e.printStackTrace();
			res.setResultado("error");
			res.setMessage(e.getMessage());
			response = Response.status(Response.Status.OK);
			return response.entity(res).build();
		} catch (Exception e) {
			res.setResultado("error");
			res.setMessage(e.getMessage());
			e.printStackTrace();
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
			return response.entity(res).build();
		} finally {

		}
	}

	@GET
	@Path("administrar")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public ResponseDto ejecutarOperacion(@QueryParam(value = "idTail") String idTail,
			@QueryParam(value = "operacion") String operacion) {
		ResponseDto res = new ResponseDto();

		try {
			ConfiaServiceLocator csl = new ConfiaServiceLocator("MailTail");
			ConfiaServiceLocator cslScheduler = new ConfiaServiceLocator("SchedulerService");		
			
			BigDecimal bigdec = new BigDecimal(idTail);
			MailTail mailTail = csl.getTailFacade().find(bigdec);
			System.out.println("mailTail =>"+ mailTail.toString());
			
			if (mailTail != null && mailTail.getIdTail() != null) {
				if (operacion != null && operacion.equals("PAUSE")) {
					if (!idTail.isEmpty()) {
						cslScheduler.getSchedulerService().pauseQueue(mailTail);
						res.setResultado("ok");
						res.setMessage("Se pausó MailTail con ID: " + mailTail.getIdTail());
					}
				}else if (operacion != null && operacion.equals("RESUME")) {
					if (!idTail.isEmpty()) {
						cslScheduler.getSchedulerService().resumeQueue(mailTail);
						res.setResultado("ok");
						res.setMessage("Se reanudó MailTail con ID: " + mailTail.getIdTail());
					}
				}else if (operacion != null && operacion.equals("ACTIVAR")) {
					if (!idTail.isEmpty()) {
						cslScheduler.getSchedulerService().addQueue(mailTail);
						res.setResultado("ok");
						res.setMessage("Se inició MailTail con ID: " + mailTail.getIdTail());
					}
				}
				else if (operacion != null && operacion.equals("DESACTIVAR")) {
					if (!idTail.isEmpty()) {
						
						cslScheduler.getSchedulerService().deleteQueue(mailTail);

						res.setResultado("ok");
						res.setMessage("Se eliminó MailTail con ID: " + mailTail.getIdTail());
					}

				}else {
					res.setResultado("error");
					res.setMessage("No fue posible ejecutar acción. La acción solicitada no se encuentra definida");
				}
			} else {
				res.setResultado("error");
				res.setMessage("No fue posible ejecutar acción. No se encontró entidad MailTail con ID: "
						+ mailTail.getIdTail());
			}		
		} catch (SchedulerException | MailTailInvalidStateException e) {
			e.printStackTrace();
			res.setResultado("error");
			res.setMessage(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			res.setResultado("error");
			res.setMessage(e.getMessage());
		}
		
		return res;
	}
	
	@GET
	@Compress
	@Path("historialErrores")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public List<MailBoxDto> obtenerHistorialErrores(@QueryParam(value = "initDate") String initDate,
			@QueryParam(value = "endDate") String endDate) {
		
		try {
			ConfiaServiceLocator csl = new ConfiaServiceLocator("MailBox");
			List<String> status = new ArrayList<String>();
			status.add("X");
			status.add("R");
			
			Calendar calInit = Calendar.getInstance();
			calInit.setTimeInMillis(new Long(initDate));
			
			Calendar calEnd = Calendar.getInstance();
			calEnd.setTimeInMillis(new Long(endDate));
			
			Date date1 = calInit.getTime();
			Date date2 = calEnd.getTime();
			
			//System.out.println("DATE 1: "+date1);
			//System.out.println("DATE 2: "+date2);

			return csl.getMailFacade().findByDatesAndStatusOptNQ(date1,date2,status);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ArrayList<MailBoxDto>();
	}
	
	@GET
	@Compress
	@Path("colas")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public List<SelectDto> obtenerValoresTipoCorreo() {
		
		try {
			ConfiaServiceLocator csl = new ConfiaServiceLocator("MailTail");
			List<SelectDto> tipos = new ArrayList<SelectDto>();
			List<MailTail> listaMailTail = csl.getTailFacade().findAll();
			
			for(MailTail mt:listaMailTail) {
				SelectDto dto = new SelectDto();
				dto.setKey(String.valueOf(mt.getIdTail()));
				dto.setValue(mt.getNombre());
				tipos.add(dto);
			}
			return tipos;
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ArrayList<SelectDto>();
	}

}
