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
import com.lonar.master.a2zmaster.utils.UtilsMaster;

@Entity
@Table(name="LT_SUPPLIER_CITIES")
@JsonInclude(Include.NON_NULL)
public class LtSupplierCities extends BaseClass{

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "CITY_ID")
	private Long cityId;
	
	@Column(name = "SUPPLIER_ID")
	private Long supplierId;
	
	@Column(name = "STATE_ID")
	private Long stateId;
	
	@Column(name = "CITY")
	private String city;

	@Transient
	private String state;
	
	@Transient
	private String pincode;

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city =UtilsMaster.setCapitalizeFully(city);
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	@Override
	public String toString() {
		return "LtSupplierCities [cityId=" + cityId + ", supplierId=" + supplierId + ", stateId=" + stateId + ", city="
				+ city + ", state=" + state + ", pincode=" + pincode + "]";
	}
	
	
	
}
