package com.lonar.master.a2zmaster.model;

import java.util.List;

public class RoleWithModule {
	private  LtMastRoles ltMastRoles;
	private  List<LtMastRoleModules> ltMastRoleModules;

	public RoleWithModule() {
	}

	
	public RoleWithModule(LtMastRoles ltMastRoles, List<LtMastRoleModules> ltMastRoleModules) {
		super();
		this.ltMastRoles = ltMastRoles;
		this.ltMastRoleModules = ltMastRoleModules;
	}


	public LtMastRoles getLtMastRoles() {
		return ltMastRoles;
	}

	public void setLtMastRoles(LtMastRoles ltMastRoles) {
		this.ltMastRoles = ltMastRoles;
	}

	public List<LtMastRoleModules> getLtMastRoleModules() {
		return ltMastRoleModules;
	}

	public void setLtMastRoleModules(List<LtMastRoleModules> ltMastRoleModules) {
		this.ltMastRoleModules = ltMastRoleModules;
	}

	@Override
	public String toString() {
		return "RoleWithModule [ltMastRoles=" + ltMastRoles + ", ltMastRoleModules=" + ltMastRoleModules + "]";
	}

	
}
