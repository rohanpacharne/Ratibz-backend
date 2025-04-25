package com.lonar.master.a2zmaster.model;

import java.util.Date;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import javax.persistence.Transient;
import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.lonar.master.a2zmaster.utils.UtilsMaster;


@Entity
@Table(name="LT_INVOICE_PAYMENTS")
@JsonInclude(Include.NON_NULL)
public class LtInvoicePayments {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "PAYMENT_ID")
	private Long paymentId;
	
	@Column(name = "INVOICE_ID")
	private Long invoiceId;
	
	@Column(name = "SUPPLIER_ID")
	private Long supplierId;
	
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "PAY_MODE")
	private String payMode;
	
	@Column(name = "PAY_PARTNER_NAME")
	private String payPartnerName;
	
	@Column(name = "PAY_AMOUNT")
	private Double payAmount;
	
	@Column(name = "PARTIAL_PAY_AMOUNT")
	private Double partialPayAmount;
	
	@Column(name = "PAY_REFERENCE_NO")
	private String payReferenceNo;
	
	@Column(name = "PAYMENT_SLIP")
	private String paymentSlip;
	
	@Column(name = "REMARK")
	private String remark;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "TXN_ID")
	private String txnId;
	
	@Column(name = "RESPONSE_CODE")
	private String responseCode;
	
	@Column(name = "TXN_STATUS")
	private String txnStatus;
	
	@Column(name = "TXN_REF")
	private String txnRef;	
	
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

	@Column(name = "SUBS_ID")
	private Long subsId;

	
	@Column(name = "FROM_WALLET_BALANCE")
	private Double fromWalletBalance;
	
	@Column(name = "PAYTM_INVOICE_ID")
	private String paytmInoviceId;
	//alter table LT_INVOICE_PAYMENTS ADD paytm_invoice_id varchar(40);

  @Column(name = "transaction_date")
	private Date transactionDate;
	
	@Transient
	private String userName;

	@Transient
	private String imagePath;
	
	@Transient
	private Date fromDeliveryDate;
	
	@Transient
	private Date toDeliveryDate;
	
	@Transient
	private Date invoiceDate;
	
	@Transient
	private Double finalAmount;
	
	@Transient
	private String invoiceNumber;

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {		
		this.payMode = UtilsMaster.setCapitalizeFully(payMode);;
	}

	public String getPayPartnerName() {
		return payPartnerName;
	}

	public void setPayPartnerName(String payPartnerName) {
		this.payPartnerName =UtilsMaster.setCapitalizeFully(payPartnerName); 
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public String getPayReferenceNo() {
		return payReferenceNo;
	}

	public void setPayReferenceNo(String payReferenceNo) {
		//this.payReferenceNo = UtilsMaster.setCapitalizeFully(payReferenceNo); ;
		this.payReferenceNo = payReferenceNo;
	}

	public String getPaymentSlip() {
		return paymentSlip;
	}

	public void setPaymentSlip(String paymentSlip) {
		this.paymentSlip = paymentSlip;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Date getFromDeliveryDate() {
		return fromDeliveryDate;
	}

	public void setFromDeliveryDate(Date fromDeliveryDate) {
		this.fromDeliveryDate = fromDeliveryDate;
	}

	public Date getToDeliveryDate() {
		return toDeliveryDate;
	}

	public void setToDeliveryDate(Date toDeliveryDate) {
		this.toDeliveryDate = toDeliveryDate;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Double getFinalAmount() {
		return finalAmount;
	}

	public void setFinalAmount(Double finalAmount) {
		this.finalAmount = finalAmount;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Double getPartialPayAmount() {
		return partialPayAmount;
	}

	public void setPartialPayAmount(Double partialPayAmount) {
		this.partialPayAmount = partialPayAmount;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getTxnStatus() {
		return txnStatus;
	}

	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}

	public Long getSubsId() {
		return subsId;
	}

	public void setSubsId(Long subsId) {
		this.subsId = subsId;
	}

	public String getTxnRef() {
		return txnRef;
	}

	public void setTxnRef(String txnRef) {
		this.txnRef = txnRef;
	}

	public Double getFromWalletBalance() {
		return fromWalletBalance;
	}

	public void setFromWalletBalance(Double fromWalletBalance) {
		this.fromWalletBalance = fromWalletBalance;
	}

	public String getPaytmInoviceId() {
		return paytmInoviceId;
	}

	public void setPaytmInoviceId(String paytmInoviceId) {
		this.paytmInoviceId = paytmInoviceId;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}	
}
