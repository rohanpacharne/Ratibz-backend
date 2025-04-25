package com.lonar.master.a2zmaster.model;
import java.util.List;
//
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
@Table(name="LT_DELIVERY_AREAS")
@JsonInclude(Include.NON_NULL)
public class LtDeliveryAreas extends BaseClass{

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "AREA_ID")
	private Long areaId;
	
	@Column(name = "SUPPLIER_ID")
	private Long supplierId;
	
	@Column(name = "STATE_ID")
	private Long stateId;
	
	@Column(name = "CITY_ID")
	private Long cityId;
	
	@Column(name = "AREA")
	private String area;
	
	@Column(name = "AREA_PIN")
	private String areaPin;

	@Transient
	private String state;
	
	@Transient
	private String city;

	@Transient
	private List<LtDeliveryAreaAgents> deliveryAgentList;

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

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		//this.area = UtilsMaster.capitalizeSentence(area);
		this.area = area;
	}

	public String getAreaPin() {
		return areaPin;
	}

	public void setAreaPin(String areaPin) {
		this.areaPin = areaPin;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List<LtDeliveryAreaAgents> getDeliveryAgentList() {
		return deliveryAgentList;
	}

	public void setDeliveryAgentList(List<LtDeliveryAreaAgents> deliveryAgentList) {
		this.deliveryAgentList = deliveryAgentList;
	}

	@Override
	public String toString() {
		return "LtDeliveryAreas [areaId=" + areaId + ", supplierId=" + supplierId + ", stateId=" + stateId + ", cityId="
				+ cityId + ", area=" + area + ", areaPin=" + areaPin + ", state=" + state + ", city=" + city
				+ ", deliveryAgentList=" + deliveryAgentList + "]";
	}

	
}
