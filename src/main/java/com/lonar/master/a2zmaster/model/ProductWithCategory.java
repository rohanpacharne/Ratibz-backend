package com.lonar.master.a2zmaster.model;

import java.util.List;

public class ProductWithCategory {
	
	String productCategory;
	List<LtProducts> productList;
	public String getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	public List<LtProducts> getProductList() {
		return productList;
	}
	public void setProductList(List<LtProducts> productList) {
		this.productList = productList;
	}
	@Override
	public String toString() {
		return "ProductWithCategory [productCategory=" + productCategory + ", productList=" + productList + "]";
	}
	
	

}
