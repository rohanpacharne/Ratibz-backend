package com.lonar.master.a2zmaster.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.dao.LtMastModulesDao;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtMastModules;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.repository.LtMastModulesRepository;
import com.lonar.master.a2zmaster.utils.UtilsMaster;


@Service
public class LtMastModulesServiceImpl implements LtMastModulesService,CodeMaster  {
	
	@Autowired
	private LtMastModulesDao ltMastModuleDao;

	@Autowired
	LtMastCommonMessageService ltMastCommonMessageService;
	
	@Autowired
	LtMastModulesRepository ltMastModulesRepository;
	
	@Override
	public CustomeDataTable getDataTable(LtMastModules input,Long supplierId) {
		CustomeDataTable customeDataTable = new CustomeDataTable();
		Long count=ltMastModuleDao.getCount(input,supplierId);
		customeDataTable.setRecordsTotal(count);
	    customeDataTable.setRecordsFiltered(count);
	    List<LtMastModules> modulelList= ltMastModuleDao.getModuleData(input,supplierId);
	    customeDataTable.setData(modulelList);
	    return customeDataTable;
	}
	
	@Override
	public Status save(LtMastModules ltMastModules) throws ServiceException {
		Status status = new Status();
		ltMastModules.setCreationDate(UtilsMaster.getCurrentDateTime());
		ltMastModules.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
		ltMastModules.setLastUpdatedBy(ltMastModules.getLastUpdateLogin());
		String stat = checkDuplicate(ltMastModules);
		if(stat.equals("null")) {
			 ltMastModules =ltMastModulesRepository.save(ltMastModules);
			if(ltMastModules.getModuleId()!=null) {
			status=ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
			status.setData(ltMastModules.getModuleId());
		   }else {
			  status=ltMastCommonMessageService.getCodeAndMessage(INSERT_FAIL);
		   }
		}
		else {
			status = ltMastCommonMessageService.getCodeAndMessage(EXCEPTION);
			status.setMessage(stat);
//			status.setCode(EXCEPTION);
		}
		return status;
	}

	@Override
	public Status update(LtMastModules ltMastModules) throws ServiceException {
		Status status = new Status();
		String stat = checkDuplicate(ltMastModules);
		if(stat.equals("null")) {
			 ltMastModules =ltMastModulesRepository.save(ltMastModules);
			if(ltMastModules.getModuleId()!=null) {
				status=ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
				status.setData(ltMastModules.getModuleId());
			   }else {
				   status=ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
			   }
		} else {
			status = ltMastCommonMessageService.getCodeAndMessage(EXCEPTION);
			status.setMessage(stat);
//			status.setCode(EXCEPTION);
		}
		
		return status;
	}
	
	public String checkDuplicate(LtMastModules ltMastModules) throws ServiceException {
		List<LtMastModules> ltMastModulesList = ltMastModuleDao.findByModuleCode(ltMastModules.getModuleCode(),ltMastModules.getSupplierId());
		if (ltMastModulesList.size()>0)  {
			if(ltMastModulesList.get(0).getModuleId().toString()!=null
					&& !ltMastModulesList.get(0).getModuleId().equals(ltMastModules.getModuleId())) {
				return "Module code already exists";
			}
		}
		
		ltMastModulesList =ltMastModuleDao.findByModuleName(ltMastModules.getModuleName(),ltMastModules.getSupplierId());
		if (ltMastModulesList.size()>0) {
			if(	ltMastModulesList.get(0).getModuleId().toString()!=null
					&& !ltMastModulesList.get(0).getModuleId().equals(ltMastModules.getModuleId())) {
				return "Module name already exists";
			}
		}
		ltMastModulesList = ltMastModuleDao.findByModuleSequence(ltMastModules.getSequenceNumber(),ltMastModules.getSupplierId());
		if (ltMastModulesList.size()>0)  {
			if(	ltMastModulesList.get(0).getModuleId().toString()!=null
					&& !ltMastModulesList.get(0).getModuleId().equals(ltMastModules.getModuleId())) {
				return "Module sequence already exists";
			}
		}
		ltMastModulesList = ltMastModuleDao.findByModuleUrl(ltMastModules.getModuleUrl(),ltMastModules.getSupplierId());
		if (ltMastModulesList.size()>0)  {
			if(	ltMastModulesList.get(0).getModuleId().toString()!=null
					&& !ltMastModulesList.get(0).getModuleId().equals(ltMastModules.getModuleId())) {
				return "Module URL already exists";
			}
		}
		return "null";
	}

	@Override
	public LtMastModules getLtMastModulesByID(Long moduleId) throws ServiceException {
		return ltMastModuleDao.getLtMastModulesByID(moduleId);
	}

	@Override
	public List<LtMastModules> getActiveLikeName(String moduleName, Long supplierId) throws ServiceException {
		return ltMastModuleDao.getActiveLikeName(moduleName,supplierId);
	}

	@Override
	public Status delete(Long moduleId) throws ServiceException {
		Status status = new Status();
	
		ltMastModulesRepository.deleteById(moduleId);
		if(!ltMastModulesRepository.existsById(moduleId)) {
			status=ltMastCommonMessageService.getCodeAndMessage(DELETE_SUCCESSFULLY);
		}else {
			status=ltMastCommonMessageService.getCodeAndMessage(DELETE_FAIL);
	   }
	return status;
   }
	
}
