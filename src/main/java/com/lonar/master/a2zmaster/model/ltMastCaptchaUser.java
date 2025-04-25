package com.lonar.master.a2zmaster.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "lt_mast_captcha_user")
public class ltMastCaptchaUser implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "captcha_user_id")
	private Long captchaUserId;
	
	@Column(name = "supplier_id")
	private Long supplierId;
	
	@Column(name = "captcha_id")
	private Long captchaId;
	
	@Column(name = "mobile_number")
	private String mobileNumber;

	public Long getCaptchaUserId() {
		return captchaUserId;
	}

	public void setCaptchaUserId(Long captchaUserId) {
		this.captchaUserId = captchaUserId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Long getCaptchaId() {
		return captchaId;
	}

	public void setCaptchaId(Long captchaId) {
		this.captchaId = captchaId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}


}
