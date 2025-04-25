package com.lonar.master.a2zmaster.dao;

import java.util.List;

import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtMastRoleModules;



public interface LtMastRoleModulesDao {

	boolean checkForDuplicate(LtMastRoleModules ltMastRoleModule) throws ServiceException;

	LtMastRoleModules saveLtMastRoleModules(LtMastRoleModules ltMastRoleModule) throws ServiceException;

	List<LtMastRoleModules> getRoleModule(Long roleId) throws ServiceException;

	LtMastRoleModules getRoleModuleById(Long roleFuncId) throws ServiceException;

	LtMastRoleModules deleteRoleModule(Long roleFuncId) throws ServiceException;
	
}
