package com.lonar.master.a2zmaster.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.dao.LtMastRoleModulesDao;
import com.lonar.master.a2zmaster.dao.LtMastRolesDao;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtMastRoleModules;
import com.lonar.master.a2zmaster.model.LtMastRoles;
import com.lonar.master.a2zmaster.model.Status;



@Service
public class LtMastRolesServiceImpl implements LtMastRolesService,CodeMaster{

	@Autowired
	LtMastRolesDao ltMastRolesDao;
	
	@Autowired
	LtMastRoleModulesDao ltMastRoleModulesDao;
	
	@Autowired
	LtMastCommonMessageService ltMastCommonMessageService;
	
	@Override
	public CustomeDataTable getDataTable(LtMastRoles input, Long supplierId) throws ServiceException {
		CustomeDataTable customeDataTable = new CustomeDataTable();
		Long count = ltMastRolesDao.getLtMastRolesCount(input,supplierId);
	    customeDataTable.setRecordsTotal(count);
	    customeDataTable.setRecordsFiltered(count);
		List<LtMastRoles> ltMastRolesList= ltMastRolesDao.getLtMastRolesDataTable(input,supplierId);
		customeDataTable.setData(ltMastRolesList);
		return customeDataTable;
	}

	@Override
	public Status save(LtMastRoles ltMastRoles) throws ServiceException {
		Status status = new Status();
		ltMastRoles.setLastUpdateDate(new Date());
		ltMastRoles.setCreationDate(new Date());
		ltMastRoles.setLastUpdatedBy(ltMastRoles.getLastUpdateLogin());
		List<LtMastRoles> ltMastRolesList = ltMastRolesDao.findByRole(ltMastRoles.getRoleName(),ltMastRoles.getSupplierId());
		if (!ltMastRolesList.isEmpty() && (ltMastRoles.getRoleId() == null ? true
				: !ltMastRoles.getRoleId().equals(ltMastRolesList.get(0).getRoleId()))) {
//			status.setCode(FAIL);
			status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
			status.setMessage("Role name already exists.");
			return status;
		}
		ltMastRoles = ltMastRolesDao.save(ltMastRoles);
		if(ltMastRoles.getRoleId()!=null) {
			 status=ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
			status.setData(ltMastRoles.getRoleId());
		}else {
			status=ltMastCommonMessageService.getCodeAndMessage(INSERT_FAIL);
		}
		return status;
	}

	@Override
	public Status update(LtMastRoles ltMastRoles) throws ServiceException {
		Status status = new Status();
		ltMastRoles.setLastUpdateDate(new Date());
		ltMastRoles.setLastUpdatedBy(ltMastRoles.getLastUpdateLogin());
		List<LtMastRoles> ltMastRolesList = ltMastRolesDao.findByRole(ltMastRoles.getRoleName(),ltMastRoles.getSupplierId());
		if (!ltMastRolesList.isEmpty() && !(ltMastRolesList.get(0).getRoleId().equals(ltMastRoles.getRoleId()))) {
//			status.setCode(FAIL);
			status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
			status.setMessage("Role name already exists.");
			return status;
		}
		
		ltMastRoles = ltMastRolesDao.save(ltMastRoles);
		if(ltMastRoles.getRoleId()!=null) {
			 status=ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
			status.setData(ltMastRoles.getRoleId());
		}else {
			 status=ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
		}
		return status;
	}

	@Override
	public LtMastRoles getRolesById(Long roleId) throws ServiceException {
		return ltMastRolesDao.getRolesById(roleId);
	}

	@Override
	public List<LtMastRoles> getAllActiveRole(Long supplierId) throws ServiceException {
		return ltMastRolesDao.getAllActiveRole(supplierId);
	}

	@Override
	public Status saveLtMastRoleModules(LtMastRoleModules ltMastRoleModule) throws ServiceException {
		Status status = new Status();
		ltMastRoleModule.setCreatedBy(ltMastRoleModule.getLastUpdateLogin());
		ltMastRoleModule.setLastUpdatedBy(ltMastRoleModule.getLastUpdateLogin());
		ltMastRoleModule.setLastUpdateLogin(ltMastRoleModule.getLastUpdateLogin());
		ltMastRoleModule.setCreationDate(new Date());
		ltMastRoleModule.setLastUpdateDate(new Date());
		if(ltMastRoleModulesDao.checkForDuplicate(ltMastRoleModule)) {
			ltMastRoleModule = ltMastRoleModulesDao.saveLtMastRoleModules(ltMastRoleModule);
		if(ltMastRoleModule.getRoleFuncId()!=null) {
			status=ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
		}else {
			status=ltMastCommonMessageService.getCodeAndMessage(INSERT_FAIL);
		}
		}else {
//			status.setCode(FAIL);
			status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
			status.setMessage("Module already present for the role.");
		}
		return status;
	}

	@Override
	public Status delete(Long roleId) throws ServiceException {
		Status status = new Status();
		LtMastRoles ltMastRoles = ltMastRolesDao.delete(roleId);
		if(ltMastRoles==null) {
			status=ltMastCommonMessageService.getCodeAndMessage(DELETE_SUCCESSFULLY);
		}else{ 
			status=ltMastCommonMessageService.getCodeAndMessage(DELETE_FAIL);
		}
		return status;
	}

	@Override
	public List<LtMastRoleModules> getRoleModule(Long roleId) throws ServiceException {
		return ltMastRoleModulesDao.getRoleModule(roleId);
	}

	@Override
	public LtMastRoleModules getRoleModuleById(Long roleFuncId) throws ServiceException {
		
		return ltMastRoleModulesDao.getRoleModuleById(roleFuncId);
	}

	@Override
	public Status deleteRoleModule(Long roleFuncId) throws ServiceException {
		Status status = new Status();
		LtMastRoleModules ltMastRoleModules = ltMastRoleModulesDao.deleteRoleModule(roleFuncId);
		if(ltMastRoleModules==null) {
			status=ltMastCommonMessageService.getCodeAndMessage(DELETE_SUCCESSFULLY);
		}else{ 
			status=ltMastCommonMessageService.getCodeAndMessage(DELETE_FAIL);
		}
		return status;
	}

}
