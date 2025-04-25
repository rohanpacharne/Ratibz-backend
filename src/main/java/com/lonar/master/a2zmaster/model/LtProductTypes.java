package com.lonar.master.a2zmaster.model;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;

import jakarta.persistence.*;

import org.apache.commons.text.WordUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name="LT_PRODUCT_TYPES")
@JsonInclude(Include.NON_NULL)
public class LtProductTypes extends BaseClass{

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "PRODUCT_TYPE_ID")
	private Long productTypeId;
	
	@Column(name = "SUPPLIER_ID")
	private Long supplierId;
	
	@Column(name = "PRODUCT_TYPE")
	private String productType;

	public Long getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Long productTypeId) {
		this.productTypeId = productTypeId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getProductType() {
		return  WordUtils.capitalizeFully(productType);
	}

	public void setProductType(String productType) {
		this.productType =  WordUtils.capitalizeFully(productType);
	}

	@Override
	public String toString() {
		return "LtProductTypes [productTypeId=" + productTypeId + ", supplierId=" + supplierId + ", productType="
				+ productType + "]";
	}
	
}
