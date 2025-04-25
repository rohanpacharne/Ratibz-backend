package com.lonar.master.a2zmaster.model;

import java.util.List;

public class Ledger {
	private Double invoiceTotal;
	private Double paymentTotal;
	private Double outstanding;
	
	private List<LedgerRecords> ledgerRecordsList;

	public Double getInvoiceTotal() {
		return invoiceTotal;
	}

	public void setInvoiceTotal(Double invoiceTotal) {
		this.invoiceTotal = invoiceTotal;
	}

	public Double getPaymentTotal() {
		return paymentTotal;
	}

	public void setPaymentTotal(Double paymentTotal) {
		this.paymentTotal = paymentTotal;
	}

	public Double getOutstanding() {
		return outstanding;
	}

	public void setOutstanding(Double outstanding) {
		this.outstanding = outstanding;
	}

	public List<LedgerRecords> getLedgerRecordsList() {
		return ledgerRecordsList;
	}

	public void setLedgerRecordsList(List<LedgerRecords> ledgerRecordsList) {
		this.ledgerRecordsList = ledgerRecordsList;
	}

	@Override
	public String toString() {
		return "Ledger [invoiceTotal=" + invoiceTotal + ", paymentTotal=" + paymentTotal + ", outstanding="
				+ outstanding + ", ledgerRecordsList=" + ledgerRecordsList + "]";
	}
	
	
	
	

}
