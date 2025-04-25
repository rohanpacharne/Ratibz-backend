package com.lonar.master.a2zmaster.model;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import javax.persistence.Transient;

import jakarta.persistence.*;

import org.apache.commons.text.WordUtils;

@Entity
@Table(name="LT_PRODUCT_CATEGORIES")
public class LtProductCategories extends BaseClass {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "CATEGORY_ID")
	private Long categoryId;
	
	@Column(name = "SUPPLIER_ID")
	private Long supplierId;
	
	@Column(name = "PRODUCT_TYPE_ID")
	private Long productTypeId;
	
	@Column(name = "CATEGORY_NAME")
	private String categoryName;

	@Transient
	private String productType;
	
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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

	public String getCategoryName() {
		return  WordUtils.capitalizeFully(categoryName);
	}

	public void setCategoryName(String categoryName) {
		this.categoryName =  WordUtils.capitalizeFully(categoryName);
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = (productType);
	}

	@Override
	public String toString() {
		return "LtProductCategories [categoryId=" + categoryId + ", supplierId=" + supplierId + ", productTypeId="
				+ productTypeId + ", categoryName=" + categoryName + ", productType=" + productType + "]";
	}

	
	
	
}
