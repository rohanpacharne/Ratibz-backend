package com.lonar.master.a2zmaster.dao;

import java.util.List;

import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtMastModules;



public interface LtMastModulesDao {
	public List<LtMastModules> findByModuleName(String moduleName, Long supplierId) throws ServiceException;
	public List<LtMastModules> findByModuleUrl(String moduleUrl, Long supplierId) throws ServiceException;
	
	public List<LtMastModules> findByModuleCode(String moduleCode, Long supplierId) throws ServiceException;
	public List<LtMastModules> findByModuleSequence(Long sequenceNumber, Long supplierId) throws ServiceException;
	
	public Long getCount(LtMastModules input, Long supplierId);
	public List<LtMastModules> getModuleData(LtMastModules input, Long supplierId);
	
	public List<LtMastModules> getLikeModuleNameAndUser(Long userId, String moduleName) throws ServiceException;
	//public List<LtMastModules> findAll(Long companyId) throws ServiceException;
	public LtMastModules getLtMastModulesByID(Long moduleId) throws ServiceException;
	public List<LtMastModules> getActiveLikeName(String moduleName, Long supplierId) throws ServiceException;
}
