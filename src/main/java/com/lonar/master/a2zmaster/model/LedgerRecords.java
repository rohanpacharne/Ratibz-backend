package com.lonar.master.a2zmaster.model;

import java.util.Date;

public class LedgerRecords {
	
	private String transactionType;
	private Date transactionDate;
	private String transactionNumber;
	private Double invoiceAmount;
	private Double paymentAmount;
	
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getTransactionNumber() {
		return transactionNumber;
	}
	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
	public Double getInvoiceAmount() {
		return invoiceAmount;
	}
	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	public Double getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	@Override
	public String toString() {
		return "Ledger [transactionType=" + transactionType + ", transactionDate=" + transactionDate
				+ ", transactionNumber=" + transactionNumber + ", invoiceAmount=" + invoiceAmount + ", paymentAmount="
				+ paymentAmount + "]";
	}

	
}
