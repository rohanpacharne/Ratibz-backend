package com.lonar.master.a2zmaster.service;

import java.util.List;

import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtMastModules;
import com.lonar.master.a2zmaster.model.Status;



public interface LtMastModulesService {

	public Status save(LtMastModules ltMastModules) throws ServiceException;
	
	public Status update(LtMastModules ltMastModules) throws ServiceException;
	
	CustomeDataTable getDataTable(LtMastModules input, Long supplierId) throws ServiceException;

	public LtMastModules getLtMastModulesByID(Long moduleId) throws ServiceException;

	public List<LtMastModules> getActiveLikeName(String moduleName, Long supplierId) throws ServiceException;

	public Status delete(Long moduleId) throws ServiceException;

}
