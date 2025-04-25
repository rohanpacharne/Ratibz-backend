
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lonar.master.a2zmaster.model;

import java.io.Serializable;
import java.util.Date;

//import javax.persistence.Basic;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "LT_MAST_PASSWORDS")
public class LtMastPasswords implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comnMasterValues_seq")
	//@SequenceGenerator(name = "comnMasterValues_seq", sequenceName = "lt_mast_comn_master_values_s", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "COMN_MASTER_VALUES_ID")
	private Long comnMasterValuesId;
	@Basic(optional = false)
	// @GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "password_seq")
	//@SequenceGenerator(name = "password_seq", sequenceName = "LT_MAST_PASSWORDS_S", allocationSize = 1)
	@Column(name = "PASSWORDID")
	private Long passwordid;
	@Basic(optional = false)

	@Column(name = "USER_ID")
	private long userId;

	@Column(name = "CREATED_BY")
	private long createdBy;
	@Basic(optional = false)

	@Column(name = "CREATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	@Size(max = 240)
	@Column(name = "PASSWORD")
	private String password;
	@Basic(optional = false)
	@NotNull
	@Column(name = "PWD_SEQUENCE")
	private Long pwdSequence;

	public LtMastPasswords() {
	}

	public LtMastPasswords(Long passwordid) {
		this.passwordid = passwordid;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getPasswordid() {
		return passwordid;
	}

	public void setPasswordid(Long passwordid) {
		this.passwordid = passwordid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getPwdSequence() {
		return pwdSequence;
	}

	public void setPwdSequence(Long pwdSequence) {
		this.pwdSequence = pwdSequence;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (passwordid != null ? passwordid.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof LtMastPasswords)) {
			return false;
		}
		LtMastPasswords other = (LtMastPasswords) object;
		if ((this.passwordid == null && other.passwordid != null)
				|| (this.passwordid != null && !this.passwordid.equals(other.passwordid))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "LtMastPasswords [passwordid=" + passwordid + ", userId=" + userId + ", createdBy=" + createdBy
				+ ", creationDate=" + creationDate + ", password=" + password + ", pwdSequence=" + pwdSequence + "]";
	}

	
}