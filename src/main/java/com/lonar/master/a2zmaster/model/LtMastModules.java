 
package com.lonar.master.a2zmaster.model;

import java.io.Serializable;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
//import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "LT_MAST_MODULES")
@XmlRootElement
public class LtMastModules extends BaseClass implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "MODULE_ID")
	private Long moduleId;
	
	@Column(name ="MODULE_CODE")
	private String moduleCode;
	
	@Column(name = "MODULE_NAME")
	private String moduleName;
	
	@Column(name = "MODULE_DESC")
	private String moduleDesc;
	
	@Column(name = "MODULE_URL")
	private String moduleUrl;
	
	@Column(name = "MODULE_GROUP")
	private String moduleGroup;

	@Column(name = "SEQUENCE_NUMBER")
	private Long sequenceNumber;
	
	@Column(name = "SUPPLIER_ID")
	private Long supplierId;
	
	@Transient
	private String statusValue;
	
	@Transient
	private String moduleGroupValue;

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleDesc() {
		return moduleDesc;
	}

	public void setModuleDesc(String moduleDesc) {
		this.moduleDesc = moduleDesc;
	}

	public String getModuleUrl() {
		return moduleUrl;
	}

	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl;
	}

	public String getModuleGroup() {
		return moduleGroup;
	}

	public void setModuleGroup(String moduleGroup) {
		this.moduleGroup = moduleGroup;
	}

	public Long getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public String getModuleGroupValue() {
		return moduleGroupValue;
	}

	public void setModuleGroupValue(String moduleGroupValue) {
		this.moduleGroupValue = moduleGroupValue;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "LtMastModules [moduleId=" + moduleId + ", moduleCode=" + moduleCode + ", moduleName=" + moduleName
				+ ", moduleDesc=" + moduleDesc + ", moduleUrl=" + moduleUrl + ", moduleGroup=" + moduleGroup
				+ ", sequenceNumber=" + sequenceNumber + ", supplierId=" + supplierId + ", statusValue=" + statusValue
				+ ", moduleGroupValue=" + moduleGroupValue + "]";
	}

	
}
