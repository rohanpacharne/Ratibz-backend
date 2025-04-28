package com.lonar.master.a2zmaster.model;
import java.util.Date;
import java.util.List;

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
@Table(name="LT_CUSTOMER_SUBS")
@JsonInclude(Include.NON_NULL)
public class LtCustomerSubs extends BaseClass{

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "SUBS_ID")
	private Long subsId;
	
	@Column(name = "SUPPLIER_ID")
	private Long supplierId;
	
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "PRODUCT_ID")
	private Long productId;
	
	@Column(name = "SUBS_QUANTITY")
	private Double subsQuantity;
	
	@Column(name = "CUSTOMER_RATE")
	private Double customerRate;
	
	/*@Column(name = "DELIVERY_SCHEDULE")
	private String deliverySchedule;*/
	
	@Column(name = "DELIVERY_MODE")
	private String deliveryMode;
	
	@Column(name = "DELIVERY_TIME")
	private String deliveryTime;

	@Column(name = "SUBSCRIPTION_TYPE")
	private String subscriptionType;
	
	@Column(name = "SUNDAY")
	private String sunday;
	
	@Column(name = "MONDAY")
	private String monday;
	
	@Column(name = "TUESDAY")
	private String tuesday;
	
	@Column(name = "WEDNESDAY")
	private String wednesday;
	
	@Column(name = "THURSDAY")
	private String thursday;
	
	@Column(name = "FRIDAY")
	private String friday;
	
	@Column(name = "SATURDAY")
	private String saturday;
	
	@Column(name = "ALTERNATE_DAY")
	private String alternateDay;

	@Column(name = "SUBS_DELIVERY_DATE")
	private Date subsDeliveryDate;
	
	@Column(name = "OFFER_ID")
	private Long offerId;
	
	@Column(name = "INVOICE_CYCLE")
	private String invoiceCycle;
	
	@Column(name = "BILLING_CYCLE")
	private String billingCycle;
	
	
	@Column(name = "USER_ADDRESS_ID")
	private Long userAddressId;
	
	@Transient
	private String productName;
	
	@Transient
	private Date deliveryDate;

	@Transient
	private String delDate;
	
	@Transient
	private String productImage;
	
	@Transient
	private String productUom;
	
	@Transient
	private String userName;
	
	@Transient
	private String offerCode;
	
	@Transient
	private String subsDays;
	
	@Transient
	private Date vacationStartDate;
	
	@Transient
	private Date vacationEndDate;
	
	@Transient
	private Long vacationId;
	
	@Transient
	private String txnId;
	
	@Transient
	private String txnRef;
	
	@Transient
	private String responseCode;
	
	@Transient
	private String productDesc;
	
	@Transient private Double payAmount;
	
	@Transient private String payMode;
	
	@Transient private String txnStatus;
	
	@Transient private String payPartnerName;

	@Transient private String mobileNumber;
	
	@Transient private String customerAddress;

	@Transient private String showTime;
	
	@Transient private Long invoiceId;
	
	@Transient private Boolean isEditable;
	
	@Transient private Double walletBalance;
	
	@Transient private String imagePath;
	
	@Transient
	private List<LtCustomerSubsDeliveries> customerSubsDeliveries;  
	
	public Long getSubsId() {
		return subsId;
	}

	public void setSubsId(Long subsId) {
		this.subsId = subsId;
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

	public Double getCustomerRate() {
		return customerRate;
	}

	public void setCustomerRate(Double customerRate) {
		this.customerRate = customerRate;
	}

	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public String getSunday() {
		return sunday;
	}

	public void setSunday(String sunday) {
		this.sunday = sunday;
	}

	public String getMonday() {
		return monday;
	}

	public void setMonday(String monday) {
		this.monday = monday;
	}

	public String getTuesday() {
		return tuesday;
	}

	public void setTuesday(String tuesday) {
		this.tuesday = tuesday;
	}

	public String getWednesday() {
		return wednesday;
	}

	public void setWednesday(String wednesday) {
		this.wednesday = wednesday;
	}

	public String getThursday() {
		return thursday;
	}

	public void setThursday(String thursday) {
		this.thursday = thursday;
	}

	public String getFriday() {
		return friday;
	}

	public void setFriday(String friday) {
		this.friday = friday;
	}

	public String getSaturday() {
		return saturday;
	}

	public void setSaturday(String saturday) {
		this.saturday = saturday;
	}

	public Date getSubsDeliveryDate() {
		return subsDeliveryDate;
	}

	public void setSubsDeliveryDate(Date subsDeliveryDate) {
		this.subsDeliveryDate = subsDeliveryDate;
	}

	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	public String getInvoiceCycle() {
		return invoiceCycle;
	}

	public void setInvoiceCycle(String invoiceCycle) {
		this.invoiceCycle = invoiceCycle;
	}

	public String getProductName() {
		//return productName;
		return UtilsMaster.setCapitalizeFully(productName);
	}

	public void setProductName(String productName) {
		//this.productName = productName;
		this.productName = UtilsMaster.setCapitalizeFully(productName);
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
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

	public String getOfferCode() {
		return offerCode;
	}

	public void setOfferCode(String offerCode) {
		this.offerCode = offerCode;
	}

	public String getSubsDays() {
		return subsDays;
	}

	public void setSubsDays(String subsDays) {
		this.subsDays = subsDays;
	}

	public Date getVacationStartDate() {
		return vacationStartDate;
	}

	public void setVacationStartDate(Date vacationStartDate) {
		this.vacationStartDate = vacationStartDate;
	}

	public Date getVacationEndDate() {
		return vacationEndDate;
	}

	public void setVacationEndDate(Date vacationEndDate) {
		this.vacationEndDate = vacationEndDate;
	}

	public Long getVacationId() {
		return vacationId;
	}

	public void setVacationId(Long vacationId) {
		this.vacationId = vacationId;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getAlternateDay() {
		return alternateDay;
	}

	public void setAlternateDay(String alternateDay) {
		this.alternateDay = alternateDay;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getTxnRef() {
		return txnRef;
	}

	public void setTxnRef(String txnRef) {
		this.txnRef = txnRef;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getPayPartnerName() {
		return payPartnerName;
	}

	public void setPayPartnerName(String payPartnerName) {
		this.payPartnerName = payPartnerName;
	}

	public String getTxnStatus() {
		return txnStatus;
	}

	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public List<LtCustomerSubsDeliveries> getCustomerSubsDeliveries() {
		return customerSubsDeliveries;
	}

	public void setCustomerSubsDeliveries(List<LtCustomerSubsDeliveries> customerSubsDeliveries) {
		this.customerSubsDeliveries = customerSubsDeliveries;
	}

	public String getDelDate() {
		return delDate;
	}

	public void setDelDate(String delDate) {
		this.delDate = delDate;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Boolean getIsEditable() {
		return isEditable;
	}

	public void setIsEditable(Boolean isEditable) {
		this.isEditable = isEditable;
	}

	public Double getWalletBalance() {
		return walletBalance;
	}

	public void setWalletBalance(Double walletBalance) {
		this.walletBalance = walletBalance;
	}

	public String getBillingCycle() {
		return billingCycle;
	}

	public void setBillingCycle(String billingCycle) {
		this.billingCycle = billingCycle;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Long getUserAddressId() {
		return userAddressId;
	}

	public void setUserAddressId(Long userAddressId) {
		this.userAddressId = userAddressId;
	}

	@Override
	public String toString() {
		return "LtCustomerSubs [subsId=" + subsId + ", supplierId=" + supplierId + ", userId=" + userId + ", productId="
				+ productId + ", subsQuantity=" + subsQuantity + ", customerRate=" + customerRate + ", deliveryMode="
				+ deliveryMode + ", deliveryTime=" + deliveryTime + ", subscriptionType=" + subscriptionType
				+ ", sunday=" + sunday + ", monday=" + monday + ", tuesday=" + tuesday + ", wednesday=" + wednesday
				+ ", thursday=" + thursday + ", friday=" + friday + ", saturday=" + saturday + ", alternateDay="
				+ alternateDay + ", subsDeliveryDate=" + subsDeliveryDate + ", offerId=" + offerId + ", invoiceCycle="
				+ invoiceCycle + ", billingCycle=" + billingCycle + ", userAddressId=" + userAddressId
				+ ", productName=" + productName + ", deliveryDate=" + deliveryDate + ", delDate=" + delDate
				+ ", productImage=" + productImage + ", productUom=" + productUom + ", userName=" + userName
				+ ", offerCode=" + offerCode + ", subsDays=" + subsDays + ", vacationStartDate=" + vacationStartDate
				+ ", vacationEndDate=" + vacationEndDate + ", vacationId=" + vacationId + ", txnId=" + txnId
				+ ", txnRef=" + txnRef + ", responseCode=" + responseCode + ", productDesc=" + productDesc
				+ ", payAmount=" + payAmount + ", payMode=" + payMode + ", txnStatus=" + txnStatus + ", payPartnerName="
				+ payPartnerName + ", mobileNumber=" + mobileNumber + ", customerAddress=" + customerAddress
				+ ", showTime=" + showTime + ", invoiceId=" + invoiceId + ", isEditable=" + isEditable
				+ ", walletBalance=" + walletBalance + ", imagePath=" + imagePath + ", customerSubsDeliveries="
				+ customerSubsDeliveries + "]";
	}

	
	
	
}
