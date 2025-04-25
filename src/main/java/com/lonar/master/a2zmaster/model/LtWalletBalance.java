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
@Table(name="lt_wallet_balance")
@JsonInclude(Include.NON_NULL)
public class LtWalletBalance {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "wallet_id")
	private Long walletId;
	
	@Column(name = "amount")
	private Double amount;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "delivery_id")
	private Long deliveryId;
	
	@Column(name = "payment_id")
	private Long paymentId;
	
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "supplier_id")
	private Long supplierId;
	
	@Column(name = "created_by")
	private Long createdBy;
	
	@Column(name = "creation_date")
	private Date creationDate;
	
	@Column(name = "last_updated_login")
	private Long lastUpdatedLogin;

	public Long getWalletId() {
		return walletId;
	}

	public void setWalletId(Long walletId) {
		this.walletId = walletId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(Long deliveryId) {
		this.deliveryId = deliveryId;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
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

	public Long getLastUpdatedLogin() {
		return lastUpdatedLogin;
	}

	public void setLastUpdatedLogin(Long lastUpdatedLogin) {
		this.lastUpdatedLogin = lastUpdatedLogin;
	}
	
}
