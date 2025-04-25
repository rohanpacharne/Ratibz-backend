package com.lonar.master.a2zmaster.controller;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.lonar.a2zcommons.model.Ledger;
//import com.lonar.a2zcommons.model.LtInvoicePayments;
//import com.lonar.a2zcommons.model.LtWalletBalance;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.Ledger;
import com.lonar.master.a2zmaster.model.LtInvoicePayments;
import com.lonar.master.a2zmaster.model.LtWalletBalance;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.service.LtInvoicePaymentsService;

@RestController
@RequestMapping("/api/payments")
public class LtInvoicePaymentsController {

	@Autowired
	LtInvoicePaymentsService ltInvoicePaymentsService;
	
	@RequestMapping(value = "/savePaymentWithFile", method = RequestMethod.POST)
	public ResponseEntity<Status> savePaymentWithFile(@RequestParam("uploadfile") MultipartFile[] uploadfile,
				@RequestParam("ltInvoicePayments") String data) throws ServiceException, JsonParseException, JsonMappingException, IOException, ParseException 
	{
		try {
			LtInvoicePayments ltInvoicePayments = new ObjectMapper().readValue(data, LtInvoicePayments.class);
		if(uploadfile.length > 0) {
			return new ResponseEntity<Status>(ltInvoicePaymentsService.savePaymentWithFile(ltInvoicePayments,uploadfile[0]), HttpStatus.OK);
		}else {
			return new ResponseEntity<Status>( ltInvoicePaymentsService.savePaymentWithFile(ltInvoicePayments,null), HttpStatus.OK);
		}
		}catch(Exception e) {
			e.printStackTrace();
		}return new ResponseEntity<Status>( ltInvoicePaymentsService.savePaymentWithFile(null,null), HttpStatus.OK);
	}
	
	//  /payments/getbalanceamount/{userId}/{supplierId}/{longTime}
	
	@GetMapping(value="/getbalanceamount/{userId}/{supplierId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<Status> getBalanceAmount(@PathVariable("userId") Long userId, @PathVariable("supplierId") Long supplierId){
		return  ltInvoicePaymentsService.getBalanceAmount(userId, supplierId);
	}
	
	
	@GetMapping(value="/walletbalance/{userId}/{supplierId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<Status> getWalletBalance(@PathVariable("userId") Long userId, @PathVariable("supplierId") Long supplierId){
		return  ltInvoicePaymentsService.getWalletBalance(userId, supplierId);
	}
		
	@GetMapping(value = "/getInvoicePaymentById/{invoicePaymentId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LtInvoicePayments> getInvoicePaymentById(@PathVariable("invoicePaymentId") Long invoicePaymentId) throws ServiceException {
		return new ResponseEntity<LtInvoicePayments>( ltInvoicePaymentsService.getInvoicePaymentById(invoicePaymentId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getInvPaymentByInvoiceId/{invoiceId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LtInvoicePayments> getInvPaymentByInvoiceId(@PathVariable("invoiceId") Long invoiceId) throws ServiceException {
		return new ResponseEntity<LtInvoicePayments>( ltInvoicePaymentsService.getInvPaymentByInvoiceId(invoiceId), HttpStatus.OK);
	}
	
	@PostMapping(value = "/acknowledge/{paymentId}")
	public ResponseEntity<Status> acknowledgePayment(@PathVariable("paymentId") Long paymentId) throws ServiceException {
		try {
			return new ResponseEntity<Status>( ltInvoicePaymentsService.acknowledgePayment(paymentId), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/ledger/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Ledger> ledgerByUserId(@PathVariable("userId") Long userId,@PathVariable("logTime") String logTime) throws ServiceException{
		try {
			return new ResponseEntity<Ledger>( ltInvoicePaymentsService.ledgerByUserId(userId), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@PostMapping(value = "/refundpayment", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> saveRefundPayment(@RequestBody LtWalletBalance walletBalance) throws ServiceException {
		try {
			return new ResponseEntity<Status>( ltInvoicePaymentsService.saveRefundPayment(walletBalance), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
