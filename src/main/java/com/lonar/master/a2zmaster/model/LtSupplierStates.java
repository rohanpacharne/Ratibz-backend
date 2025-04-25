package com.lonar.master.a2zmaster.model;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.lonar.master.a2zmaster.utils.UtilsMaster;


@Entity
@Table(name="LT_SUPPLIER_STATES")
@JsonInclude(Include.NON_NULL)
public class LtSupplierStates extends BaseClass{


	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "STATE_ID")
	private Long stateId;
	
	@Column(name = "SUPPLIER_ID")
	private Long supplierId;
	
	@Column(name = "SUPPLIER_STATE")
	private String supplierState;
	
	@Column(name = "SUPPLIER_GST_NO")
	private String supplierGstNo;

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierState() {
		return supplierState;
	}

	public void setSupplierState(String supplierState) {
				
		this.supplierState = UtilsMaster.setCapitalizeFully(supplierState);
	}

	public String getSupplierGstNo() {
		return supplierGstNo;
	}

	public void setSupplierGstNo(String supplierGstNo) {
		this.supplierGstNo = supplierGstNo;
	}

	@Override
	public String toString() {
		return "LtSupplierStates [stateId=" + stateId + ", supplierId=" + supplierId + ", supplierState="
				+ supplierState + ", supplierGstNo=" + supplierGstNo + "]";
	}
	
	
	
}
