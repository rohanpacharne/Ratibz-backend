package com.lonar.master.a2zmaster.model;

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
//import com.users.usersmanagement.common.UtilsMaster;
import com.lonar.master.a2zmaster.utils.UtilsMaster;

@Entity
@Table(name="LT_MAST_SUPPLIER_REGISTRATION")
@JsonInclude(Include.NON_NULL)
public class LtMastSupplierRegistration extends BaseClass {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "SUPPLIER_REG_ID")
	private Long supplierRegId;
	
	@Column(name = "SUPPLIER_NAME")
	private String supplierName;
	
	@Column(name = "MOBILE_NUMBER")
	private Long mobileNumber;
	
	@Column(name = "EMAIL_ID")
	private String emailId;
	
	@Column(name = "ADDRESS")
	private String address;
	
	@Column(name = "WEBSITE")
	private String website;

	@Column(name = "business_type")
	private String businessType;

	@Transient
	private Long supplierId;
	
	public Long getSupplierRegId() {
		return supplierRegId;
	}

	public void setSupplierRegId(Long supplierRegId) {
		this.supplierRegId = supplierRegId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = UtilsMaster.setCapitalizeFully(businessType);
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
}
