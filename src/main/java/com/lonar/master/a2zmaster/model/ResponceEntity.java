package com.lonar.master.a2zmaster.model;

public class ResponceEntity {

	private Integer code;
	private  String message;
	private Object data;
	private Long userId;
	private Long supplierId;
	private String userName;
	private String role;
	private String status;
	private Long lastLoginId; 
	private String adhocDelivery;
	private String isPrepaid;
	private String ownContainers;
	private String supStatus;
	private String mobileNumber;
	private Long pinCode;
	
	
	
	
	
	public Long getPinCode() {
		return pinCode;
	}
	public void setPinCode(Long pinCode) {
		this.pinCode = pinCode;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Long getLastLoginId() {
		return lastLoginId;
	}
	public void setLastLoginId(Long lastLoginId) {
		this.lastLoginId = lastLoginId;
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
	public String getSupStatus() {
		return supStatus;
	}
	public void setSupStatus(String supStatus) {
		this.supStatus = supStatus;
	}

}
