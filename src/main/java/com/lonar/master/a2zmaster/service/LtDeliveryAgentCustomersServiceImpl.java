package com.lonar.master.a2zmaster.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.DeliveryAgentCustomers;
//import com.lonar.a2zcommons.model.LtDeliveryAgentCustomers;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.dao.LtDeliveryAgentCustomersDao;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.DeliveryAgentCustomers;
import com.lonar.master.a2zmaster.model.LtDeliveryAgentCustomers;
import com.lonar.master.a2zmaster.model.Status;

@Service
public class LtDeliveryAgentCustomersServiceImpl implements LtDeliveryAgentCustomersService,CodeMaster{

	@Autowired
	LtDeliveryAgentCustomersDao ltDeliveryAgentCustomersDao;
	
	@Autowired
	LtMastCommonMessageService ltMastCommonMessageService;
	
	@Override
	public CustomeDataTable getDataTable(LtDeliveryAgentCustomers input, Long supplierId) throws ServiceException {
		CustomeDataTable customeDataTable = new CustomeDataTable();
		Long count = ltDeliveryAgentCustomersDao.getDeliveryAgentCustomersCount(input,supplierId);
	    customeDataTable.setRecordsTotal(count);
	    customeDataTable.setRecordsFiltered(count);
		List<LtDeliveryAgentCustomers> deliveryAgentCustomersList= ltDeliveryAgentCustomersDao.getDeliveryAgentCustomersDataTable(input,supplierId);
		customeDataTable.setData(deliveryAgentCustomersList);
		return customeDataTable;
	}

	@Override
	public Status save(LtDeliveryAgentCustomers ltDeliveryAgentCustomers) throws ServiceException {
		Status status = new Status();
		ltDeliveryAgentCustomers.setLastUpdateDate(new Date());
		ltDeliveryAgentCustomers.setCreationDate(new Date());
		ltDeliveryAgentCustomers.setLastUpdatedBy(ltDeliveryAgentCustomers.getLastUpdateLogin());
		ltDeliveryAgentCustomers.setCreatedBy(ltDeliveryAgentCustomers.getLastUpdateLogin());
		ltDeliveryAgentCustomers.setStatus(ACTIVE);
		ltDeliveryAgentCustomers = ltDeliveryAgentCustomersDao.save(ltDeliveryAgentCustomers);
		if(ltDeliveryAgentCustomers.getDeliveryAssignmentId()!=null) {
			status=ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
			
			status.setData(ltDeliveryAgentCustomers.getDeliveryAssignmentId());
		}else {
			status=ltMastCommonMessageService.getCodeAndMessage(INSERT_FAIL);
		}
		return status;
	}

	@Override
	public Status update(LtDeliveryAgentCustomers ltDeliveryAgentCustomers) throws ServiceException {
		Status status = new Status();
		ltDeliveryAgentCustomers.setLastUpdateDate(new Date());
		ltDeliveryAgentCustomers.setLastUpdatedBy(ltDeliveryAgentCustomers.getLastUpdateLogin());
		ltDeliveryAgentCustomers.setStatus(ACTIVE);
		
		ltDeliveryAgentCustomers = ltDeliveryAgentCustomersDao.save(ltDeliveryAgentCustomers);
		if(ltDeliveryAgentCustomers.getDeliveryAssignmentId()!=null) {
			status=ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
			status.setData(ltDeliveryAgentCustomers.getDeliveryAssignmentId());
		}else {
			status=ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
		}
		return status;
	}

	@Override
	public LtDeliveryAgentCustomers getLtDeliveryAgentCustomers(Long deliveryAssId) throws ServiceException {
		return ltDeliveryAgentCustomersDao.getLtDeliveryAgentCustomers(deliveryAssId);
	}

	@Override
	public List<LtDeliveryAgentCustomers> getAllActiveDeliveryAgentCustomers(Long supplierId) throws ServiceException {
		return ltDeliveryAgentCustomersDao.getAllActiveDeliveryAgentCustomers(supplierId);
	}

	@Override
	public Status delete(Long deliveryAssignmentId) throws ServiceException {
		Status status = new Status();
		LtDeliveryAgentCustomers ltDeliveryAgentCustomers = ltDeliveryAgentCustomersDao.delete(deliveryAssignmentId);
		if(ltDeliveryAgentCustomers==null) {
			status=ltMastCommonMessageService.getCodeAndMessage(DELETE_SUCCESSFULLY);
		}else{ 
			status=ltMastCommonMessageService.getCodeAndMessage(DELETE_FAIL);
		}
		return status;
	}

	@Override
	public DeliveryAgentCustomers getAllAssignedCustomers(Long userId) throws ServiceException {
		DeliveryAgentCustomers deliveryAgentCustomer = ltDeliveryAgentCustomersDao.getDeliveryAgentInfoByUserId(userId);
	
		if(deliveryAgentCustomer!=null) {
			deliveryAgentCustomer.setCustomerList(ltDeliveryAgentCustomersDao.getAllAssignedCustomers(userId));
			return deliveryAgentCustomer;
		}else {
			return new DeliveryAgentCustomers();
		}
		
	}

	@Override
	public Status assignCustToAgent(DeliveryAgentCustomers deliveryAgentCustomers)
			throws ServiceException {
		Status status = new Status();
		/*status = checkCustomerAssignment(deliveryAgentCustomers.getCustomerList());
		if(status.getCode()==FAIL) {
			return status;
		}*/
		List<LtDeliveryAgentCustomers> custList = ltDeliveryAgentCustomersDao.getAllAssignedCustomers(deliveryAgentCustomers.getUserId());
		if(custList!=null && custList.size()>0) {
			if(ltDeliveryAgentCustomersDao.deleteAllAssignedCustByUser(deliveryAgentCustomers.getUserId(), deliveryAgentCustomers.getSupplierId())) {
				status = this.saveRecord(deliveryAgentCustomers);			
			}else {
				status = ltMastCommonMessageService.getCodeAndMessage(INSERT_FAIL);
			}
		}else {
			status = this.saveRecord(deliveryAgentCustomers);
		}
		return status;
	}

	private Status checkCustomerAssignment(List<LtDeliveryAgentCustomers> customerList) throws ServiceException {
		Status status = new Status();
		if(customerList!=null) {
			for(LtDeliveryAgentCustomers ltDeliveryAgentCustomers : customerList) {
				LtDeliveryAgentCustomers customer = ltDeliveryAgentCustomersDao.getLtDeliveryAgentCustomersByCustId(ltDeliveryAgentCustomers.getCustomerId());
				if(customer!=null) {
					status = ltMastCommonMessageService.getCodeAndMessage(INSERT_FAIL);
					status.setMessage("Customer is already assigned to Delivery agent");
					return status;
				}
			}
		}
		
		return null;
	}

	private Status saveRecord(DeliveryAgentCustomers deliveryAgentCustomers) throws ServiceException {
		Status status = new Status();
		List<LtDeliveryAgentCustomers>  ltDeliveryAgentCustomerList = new ArrayList<LtDeliveryAgentCustomers>();
		for(LtDeliveryAgentCustomers ltDeliveryAgentCustomers : deliveryAgentCustomers.getCustomerList()) {
			ltDeliveryAgentCustomers.setCustomerId(ltDeliveryAgentCustomers.getCustomerId());
			ltDeliveryAgentCustomers.setCustomerSeq(ltDeliveryAgentCustomers.getCustomerSeq());
			ltDeliveryAgentCustomers.setUserId(deliveryAgentCustomers.getUserId());
			ltDeliveryAgentCustomers.setSupplierId(deliveryAgentCustomers.getSupplierId());
			ltDeliveryAgentCustomers.setStatus(deliveryAgentCustomers.getStatus());
			ltDeliveryAgentCustomers.setStartDate(deliveryAgentCustomers.getStartDate());
			ltDeliveryAgentCustomers.setEndDate(deliveryAgentCustomers.getEndDate());
			ltDeliveryAgentCustomers.setLastUpdateDate(new Date());
			ltDeliveryAgentCustomers.setCreationDate(new Date());
			ltDeliveryAgentCustomers.setCreatedBy(deliveryAgentCustomers.getLastUpdateLogin());
			ltDeliveryAgentCustomers.setLastUpdatedBy(deliveryAgentCustomers.getLastUpdateLogin());
			ltDeliveryAgentCustomerList.add(ltDeliveryAgentCustomers);
		}
		List<LtDeliveryAgentCustomers> list = ltDeliveryAgentCustomersDao.assignCustToAgent(ltDeliveryAgentCustomerList);
		if(ltDeliveryAgentCustomerList.size() == list.size()) {
			status=ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
		}else {
			status=ltMastCommonMessageService.getCodeAndMessage(INSERT_FAIL);
		}
		return status;
	}

	@Override
	public CustomeDataTable getAllCustomers(LtDeliveryAgentCustomers input, Long supplierId,Long deliveryAgentId) throws ServiceException {
		CustomeDataTable customeDataTable = new CustomeDataTable();
		Long count = ltDeliveryAgentCustomersDao.getAllCustomersCount(input,supplierId,deliveryAgentId);
	    customeDataTable.setRecordsTotal(count);
	    customeDataTable.setRecordsFiltered(count);
		List<LtDeliveryAgentCustomers> deliveryAgentCustomersList = ltDeliveryAgentCustomersDao.getAllCustomersData(input, supplierId,deliveryAgentId);
		/*deliveryAgentCustomersList.forEach((ltDeliveryAgentCustomers) -> {
			try {
				ltDeliveryAgentCustomers.setDeliveryAgentName(ltDeliveryAgentCustomersDao.getDeliveryAgentName(ltDeliveryAgentCustomers.getCustomerId()));
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		});*/
		customeDataTable.setData(deliveryAgentCustomersList);
		return customeDataTable;
	}

}
