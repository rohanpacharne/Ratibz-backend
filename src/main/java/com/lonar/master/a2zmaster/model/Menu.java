package com.lonar.master.a2zmaster.model;

public class Menu {

	private Long moduleId;
	private String moduleGroup;
	private String moduleName;
	private String moduleCode;
	private String moduleUrl;
	private String create;
	private String edit;
	private String read;
	private String delete;
	private String update;
	
	public Menu() {

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

	public String getModuleGroup() {
		return moduleGroup;
	}
	public void setModuleGroup(String moduleGroup) {
		this.moduleGroup = moduleGroup;
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


	public String getRead() {
		return read;
	}

	public void setRead(String read) {
		this.read = read;
	}

	public String getDelete() {
		return delete;
	}

	public void setDelete(String delete) {
		this.delete = delete;
	}
	
	
	public String getModuleCode() {
		return moduleCode;
	}


	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}







	public String getUpdate() {
		return update;
	}



	public void setUpdate(String update) {
		this.update = update;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((moduleId == null) ? 0 : moduleId.hashCode());
		result = prime * result + ((moduleName == null) ? 0 : moduleName.hashCode());
		result = prime * result + ((moduleUrl == null) ? 0 : moduleUrl.hashCode());
		result = prime * result + ((moduleCode == null) ? 0 : moduleCode.hashCode());
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
		Menu other = (Menu) obj;
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
		if (moduleCode == null) {
			if (other.moduleCode != null)
				return false;
		} else if (!moduleCode.equals(other.moduleCode))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "Menu [moduleId=" + moduleId + ", moduleGroup=" + moduleGroup + ", moduleName=" + moduleName
				+ ", moduleCode=" + moduleCode + ", moduleUrl=" + moduleUrl + ", create=" + create + ", edit=" + edit
				+ ", read=" + read + ", delete=" + delete + "]";
	}

	
}
