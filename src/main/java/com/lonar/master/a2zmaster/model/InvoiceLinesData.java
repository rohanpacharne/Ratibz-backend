package com.lonar.master.a2zmaster.model;

import com.lonar.master.a2zmaster.utils.UtilsMaster;

//import com.lonar.a2zcommons.util.UtilsMaster;

public class InvoiceLinesData {

	private int srNo;
	private String deliveryDate;
	private String productName;
	private String uom;
	private Double deliveryQuantity;
	private Double invoiceRate;
	private Double lineAmount;
	
	public int getSrNo() {
		return srNo;
	}
	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getProductName() {
		//return productName;
		return UtilsMaster.setCapitalizeFully(productName);
	}
	public void setProductName(String productName) {
		this.productName = UtilsMaster.setCapitalizeFully(productName);
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public Double getDeliveryQuantity() {
		return deliveryQuantity;
	}
	public void setDeliveryQuantity(Double deliveryQuantity) {
		this.deliveryQuantity = deliveryQuantity;
	}
	public Double getInvoiceRate() {
		return invoiceRate;
	}
	public void setInvoiceRate(Double invoiceRate) {
		this.invoiceRate = invoiceRate;
	}
	public Double getLineAmount() {
		return lineAmount;
	}
	public void setLineAmount(Double lineAmount) {
		this.lineAmount = lineAmount;
	}
	@Override
	public String toString() {
		return "InvoiceLinesData [srNo=" + srNo + ", deliveryDate=" + deliveryDate + ", productName=" + productName
				+ ", uom=" + uom + ", deliveryQuantity=" + deliveryQuantity + ", invoiceRate=" + invoiceRate
				+ ", lineAmount=" + lineAmount + "]";
	}
	
	
}
