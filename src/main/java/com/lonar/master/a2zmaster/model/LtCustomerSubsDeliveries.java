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
import com.lonar.master.a2zmaster.utils.UtilsMaster;


@Entity
@Table(name="LT_CUSTOMER_SUBS_DELIVERIES")
@JsonInclude(Include.NON_NULL)
public class LtCustomerSubsDeliveries extends BaseClass{

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "DELIVERY_ID")
	private Long deliveryId;
	
	@Column(name = "SUPPLIER_ID")
	private Long supplierId;
	
	@Column(name = "DELIVERY_DATE")
	private Date deliveryDate;
	
	@Column(name = "SUBS_ID")
	private Long subsId;
	
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "PRODUCT_ID")
	private Long productId;
	
	@Column(name = "SUBS_QUANTITY")
	private Double subsQuantity;
	
	@Column(name = "DELIVERY_MODE")
	private String deliveryMode;
	
	@Column(name = "DELIVERY_QUANTITY")
	private Double deliveryQuantity;
	
	@Column(name = "INVOICE_ID")
	private Long invoiceId;
	
	@Column(name = "INVOICE_RATE")
	private Double invoiceRate;
	
	@Column(name = "LINE_AMOUNT")
	private Double lineAmount;
	
	@Column(name = "VACATION_ID")
	private Long vacationId;

	@Transient
	private String productName;
	
	@Transient
	private String productImage;
	
	@Transient
	private String productImagePath;
	
	@Transient
	private String productUom;
	
	@Transient
	private String userName;
	
	@Transient
	private String address;
	
	@Transient 
	private Long containersCollected;
	
	@Transient
	private String deliveryTime;
	
	@Transient
	private String delDate;

	@Transient
	private String collectContainer;
	
	@Transient
	private Date currentTime;
	
	@Transient
	private String subscriptionType;
	
	@Transient
	private String showTime;
	
	@Transient
	private String fromTime;
	
	@Transient
	private String toTime;

	@Transient
	private String mobileNumber;
	
	@Transient
	private String timeLimit ;
	
	@Transient
	private Double dQty;
	
	@Transient
	private String supplierName;
	  

	
	
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Long getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(Long deliveryId) {
		this.deliveryId = deliveryId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Long getSubsId() {
		return subsId;
	}

	public void setSubsId(Long subsId) {
		this.subsId = subsId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Double getSubsQuantity() {
		return subsQuantity;
	}

	public void setSubsQuantity(Double subsQuantity) {
		this.subsQuantity = subsQuantity;
	}

	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public Double getDeliveryQuantity() {
		return deliveryQuantity;
	}

	public void setDeliveryQuantity(Double deliveryQuantity) {
		this.deliveryQuantity = deliveryQuantity;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Double getInvoiceRate() {
		return invoiceRate;
	}

	public void setInvoiceRate(Double invoiceRate) {
		this.invoiceRate = invoiceRate;
	}

	public Double getLineAmount() {
		return lineAmount;
	}

	public void setLineAmount(Double lineAmount) {
		this.lineAmount = lineAmount;
	}

	public Long getVacationId() {
		return vacationId;
	}

	public void setVacationId(Long vacationId) {
		this.vacationId = vacationId;
	}

	public String getProductName() {
		//return productName;
		return UtilsMaster.setCapitalizeFully(productName);
	}

	public void setProductName(String productName) {
		//this.productName = productName;
		this.productName =  UtilsMaster.setCapitalizeFully(productName);
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public String getProductImagePath() {
		return productImagePath;
	}

	public void setProductImagePath(String productImagePath) {
		this.productImagePath = productImagePath;
	}

	public String getProductUom() {
		return productUom;
	}

	public void setProductUom(String productUom) {
		this.productUom = productUom;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getContainersCollected() {
		return containersCollected;
	}

	public void setContainersCollected(Long containersCollected) {
		this.containersCollected = containersCollected;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getDelDate() {
		return delDate;
	}

	public void setDelDate(String delDate) {
		this.delDate = delDate;
	}

	public String getCollectContainer() {
		return collectContainer;
	}

	public void setCollectContainer(String collectContainer) {
		this.collectContainer = collectContainer;
	}

	public Date getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}

	public String getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
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

	public String getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Double getdQty() {
		return dQty;
	}

	public void setdQty(Double dQty) {
		this.dQty = dQty;
	}

	
	
}
