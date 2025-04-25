package com.lonar.master.a2zmaster.model;


import java.util.Date;

public class RoleModule {

	private Long moduleId;
	private String moduleName;
	private String moduleUrl;
	private Long roleFuncId;
	private Date startDate;
	private Date endDate;
	private String create;
	private String edit;
	private String delete;
	private String read;

	public RoleModule() {

	}

	public RoleModule(Long moduleId, String moduleName, String moduleUrl,
			Long roleFuncId, Date startDate, Date endDate, String create,
			String edit, String delete, String read) {
		super();
		this.moduleId = moduleId;
		this.moduleName = moduleName;
		this.moduleUrl = moduleUrl;
		this.roleFuncId = roleFuncId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.create = create;
		this.edit = edit;
		this.delete = delete;
		this.read = read;
	}



	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleUrl() {
		return moduleUrl;
	}

	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl;
	}

	public Long getRoleFuncId() {
		return roleFuncId;
	}

	public void setRoleFuncId(Long roleFuncId) {
		this.roleFuncId = roleFuncId;
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

	


	public String getCreate() {
		return create;
	}


	public void setCreate(String create) {
		this.create = create;
	}



	public String getEdit() {
		return edit;
	}



	public void setEdit(String edit) {
		this.edit = edit;
	}



	public String getDelete() {
		return delete;
	}



	public void setDelete(String delete) {
		this.delete = delete;
	}



	public String getRead() {
		return read;
	}



	public void setRead(String read) {
		this.read = read;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((moduleId == null) ? 0 : moduleId.hashCode());
		result = prime * result + ((moduleName == null) ? 0 : moduleName.hashCode());
		result = prime * result + ((moduleUrl == null) ? 0 : moduleUrl.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoleModule other = (RoleModule) obj;
		if (moduleId == null) {
			if (other.moduleId != null)
				return false;
		} else if (!moduleId.equals(other.moduleId))
			return false;
		if (moduleName == null) {
			if (other.moduleName != null)
				return false;
		} else if (!moduleName.equals(other.moduleName))
			return false;
		if (moduleUrl == null) {
			if (other.moduleUrl != null)
				return false;
		} else if (!moduleUrl.equals(other.moduleUrl))
			return false;
		return true;
	}

}
