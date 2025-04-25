/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lonar.master.a2zmaster.model;

import java.io.Serializable;

//import javax.persistence.Basic;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import javax.persistence.Transient;
import jakarta.persistence.*;
//import javax.validation.constraints.Size;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "LT_MAST_ROLE_MODULES")
@XmlRootElement

public class LtMastRoleModules extends BaseClass implements Serializable {
 
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ROLE_FUNC_ID")
	private Long roleFuncId;
	
	@Column(name = "ROLE_ID")
	private Long roleId;
	
	@Column(name = "MODULE_ID")
	private Long moduleId;
	
	@Size(max = 1)
	@Column(name ="CREATE_FLAG")
	private String createFlag;
	
	@Size(max = 1)
	@Column(name ="EDIT_FLAG")
	private String editFlag;
	
	@Size(max = 1)
	@Column(name = "READ_FLAG")
	private String readFlag;
	
	@Size(max = 1)
	@Column(name = "DELETE_FLAG")
	private String deleteFlag;
	
	@Size(max = 1)
	@Column(name = "UPDATE_FLAG")
	private String updateFlag;
	
	@Transient
	private String moduleName;

	@Transient
	private String moduleDescription;

	public Long getRoleFuncId() {
		return roleFuncId;
	}

	public void setRoleFuncId(Long roleFuncId) {
		this.roleFuncId = roleFuncId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getCreateFlag() {
		return createFlag;
	}

	public void setCreateFlag(String createFlag) {
		this.createFlag = createFlag;
	}

	public String getEditFlag() {
		return editFlag;
	}

	public void setEditFlag(String editFlag) {
		this.editFlag = editFlag;
	}

	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleDescription() {
		return moduleDescription;
	}

	public void setModuleDescription(String moduleDescription) {
		this.moduleDescription = moduleDescription;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
 
	
}
