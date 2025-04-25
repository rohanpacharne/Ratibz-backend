package com.lonar.master.a2zmaster.model;

import java.io.Serializable;
import java.util.Date;

//import javax.persistence.Basic;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
//import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "LT_MAST_EMAIL_TOKEN")
@XmlRootElement
public class LtMastEmailToken implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "EMAIL_TOKEN_ID")
	private Long emailTokenId;

	@Column(name = "SUPPLIER_ID")
	private Long supplierId;
	
	@Column(name = "TRANSACTION_ID")
	private Long transactionId;

	@Column(name = "TOKEN_ID")
	private String tokenId;
	
	@Column(name = "SEND_DATE")
	private Date sendDate;

	@Column(name = "EXPIRED_WITHIN")
	private Long expiredWithin;

	@Column(name = "EMAIL_TEMPLATE")
	private String emailTemplate;

	@Column(name = "EMAIL_STATUS")
	private String emailStatus;

	@Column(name = "EMAIL_OBJECT")
	private String emailObject;

	@Column(name = "EMAIL_TITLE")
	private String emailTitle;

	@Column(name = "SEND_TO")
	private String sendTo;

	@Column(name = "SEND_CC")
	private String sendCc;

	@Column(name = "ATTACHMENT_PATH")
	private String attachmentPath;

	@Column(name = "FAILURE_COUNT")
	private Long failureCount;

	public Long getEmailTokenId() {
		return emailTokenId;
	}

	public void setEmailTokenId(Long emailTokenId) {
		this.emailTokenId = emailTokenId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Long getExpiredWithin() {
		return expiredWithin;
	}

	public void setExpiredWithin(Long expiredWithin) {
		this.expiredWithin = expiredWithin;
	}

	public String getEmailTemplate() {
		return emailTemplate;
	}

	public void setEmailTemplate(String emailTemplate) {
		this.emailTemplate = emailTemplate;
	}

	public String getEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(String emailStatus) {
		this.emailStatus = emailStatus;
	}

	public String getEmailObject() {
		return emailObject;
	}

	public void setEmailObject(String emailObject) {
		this.emailObject = emailObject;
	}

	public String getEmailTitle() {
		return emailTitle;
	}

	public void setEmailTitle(String emailTitle) {
		this.emailTitle = emailTitle;
	}

	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	public String getSendCc() {
		return sendCc;
	}

	public void setSendCc(String sendCc) {
		this.sendCc = sendCc;
	}

	public String getAttachmentPath() {
		return attachmentPath;
	}

	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}

	public Long getFailureCount() {
		return failureCount;
	}

	public void setFailureCount(Long failureCount) {
		this.failureCount = failureCount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "LtMastEmailToken [emailTokenId=" + emailTokenId + ", supplierId=" + supplierId + ", transactionId="
				+ transactionId + ", tokenId=" + tokenId + ", sendDate=" + sendDate + ", expiredWithin=" + expiredWithin
				+ ", emailTemplate=" + emailTemplate + ", emailStatus=" + emailStatus + ", emailObject=" + emailObject
				+ ", emailTitle=" + emailTitle + ", sendTo=" + sendTo + ", sendCc=" + sendCc + ", attachmentPath="
				+ attachmentPath + ", failureCount=" + failureCount + "]";
	}

}
