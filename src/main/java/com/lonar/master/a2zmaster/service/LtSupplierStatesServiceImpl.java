package com.lonar.master.a2zmaster.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//
//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.LtSupplierStates;
//import com.lonar.a2zcommons.util.UtilsMaster;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.dao.LtSupplierStatesDao;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtSupplierStates;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.utils.UtilsMaster;

@Service
public class LtSupplierStatesServiceImpl implements LtSupplierStatesService,CodeMaster {

	@Autowired
	private LtSupplierStatesDao ltSupplierStatesDao;
	
	@Autowired
	private LtMastCommonMessageService ltMastCommonMessageService;
	
	@Override
	public CustomeDataTable getDataTable(LtSupplierStates input, Long supplierId) throws ServiceException {
		CustomeDataTable customeDataTable = new CustomeDataTable();
		Long count = ltSupplierStatesDao.getLtSupplierStatesCount(input,supplierId);
	    customeDataTable.setRecordsTotal(count);
	    customeDataTable.setRecordsFiltered(count);
		List<LtSupplierStates> ltSupplierStatesList= ltSupplierStatesDao.getLtSupplierStatesDataTable(input,supplierId);
		customeDataTable.setData(ltSupplierStatesList);
		return customeDataTable;
	}

	@Override
	public Status save(LtSupplierStates ltSupplierStates) throws ServiceException {
		Status status = new Status();
		ltSupplierStates.setCreatedBy(ltSupplierStates.getLastUpdateLogin());
		ltSupplierStates.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
		ltSupplierStates.setCreationDate(UtilsMaster.getCurrentDateTime());
		ltSupplierStates.setLastUpdatedBy(ltSupplierStates.getLastUpdateLogin());
		status = checkDuplicate(ltSupplierStates);
		if(status!=null) {
			return status;
		}
		ltSupplierStates = ltSupplierStatesDao.save(ltSupplierStates);
		if(ltSupplierStates.getStateId()!=null) {
			status=ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
			status.setData(ltSupplierStates.getStateId());
		}else {
			status=ltMastCommonMessageService.getCodeAndMessage(INSERT_FAIL);
		}
		return status;
	}

	private Status checkDuplicate(LtSupplierStates ltSupplierStates) throws ServiceException {
		Status status = new Status();
		List<LtSupplierStates> ltSupplierStatesList = ltSupplierStatesDao.getByStateName(ltSupplierStates.getSupplierState(),
				ltSupplierStates.getSupplierId());
		if (!ltSupplierStatesList.isEmpty() && (ltSupplierStates.getStateId() == null ? true
				: !ltSupplierStates.getStateId().equals(ltSupplierStatesList.get(0).getStateId()))) {
//			status.setCode(FAIL);
			status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
			status.setMessage("State name already exists.");
			return status;
		}else
			return null;
	}
	
	

	@Override
	public Status update(LtSupplierStates ltSupplierStates) throws ServiceException {
		Status status = new Status();
		ltSupplierStates.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
		ltSupplierStates.setLastUpdatedBy(ltSupplierStates.getLastUpdateLogin());
		status = checkDuplicate(ltSupplierStates);
		if(status!=null) {
			return status;
		}
		//ltSupplierStates = ltSupplierStatesDao.save(ltSupplierStates);
		if(ltSupplierStatesDao.update(ltSupplierStates)) {
			status=ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
			status.setData(ltSupplierStates.getStateId());
		}else {
			 status=ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
		}
		return status;
	}

	@Override
	public LtSupplierStates getStatesById(Long stateId) throws ServiceException {
		return ltSupplierStatesDao.getStatesById(stateId);
	}

	@Override
	public Status delete(Long stateId) throws ServiceException {
		Status status = new Status();
		LtSupplierStates ltSupplierStates = ltSupplierStatesDao.delete(stateId);
		if(ltSupplierStates==null) {
			status=ltMastCommonMessageService.getCodeAndMessage(DELETE_SUCCESSFULLY);
		}else{ 
			status=ltMastCommonMessageService.getCodeAndMessage(DELETE_FAIL);
		}
		return status;
	}

	@Override
	public List<LtSupplierStates> getAllStates(Long supplierId) throws ServiceException {
		return ltSupplierStatesDao.getAllStates(supplierId);
	}

}
