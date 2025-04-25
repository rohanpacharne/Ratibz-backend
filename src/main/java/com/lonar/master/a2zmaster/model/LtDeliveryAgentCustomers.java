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

@Entity
@Table(name="LT_DELIVERY_AGENT_CUSTOMERS")
@JsonInclude(Include.NON_NULL)
public class LtDeliveryAgentCustomers extends BaseClass {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "DELIVERY_ASSIGNMENT_ID")
	private Long deliveryAssignmentId;
	
	@Column(name = "SUPPLIER_ID")
	private Long supplierId;
	
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "CUSTOMER_SEQ")
	private Long customerSeq;
	
	@Column(name = "CUSTOMER_Id")
	private Long customerId;
	
	@Transient
	private String customerName;
	
	@Transient
	private String customerAddress;
	
	@Transient
	private String deliveryAgentName;

	@Transient
	private String isChecked;
	
	@Transient
	private Boolean unasigned;
	
	@Transient
	private String search;
	
	
	
	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public Long getDeliveryAssignmentId() {
		return deliveryAssignmentId;
	}

	public void setDeliveryAssignmentId(Long deliveryAssignmentId) {
		this.deliveryAssignmentId = deliveryAssignmentId;
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

	public Long getCustomerSeq() {
		return customerSeq;
	}

	public void setCustomerSeq(Long customerSeq) {
		this.customerSeq = customerSeq;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getDeliveryAgentName() {
		return deliveryAgentName;
	}

	public void setDeliveryAgentName(String deliveryAgentName) {
		this.deliveryAgentName = deliveryAgentName;
	}

	public String getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}

	public Boolean getUnasigned() {
		return unasigned;
	}

	public void setUnasigned(Boolean unasigned) {
		this.unasigned = unasigned;
	}

}
