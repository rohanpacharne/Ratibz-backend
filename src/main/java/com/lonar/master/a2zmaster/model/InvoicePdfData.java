package com.lonar.master.a2zmaster.model;

import java.util.List;

public class InvoicePdfData {
	private Long invoiceId;
	private Long supplierId;
    private String supplierName;
    private String supplierAddress; 
    private String mobileNumber; 
    private String customerName;
    private String customerAddress;
    private String  gstNumber;
    private String  invoiceDate;
	private String invoiceNumber;
	private String dueDate;
	private String poNumber;
	private String dispatchThrough;
	private String fromDeliveryDate;
	private String toDeliveryDate;
	private Double discountAmount;
	private Double finalAmount;
	private Double invoiceAmount;
	private String amountInWords;
	private Double totalQty;
	private Double totalLineAmt;
	
	List<InvoiceLinesData> invoiceLines;

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierAddress() {
		return supplierAddress;
	}

	public void setSupplierAddress(String supplierAddress) {
		this.supplierAddress = supplierAddress;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getGstNumber() {
		return gstNumber;
	}

	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public String getDispatchThrough() {
		return dispatchThrough;
	}

	public void setDispatchThrough(String dispatchThrough) {
		this.dispatchThrough = dispatchThrough;
	}

	public String getFromDeliveryDate() {
		return fromDeliveryDate;
	}

	public void setFromDeliveryDate(String fromDeliveryDate) {
		this.fromDeliveryDate = fromDeliveryDate;
	}

	public String getToDeliveryDate() {
		return toDeliveryDate;
	}

	public void setToDeliveryDate(String toDeliveryDate) {
		this.toDeliveryDate = toDeliveryDate;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Double getFinalAmount() {
		return finalAmount;
	}

	public void setFinalAmount(Double finalAmount) {
		this.finalAmount = finalAmount;
	}

	public Double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public String getAmountInWords() {
		return amountInWords;
	}

	public void setAmountInWords(String amountInWords) {
		this.amountInWords = amountInWords;
	}

	public Double getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(Double totalQty) {
		this.totalQty = totalQty;
	}

	public Double getTotalLineAmt() {
		return totalLineAmt;
	}

	public void setTotalLineAmt(Double totalLineAmt) {
		this.totalLineAmt = totalLineAmt;
	}

	public List<InvoiceLinesData> getInvoiceLines() {
		return invoiceLines;
	}

	public void setInvoiceLines(List<InvoiceLinesData> invoiceLines) {
		this.invoiceLines = invoiceLines;
	}

	@Override
	public String toString() {
		return "InvoicePdfData [invoiceId=" + invoiceId + ", supplierId=" + supplierId + ", supplierName="
				+ supplierName + ", supplierAddress=" + supplierAddress + ", mobileNumber=" + mobileNumber
				+ ", customerName=" + customerName + ", customerAddress=" + customerAddress + ", gstNumber=" + gstNumber
				+ ", invoiceDate=" + invoiceDate + ", invoiceNumber=" + invoiceNumber + ", dueDate=" + dueDate
				+ ", poNumber=" + poNumber + ", dispatchThrough=" + dispatchThrough + ", fromDeliveryDate="
				+ fromDeliveryDate + ", toDeliveryDate=" + toDeliveryDate + ", discountAmount=" + discountAmount
				+ ", finalAmount=" + finalAmount + ", invoiceAmount=" + invoiceAmount + ", amountInWords="
				+ amountInWords + ", totalQty=" + totalQty + ", totalLineAmt=" + totalLineAmt + ", invoiceLines="
				+ invoiceLines + "]";
	}

	
}
