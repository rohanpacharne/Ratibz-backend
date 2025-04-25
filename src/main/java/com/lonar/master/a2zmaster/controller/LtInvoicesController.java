package com.lonar.master.a2zmaster.controller;

import java.rmi.ServerException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.InvoicePdfData;
//import com.lonar.a2zcommons.model.LtInvoices;
import com.lonar.master.a2zmaster.common.BusinessException;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.InvoicePdfData;
import com.lonar.master.a2zmaster.model.LtInvoices;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.service.LtInvoicesService;

@RestController
@RequestMapping("/api/invoices")
public class LtInvoicesController implements CodeMaster {
	
	@Autowired
	LtInvoicesService ltInvoicesService;
	 
	@GetMapping(value = "/create/{supplierId}/{invoiceCycle}/{logTime}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> create(@PathVariable("supplierId") Long supplierId,
			@PathVariable("invoiceCycle") String invoiceCycle, @PathVariable("logTime") String logTime) throws ServerException {
		try {
			return new ResponseEntity<Status>(ltInvoicesService.create(supplierId, invoiceCycle, logTime),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(0, null, e);
		}
	}
	
	// API to create Adhoc invoice for customer
		@PostMapping(value = "/createInvoice", consumes = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Status> createInvoice(@RequestBody LtInvoices ltInvoices) {
				return new ResponseEntity<Status>(ltInvoicesService.createInvoice(ltInvoices), HttpStatus.OK);
		}
		
		// API to edit invoice by invoiceId
		@GetMapping(value = "/getInvoicesById/{invoiceId}/{logTime}", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<LtInvoices> getInvoicesById(@PathVariable("invoiceId") Long invoiceId,
				@PathVariable("logTime") String logTime) throws ServiceException {
			return new ResponseEntity<LtInvoices>(ltInvoicesService.getInvoicesById(invoiceId), HttpStatus.OK);
		}
		
		// API to show all invoices to user
		@GetMapping(value = "/getAllInvoicesByUser/{userId}/{logTime}",  produces = MediaType.APPLICATION_JSON_VALUE)
		public CustomeDataTable getAllInvoicesByUser(@PathVariable("userId") Long userId, LtInvoices input,
				@PathVariable("logTime") String logTime) throws ServiceException {
			return ltInvoicesService.getAllInvoicesByUser(userId, input);
		}
		
		// API to show all users all invoices to supplier
		@GetMapping(value = "/getAllInvoicesBySupplier/{supplierId}", produces = MediaType.APPLICATION_JSON_VALUE)
		public CustomeDataTable getAllInvoicesBySupplier(@PathVariable("supplierId") Long supplierId, LtInvoices input
				) throws ServiceException {
			return ltInvoicesService.getAllInvoicesBySupplier(supplierId, input);
		}
		
		@GetMapping(value = "/getInvoicesData/{supplierId}/{logTime}",  produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<InvoicePdfData>> getInvoicesData(@PathVariable("supplierId") Long supplierId,
				@PathVariable("logTime") String logTime) throws ServiceException {
			return new ResponseEntity<List<InvoicePdfData>>(ltInvoicesService.getInvoicesData(supplierId), HttpStatus.OK);
		}
		
		// API to create Invoice PDF
		@GetMapping(value = "/createPdf/{invoiceId}/{logTime}", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Status> createPdf(@PathVariable("invoiceId") Long invoiceId,
				@PathVariable("logTime") String logTime) throws ServiceException {
			return new ResponseEntity<Status>(ltInvoicesService.createPdf(invoiceId), HttpStatus.OK);
		}
		
		@GetMapping(value="/createpostpaidInvoice")
		public void createPostpaidInvoice(){
			try {
				ltInvoicesService.createPostpaidInvoice("D");		
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

}
