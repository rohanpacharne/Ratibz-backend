package com.lonar.master.a2zmaster.model;

//import javax.persistence.Basic;
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
@Table(name = "LT_MAST_COMMON_MESSAGES")
@JsonInclude(Include.NON_NULL)
public class LtMastCommonMessage extends BaseClass {

	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "MESSAGE_ID")
	private Long messageId;

	@Basic(optional = false)
	@Column(name = "MESSAGE_CODE")
	private String messageCode;

	@Column(name = "MESSAGE_NAME")
	private String messageName;

	@Column(name = "MESSAGE_DESC")
	private String messageDesc;

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public String getMessageName() {
		return messageName;
	}

	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}

	public String getMessageDesc() {
		return messageDesc;
	}

	public void setMessageDesc(String messageDesc) {
		this.messageDesc = messageDesc;
	}

	@Override
	public String toString() {
		return "LtMastCommonMessage [messageId=" + messageId + ", messageCode=" + messageCode + ", messageName="
				+ messageName + ", messageDesc=" + messageDesc + "]";
	}


}
