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

@Entity
@Table(name="lt_mast_company")
@JsonInclude(Include.NON_NULL)
public class LtMastCompany {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "company_id")
	private Long companyId;
	
	@Column(name = "company_CODE")
	private String companyCode;
	
	@Column(name = "company_NAME")
	private String companyName;

	
	@Column(name = "TXN_NOTE")
	private String txnNote;

	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "START_DATE")
	private Date startDate;
	
	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name = "CREATED_BY")
	private Long createdBy;
	
	@Column(name = "COMPANY_UPI_ID")
	private String companyUpiId;
	
	@Column(name = "COMPANY_PAYEE_NAME")
	private String companyPayeeName;
	
	@Column(name = "CREATION_DATE")
	private Date creationDate;
	
	@Column(name = "LAST_UPDATE_LOGIN")
	private Long lastUpdateLogin;
	
	@Column(name = "LAST_UPDATED_BY")
	private Long lastUpdatedBy;
	
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;

	@Column(name = "supplier_amt")
	private Double supplierAmt;

	@Column(name = "supplier_discount_amt")
	private Double supplierDiscountAmt;
	
	@Transient
	private String supplierCode;

	@Transient
	private String supStatus;
 
	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTxnNote() {
		return txnNote;
	}

	public void setTxnNote(String txnNote) {
		this.txnNote = txnNote;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public String getCompanyPayeeName() {
		return companyPayeeName;
	}

	public void setCompanyPayeeName(String companyPayeeName) {
		this.companyPayeeName = companyPayeeName;
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

	public String getCompanyUpiId() {
		return companyUpiId;
	}

	public void setCompanyUpiId(String companyUpiId) {
		this.companyUpiId = companyUpiId;
	}

	public Double getSupplierAmt() {
		return supplierAmt;
	}

	public void setSupplierAmt(Double supplierAmt) {
		this.supplierAmt = supplierAmt;
	}

	public Double getSupplierDiscountAmt() {
		return supplierDiscountAmt;
	}

	public void setSupplierDiscountAmt(Double supplierDiscountAmt) {
		this.supplierDiscountAmt = supplierDiscountAmt;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getSupStatus() {
		return supStatus;
	}

	public void setSupStatus(String supStatus) {
		this.supStatus = supStatus;
	}

	
	
}
