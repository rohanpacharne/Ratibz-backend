package com.lonar.master.a2zmaster.model;

import java.util.Date;

import jakarta.persistence.*;


@Entity
@Table(name="LT_SUPPLIER_DELIVERY_TIMINGS")
public class LtSupplierDeliveryTimings1 extends BaseClass{

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "DELIVERY_TIME_ID")
	private Long deliveryTimeId;
	
	@Column(name = "SUPPLIER_ID")
	private Long supplierId;
	
	@Column(name = "DELIVERY_TIME")
	private String deliveryTime;

	@Column(name = "FROM_TIME")
	private Date fromTime;
	
	@Column(name = "TO_TIME")
	private Date toTime;
	
	@Column(name = "TIME_LIMIT")
	private Date timeLimit;	
	
	@Transient
	private String strTimeLimit;	
	
	@Transient
	private String strFromTime;
	
	@Transient
	private String strToTime;
	
	@Transient 
	private String showTime;
	
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

	public Date getFromTime() {
		return fromTime;
	}

	public void setFromTime(Date fromTime) {
		this.fromTime = fromTime;
	}

	public Date getToTime() {
		return toTime;
	}

	public void setToTime(Date toTime) {
		this.toTime = toTime;
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

	public Date getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Date timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getStrTimeLimit() {
		return strTimeLimit;
	}

	public void setStrTimeLimit(String strTimeLimit) {
		this.strTimeLimit = strTimeLimit;
	}

	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}	
}
