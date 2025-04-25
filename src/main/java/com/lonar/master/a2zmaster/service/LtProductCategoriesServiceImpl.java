package com.lonar.master.a2zmaster.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.LtProductCategories;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.dao.LtProductCategoriesDao;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtProductCategories;
import com.lonar.master.a2zmaster.model.Status;

@Service
public class LtProductCategoriesServiceImpl implements LtProductCategoriesService,CodeMaster{

	@Autowired
	LtProductCategoriesDao ltProductCategoriesDao;
	
	@Autowired
	LtMastCommonMessageService ltMastCommonMessageService;
	
	@Override
	public CustomeDataTable getDataTable(LtProductCategories input, Long supplierId) throws ServiceException {
		CustomeDataTable customeDataTable = new CustomeDataTable();
		Long count = ltProductCategoriesDao.getLtProductCategoriesCount(input,supplierId);
	    customeDataTable.setRecordsTotal(count);
	    customeDataTable.setRecordsFiltered(count);
		List<LtProductCategories> ltProductTypesList= ltProductCategoriesDao.getLtProductCategoriesDataTable(input,supplierId);
		customeDataTable.setData(ltProductTypesList);
		return customeDataTable;
	}

	@Override
	public Status save(LtProductCategories ltProductCategories) throws ServiceException {
		Status status = new Status();
		ltProductCategories.setLastUpdateDate(new Date());
		ltProductCategories.setCreationDate(new Date());
		ltProductCategories.setLastUpdatedBy(ltProductCategories.getLastUpdateLogin());
		ltProductCategories.setCreatedBy(ltProductCategories.getLastUpdateLogin());
		status = checkDuplicate(ltProductCategories);
		if(status!=null) {
			return status;
		}
		ltProductCategories = ltProductCategoriesDao.save(ltProductCategories);
		if(ltProductCategories.getCategoryId()!=null) {
			status=ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
			status.setData(ltProductCategories.getCategoryId());
		}else {
			status=ltMastCommonMessageService.getCodeAndMessage(INSERT_FAIL);
		}
		return status;
	}

	private Status checkDuplicate(LtProductCategories ltProductCategories) throws ServiceException {
		Status status = new Status();
		List<LtProductCategories> ltProductCategoriesList = ltProductCategoriesDao.findByProductCategoryName(ltProductCategories.getCategoryName(),
				ltProductCategories.getSupplierId());
		if (!ltProductCategoriesList.isEmpty() && (ltProductCategories.getCategoryId() == null ? true
				: !ltProductCategories.getCategoryId().equals(ltProductCategoriesList.get(0).getCategoryId()))) {
//			status.setCode(FAIL);
			status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
			status.setMessage("Product category already exists.");
			return status;
		}else
			return null;
	}

	@Override
	public Status update(LtProductCategories ltProductCategories) throws ServiceException {
		Status status = new Status();
		ltProductCategories.setLastUpdateDate(new Date());
		ltProductCategories.setLastUpdatedBy(ltProductCategories.getLastUpdateLogin());
		status = checkDuplicate(ltProductCategories);
		
		ltProductCategories = ltProductCategoriesDao.save(ltProductCategories);
		if(ltProductCategories.getCategoryId()!=null) {
			status=ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
			status.setData(ltProductCategories.getCategoryId());
		}else {
			status=ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
		}
		return status;
	}

	@Override
	public LtProductCategories getLtProductCategoriesById(Long categoryId) throws ServiceException {
		return ltProductCategoriesDao.getLtProductCategoriesById(categoryId);
	}

	@Override
	public Status delete(Long categoryId) throws ServiceException {
		Status status = new Status();
		LtProductCategories ltProductCategories = ltProductCategoriesDao.delete(categoryId);
		if(ltProductCategories==null) {
			status=ltMastCommonMessageService.getCodeAndMessage(DELETE_SUCCESSFULLY);
		}else{ 
			status=ltMastCommonMessageService.getCodeAndMessage(DELETE_FAIL);
		}
		return status;
	}

	@Override
	public List<LtProductCategories> getAllActiveCategories(Long supplierId) throws ServiceException {
		return ltProductCategoriesDao.getAllActiveCategories(supplierId);
	}

	@Override
	public List<LtProductCategories> getAllActiveCategoriesByType(Long typeId, Long supplierId)
			throws ServiceException {
		return ltProductCategoriesDao.getAllActiveCategoriesByTypeSupplier(typeId,supplierId);
	}

}
