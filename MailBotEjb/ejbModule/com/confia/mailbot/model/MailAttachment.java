/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.confia.mailbot.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lumelen
 */
@Entity
@Table(name = "MAIL_ATTACHMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MailAttachment.findAll", query = "SELECT m FROM MailAttachment m"),
    @NamedQuery(name = "MailAttachment.findByIdAttachment", query = "SELECT m FROM MailAttachment m WHERE m.idAttachment = :idAttachment"),
    @NamedQuery(name = "MailAttachment.findByAttachmentName", query = "SELECT m FROM MailAttachment m WHERE m.attachmentName = :attachmentName"),
    @NamedQuery(name = "MailAttachment.findByAttachmentType", query = "SELECT m FROM MailAttachment m WHERE m.attachmentType = :attachmentType"),
    @NamedQuery(name = "MailAttachment.findByContentId", query = "SELECT m FROM MailAttachment m WHERE m.contentId = :contentId"),
    @NamedQuery(name = "MailAttachment.findByAttachmentPath", query = "SELECT m FROM MailAttachment m WHERE m.attachmentPath = :attachmentPath")})
public class MailAttachment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(generator = "att_seq",strategy=GenerationType.SEQUENCE)
    @SequenceGenerator(name = "att_seq", sequenceName = "att_seq", allocationSize = 1)
    @Column(name = "ID_ATTACHMENT")
    private BigDecimal idAttachment;
    @Column(name = "ATTACHMENT_NAME")
    private String attachmentName;
    @Column(name = "ATTACHMENT_TYPE")
    private String attachmentType;
    @Column(name = "CONTENT_ID")
    private String contentId;
    @Column(name = "ATTACHMENT_PATH")
    private String attachmentPath;
    @JoinColumn(name = "ID_MAIL", referencedColumnName = "ID_MAIL")
    @ManyToOne
    private MailBox idMail;

    public MailAttachment() {
    }

    public MailAttachment(BigDecimal idAttachment) {
        this.idAttachment = idAttachment;
    }

    public BigDecimal getIdAttachment() {
        return idAttachment;
    }

    public void setIdAttachment(BigDecimal idAttachment) {
        this.idAttachment = idAttachment;
    }

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

    public MailBox getIdMail() {
        return idMail;
    }

    public void setIdMail(MailBox idMail) {
        this.idMail = idMail;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAttachment != null ? idAttachment.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MailAttachment)) {
            return false;
        }
        MailAttachment other = (MailAttachment) object;
        if ((this.idAttachment == null && other.idAttachment != null) || (this.idAttachment != null && !this.idAttachment.equals(other.idAttachment))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.datum.confia.mailbot.model.MailAttachment[ idAttachment=" + idAttachment + " ]";
    }
    
}
