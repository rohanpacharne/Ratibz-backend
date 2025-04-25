package com.lonar.master.a2zmaster.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.LtDeliveryAreaAgents;
//import com.lonar.a2zcommons.model.LtDeliveryAreas;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.dao.LtDeliveryAreaAgentsDao;
import com.lonar.master.a2zmaster.dao.LtDeliveryAreasDao;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtDeliveryAreaAgents;
import com.lonar.master.a2zmaster.model.LtDeliveryAreas;
import com.lonar.master.a2zmaster.model.Status;

@Service
public class LtDeliveryAreasServiceImpl implements LtDeliveryAreasService, CodeMaster {

	@Autowired
	LtMastCommonMessageService ltMastCommonMessageService;

	@Autowired
	LtDeliveryAreasDao ltDeliveryAreasDao;

	@Autowired
	LtDeliveryAreaAgentsDao ltDeliveryAreaAgentsDao;

	@Override
	public CustomeDataTable getDataTable(LtDeliveryAreas input, Long supplierId) throws ServiceException {
		CustomeDataTable customeDataTable = new CustomeDataTable();
		Long count = ltDeliveryAreasDao.getLtDeliveryAreasCount(input, supplierId);
		customeDataTable.setRecordsTotal(count);
		customeDataTable.setRecordsFiltered(count);
		List<LtDeliveryAreas> ltDeliveryAreasList = ltDeliveryAreasDao.getLtDeliveryAreasDataTable(input, supplierId);
		System.out.println("ltDeliveryAreasList::"+ltDeliveryAreasList);
		customeDataTable.setData(ltDeliveryAreasList);
		return customeDataTable;
	}

	@Override
	public Status save(LtDeliveryAreas ltDeliveryAreas) throws ServiceException {
		Status status = new Status();
		ltDeliveryAreas.setLastUpdateDate(new Date());
		ltDeliveryAreas.setCreationDate(new Date());
		ltDeliveryAreas.setLastUpdatedBy(ltDeliveryAreas.getLastUpdateLogin());
		ltDeliveryAreas.setCreatedBy(ltDeliveryAreas.getLastUpdateLogin());
		status = checkDuplicate(ltDeliveryAreas);
		if (status != null) {
			return status;
		}
		ltDeliveryAreas = ltDeliveryAreasDao.save(ltDeliveryAreas);
		if (ltDeliveryAreas.getAreaId() != null) {
			if (ltDeliveryAreas.getDeliveryAgentList() != null) {
				for (LtDeliveryAreaAgents ltDeliveryAreaAgents : ltDeliveryAreas.getDeliveryAgentList()) {
					// LtDeliveryAreaAgents ltDeliveryAreaAgents = new LtDeliveryAreaAgents();
					ltDeliveryAreaAgents.setUserId(ltDeliveryAreaAgents.getUserId());
					ltDeliveryAreaAgents.setLastUpdateDate(new Date());
					ltDeliveryAreaAgents.setCreationDate(new Date());
					ltDeliveryAreaAgents.setLastUpdatedBy(ltDeliveryAreaAgents.getLastUpdateLogin());
					ltDeliveryAreaAgents.setAreaId(ltDeliveryAreas.getAreaId());
					ltDeliveryAreaAgents.setSupplierId(ltDeliveryAreas.getSupplierId());
					ltDeliveryAreaAgents = ltDeliveryAreaAgentsDao.save(ltDeliveryAreaAgents);
				}
			}
			status = ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
			status.setData(ltDeliveryAreas.getAreaId());
		} else {
			status = ltMastCommonMessageService.getCodeAndMessage(INSERT_FAIL);
		}
		return status;
	}

	private Status checkDuplicate(LtDeliveryAreas ltDeliveryAreas) throws ServiceException {
		Status status = new Status();
		List<LtDeliveryAreas> ltDeliveryAreasList = ltDeliveryAreasDao.getByAreaName(ltDeliveryAreas.getArea(),
				ltDeliveryAreas.getSupplierId());
		if (!ltDeliveryAreasList.isEmpty() && (ltDeliveryAreas.getAreaId() == null ? true
				: !ltDeliveryAreas.getAreaId().equals(ltDeliveryAreasList.get(0).getAreaId()))) {
//			status.setCode(FAIL);
			status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
			status.setMessage("Delivery area already exists.");
			return status;
		} else
			return null;
	}

	@Override
	public Status update(LtDeliveryAreas ltDeliveryAreas) throws ServiceException {
		Status status = new Status();
		ltDeliveryAreas.setLastUpdateDate(new Date());
		ltDeliveryAreas.setLastUpdatedBy(ltDeliveryAreas.getLastUpdateLogin());
		status = checkDuplicate(ltDeliveryAreas);
		if(status!=null) {
			return status;
		}

		if (ltDeliveryAreasDao.update(ltDeliveryAreas)) {
			List<LtDeliveryAreaAgents> agentsList = ltDeliveryAreaAgentsDao
					.getAllDeliveryAreaAgents(ltDeliveryAreas.getAreaId());
			if (!agentsList.isEmpty() && agentsList != null) {
				ltDeliveryAreaAgentsDao.deleteByAreaId(ltDeliveryAreas.getAreaId());
			}

			if (ltDeliveryAreas.getDeliveryAgentList() != null) {
				for (LtDeliveryAreaAgents ltDeliveryAreaAgents : ltDeliveryAreas.getDeliveryAgentList()) {
					ltDeliveryAreaAgents.setUserId(ltDeliveryAreaAgents.getUserId());
				//	ltDeliveryAreaAgents.setCreatedBy(ltDeliveryAreaAgents.getLastUpdateLogin());
					ltDeliveryAreaAgents.setLastUpdateDate(new Date());
					ltDeliveryAreaAgents.setCreationDate(new Date());
					ltDeliveryAreaAgents.setLastUpdatedBy(ltDeliveryAreaAgents.getLastUpdateLogin());
					ltDeliveryAreaAgents.setAreaId(ltDeliveryAreas.getAreaId());
					ltDeliveryAreaAgents.setSupplierId(ltDeliveryAreas.getSupplierId());
					ltDeliveryAreaAgents = ltDeliveryAreaAgentsDao.save(ltDeliveryAreaAgents);
				}
			}
			status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
			status.setData(ltDeliveryAreas.getAreaId());

		} else {
			status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
		}
		return status;
	}

	@Override
	public LtDeliveryAreas getLtDeliveryAreasById(Long areaId) throws ServiceException {
		LtDeliveryAreas ltDeliveryAreas = ltDeliveryAreasDao.getLtDeliveryAreasById(areaId);
		ltDeliveryAreas.setDeliveryAgentList(ltDeliveryAreaAgentsDao.getAllDeliveryAreaAgents(areaId));
		return ltDeliveryAreas;
	}

	@Override
	public List<LtDeliveryAreas> getAllActiveDeliveryAreas(Long supplierId) throws ServiceException {
		return ltDeliveryAreasDao.getAllActiveDeliveryAreas(supplierId);
	}

	@Override
	public Status delete(Long areaId) throws ServiceException {
		Status status = new Status();
		LtDeliveryAreas ltDeliveryAreas = ltDeliveryAreasDao.delete(areaId);
		if (ltDeliveryAreas == null) {
			status = ltMastCommonMessageService.getCodeAndMessage(DELETE_SUCCESSFULLY);
		} else {
			status = ltMastCommonMessageService.getCodeAndMessage(DELETE_FAIL);
		}
		return status;
	}

	@Override
	public List<LtDeliveryAreas> getAllActiveAreasByCity(Long cityId, Long supplierId) throws ServiceException {
		return ltDeliveryAreasDao.getAllActiveAreasByCity(cityId, supplierId);
	}

	@Override
	public List<LtDeliveryAreas> getAllActiveAreasByPin(String pincode, Long supplierId) throws ServiceException {
		return ltDeliveryAreasDao.getAllActiveAreasByPin(pincode, supplierId);
	}

	@Override
	public List<LtDeliveryAreas> getAllPinCodes(Long supplierId) throws ServiceException {
		return ltDeliveryAreasDao.getAllPinCodes(supplierId);
	}

}
