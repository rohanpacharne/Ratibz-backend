package com.lonar.master.a2zmaster.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.LtSupplierCities;
//import com.lonar.a2zcommons.util.UtilsMaster;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.dao.LtSupplierCitiesDao;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtSupplierCities;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.utils.UtilsMaster;



@Service
public class LtSupplierCitiesServiceImpl implements LtSupplierCitiesService,CodeMaster{

	@Autowired
	LtSupplierCitiesDao ltSupplierCitiesDao;
	
	@Autowired
	LtMastCommonMessageService ltMastCommonMessageService;
	
	@Override
	public CustomeDataTable getDataTable(LtSupplierCities input, Long supplierId) throws ServiceException {
		CustomeDataTable customeDataTable = new CustomeDataTable();
		Long count = ltSupplierCitiesDao.getLtSupplierCitiesCount(input,supplierId);
	    customeDataTable.setRecordsTotal(count);
	    customeDataTable.setRecordsFiltered(count);
		List<LtSupplierCities> ltSupplierCitiesList= ltSupplierCitiesDao.getLtSupplierCitiesDataTable(input,supplierId);
		customeDataTable.setData(ltSupplierCitiesList);
		return customeDataTable;
	}

	@Override
	public Status save(LtSupplierCities ltSupplierCities) throws ServiceException {
		Status status = new Status();
		
		ltSupplierCities.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
		ltSupplierCities.setCreatedBy(ltSupplierCities.getLastUpdatedBy());
		ltSupplierCities.setCreationDate(UtilsMaster.getCurrentDateTime());
		
		status = checkDuplicate(ltSupplierCities);
		if(status!=null) {
			return status;
		}
		ltSupplierCities = ltSupplierCitiesDao.save(ltSupplierCities);
		if(ltSupplierCities.getCityId()!=null) {
			status=ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
			if( status.getMessage()==null)
			{
//				status.setCode(INSERT_SUCCESSFULLY);
				status = ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
				status.setMessage("Error in finding message! The action is completed successfully.");
			}
			status.setData(ltSupplierCities.getCityId());
		}else {
			status=ltMastCommonMessageService.getCodeAndMessage(INSERT_FAIL);
//			status.setCode(UPDATE_FAIL);
			status.setMessage("Error in finding message! The action is completed unsuccessfully.");	
		}
		return status;
	}

	private Status checkDuplicate(LtSupplierCities ltSupplierCities) throws ServiceException {
		Status status = new Status();
		List<LtSupplierCities> ltSupplierStatesList = ltSupplierCitiesDao.findByCityName(ltSupplierCities.getCity(),
				ltSupplierCities.getSupplierId());
		if (!ltSupplierStatesList.isEmpty() && (ltSupplierCities.getCityId() == null ? true
				: !ltSupplierCities.getCityId().equals(ltSupplierStatesList.get(0).getCityId()))) {
//			status.setCode(FAIL);
			status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
			status.setMessage("City name already exists.");
			return status;
		}else
			return null;
	}

	@Override
	public Status update(LtSupplierCities ltSupplierCities) throws ServiceException {
		Status status = new Status();

		ltSupplierCities.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
		if(ltSupplierCities.getCityId() == null ) {
			ltSupplierCities.setCreatedBy(ltSupplierCities.getLastUpdatedBy());
			ltSupplierCities.setCreationDate(UtilsMaster.getCurrentDateTime());
		}else {
			LtSupplierCities cities = ltSupplierCitiesDao.getLtSupplierCitiesById(ltSupplierCities.getCityId());
			ltSupplierCities.setCreatedBy(cities.getCreatedBy());
			ltSupplierCities.setCreationDate(cities.getCreationDate());
		}
		
		status = checkDuplicate(ltSupplierCities);
		
		if(status!=null) {
			return status;
		}
		
		//ltSupplierCities = ltSupplierCitiesDao.update(ltSupplierCities);
		if(ltSupplierCitiesDao.update(ltSupplierCities)) {
			status=ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
			status.setData(ltSupplierCities.getCityId());
		}else {
			status=ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
		}
		return status;
	}

	@Override
	public LtSupplierCities getLtSupplierCitiesById(Long cityId) throws ServiceException {
		return ltSupplierCitiesDao.getLtSupplierCitiesById(cityId);
	}

	@Override
	public Status delete(Long cityId) throws ServiceException {
		Status status = new Status();
		LtSupplierCities ltSupplierCities = ltSupplierCitiesDao.delete(cityId);
		if(ltSupplierCities==null) {
			status=ltMastCommonMessageService.getCodeAndMessage(DELETE_SUCCESSFULLY);
		}else{ 
			status=ltMastCommonMessageService.getCodeAndMessage(DELETE_FAIL);
		}
		return status;
	}

	@Override
	public List<LtSupplierCities> getAllLtSupplierCities(Long supplierId) throws ServiceException {
		return ltSupplierCitiesDao.getAllLtSupplierCities(supplierId);
	}

	@Override
	public List<LtSupplierCities> getAllCitiesByState(Long stateId, Long supplierId) throws ServiceException {
		return ltSupplierCitiesDao.getAllCitiesByState(stateId,supplierId);
	}

}
