package com.lonar.master.a2zmaster.dashboard;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

//import com.lonar.a2zcommons.model.LtCustomerSubs;
//import com.lonar.a2zcommons.model.LtCustomerSubsDeliveries;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtCustomerSubs;
import com.lonar.master.a2zmaster.model.LtCustomerSubsDeliveries;
import com.lonar.master.a2zmaster.model.Status;

public interface LtDashboardService {

	Map<String, Long> getDashboardCount(Long supplierId) throws ServiceException;

	List<LtCustomerSubsDeliveries> getNextDeliveries(Long supplierId) throws ServiceException;

	List<InvoicePayments>  getInvoiceVsPayments(Long supplierId) throws ServiceException;

	LtCustomerSubs getNextDeliveryDateTime(Long supplierId) throws ServiceException;
	
	ResponseEntity<Status> getNextDeliveryDateTimeWithList(Long supplierId);

}
