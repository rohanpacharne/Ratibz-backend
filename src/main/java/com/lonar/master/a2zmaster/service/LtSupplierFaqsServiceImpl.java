package com.lonar.master.a2zmaster.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.LtSupplierFaqs;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.dao.LtSupplierFaqsDao;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtSupplierFaqs;
import com.lonar.master.a2zmaster.model.Status;

@Service
public class LtSupplierFaqsServiceImpl implements LtSupplierFaqsService,CodeMaster{

	@Autowired
	LtSupplierFaqsDao ltSupplierFaqsDao;
	
	@Autowired
	LtMastCommonMessageService ltMastCommonMessageService;
	
	@Override
	public CustomeDataTable getDataTable(Long supplierId, LtSupplierFaqs input) throws ServiceException {
		CustomeDataTable customeDataTable = new CustomeDataTable();
		Long count = ltSupplierFaqsDao.getLtSupplierFaqsCount(input,supplierId);
	    customeDataTable.setRecordsTotal(count);
	    customeDataTable.setRecordsFiltered(count);
		List<LtSupplierFaqs> ltSupplierFaqsList= ltSupplierFaqsDao.getLtSupplierFaqsDataTable(input,supplierId);
		customeDataTable.setData(ltSupplierFaqsList);
		return customeDataTable;
	}

	@Override
	public Status save(LtSupplierFaqs ltSupplierFaqs) throws ServiceException {
		Status status = new Status();
		ltSupplierFaqs.setLastUpdateDate(new Date());
		ltSupplierFaqs.setCreationDate(new Date());
		ltSupplierFaqs.setLastUpdatedBy(ltSupplierFaqs.getLastUpdateLogin());
		
		ltSupplierFaqs = ltSupplierFaqsDao.save(ltSupplierFaqs);
		if(ltSupplierFaqs.getFaqId()!=null) {
			status=ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
			status.setData(ltSupplierFaqs.getFaqId());
		}else {
			status=ltMastCommonMessageService.getCodeAndMessage(INSERT_FAIL);
			
		}
		return status;
	}

	@Override
	public Status update(LtSupplierFaqs ltSupplierFaqs) throws ServiceException {
		Status status = new Status();
		ltSupplierFaqs.setLastUpdateDate(new Date());
		ltSupplierFaqs.setLastUpdatedBy(ltSupplierFaqs.getLastUpdateLogin());
		
		ltSupplierFaqs = ltSupplierFaqsDao.save(ltSupplierFaqs);
		if(ltSupplierFaqs.getFaqId()!=null) {
			status=ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
			status.setData(ltSupplierFaqs.getFaqId());
		}else {
			status=ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
		}
		return status;
	}

	@Override
	public LtSupplierFaqs getLtSupplierFaqsById(Long faqId) throws ServiceException {
		return ltSupplierFaqsDao.getLtSupplierFaqsById(faqId);
	}

	@Override
	public List<LtSupplierFaqs> getAllActiveLtSupplierFaqs(Long supplierId) throws ServiceException {
		return ltSupplierFaqsDao.getAllActiveLtSupplierFaqs(supplierId);
	}

	@Override
	public Status delete(Long faqId) throws ServiceException {
		Status status = new Status();
		LtSupplierFaqs ltSupplierFaqs = ltSupplierFaqsDao.delete(faqId);
		if(ltSupplierFaqs==null) {
			status=ltMastCommonMessageService.getCodeAndMessage(DELETE_SUCCESSFULLY);
		}else{ 
			status=ltMastCommonMessageService.getCodeAndMessage(DELETE_FAIL);
		}
		return status;
	}

}
