package com.lonar.master.a2zmaster.model;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import javax.persistence.Transient;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lonar.master.a2zmaster.utils.UtilsMaster;
//import com.lonar.a2zcommons.util.UtilsMaster;

@Entity
@Table(name="LT_PRODUCTS")
@JsonInclude(Include.NON_NULL)
public class LtProducts extends BaseClass{

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "PRODUCT_ID")
	private Long productId;
	
	@Column(name = "SUPPLIER_ID")
	private Long supplierId;
	
	@Column(name = "PRODUCT_TYPE_ID")
	private Long productTypeId;
	
	@Column(name = "CATEGORY_ID")
	private Long categoryId;
	
	@Column(name = "PRODUCT_CODE")
	private String productCode;
	
	@Column(name = "PRODUCT_NAME")
	private String productName;
	
	@Column(name = "PRODUCT_RATE")
	private Double productRate;
	
	@Column(name = "PRODUCT_UOM")
	private String productUom;
	
	@Column(name = "PRODUCT_IMAGE")
	private String productImage;
	
	@Column(name = "HSN_CODE")
	private String hsnCode;
	
	@Column(name = "COLLECT_CONTAINER")
	private String collectContainer;

	@Column(name = "COMMODITY")
	private String commodity;
	
	@Column(name = "DISPLAY_FLAG")
	private String displayFlag;
	
	@Column(name = "PRODUCT_DESC")
	private String productDesc;
	
	@Transient
	private String productType;
	
	@Transient
	private String productCategory;
	
	@Transient
	private Long subId;
	
	@Transient
	private String imagePath;

	@Transient
	private String deliveryMode;
	
	@Transient
	private Double quantity;
	
	public LtProducts() {
		super();
	}
	
	

	@JsonCreator
    public LtProducts(
        @JsonProperty("supplierId") Long supplierId,
        @JsonProperty("productTypeId") Long productTypeId,
        @JsonProperty("categoryId") Long categoryId,
        @JsonProperty("productCode") String productCode,
        @JsonProperty("productName") String productName,
        @JsonProperty("productRate") Double productRate,
        @JsonProperty("hsnCode") String hsnCode,
        @JsonProperty("productUom") String productUom,
        @JsonProperty("collectContainer") String collectContainer,
        @JsonProperty("commodity") String commodity,
//        @JsonProperty("createdBy") Long createdBy,
//        @JsonProperty("lastUpdateLogin") Long lastUpdateLogin,
        @JsonProperty("displayFlag") String displayFlag,
        @JsonProperty("productDesc") String productDesc
    ) {
        this.supplierId = supplierId;
        this.productTypeId = productTypeId;
        this.categoryId = categoryId;
        this.productCode = productCode;
        this.productName = productName;
        this.productRate = productRate;
        this.hsnCode = hsnCode;
        this.productUom = productUom;
        this.collectContainer = collectContainer;
        this.commodity = commodity;
        this.displayFlag = displayFlag;
        this.productDesc = productDesc;
        
    }


	
	
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Long getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Long productTypeId) {
		this.productTypeId = productTypeId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {		
		return  UtilsMaster.setCapitalizeFully(productName);
	}

	public void setProductName(String productName) {		
		this.productName = UtilsMaster.setCapitalizeFully(productName);		
		//this.productName =(productName);
	}

	public Double getProductRate() {
		return productRate;
	}

	public void setProductRate(Double productRate) {
		this.productRate = productRate;
	}

	public String getProductUom() {
		return productUom;
	}

	public void setProductUom(String productUom) {
		this.productUom =UtilsMaster.setCapitalizeFully( productUom);
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public String getHsnCode() {
		return hsnCode;
	}

	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}

	public String getCollectContainer() {
		return collectContainer;
	}

	public void setCollectContainer(String collectContainer) {
		this.collectContainer = collectContainer;
	}

	public String getCommodity() {
		return commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getDisplayFlag() {
		return displayFlag;
	}

	public void setDisplayFlag(String displayFlag) {
		this.displayFlag = displayFlag;
	}
	
	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public Long getSubId() {
		return subId;
	}

	public void setSubId(Long subId) {
		this.subId = subId;
	}

	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "LtProducts [productId=" + productId + ", supplierId=" + supplierId + ", productTypeId=" + productTypeId
				+ ", categoryId=" + categoryId + ", productCode=" + productCode + ", productName=" + productName
				+ ", productRate=" + productRate + ", productUom=" + productUom + ", productImage=" + productImage
				+ ", hsnCode=" + hsnCode + ", collectContainer=" + collectContainer + ", commodity=" + commodity
				+ ", displayFlag=" + displayFlag + ", productDesc=" + productDesc + ", productType=" + productType
				+ ", productCategory=" + productCategory + ", subId=" + subId + ", imagePath=" + imagePath
				+ ", deliveryMode=" + deliveryMode + ", quantity=" + quantity + "]";
	}
	
}
