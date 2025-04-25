package com.lonar.master.a2zmaster.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.*;

@Entity
@Table(name="LT_MAST_SUPPLIERS")
@JsonInclude(Include.NON_NULL)
public class LtMastSuppliers extends BaseClass{

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "SUPPLIER_ID")
	private Long supplierId;
	
	@Column(name = "SUPPLIER_CODE")
	private String supplierCode;
	
	@Column(name = "SUPPLIER_NAME")
	private String supplierName;
	
	@Column(name = "SUPPLIER_TYPE")
	private String supplierType;
	
	@Column(name = "PROPRIETOR_NAME")
	private String proprietorName;
	
	@Column(name = "PAN_NUMBER")
	private String panNumber;
	
	@Column(name = "gst_no")
	private String gstNo;
	
	@Column(name = "PRIMARY_NUMBER")
	private Long primaryNumber;
	
	@Column(name = "PRIMARY_EMAIL")
	private String primaryEmail;
	
	@Column(name = "ALTERNATE_NUMBER_1")
	private Long alternateNumber1;
	
	@Column(name = "ALTERNATE_NUMBER_2")
	private Long alternateNumber2;
	
	@Column(name = "ALTERNATE_NUMBER_3")
	private Long alternateNumber3;
	
	@Column(name = "ADDRESS")
	private String address;
	
	@Column(name = "STATE_ID")
	private Long stateId;
	
	@Column(name = "CITY_ID")
	private Long cityId;
	
	@Column(name = "PIN_CODE")
	private Long pinCode;
	
	@Column(name = "SUPPLIER_LOGO")
	private String supplierLogo;
	
	@Column(name = "FACEBOOK_PAGE")
	private String facebookPage;
	
	@Column(name = "TWITTER_PAGE")
	private String twitterPage;
	
	@Column(name = "LINKEDIN_PAGE")
	private String linkedinPage;
	
	@Column(name = "WEBSITE")
	private String website;
	
	@Column(name = "WHATSAPP_PACK")
	private String whatsappPack;
	
	@Column(name = "SMS_PACK")
	private String smsPack;
	
	@Column(name = "REMARK")
	private String remark;
	
	@Column(name = "SUPPLIER_UPI_ID")
	private String supplierUpiId;
	
	@Column(name = "SUPPLIER_PAYEE_NAME")
	private String supplierPayeeName;
	
	@Column(name = "ADHOC_DELIVERY")
	private String adhocDelivery;

	@Column(name = "IS_PREPAID")
	private String isPrepaid;
	
	@Column(name = "TXN_NOTE")
	private String txnNote;
	
	@Column(name = "Own_Containers")
	private String ownContainers;
	
	@Column(name = "FLAT_NO")
	private String flatNo;
	
	
	@Column(name = "firebase_server_key")
	private String firebaseServerKey;
	

	
	@Column(name = "business_type")
	private String businessType;
	
	
	@Transient
	private String city;
	
	@Transient
	private String supplierState;
	
	@Transient
	List<LtSupplierDeliveryTimings1> deliveryTimings; 
/*	@Transient
	private String deliveryTime;

	@Transient
	private String fromTime;
	
	@Transient
	private String toTime;
	
	@Transient 
	private Long deliveryTimeId;*/
	
	@Transient 
	private Long userId;
	
	@Transient
	private String cutomerSupportContact;
	
	@Transient
	private String aboutUs;
	
	
	@Transient
	private Long mastProdTypeId;
	
	
	
	
	public Long getMastProdTypeId() {
		return mastProdTypeId;
	}

	public void setMastProdTypeId(Long mastProdTypeId) {
		this.mastProdTypeId = mastProdTypeId;
	}

	public String getCutomerSupportContact() {
		return cutomerSupportContact;
	}

	public void setCutomerSupportContact(String cutomerSupportContact) {
		this.cutomerSupportContact = cutomerSupportContact;
	}

	public String getAboutUs() {
		return aboutUs;
	}

	public void setAboutUs(String aboutUs) {
		this.aboutUs = aboutUs;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierType() {
		return supplierType;
	}

	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}

	public String getProprietorName() {
		return proprietorName;
	}

	public void setProprietorName(String proprietorName) {
		this.proprietorName = proprietorName;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public Long getPrimaryNumber() {
		return primaryNumber;
	}

	public void setPrimaryNumber(Long primaryNumber) {
		this.primaryNumber = primaryNumber;
	}

	public String getPrimaryEmail() {
		return primaryEmail;
	}

	public void setPrimaryEmail(String primaryEmail) {
		this.primaryEmail = primaryEmail;
	}

	public Long getAlternateNumber1() {
		return alternateNumber1;
	}

	public void setAlternateNumber1(Long alternateNumber1) {
		this.alternateNumber1 = alternateNumber1;
	}

	public Long getAlternateNumber2() {
		return alternateNumber2;
	}

	public void setAlternateNumber2(Long alternateNumber2) {
		this.alternateNumber2 = alternateNumber2;
	}

	public Long getAlternateNumber3() {
		return alternateNumber3;
	}

	public void setAlternateNumber3(Long alternateNumber3) {
		this.alternateNumber3 = alternateNumber3;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public Long getPinCode() {
		return pinCode;
	}

	public void setPinCode(Long pinCode) {
		this.pinCode = pinCode;
	}

	public String getSupplierLogo() {
		return supplierLogo;
	}

	public void setSupplierLogo(String supplierLogo) {
		this.supplierLogo = supplierLogo;
	}

	public String getFacebookPage() {
		return facebookPage;
	}

	public void setFacebookPage(String facebookPage) {
		this.facebookPage = facebookPage;
	}

	public String getTwitterPage() {
		return twitterPage;
	}

	public void setTwitterPage(String twitterPage) {
		this.twitterPage = twitterPage;
	}

	public String getLinkedinPage() {
		return linkedinPage;
	}

	public void setLinkedinPage(String linkedinPage) {
		this.linkedinPage = linkedinPage;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getWhatsappPack() {
		return whatsappPack;
	}

	public void setWhatsappPack(String whatsappPack) {
		this.whatsappPack = whatsappPack;
	}

	public String getSmsPack() {
		return smsPack;
	}

	public void setSmsPack(String smsPack) {
		this.smsPack = smsPack;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSupplierUpiId() {
		return supplierUpiId;
	}

	public void setSupplierUpiId(String supplierUpiId) {
		this.supplierUpiId = supplierUpiId;
	}

	public String getSupplierPayeeName() {
		return supplierPayeeName;
	}

	public void setSupplierPayeeName(String supplierPayeeName) {
		this.supplierPayeeName = supplierPayeeName;
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

	public String getTxnNote() {
		return txnNote;
	}

	public void setTxnNote(String txnNote) {
		this.txnNote = txnNote;
	}

	public String getOwnContainers() {
		return ownContainers;
	}

	public void setOwnContainers(String ownContainers) {
		this.ownContainers = ownContainers;
	}

	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}

	public String getFirebaseServerKey() {
		return firebaseServerKey;
	}

	public void setFirebaseServerKey(String firebaseServerKey) {
		this.firebaseServerKey = firebaseServerKey;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSupplierState() {
		return supplierState;
	}

	public void setSupplierState(String supplierState) {
		this.supplierState = supplierState;
	}

	public List<LtSupplierDeliveryTimings1> getDeliveryTimings() {
		return deliveryTimings;
	}

	public void setDeliveryTimings(List<LtSupplierDeliveryTimings1> deliveryTimings) {
		this.deliveryTimings = deliveryTimings;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "LtMastSuppliers [supplierId=" + supplierId + ", supplierCode=" + supplierCode + ", supplierName="
				+ supplierName + ", supplierType=" + supplierType + ", proprietorName=" + proprietorName
				+ ", panNumber=" + panNumber + ", gstNo=" + gstNo + ", primaryNumber=" + primaryNumber
				+ ", primaryEmail=" + primaryEmail + ", alternateNumber1=" + alternateNumber1 + ", alternateNumber2="
				+ alternateNumber2 + ", alternateNumber3=" + alternateNumber3 + ", address=" + address + ", stateId="
				+ stateId + ", cityId=" + cityId + ", pinCode=" + pinCode + ", supplierLogo=" + supplierLogo
				+ ", facebookPage=" + facebookPage + ", twitterPage=" + twitterPage + ", linkedinPage=" + linkedinPage
				+ ", website=" + website + ", whatsappPack=" + whatsappPack + ", smsPack=" + smsPack + ", remark="
				+ remark + ", supplierUpiId=" + supplierUpiId + ", supplierPayeeName=" + supplierPayeeName
				+ ", adhocDelivery=" + adhocDelivery + ", isPrepaid=" + isPrepaid + ", txnNote=" + txnNote
				+ ", ownContainers=" + ownContainers + ", flatNo=" + flatNo + ", firebaseServerKey=" + firebaseServerKey
				+ ", businessType=" + businessType + ", city=" + city + ", supplierState=" + supplierState
				+ ", deliveryTimings=" + deliveryTimings + ", userId=" + userId + ", cutomerSupportContact="
				+ cutomerSupportContact + ", aboutUs=" + aboutUs + ", mastProdTypeId=" + mastProdTypeId + "]";
	}

	
	
	
}
