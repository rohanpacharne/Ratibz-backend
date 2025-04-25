package com.lonar.master.a2zmaster.dao;

import java.util.List;

import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtMastRoles;



public interface LtMastRolesDao {

	Long getLtMastRolesCount(LtMastRoles input, Long supplierId) throws ServiceException;

	List<LtMastRoles> getLtMastRolesDataTable(LtMastRoles input, Long supplierId) throws ServiceException;

	List<LtMastRoles> findByRole(String roleName, Long supplierId) throws ServiceException;

	LtMastRoles save(LtMastRoles ltMastRoles) throws ServiceException;

	LtMastRoles getRolesById(Long roleId) throws ServiceException;

	List<LtMastRoles> getAllActiveRole(Long supplierId) throws ServiceException;

	LtMastRoles delete(Long roleId) throws ServiceException;



}
