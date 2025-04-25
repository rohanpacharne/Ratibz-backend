package com.lonar.master.a2zmaster.dashboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

//import com.lonar.a2zcommons.model.LtCustomerSubs;
//import com.lonar.a2zcommons.model.LtCustomerSubsDeliveries;
//import com.lonar.a2zcommons.model.LtSupplierDeliveryTimings;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.dao.LtCustomerSubsDao;
import com.lonar.master.a2zmaster.dao.LtCustomerSubsDeliveriesDao;
import com.lonar.master.a2zmaster.dao.LtDeliveryAgentCustomersDao;
import com.lonar.master.a2zmaster.dao.LtInvoicesDao;
import com.lonar.master.a2zmaster.dao.LtSupplierDeliveryTimingsDao;
import com.lonar.master.a2zmaster.model.LtCustomerSubs;
import com.lonar.master.a2zmaster.model.LtCustomerSubsDeliveries;
import com.lonar.master.a2zmaster.model.LtSupplierDeliveryTimings;
import com.lonar.master.a2zmaster.model.Status;

@Service
public class LtDashboardServiceImpl implements LtDashboardService{

	@Autowired
	private LtCustomerSubsDao ltCustomerSubsDao;
	
	@Autowired
	private LtDeliveryAgentCustomersDao ltDeliveryAgentCustomersDao;
	
	@Autowired
	private LtCustomerSubsDeliveriesDao ltCustomerSubsDeliveriesDao;
	
	@Autowired private LtSupplierDeliveryTimingsDao supplierDeliveryTimingsDao;
	@Autowired
	private LtInvoicesDao ltInvoicesDao;
	
	@Override
	public Map<String, Long> getDashboardCount(Long supplierId) throws ServiceException {
		try {
			Map<String, Long> dashboardCount= new HashMap<String, Long>();
	 		
			dashboardCount.put("PENDING_SUBS", ltCustomerSubsDao.getMonthlyWeeklyCustomerSubsCount(supplierId));
	 		dashboardCount.put("USER", ltCustomerSubsDao.getAllActiveUsersCountBySupplier(supplierId));
			dashboardCount.put("ACTIVE_SUBS", ltCustomerSubsDao.getAllActiveSubsCountBySupplier(supplierId));
	 		dashboardCount.put("UNASSIGNED_CUST", ltDeliveryAgentCustomersDao.getAllUnAssignedCustomersCount(supplierId ));
			return dashboardCount;
		}catch(Exception e) {
			e.printStackTrace();
			throw new ServiceException(500, null, e);
		}
	}

	@Override
	public List<LtCustomerSubsDeliveries> getNextDeliveries(Long supplierId) throws ServiceException {
		LtCustomerSubs ltCustomerSubs = ltCustomerSubsDeliveriesDao.getNextDeliveryDateTime(supplierId);
		if(ltCustomerSubs!=null) {
			ltCustomerSubs.setSupplierId(supplierId);
			List<LtCustomerSubsDeliveries>  ltCustomerSubsDeliveriesList = null;//28-8-2020  // ltCustomerSubsDeliveriesDao.getNextDeliveries(ltCustomerSubs);
			return ltCustomerSubsDeliveriesList;
		}else
			return null;
	}
	

	@Override
	public LtCustomerSubs getNextDeliveryDateTime(Long supplierId) throws ServiceException {
		return ltCustomerSubsDeliveriesDao.getNextDeliveryDateTime(supplierId);
	}

	@Override
	public ResponseEntity<Status> getNextDeliveryDateTimeWithList(Long supplierId) {
		Status status = new Status();
		try {
			LtSupplierDeliveryTimings supplierDeliveryTimings = supplierDeliveryTimingsDao.getNextDeliveryTiming(supplierId);
			supplierDeliveryTimings.setSupplierId(supplierId);
			//LtCustomerSubs ltCustomerSubs = ltCustomerSubsDeliveriesDao.getNextDeliveryDateTime(supplierId);
			//if(ltCustomerSubs!=null) {
				//ltCustomerSubs.setSupplierId(supplierId);
			List<LtCustomerSubsDeliveries>  customerSubsDeliveries = ltCustomerSubsDeliveriesDao.getNextDeliveries(supplierDeliveryTimings);
				//ltCustomerSubs.setCustomerSubsDeliveries(customerSubsDeliveries);
			//} 			
			status.setCode(200);
			status.setData(customerSubsDeliveries);
			status.setData2(supplierDeliveryTimings);
		}catch(Exception e ) {
			e.printStackTrace();
			return new ResponseEntity<Status>(status, HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<Status>(status, HttpStatus.OK); 
	}


	@Override
	public List<InvoicePayments> getInvoiceVsPayments(Long supplierId) throws ServiceException {
		return ltInvoicesDao.getInvoiceVsPayments(supplierId);
	}

	
}
