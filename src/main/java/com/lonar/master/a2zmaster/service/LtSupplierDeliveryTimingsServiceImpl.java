package com.lonar.master.a2zmaster.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.lonar.a2zcommons.model.LtSupplierDeliveryTimings;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.dao.LtSupplierDeliveryTimingsDao;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.LtSupplierDeliveryTimings;
import com.lonar.master.a2zmaster.model.Status;

@Service
public class LtSupplierDeliveryTimingsServiceImpl implements LtSupplierDeliveryTimingsService,CodeMaster{

	@Autowired
	private LtSupplierDeliveryTimingsDao ltSupplierDeliveryTimingsDao;
	
	@Autowired
	LtMastCommonMessageService ltMastCommonMessageService;
	
	@Override
	public Status save(LtSupplierDeliveryTimings ltSupplierDeliveryTimings) throws ServiceException {
		Status status = new Status();
		ltSupplierDeliveryTimings.setLastUpdateDate(new Date());
		ltSupplierDeliveryTimings.setCreationDate(new Date());
		ltSupplierDeliveryTimings.setLastUpdatedBy(ltSupplierDeliveryTimings.getLastUpdateLogin());
		
		ltSupplierDeliveryTimings = ltSupplierDeliveryTimingsDao.save(ltSupplierDeliveryTimings);
		if(ltSupplierDeliveryTimings.getDeliveryTimeId()!=null) {
			status=ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
			status.setData(ltSupplierDeliveryTimings.getDeliveryTimeId());
		}else {
			status=ltMastCommonMessageService.getCodeAndMessage(INSERT_FAIL);
		}
		return status;
	}

	@Override
	public Status update(LtSupplierDeliveryTimings ltSupplierDeliveryTimings) throws ServiceException {
		Status status = new Status();
		ltSupplierDeliveryTimings.setLastUpdateDate(new Date());
		ltSupplierDeliveryTimings.setLastUpdatedBy(ltSupplierDeliveryTimings.getLastUpdateLogin());
		
		ltSupplierDeliveryTimings = ltSupplierDeliveryTimingsDao.save(ltSupplierDeliveryTimings);
		if(ltSupplierDeliveryTimings.getDeliveryTimeId()!=null) {
			status=ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
			status.setData(ltSupplierDeliveryTimings.getDeliveryTimeId());
		}else {
			status=ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
		}
		return status;
	}

	@Override
	public LtSupplierDeliveryTimings getLtSupplierDeliveryTimingsById(Long deliveryTimeId) throws ServiceException {
		return ltSupplierDeliveryTimingsDao.getLtSupplierDeliveryTimingsById(deliveryTimeId);
	}

	@Override
	public Status delete(Long deliveryTimeId) throws ServiceException {
		Status status = new Status();
		LtSupplierDeliveryTimings ltSupplierDeliveryTimings = ltSupplierDeliveryTimingsDao.delete(deliveryTimeId);
		if(ltSupplierDeliveryTimings==null) {
			status=ltMastCommonMessageService.getCodeAndMessage(DELETE_SUCCESSFULLY);
		}else{ 
			status=ltMastCommonMessageService.getCodeAndMessage(DELETE_FAIL);
		}
		return status;
	}

	@Override
	public List<LtSupplierDeliveryTimings> getAllDeliveryTimings(Long supplierId) throws ServiceException {
		return ltSupplierDeliveryTimingsDao.getAllDeliveryTimings(supplierId);
	}

}
