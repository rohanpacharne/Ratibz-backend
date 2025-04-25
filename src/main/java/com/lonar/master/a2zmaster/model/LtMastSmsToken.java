package com.lonar.master.a2zmaster.model;



import java.util.Date;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
import jakarta.persistence.*;

@Entity
@Table(name = "LT_MAST_SMS_TOKEN")
public class LtMastSmsToken {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "SMS_TOKEN_ID")
	private Long smsTokenId;
	
	@Column(name = "SUPPLIER_ID")
	private Long supplierId;
	
	@Column(name = "TRANSACTION_ID")
	private Long transactionId;
	
	@Column(name = "SEND_DATE")
	private Date sendDate;
	
	@Column(name = "SMS_STATUS")
	private String smsStatus;
	
	@Column(name = "SMS_OBJECT")
	private String smsObject;

	@Column(name = "SEND_TO")
	private String sendTo;
	
	@Column(name = "sms_type")
	private String smsType;
	
	

	public String getSmsType() {
		return smsType;
	}

	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}

	public Long getSmsTokenId() {
		return smsTokenId;
	}

	public void setSmsTokenId(Long smsTokenId) {
		this.smsTokenId = smsTokenId;
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

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getSmsStatus() {
		return smsStatus;
	}

	public void setSmsStatus(String smsStatus) {
		this.smsStatus = smsStatus;
	}

	public String getSmsObject() {
		return smsObject;
	}

	public void setSmsObject(String smsObject) {
		this.smsObject = smsObject;
	}

	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	
	
	
}
