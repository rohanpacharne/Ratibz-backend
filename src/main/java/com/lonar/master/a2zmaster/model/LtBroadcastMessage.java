package com.lonar.master.a2zmaster.model;

import java.util.Date;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "LT_BROADCAST_MESSAGE")
@JsonInclude(Include.NON_NULL)
public class LtBroadcastMessage {


	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "broadcast_message_id")
	private Long broadcastMessageId;
	
	@Column(name = "CUSTOMER_STATUS")
	private String customerStatus;
	
	@Column(name = "CITY_ID")
	private Long cityId;
	
	@Column(name = "AREA_ID")
	private Long areaId;
	
	@Column(name = "SUPPLIER_ID")
	private Long supplierId;
	
	@Column(name = "BROADCAST_MESSAGE")
	private String broadcastMessage;
	
	@Column(name = "SMS")
	private String sms;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "WHATSAPP")
	private String whatsapp;
	
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "CREATED_BY")
	private Long createdBy;
	
	@Column(name = "CREATION_DATE")
	private Date creationDate;
	
	@Column(name = "LAST_UPDATE_LOGIN")
	private Long lastUpdateLogin;
	
	@Column(name = "LAST_UPDATED_BY")
	private Long lastUpdatedBy;
	
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;
	
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Long getLastUpdateLogin() {
		return lastUpdateLogin;
	}
	public void setLastUpdateLogin(Long lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}
	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public Long getBroadcastMessageId() {
		return broadcastMessageId;
	}
	public void setBroadcastMessageId(Long broadcastMessageId) {
		this.broadcastMessageId = broadcastMessageId;
	}
	public String getCustomerStatus() {
		return customerStatus;
	}
	public void setCustomerStatus(String customerStatus) {
		this.customerStatus = customerStatus;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public String getBroadcastMessage() {
		return broadcastMessage;
	}
	public void setBroadcastMessage(String broadcastMessage) {
		this.broadcastMessage = broadcastMessage;
	}
	public String getSms() {
		return sms;
	}
	public void setSms(String sms) {
		this.sms = sms;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWhatsapp() {
		return whatsapp;
	}
	public void setWhatsapp(String whatsapp) {
		this.whatsapp = whatsapp;
	}
	@Override
	public String toString() {
		return "LtBroadcastMessage [broadcastMessageId=" + broadcastMessageId + ", customerStatus=" + customerStatus
				+ ", cityId=" + cityId + ", areaId=" + areaId + ", supplierId=" + supplierId + ", broadcastMessage="
				+ broadcastMessage + ", sms=" + sms + ", email=" + email + ", whatsapp=" + whatsapp + ", status="
				+ status + ", createdBy=" + createdBy + ", creationDate=" + creationDate + ", lastUpdateLogin="
				+ lastUpdateLogin + ", lastUpdatedBy=" + lastUpdatedBy + ", lastUpdateDate=" + lastUpdateDate
				+ ", getStatus()=" + getStatus() + ", getCreatedBy()=" + getCreatedBy() + ", getCreationDate()="
				+ getCreationDate() + ", getLastUpdateLogin()=" + getLastUpdateLogin() + ", getLastUpdatedBy()="
				+ getLastUpdatedBy() + ", getLastUpdateDate()=" + getLastUpdateDate() + ", getBroadcastMessageId()="
				+ getBroadcastMessageId() + ", getCustomerStatus()=" + getCustomerStatus() + ", getCityId()="
				+ getCityId() + ", getAreaId()=" + getAreaId() + ", getSupplierId()=" + getSupplierId()
				+ ", getBroadcastMessage()=" + getBroadcastMessage() + ", getSms()=" + getSms() + ", getEmail()="
				+ getEmail() + ", getWhatsapp()=" + getWhatsapp() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
	
}
