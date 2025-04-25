package com.lonar.master.a2zmaster.model;

public class ModuleList {
	private long roleFuncId;
	//private String makerRole;
	private long moduleId;

	public ModuleList(long roleFuncId,  long moduleId) {
		super();
		this.roleFuncId = roleFuncId;
		//this.makerRole = makerRole;
		this.moduleId = moduleId;
	}

	public ModuleList() {
		// TODO Auto-generated constructor stub
	}

	public long getRoleFuncId() {
		return roleFuncId;
	}

	public void setRoleFuncId(long roleFuncId) {
		this.roleFuncId = roleFuncId;
	}

	/*public String getMakerRole() {
		return makerRole;
	}

	public void setMakerRole(String makerRole) {
		this.makerRole = makerRole;
	}*/

	public long getModuleId() {
		return moduleId;
	}

	public void setModuleId(long moduleId) {
		this.moduleId = moduleId;
	}

	@Override
	public String toString() {
		return "ModuleList [roleFuncId=" + roleFuncId + ", moduleId=" + moduleId + "]";
	}

	

}
