package com.lonar.master.a2zmaster.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.LtDeliveryAreaAgents;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.dao.LtDeliveryAreaAgentsDao;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtDeliveryAreaAgents;
import com.lonar.master.a2zmaster.model.Status;

@Service
public class LtDeliveryAreaAgentsServiceImpl implements LtDeliveryAreaAgentsService,CodeMaster{

	@Autowired
	LtMastCommonMessageService ltMastCommonMessageService;
	
	@Autowired
	LtDeliveryAreaAgentsDao ltDeliveryAreaAgentsDao;
	
	@Override
	public CustomeDataTable getDataTable(LtDeliveryAreaAgents input, Long areaId) throws ServiceException {
		CustomeDataTable customeDataTable = new CustomeDataTable();
		Long count = ltDeliveryAreaAgentsDao.getLtDeliveryAreaAgentsCount(input,areaId);
	    customeDataTable.setRecordsTotal(count);
	    customeDataTable.setRecordsFiltered(count);
		List<LtDeliveryAreaAgents> ltDeliveryAreasList= ltDeliveryAreaAgentsDao.getLtDeliveryAreaAgentsDataTable(input,areaId);
		customeDataTable.setData(ltDeliveryAreasList);
		return customeDataTable;
	}

	@Override
	public Status save(LtDeliveryAreaAgents ltDeliveryAreaAgents) throws ServiceException {
		Status status = new Status();
		ltDeliveryAreaAgents.setLastUpdateDate(new Date());
		ltDeliveryAreaAgents.setCreationDate(new Date());
		ltDeliveryAreaAgents.setLastUpdatedBy(ltDeliveryAreaAgents.getLastUpdateLogin());
		
		ltDeliveryAreaAgents = ltDeliveryAreaAgentsDao.save(ltDeliveryAreaAgents);
		if(ltDeliveryAreaAgents.getAreaBoyId()!=null) {
			status=ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
			status.setData(ltDeliveryAreaAgents.getAreaBoyId());
		}else {
			status=ltMastCommonMessageService.getCodeAndMessage(INSERT_FAIL);
		}
		return status;
	}

	@Override
	public Status update(LtDeliveryAreaAgents ltDeliveryAreaAgents) throws ServiceException {
		Status status = new Status();
		ltDeliveryAreaAgents.setLastUpdateDate(new Date());
		ltDeliveryAreaAgents.setLastUpdatedBy(ltDeliveryAreaAgents.getLastUpdateLogin());
		ltDeliveryAreaAgents = ltDeliveryAreaAgentsDao.save(ltDeliveryAreaAgents);
		if(ltDeliveryAreaAgents.getAreaBoyId()!=null) {
			status=ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
			status.setData(ltDeliveryAreaAgents.getAreaBoyId());
		}else {
			status=ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
		}
		return status;
	}

	@Override
	public LtDeliveryAreaAgents getLtDeliveryAreaAgentsById(Long areaId) throws ServiceException {
		return ltDeliveryAreaAgentsDao.getLtDeliveryAreaAgentsById(areaId);
	}

	@Override
	public List<LtDeliveryAreaAgents> getAllDeliveryAreaAgents(Long areaId) throws ServiceException {
		return ltDeliveryAreaAgentsDao.getAllDeliveryAreaAgents(areaId);
	}

	@Override
	public Status delete(Long areaBoyId) throws ServiceException {
		Status status = new Status();
		LtDeliveryAreaAgents ltDeliveryAreaAgents = ltDeliveryAreaAgentsDao.delete(areaBoyId);
		if(ltDeliveryAreaAgents==null) {
			status=ltMastCommonMessageService.getCodeAndMessage(DELETE_SUCCESSFULLY);
		}else{ 
			status=ltMastCommonMessageService.getCodeAndMessage(DELETE_FAIL);
		}
		return status;
	}

	@Override
	public List<LtDeliveryAreaAgents> getAllDeliveryAgents(Long supplierId) throws ServiceException {
		return ltDeliveryAreaAgentsDao.getAllDeliveryAgents(supplierId);
	}

}
