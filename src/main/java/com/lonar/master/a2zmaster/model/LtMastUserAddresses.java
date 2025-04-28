package com.lonar.master.a2zmaster.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "LT_MAST_USER_ADDRESSES")
public class LtMastUserAddresses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ADDRESS_ID")
    private Long userAddressId;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "ADDRESS_TYPE")
    private String addressType;

    @Column(name = "FLAT_NO")
    private String flatNo;

    @Column(name = "SOCIETY_NAME")
    private String societyName;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PIN_CODE")
    private String pinCode;

    @Column(name = "LAND_MARK")
    private String landMark;

    @Column(name = "AREA_ID")
    private Long areaId;

    @Column(name = "STATE_ID")
    private Long stateId;

    @Column(name = "CITY_ID")
    private Long cityId;

    @Column(name = "PRIMARY_ADDRESS")
    private String primaryAddress;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @Column(name = "LAST_UPDATE_DATE")
    private Date lastUpdateDate;

    @Column(name = "LAST_UPDATED_BY")
    private Long lastUpdatedBy;

    @Column(name = "LAST_UPDATE_LOGIN")
    private Long lastUpdateLogin;
    
    @Column(name = "MOBILE")
    private String mobile;
    
    @Column(name = "ALTERNATE_NO")
    private String alternateNo;
    
    @Column(name = "EMAIL")
    private String email;
    
    @Transient
    private String area;
    
    @Transient
    private String city;
    
    @Transient
    private String state;

	public Long getUserAddressId() {
		return userAddressId;
	}

	public void setUserAddressId(Long userAddressId) {
		this.userAddressId = userAddressId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
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
		this.address = address;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getLandMark() {
		return landMark;
	}

	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
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

	public String getPrimaryAddress() {
		return primaryAddress;
	}

	public void setPrimaryAddress(String primaryAddress) {
		this.primaryAddress = primaryAddress;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Long getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdateLogin(Long lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAlternateNo() {
		return alternateNo;
	}

	public void setAlternateNo(String alternateNo) {
		this.alternateNo = alternateNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "LtMastUserAddresses [userAddressId=" + userAddressId + ", userId=" + userId + ", addressType="
				+ addressType + ", flatNo=" + flatNo + ", societyName=" + societyName + ", address=" + address
				+ ", pinCode=" + pinCode + ", landMark=" + landMark + ", areaId=" + areaId + ", stateId=" + stateId
				+ ", cityId=" + cityId + ", primaryAddress=" + primaryAddress + ", createdBy=" + createdBy
				+ ", creationDate=" + creationDate + ", lastUpdateDate=" + lastUpdateDate + ", lastUpdatedBy="
				+ lastUpdatedBy + ", lastUpdateLogin=" + lastUpdateLogin + ", mobile=" + mobile + ", alternateNo="
				+ alternateNo + ", email=" + email + ", area=" + area + ", city=" + city + ", state=" + state + "]";
	}
    
    
}
