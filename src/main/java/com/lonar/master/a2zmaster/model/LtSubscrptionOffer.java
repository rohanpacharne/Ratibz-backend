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
@Table(name="LT_SUBSCRIPTION_OFFER") 
@JsonInclude(Include.NON_NULL)
public class LtSubscrptionOffer extends BaseClass{

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "OFFER_ID")
	private Long offerId;
	
	@Column(name = "SUPPLIER_ID")
	private Long supplierId;
	
	@Column(name = "OFFER_CODE")
	private String offerCode;
	
	@Column(name = "OFFER_DETAILS")
	private String offerDetails;
	
	@Column(name = "PRODUCT_ID")
	private Long productId;      
	
	@Column(name = "OFFER_IMAGE")
	private String offerImage;
	
	@Column(name = "OFFER_TYPE")
	private String offerType;
	
	@Column(name = "OFFER_VALUE")
	private Double offerValue;

	@Transient
	private String offerImagePath;

	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getOfferCode() {
		return offerCode;
	}

	public void setOfferCode(String offerCode) {
		this.offerCode = offerCode;
	}

	public String getOfferDetails() {
		return offerDetails;
	}

	public void setOfferDetails(String offerDetails) {
		this.offerDetails = UtilsMaster.setCapitalizeFully(offerDetails);
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getOfferImage() {
		return offerImage;
	}

	public void setOfferImage(String offerImage) {
		this.offerImage = offerImage;
	}

	public String getOfferType() {
		return offerType;
	}

	public void setOfferType(String offerType) {
		this.offerType = offerType;
	}

	public Double getOfferValue() {
		return offerValue;
	}

	public void setOfferValue(Double offerValue) {
		this.offerValue = offerValue;
	}

	public String getOfferImagePath() {
		return offerImagePath;
	}

	public void setOfferImagePath(String offerImagePath) {
		this.offerImagePath = offerImagePath;
	}

	@Override
	public String toString() {
		return "LtSubscrptionOffer [offerId=" + offerId + ", supplierId=" + supplierId + ", offerCode=" + offerCode
				+ ", offerDetails=" + offerDetails + ", productId=" + productId + ", offerImage=" + offerImage
				+ ", offerType=" + offerType + ", offerValue=" + offerValue + ", offerImagePath=" + offerImagePath
				+ "]";
	}
	
	
}
