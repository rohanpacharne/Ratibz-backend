package com.lonar.master.a2zmaster.service;

import java.util.List;

import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtMastRoleModules;
import com.lonar.master.a2zmaster.model.LtMastRoles;
import com.lonar.master.a2zmaster.model.Status;



public interface LtMastRolesService {

	CustomeDataTable getDataTable(LtMastRoles input, Long supplierId) throws ServiceException;

	Status save(LtMastRoles ltMastRoles) throws ServiceException;

	Status update(LtMastRoles ltMastRoles) throws ServiceException;

	LtMastRoles getRolesById(Long roleId) throws ServiceException;

	List<LtMastRoles> getAllActiveRole(Long supplierId) throws ServiceException;

	Status saveLtMastRoleModules(LtMastRoleModules ltMastRoleModule) throws ServiceException;

	Status delete(Long roleId) throws ServiceException;

	List<LtMastRoleModules> getRoleModule(Long roleId) throws ServiceException;

	LtMastRoleModules getRoleModuleById(Long roleFuncId) throws ServiceException;

	Status deleteRoleModule(Long roleFuncId) throws ServiceException;

}
