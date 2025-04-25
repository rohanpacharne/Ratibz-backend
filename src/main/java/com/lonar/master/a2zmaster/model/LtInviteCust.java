package com.lonar.master.a2zmaster.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class LtInviteCust extends BaseClass {
	
	
	private Long supplierId;

	private String inviteMessage;
	
	List<LtInviteCustDetails> ltInviteCustDetailsList;


	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getInviteMessage() {
		return inviteMessage;
	}

	public void setInviteMessage(String inviteMessage) {
		this.inviteMessage = inviteMessage;
	}

	public List<LtInviteCustDetails> getLtInviteCustDetailsList() {
		return ltInviteCustDetailsList;
	}

	public void setLtInviteCustDetailsList(List<LtInviteCustDetails> ltInviteCustDetailsList) {
		this.ltInviteCustDetailsList = ltInviteCustDetailsList;
	}
	
	
}
