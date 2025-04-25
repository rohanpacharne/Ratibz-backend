package com.lonar.master.a2zmaster.model;

import java.util.Date;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name="LT_UPI_RESPONSE")
@JsonInclude(Include.NON_NULL)
public class LtUpiResponse {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "UPI_RESPONSE_ID")
	private Long upiResponseId;
	
	@Column(name = "CREATION_DATE")
	private Date creationDate;
	
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "UPI_RESPONSE")
	private String upiResponse;

	public Long getUpiResponseId() {
		return upiResponseId;
	}

	public void setUpiResponseId(Long upiResponseId) {
		this.upiResponseId = upiResponseId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUpiResponse() {
		return upiResponse;
	}

	public void setUpiResponse(String upiResponse) {
		this.upiResponse = upiResponse;
	}
	
	
}
	
