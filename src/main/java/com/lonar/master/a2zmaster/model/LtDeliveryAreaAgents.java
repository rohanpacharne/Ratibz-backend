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
@Table(name="LT_DELIVERY_AREA_AGENTS")
@JsonInclude(Include.NON_NULL)
public class LtDeliveryAreaAgents extends BaseClass{

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "AREA_BOY_ID")
	private Long areaBoyId;
	
	@Column(name = "AREA_ID")
	private Long areaId;
	
	@Column(name = "SUPPLIER_ID")
	private Long supplierId;
	
	@Column(name = "USER_ID")
	private Long userId;

	@Transient
	private String area;
	
	@Transient
	private String userName;

	public Long getAreaBoyId() {
		return areaBoyId;
	}

	public void setAreaBoyId(Long areaBoyId) {
		this.areaBoyId = areaBoyId;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "LtDeliveryAreaAgents [areaBoyId=" + areaBoyId + ", areaId=" + areaId + ", supplierId=" + supplierId
				+ ", userId=" + userId + ", area=" + area + ", userName=" + userName + "]";
	}
	
	
}
