package com.confia.mailbot.ws.mail;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;

import com.confia.mailbot.dto.AttachmentDto;
import com.confia.mailbot.dto.MailBoxDto;
import com.confia.mailbot.dto.ResponseDto;
import com.confia.mailbot.facades.MailAttachmentFacadeLocal;
import com.confia.mailbot.facades.MailBoxFacadeLocal;
import com.confia.mailbot.facades.MailProcesoFacadeLocal;
import com.confia.mailbot.facades.MailTailFacadeLocal;
import com.confia.mailbot.facades.ParametroWebFacadeLocal;
import com.confia.mailbot.model.MailAttachment;
import com.confia.mailbot.model.MailBox;
import com.confia.mailbot.model.MailProceso;
import com.confia.mailbot.model.MailTail;
import com.confia.mailbot.model.ParametroWeb;
import com.confia.mailbot.utils.MailBotUtils;

@WebService(serviceName = "MailBoxService")
public class MailBoxService {

	@EJB
	private MailBoxFacadeLocal mailBoxFacade;

	@EJB
	private MailTailFacadeLocal mailTailFacade;

	@EJB
	private MailAttachmentFacadeLocal attachmentFacade;

	@EJB
	private MailProcesoFacadeLocal procesoFacade;
	
	@EJB
	private ParametroWebFacadeLocal parametroFacade;

	@WebMethod(operationName = "crearMailBox")
	public ResponseDto createMailBox(@WebParam(name = "mailBox") MailBoxDto mailboxDto) {
		ResponseDto responseDto = new ResponseDto();
		try {

			MailBox mailBox = new MailBox();

			if (mailboxDto.getActualizaGestion() != null && !mailboxDto.getActualizaGestion().isEmpty()) {
				mailBox.setActualizaGestion(mailboxDto.getActualizaGestion());
			} else {
				responseDto.setMessage("El campo Actualiza Gestión no debe estar vacio ni debe ser nulo");
				responseDto.setResultado("error");
				return responseDto;
			}

			mailBox.setAdicionadoPor(mailboxDto.getAdicionadoPor());
			mailBox.setCanContactos(mailboxDto.getCanContactos());
			mailBox.setCanEnvios(mailboxDto.getCanEnvios());
			mailBox.setCc(mailboxDto.getCc());
			mailBox.setCco(mailboxDto.getCco());
			mailBox.setCodCliente(mailboxDto.getCodCliente());
			mailBox.setCorrelativoPlanilla(mailboxDto.getCorrelativoPlanilla());
			mailBox.setEstado("C");
			mailBox.setEstructura(mailboxDto.getEstructura());
			mailBox.setFechaAdicion(new Date());
			mailBox.setFechaExpiracion(mailboxDto.getFechaExpiracion());

			if (mailboxDto.getFrom() != null && !mailboxDto.getFrom().isEmpty()) {
				mailBox.setFrom(mailboxDto.getFrom());
			} else {
				responseDto.setMessage("El campo From no debe estar vacio ni debe ser nulo");
				responseDto.setResultado("error");
				return responseDto;
			}

			if (mailboxDto.getPlantillaHtml() != null && !mailboxDto.getPlantillaHtml().isEmpty()) {
				mailBox.setHtmlContent(mailboxDto.getPlantillaHtml());
			} else {
				responseDto.setMessage("El campo htmlContent no debe estar vacio ni debe ser nulo");
				responseDto.setResultado("error");
				return responseDto;
			}

			mailBox.setIdEnvioMasivo(mailboxDto.getIdEnvioMasivo());


			if (mailboxDto.getProcesoId() != null) {
				MailProceso proceso = procesoFacade.find(mailboxDto.getProcesoId());
				if (proceso != null && proceso.getIdProceso() != null) {
					mailBox.setIdProceso(proceso);
				} else {
					responseDto.setMessage(
							"El campo procesoId no debe estar vacio ni debe ser nulo, y debe ser un proceso válido");
					responseDto.setResultado("error");
					return responseDto;
				}
			}

			if (mailboxDto.getIdTail() != null) {
				MailTail mailTail = mailTailFacade.find(mailboxDto.getIdTail());

				if (mailTail != null && mailTail.getIdTail() != null) {
					mailBox.setIdTail(mailTail);
				} else {
					responseDto.setMessage(
							"El campo idTail no debe estar vacio, no debe ser nulo, y debe ser un Id Tail válido");
					responseDto.setResultado("error");
					return responseDto;
				}
			}

			mailBox.setPrioridad(mailboxDto.getPrioridad());
			mailBox.setReferencia(mailboxDto.getReferencia());

			if (mailboxDto.getSubject() != null && !mailboxDto.getSubject().isEmpty()) {
				mailBox.setSubject(mailboxDto.getSubject());
			} else {
				responseDto.setMessage("El campo subject no debe estar vacio y no debe ser nulo");
				responseDto.setResultado("error");
				return responseDto;
			}

			if (mailboxDto.getTo() != null && !mailboxDto.getTo().isEmpty()) {
				mailBox.setTo(mailboxDto.getTo());
			} else {
				responseDto.setMessage("El campo to no debe estar vacio y no debe ser nulo");
				responseDto.setResultado("error");
				return responseDto;
			}
			mailBox = mailBoxFacade.editAndGetEntity(mailBox);

			List<String> contentIds = new ArrayList<String>();
			if(!mailboxDto.getAdjuntos().isEmpty()) {
				Map<Integer,String> parameters = new HashMap<Integer,String>();
				boolean isWindows = MailBotUtils.isWindows();
				
				if(isWindows) {
					ParametroWeb param = parametroFacade.find("ADMIN_CORREOS");
					parameters.put(0,param.getValor());
				}else{
					ParametroWeb param = parametroFacade.find("MOUNT_UNIX");
					parameters.put(0,param.getValor());
				}
				
				for (AttachmentDto attachment : mailboxDto.getAdjuntos()) {
					MailAttachment mailAttachment = new MailAttachment();
					mailAttachment.setAttachmentName(attachment.getAttachmentName());
					mailAttachment.setAttachmentPath(attachment.getAttachmentPath());
					mailAttachment.setAttachmentType(attachment.getAttachmentType());
					mailAttachment.setContentId(attachment.getContentId());
					mailAttachment.setIdMail(mailBox);
					mailAttachment = attachmentFacade.editAndGetEntity(mailAttachment);
					
					if(mailAttachment == null || mailAttachment.getIdAttachment() == null) {
						responseDto.setMessage("No fue posible guardar attachment");
						responseDto.setResultado("error");
						return responseDto;
					}
				
					if(!contentIds.contains(attachment.getContentId())) {
						if (attachment.getGuardar() != null && !attachment.getGuardar().isEmpty()
								&& !attachment.getGuardar().equals("S") && attachment.getBase64()!= null && !attachment.getBase64().isEmpty()) {
							//System.out.println("attachment: " + attachment.getAttachmentName());
							//System.out.println("BASE64: " + attachment.getBase64());
							
							String attachmentExtension = FilenameUtils.getExtension(attachment.getAttachmentName());
							parameters.put(1,attachment.getAttachmentName());
							parameters.put(2,attachmentExtension);
							
							byte[] base64Array = attachment.getBase64().getBytes();
							byte[] fileArray = Base64.decodeBase64(base64Array);
							String address = MailBotUtils.getFileAddress(parameters, isWindows);
							String attachUrl = MailBotUtils.saveAttachment(address,mailAttachment.getIdAttachment().toString(),fileArray);
							if(attachUrl == null || attachUrl.isEmpty()) {
								responseDto.setMessage("No fue posible guardar attachment");
								responseDto.setResultado("error");
								return responseDto;
							}else {
								mailAttachment.setAttachmentPath(attachUrl);
								mailAttachment = attachmentFacade.editAndGetEntity(mailAttachment);
							}
						}else if (attachment.getGuardar() != null && !attachment.getGuardar().isEmpty()
								&& !attachment.getGuardar().equals("N")) {
							
							//System.out.println("attachment: " + attachment.getAttachmentName());
							
							
						}
						contentIds.add(attachment.getContentId());
					}
		
				}
			}
			
			responseDto.setMessage("La entidad de MailBox ha sido creada. Con ID: " + mailBox.getIdMail());
			responseDto.setResultado("ok");
			return responseDto;
		} catch (Exception ex) {
			ex.printStackTrace();
			responseDto.setMessage(ex.getMessage());
			responseDto.setResultado("error");
			return responseDto;
		}
	}

}
