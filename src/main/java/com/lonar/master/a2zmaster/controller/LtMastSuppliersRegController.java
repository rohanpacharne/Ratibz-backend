package com.lonar.master.a2zmaster.controller;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

//import jakarta.servlet.http.HttpServletResponse;

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

import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtAboutUs;
import com.lonar.master.a2zmaster.model.LtMastLogins;
import com.lonar.master.a2zmaster.model.LtMastSupplierRegistration;
import com.lonar.master.a2zmaster.model.LtMastSuppliers;
import com.lonar.master.a2zmaster.model.LtSupplierInvoicePayments;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.service.LtMastSuppliersRegService;
import com.lonar.master.a2zmaster.service.LtMastUsersService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api/suppliersReg")
public class LtMastSuppliersRegController {
	
	@Autowired
	LtMastSuppliersRegService ltMastSuppliersRegService;
	
	@Autowired
	private LtMastUsersService ltMastUsersService;
	
	@GetMapping(value = "/getSuppliersRegById/{supplierRegId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LtMastSupplierRegistration> getLtMastSupplierRegById(@PathVariable("supplierRegId") Long supplierId ) throws ServiceException {
		try {
		return new ResponseEntity<LtMastSupplierRegistration>( ltMastSuppliersRegService.getLtMastSupplierRegById(supplierId), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//suppliersReg/saveLtMastSuppliersReg
	@PostMapping(value = "/saveLtMastSuppliersReg", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> saveLtMastSuppliersReg(@RequestBody LtMastSupplierRegistration ltMastSupplierRegistration) throws ServiceException{
		return new ResponseEntity<Status>(ltMastSuppliersRegService.saveLtMastSuppliersReg(ltMastSupplierRegistration), HttpStatus.OK);
	}

	// /suppliersReg/aboutus/{supplierId}/{longTime}
	@GetMapping(value="/aboutus/{supplierId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> getSupplierAboutUs(@PathVariable("supplierId") Long supplierId){
		return ltMastSuppliersRegService.getSupplierAboutUs(supplierId) ;
	}
	
	// /suppliersReg/sendsms/{mobileno}
	@GetMapping(value="/supplierregistrationotp/{mobileno}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> sendSmsToNewSupplier(@PathVariable("mobileno") String mobileNo, HttpServletRequest httpRequest){
		return ltMastSuppliersRegService.sendSmsToNewSupplier(mobileNo, httpRequest) ;
	}
		
	@PostMapping(value="/validateregistrationotp", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> validateSupplierRegistationOtp(@RequestBody LtMastLogins  login, HttpServletResponse response){
		return ltMastSuppliersRegService.validateSupplierRegistationOtp(login, response);
	}
		
	@PostMapping(value="/newsupplier", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> saveNewSupplier(@RequestBody LtMastSuppliers mastSuppliers, HttpServletResponse response){
		return ltMastSuppliersRegService.saveNewSupplier(mastSuppliers, response) ;
	}

	@GetMapping(value="/supplierdetails/{supplierId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> getSupplierDetails(@PathVariable("supplierId") Long supplierId ){
		return ltMastSuppliersRegService.getSupplierDetails(supplierId) ;
	}
	
	@PostMapping(value="/additionaldetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> saveSupplierAdditionalDetails(@RequestBody LtMastSuppliers mastSuppliers){
		return ltMastSuppliersRegService.saveSupplierAdditionalDetails(mastSuppliers) ;
	}

	@GetMapping(value="/additionaldetails/{supplierId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> getSupplierAdditionalDetails(@PathVariable("supplierId") Long supplierId){
		return ltMastSuppliersRegService.getSupplierAdditionalDetails(supplierId) ;
	}
	
	@PostMapping(value="/suppliercontacts", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> saveSupplierContactDetails(@RequestBody LtAboutUs aboutUs){
		return ltMastSuppliersRegService.saveSupplierContactDetails(aboutUs) ;
	}
	
	@GetMapping(value="/suppliercontacts/{supplierId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> getSupplierContactDetails(@PathVariable("supplierId") Long supplierId){
		return ltMastSuppliersRegService.getSupplierContactDetails(supplierId) ;
	}
	
	
	@GetMapping(value="/configuration/{supplierId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> getSupplierConfigurationDetails(@PathVariable("supplierId") Long supplierId){
		return ltMastSuppliersRegService.getSupplierConfigurationDetails(supplierId) ;
	}
	
	@PostMapping(value="/configuration", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> saveSupplierConfigurationDetails(@RequestBody LtMastSuppliers mastSuppliers){
		return ltMastSuppliersRegService.saveSupplierConfigurationDetails(mastSuppliers) ;
	}
	
	@PostMapping(value="/supplierpaymentdetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> saveSupplierPaymentDetails(@RequestBody LtMastSuppliers mastSupplier){
		return ltMastSuppliersRegService.saveSupplierPaymentDetails(mastSupplier) ;
	}
	
	@GetMapping(value="/supplierpaymentdetails/{supplierId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> getSupplierPaymentDetails(@PathVariable("supplierId") Long supplierId){
		return ltMastSuppliersRegService.getSupplierPaymentDetails(supplierId) ;
	}
	
	@GetMapping(value="/companyupidetails/{supplierId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> getCompanyUPIDetails(@PathVariable("supplierId") Long supplierId){
		return ltMastSuppliersRegService.getCompanyUPIDetails( supplierId) ;
	}

	
 //   /suppliersReg/activatetrialsupplier
	@PostMapping(value="/activatetrialsupplier", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> activateTrialSupplier(@RequestBody LtSupplierInvoicePayments payment ){
		return ltMastSuppliersRegService.activateTrialSupplier( payment) ;
	}
	
	
	@PostMapping(value="/supplierpayment", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> getSupplierPayment(@RequestBody LtSupplierInvoicePayments payment ){
		return ltMastSuppliersRegService.createSupplierInvoice( payment) ;
	}
	
	 //  /suppliersReg/sysvariable
	
	@GetMapping(value="/sysvariable/{supplierId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> saveSysVariable(@PathVariable("supplierId") Long supplierId ){
		return ltMastSuppliersRegService.saveSysVariable(supplierId) ;
	}

//  /users/getappversion/{SupplierCode}/{longTime} 
	@GetMapping(value="/getappversion/{supplierId}")
	public ResponseEntity<Status> getAppVersion(@PathVariable("supplierId") Long supplierId) {
		return ltMastUsersService.getAppVersion(supplierId);
	}
	//   suppliersReg/deliverytime/{supplierId}/{longTime}
	@GetMapping(value="/activedeliverytime/{supplierId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> getDeliveryTime(@PathVariable("supplierId") Long supplierId) {
		return ltMastUsersService.getDeliveryTime(supplierId);
	}
}
