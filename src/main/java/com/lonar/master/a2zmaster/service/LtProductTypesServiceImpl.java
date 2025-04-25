package com.lonar.master.a2zmaster.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.LtProductTypes;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.dao.LtProductTypesDao;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtProductTypes;
//import com.lonar.master.a2zmaster.model.LtProductTypes;
import com.lonar.master.a2zmaster.model.Status;

@Service
public class LtProductTypesServiceImpl implements LtProductTypesService,CodeMaster{

	@Autowired
	LtProductTypesDao ltProductTypesDao;
	
	@Autowired
	LtMastCommonMessageService ltMastCommonMessageService;
	
	@Override
	public CustomeDataTable getDataTable(LtProductTypes input, Long supplierId) throws ServiceException {
		
		CustomeDataTable customeDataTable = new CustomeDataTable();
		
		Long count = ltProductTypesDao.getLtProductTypesCount(input,supplierId);
		
	    customeDataTable.setRecordsTotal(count);
	    customeDataTable.setRecordsFiltered(count);
	    
		List<LtProductTypes> ltProductTypesList= ltProductTypesDao.getLtProductTypesDataTable(input,supplierId);
		
		customeDataTable.setData(ltProductTypesList);
		return customeDataTable;
	}

	@Override
	public Status save(LtProductTypes ltProductTypes) throws ServiceException {
		Status status = new Status();
		ltProductTypes.setLastUpdateDate(new Date());
		ltProductTypes.setCreationDate(new Date());
		ltProductTypes.setLastUpdatedBy(ltProductTypes.getLastUpdateLogin());
		ltProductTypes.setCreatedBy(ltProductTypes.getLastUpdateLogin());
		status = checkDuplicate(ltProductTypes);
		if(status!=null) {
			return status;
		}
		ltProductTypes = ltProductTypesDao.save(ltProductTypes);
		if(ltProductTypes.getProductTypeId()!=null) {
			status=ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
			if( status.getMessage()==null)
			{
//				status.setCode(INSERT_SUCCESSFULLY);
				status = ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
				status.setMessage("Error in finding message! The action is completed successfully.");
			}
			status.setData(ltProductTypes.getProductTypeId());
		}else {
			status=ltMastCommonMessageService.getCodeAndMessage(INSERT_FAIL);
//			status.setCode(UPDATE_FAIL);
			status = ltMastCommonMessageService.getCodeAndMessage(INSERT_FAIL);
			status.setMessage("Error in finding message! The action is completed unsuccessfully.");	
		}
		return status;
	}

	private Status checkDuplicate(LtProductTypes ltProductTypes) throws ServiceException {
		Status status = new Status();
		List<LtProductTypes> ltSupplierStatesList = ltProductTypesDao.findByProductTypeName(ltProductTypes.getProductType(),
				ltProductTypes.getSupplierId());
		if (!ltSupplierStatesList.isEmpty() && (ltProductTypes.getProductTypeId() == null ? true
				: !ltProductTypes.getProductTypeId().equals(ltSupplierStatesList.get(0).getProductTypeId()))) {
//			status.setCode(FAIL);
			status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
			status.setMessage("Product type already exists.");
			return status;
		}else
			return null;
	}

	@Override
	public Status update(LtProductTypes ltProductTypes) throws ServiceException {
		Status status = new Status();
		ltProductTypes.setLastUpdateDate(new Date());
		ltProductTypes.setLastUpdatedBy(ltProductTypes.getLastUpdateLogin());
		status = checkDuplicate(ltProductTypes);
		
		ltProductTypes = ltProductTypesDao.save(ltProductTypes);
		if(ltProductTypes.getProductTypeId()!=null) {
			status=ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
			if( status.getMessage()==null)
			{
//				status.setCode(UPDATE_SUCCESSFULLY);
				status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
				status.setMessage("Error in finding message! The action is completed successfully.");
			}
			status.setData(ltProductTypes.getProductTypeId());
		}else {
			status=ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
			if( status.getMessage()==null)
			{
//				status.setCode(UPDATE_FAIL);
				status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
				status.setMessage("Error in finding message! The action is completed unsuccessfully.");
			}
		}
		return status;
	}

	@Override
	public LtProductTypes getLtProductTypesById(Long productTypeId) throws ServiceException {
		return ltProductTypesDao.getLtProductTypesById(productTypeId);
	}

	@Override
	public Status delete(Long productTypeId) throws ServiceException {
		Status status = new Status();
		LtProductTypes ltProductTypes = ltProductTypesDao.delete(productTypeId);
		if(ltProductTypes==null) {
			status=ltMastCommonMessageService.getCodeAndMessage(DELETE_SUCCESSFULLY);
		}else{ 
			status=ltMastCommonMessageService.getCodeAndMessage(DELETE_FAIL);
		}
		return status;
	}

	@Override
	public List<LtProductTypes> getAllActiveProductType(Long supplierId) throws ServiceException {
		return ltProductTypesDao.getAllActiveProductType(supplierId);
	}

}
