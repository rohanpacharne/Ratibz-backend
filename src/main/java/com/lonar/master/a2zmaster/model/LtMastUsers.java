package com.lonar.master.a2zmaster.model;

//import javax.persistence.Basic;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import javax.persistence.Transient;
import jakarta.persistence.*;

import org.apache.commons.text.WordUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.lonar.master.a2zmaster.utils.UtilsMaster;


@Entity
@Table(name = "LT_MAST_USERS")
@JsonInclude(Include.NON_NULL)
public class LtMastUsers  extends BaseClass {

private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "SUPPLIER_ID")
	private Long  supplierId;
	
	@Column(name = "MOBILE_NUMBER")
	private String mobileNumber;
	
	@Column(name = "USER_TYPE")
	private String userType;
	
	@Column(name = "USER_NAME")
	private String userName;
	
	@Column(name = "FLAT_NO")
	private String flatNo;
	
	@Column(name = "SOCIETY_NAME")
	private String societyName;
	
	@Column(name = "ADDRESS")
	private String address;
	
	@Column(name = "PIN_CODE")
	private Long pinCode;
	
	@Column(name = "AREA")
	private String area;
	
	@Column(name = "STATE_ID")
	private Long stateId;
	
	@Column(name = "LANDMARK")
	private String landmark;
	
	@Column(name = "CITY_ID")
	private Long cityId;

	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "ALTERNATE_NO")
	private String alternateNo;

	@Column(name = "GST_NO")
	private String gstNo;
	
	@Column(name = "PO_NUMBER")
	private String poNumber;

	@Column(name = "BILLING_CYCLE")
	private String billingCycle;
	
	@Column(name = "IP_ADDRESS")
	private String ipAddress;
	
	@Column(name = "AREA_ID")
	private Long areaId;
	
	@Transient
	private String city;
	
	@Transient
	private String supplierState;
	
	@Transient
	private String supplierName;
	
	@Transient
	private String supplierCode;
	
	@Transient
	private String customerAddress;
	
	@Transient
	private Long subsCount;
	
	@Transient
	private String supStatus;
	
	@Transient
	private String adhocDelivery;
	
	@Transient
	private String isPrepaid;
	
	@Transient
	private String ownContainers;

	@Transient
	private String agentAssigned;
	
	@Transient
	private Long productId;
	
	@Transient
	private Long collectedQuantity;
	
	@Transient
	private Double deliveryQuantity;
	
	@Transient
	private Boolean isA2Z;
	
	@Transient
	private String imgText;
	
	
	

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getImgText() {
		return imgText;
	}

	public void setImgText(String imgText) {
		this.imgText = imgText;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		//this.userName = userName; 
		this.userName = WordUtils.capitalizeFully(userName);
	}

	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		//this.flatNo = flatNo;
		this.flatNo = WordUtils.capitalizeFully(flatNo);
	}

	public String getSocietyName() {
		return societyName;
	}

	public void setSocietyName(String societyName) {
		this.societyName = societyName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		//this.address = address;
		this.address = WordUtils.capitalizeFully(address);
	}

	public Long getPinCode() {
		return pinCode;
	}

	public void setPinCode(Long pinCode) {
		this.pinCode = pinCode;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		//this.area = area;
		this.area = WordUtils.capitalizeFully(area);
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAlternateNo() {
		return alternateNo;
	}

	public void setAlternateNo(String alternateNo) {
		this.alternateNo = alternateNo;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		//this.landmark = landmark;
		this.landmark = WordUtils.capitalizeFully(landmark);
	}

	public String getBillingCycle() {
		return billingCycle;
	}

	public void setBillingCycle(String billingCycle) {
		this.billingCycle = billingCycle;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		//this.supplierName = supplierName;
		this.supplierName = UtilsMaster.setCapitalizeFully(supplierName);
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public Long getSubsCount() {
		return subsCount;
	}

	public void setSubsCount(Long subsCount) {
		this.subsCount = subsCount;
	}

	public String getSupStatus() {
		return supStatus;
	}

	public void setSupStatus(String supStatus) {
		this.supStatus = supStatus;
	}

	public String getAdhocDelivery() {
		return adhocDelivery;
	}

	public void setAdhocDelivery(String adhocDelivery) {
		this.adhocDelivery = adhocDelivery;
	}

	public String getIsPrepaid() {
		return isPrepaid;
	}

	public void setIsPrepaid(String isPrepaid) {
		this.isPrepaid = isPrepaid;
	}

	public String getOwnContainers() {
		return ownContainers;
	}

	public void setOwnContainers(String ownContainers) {
		this.ownContainers = ownContainers;
	}

	public Boolean getIsA2Z() {
		return isA2Z;
	}

	public void setIsA2Z(Boolean isA2Z) {
		this.isA2Z = isA2Z;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getAgentAssigned() {
		return agentAssigned;
	}

	public void setAgentAssigned(String agentAssigned) {
		this.agentAssigned = agentAssigned;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getCollectedQuantity() {
		return collectedQuantity;
	}

	public void setCollectedQuantity(Long collectedQuantity) {
		this.collectedQuantity = collectedQuantity;
	}

	public Double getDeliveryQuantity() {
		return deliveryQuantity;
	}

	public void setDeliveryQuantity(Double deliveryQuantity) {
		this.deliveryQuantity = deliveryQuantity;
	}

	public String getSupplierState() {
		return supplierState;
	}

	public void setSupplierState(String supplierState) {
		this.supplierState = supplierState;
	}

}
