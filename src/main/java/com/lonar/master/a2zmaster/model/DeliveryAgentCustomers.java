//package com.lonar.a2zcommons.model;
package com.lonar.master.a2zmaster.model;


import java.util.Date;
import java.util.List;

public class DeliveryAgentCustomers {
	public Long userId;
	public Long supplierId;
	public String deliveryAgentName;
	public String status;
	public Date startDate;
	public Date endDate;
	public Long lastUpdateLogin;
	public List<LtDeliveryAgentCustomers> customerList;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public String getDeliveryAgentName() {
		return deliveryAgentName;
	}
	public void setDeliveryAgentName(String deliveryAgentName) {
		this.deliveryAgentName = deliveryAgentName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Long getLastUpdateLogin() {
		return lastUpdateLogin;
	}
	public void setLastUpdateLogin(Long lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}
	public List<LtDeliveryAgentCustomers> getCustomerList() {
		return customerList;
	}
	public void setCustomerList(List<LtDeliveryAgentCustomers> customerList) {
		this.customerList = customerList;
	}
	@Override
	public String toString() {
		return "DeliveryAgentCustomers [userId=" + userId + ", supplierId=" + supplierId + ", deliveryAgentName="
				+ deliveryAgentName + ", status=" + status + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", lastUpdateLogin=" + lastUpdateLogin + ", customerList=" + customerList + "]";
	}
	
	
}
