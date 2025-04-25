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
import com.lonar.master.a2zmaster.utils.UtilsMaster;


@Entity
@Table(name = "lt_about_us")
@JsonInclude(Include.NON_NULL)
public class LtAboutUs {

		@Id
		@GeneratedValue(strategy= GenerationType.IDENTITY)
		@Column(name = "about_us_id")
		private Long aboutUsId;
		
		@Column(name="cutomer_Support_Contact")
		private String cutomerSupportContact;
		
		@Column(name="supplier_support_contact")
		private String supplierSupportContact;
		
		@Column(name="cutomer_support_email")
		private String cutomerSupportEmail;
		
		@Column(name="supplier_support_Email")
		private String supplierSupportEmail;
		
		@Column(name = "supplier_id")
		private Long supplierId;
		
		@Column(name="about_us")
		private String aboutUs;
		
		@Column(name = "CREATED_BY")
		private Long createdBy;
		
		@Column(name = "CREATION_DATE")
		private Date creationDate;

		@Column(name = "LAST_UPDATED_BY")
		private Long lastUpdatedBy;
		
		@Column(name = "last_update_date")
		private Date lastUpdateDate;

		@Column(name = "LAST_UPDATE_LOGIN")
		private Long lastUpdateLogin;
		
		@Column(name = "app_version")
		private String appVersion;
		
		public Long getAboutUsId() {
			return aboutUsId;
		}

		public void setAboutUsId(Long aboutUsId) {
			this.aboutUsId = aboutUsId;
		}

		public String getCutomerSupportContact() {
			return cutomerSupportContact;
		}

		public void setCutomerSupportContact(String cutomerSupportContact) {
			this.cutomerSupportContact = cutomerSupportContact;
		}

		public String getSupplierSupportContact() {
			return supplierSupportContact;
		}

		public void setSupplierSupportContact(String supplierSupportContact) {
			this.supplierSupportContact = supplierSupportContact;
		}

		public String getCutomerSupportEmail() {
			return cutomerSupportEmail;
		}

		public void setCutomerSupportEmail(String cutomerSupportEmail) {
			this.cutomerSupportEmail = cutomerSupportEmail;
		}

		public String getSupplierSupportEmail() {
			return supplierSupportEmail;
		}

		public void setSupplierSupportEmail(String supplierSupportEmail) {
			this.supplierSupportEmail = supplierSupportEmail;
		}

		public Long getSupplierId() {
			return supplierId;
		}

		public void setSupplierId(Long supplierId) {
			this.supplierId = supplierId;
		}

		public String getAboutUs() {
			return aboutUs;
		}

		public void setAboutUs(String aboutUs) {
			this.aboutUs = UtilsMaster.capitalizeSentence( aboutUs);
		}

		public Long getCreatedBy() {
			return createdBy;
		}

		public void setCreatedBy(Long createdBy) {
			this.createdBy = createdBy;
		}

		public Date getCreationDate() {
			return creationDate;
		}

		public void setCreationDate(Date creationDate) {
			this.creationDate = creationDate;
		}

		public Long getLastUpdatedBy() {
			return lastUpdatedBy;
		}

		public void setLastUpdatedBy(Long lastUpdatedBy) {
			this.lastUpdatedBy = lastUpdatedBy;
		}

		public Date getLastUpdateDate() {
			return lastUpdateDate;
		}

		public void setLastUpdateDate(Date lastUpdateDate) {
			this.lastUpdateDate = lastUpdateDate;
		}

		public Long getLastUpdateLogin() {
			return lastUpdateLogin;
		}

		public void setLastUpdateLogin(Long lastUpdateLogin) {
			this.lastUpdateLogin = lastUpdateLogin;
		}

		public String getAppVersion() {
			return appVersion;
		}

		public void setAppVersion(String appVersion) {
			this.appVersion = appVersion;
		}

}
