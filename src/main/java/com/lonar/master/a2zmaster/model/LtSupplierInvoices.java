package com.lonar.master.a2zmaster.model;

import java.util.Date;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name="LT_SUPPLIER_INVOICES")
@JsonInclude(Include.NON_NULL)
public class LtSupplierInvoices {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "INVOICE_ID")
	private Long invoiceId;
	
	@Column(name = "SUPPLIER_ID")
	private Long supplierId;
	
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "INVOICE_NUMBER")
	private String invoiceNumber;
	
	@Column(name = "INVOICE_DATE")
	private Date invoiceDate;
	
	/*@Column(name = "SUBS_ID")
	private Long subsId;
	*/
	@Column(name = "SUBS_MONTH")
	private Date subsMonth;
	
	@Column(name = "FROM_DELIVERY_DATE")
	private Date fromDeliveryDate;
	
	@Column(name = "TO_DELIVERY_DATE")
	private Date toDeliveryDate;
	
	@Column(name = "INVOICE_AMOUNT")
	private Double invoiceAmount;
	
	@Column(name = "DISCOUNT_AMOUNT")
	private Double discountAmount;
	
	@Column(name = "FINAL_AMOUNT")
	private Double finalAmount;
	
	@Column(name = "TAX_NAME")
	private String taxName;
	
	@Column(name = "TAX_AMOUNT")
	private Double taxAmount;
	
	@Column(name = "INVOICE_TOTAL")
	private Double invoiceTotal;
	
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
 
	@Transient
	private String userName;

	@Transient
	private Integer start;

	@Transient
	private Integer length;
	
	@Transient
	private Integer columnNo;

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

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

//	public Long getSubsId() {
//		return subsId;
//	}
//
//	public void setSubsId(Long subsId) {
//		this.subsId = subsId;
//	}

	public Date getSubsMonth() {
		return subsMonth;
	}

	public void setSubsMonth(Date subsMonth) {
		this.subsMonth = subsMonth;
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

	public Double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Double getFinalAmount() {
		return finalAmount;
	}

	public void setFinalAmount(Double finalAmount) {
		this.finalAmount = finalAmount;
	}

	public String getTaxName() {
		return taxName;
	}

	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}

	public Double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public Double getInvoiceTotal() {
		return invoiceTotal;
	}

	public void setInvoiceTotal(Double invoiceTotal) {
		this.invoiceTotal = invoiceTotal;
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

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getColumnNo() {
		return columnNo;
	}

	public void setColumnNo(Integer columnNo) {
		this.columnNo = columnNo;
	}

 
	
}
