package com.lonar.master.a2zmaster.controller;

public class RefundResponseDto {

	private String orderId;
	private String refId;
	private String refundStatus;
	private String refundId;
	private String remark;
	private String txnId;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	public String getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}
	public String getRefundId() {
		return refundId;
	}
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	@Override
	public String toString() {
		return "RefundResponseDto [orderId=" + orderId + ", refId=" + refId + ", refundStatus=" + refundStatus
				+ ", refundId=" + refundId + ", remark=" + remark + ", txnId=" + txnId + "]";
	}
	
	
}
