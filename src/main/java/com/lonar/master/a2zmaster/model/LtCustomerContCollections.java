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

@Entity
@Table(name="LT_CUSTOMER_CONT_COLLECTIONS")
@JsonInclude(Include.NON_NULL)
public class LtCustomerContCollections extends BaseClass{

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "CONT_COLLECTION_ID")
	private Long contCollectionId;
	
	@Column(name = "SUPPLIER_ID")
	private Long supplierId;
	
	@Column(name = "COLLECTION_DATE")
	private Date collectionDate;
	
	@Column(name = "SUBS_ID")
	private Long subsId;
	
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "PRODUCT_ID")
	private Long productId;
	
	@Column(name = "COLLECTED_QUANTITY")
	private Long collectedQuantity;
	
	@Column(name = "DELIVERY_QUANTITY")
	private Double deliveryQuantity;
	
	@Column(name = "DELIVERY_ID")
	private Long deliveryId;
	
	//@Transient
	//private Double deliveredQuantity;
		
	@Transient
	private Double productRate;
	
	@Transient
	private String searchParameter;
	
	@Transient
	private String deliveryMode;
	
	@Transient
	private Integer start;

	@Transient
	private Integer length;
	
	public Long getContCollectionId() {
		return contCollectionId;
	}

	public void setContCollectionId(Long contCollectionId) {
		this.contCollectionId = contCollectionId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Date getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(Date collectionDate) {
		this.collectionDate = collectionDate;
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

	public Long getCollectedQuantity() {
		return collectedQuantity;
	}

	public void setCollectedQuantity(Long collectedQuantity) {
		this.collectedQuantity = collectedQuantity;
	}

	public String getSearchParameter() {
		return searchParameter;
	}

	public void setSearchParameter(String searchParameter) {
		this.searchParameter = searchParameter;
	}

	/*public Double getDeliveredQuantity() {
		return deliveredQuantity;
	}

	public void setDeliveredQuantity(Double deliveredQuantity) {
		this.deliveredQuantity = deliveredQuantity;
	}*/

	public Double getProductRate() {
		return productRate;
	}

	public void setProductRate(Double productRate) {
		this.productRate = productRate;
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

	public Long getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(Long deliveryId) {
		this.deliveryId = deliveryId;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}
	
}
