package com.lonar.master.a2zmaster.dashboard;

public class InvoicePayments {

	String month;
	Double invAmount;
	Double payAmount;
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public Double getInvAmount() {
		return invAmount;
	}
	public void setInvAmount(Double invAmount) {
		this.invAmount = invAmount;
	}
	public Double getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	@Override
	public String toString() {
		return "InvoicePayments [month=" + month + ", invAmount=" + invAmount + ", payAmount=" + payAmount + "]";
	}
	
	
}
