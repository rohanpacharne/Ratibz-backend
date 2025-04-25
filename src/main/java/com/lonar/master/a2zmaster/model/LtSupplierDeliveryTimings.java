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


@Entity
@Table(name="LT_SUPPLIER_DELIVERY_TIMINGS")
public class LtSupplierDeliveryTimings extends BaseClass{

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "DELIVERY_TIME_ID")
	private Long deliveryTimeId;
	
	@Column(name = "SUPPLIER_ID")
	private Long supplierId;
	
	@Column(name = "DELIVERY_TIME")
	private String deliveryTime;

	@Column(name = "FROM_TIME")
	private String fromTime;
	
	@Column(name = "TO_TIME")
	private String toTime;

	@Column(name = "time_limit")
	private String timeLimit ;
	
	@Transient
	private String strTimeLimit;	
	
	@Transient
	private String strFromTime;
	
	
	@Transient 
	private String showTime;
	
	@Transient
	private String strToTime;
	
	@Transient
	private String subscriptionType;

	@Transient
	private Date deliveryDate;
	
	public Long getDeliveryTimeId() {
		return deliveryTimeId;
	}

	public void setDeliveryTimeId(Long deliveryTimeId) {
		this.deliveryTimeId = deliveryTimeId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public String getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getStrTimeLimit() {
		return strTimeLimit;
	}

	public void setStrTimeLimit(String strTimeLimit) {
		this.strTimeLimit = strTimeLimit;
	}

	public String getStrFromTime() {
		return strFromTime;
	}

	public void setStrFromTime(String strFromTime) {
		this.strFromTime = strFromTime;
	}

	public String getStrToTime() {
		return strToTime;
	}

	public void setStrToTime(String strToTime) {
		this.strToTime = strToTime;
	}

	public String getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	
}
