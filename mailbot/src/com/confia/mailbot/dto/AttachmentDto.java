package com.confia.mailbot.dto;

public class AttachmentDto {
    private String attachmentName;
    private String attachmentType;
    private String contentId;
    private String attachmentPath;
    private String base64;
    private String guardar;
    
	public String getAttachmentName() {
		return attachmentName;
	}
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	public String getAttachmentType() {
		return attachmentType;
	}
	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	public String getAttachmentPath() {
		return attachmentPath;
	}
	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}
	public String getBase64() {
		return base64;
	}
	public void setBase64(String base64) {
		this.base64 = base64;
	}
	public String getGuardar() {
		return guardar;
	}
	public void setGuardar(String guardar) {
		this.guardar = guardar;
	}
    
    
}
