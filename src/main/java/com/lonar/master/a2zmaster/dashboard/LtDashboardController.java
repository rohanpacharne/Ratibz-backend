package com.lonar.master.a2zmaster.dashboard;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//import com.lonar.a2zcommons.model.LtCustomerSubs;
//import com.lonar.a2zcommons.model.LtCustomerSubsDeliveries;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtCustomerSubs;
import com.lonar.master.a2zmaster.model.LtCustomerSubsDeliveries;
import com.lonar.master.a2zmaster.model.Status;

@RestController
@RequestMapping("/dashboard")
public class LtDashboardController {

	@Autowired
	private LtDashboardService ltDashboardService;
	
	@RequestMapping(value = "/getDashboardCount/{supplierId}/{logTime}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String,Long>> getDashboardCount(@PathVariable("supplierId") Long supplierId,@PathVariable("logTime") String logTime) throws ServiceException {
		try {
			return new ResponseEntity<Map<String,Long>>( ltDashboardService.getDashboardCount(supplierId), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/getNextDeliveryDateTime/{supplierId}/{logTime}", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LtCustomerSubs> getNextDeliveryDateTime(@PathVariable("supplierId") Long supplierId,@PathVariable("logTime") String logTime) throws ServiceException {
		return new ResponseEntity<LtCustomerSubs>(ltDashboardService.getNextDeliveryDateTime(supplierId), HttpStatus.OK);
	}
		
	@RequestMapping(value = "/getNextDeliveries/{supplierId}/{logTime}", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtCustomerSubsDeliveries>> getNextDeliveries(@PathVariable("supplierId") Long supplierId,@PathVariable("logTime") String logTime) throws ServiceException {
		return new ResponseEntity<List<LtCustomerSubsDeliveries>>(ltDashboardService.getNextDeliveries(supplierId), HttpStatus.OK);
	}
	
	@GetMapping(value = "/getnextdeliverytimeandlist/{supplierId}/{logTime}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> getNextDeliveryDateTimeWithList(@PathVariable("supplierId") Long supplierId ) throws ServiceException {
		return ltDashboardService.getNextDeliveryDateTimeWithList(supplierId);
	}
		
	
	@RequestMapping(value = "/getInvoiceVsPayments/{supplierId}/{logTime}", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity< List<InvoicePayments> > getInvoiceVsPayments(@PathVariable("supplierId") Long supplierId,@PathVariable("logTime") String logTime) throws ServiceException {
		return new ResponseEntity< List<InvoicePayments> >(ltDashboardService.getInvoiceVsPayments(supplierId), HttpStatus.OK);
	}
}
