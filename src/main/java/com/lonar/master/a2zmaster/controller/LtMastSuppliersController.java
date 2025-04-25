package com.lonar.master.a2zmaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//import com.lonar.a2zcommons.model.LtMastSuppliers;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtMastSuppliers;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.service.LtMastSuppliersService;

@RestController
@RequestMapping("/api/suppliers")
public class LtMastSuppliersController {

	@Autowired
	LtMastSuppliersService ltMastSuppliersService;
	
	@GetMapping(value = "/getById/{supplierId}/{logTime}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LtMastSuppliers> getLtMastSuppliersById(@PathVariable("supplierId") Long supplierId,@PathVariable("logTime") String logTime) throws ServiceException {
		try {
		return new ResponseEntity<LtMastSuppliers>( ltMastSuppliersService.getLtMastSuppliersById(supplierId), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/getPaymentDetailsById/{supplierId}/{logTime}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LtMastSuppliers> getPaymentDetailsById(@PathVariable("supplierId") Long supplierId,@PathVariable("logTime") String logTime) throws ServiceException {
		try {
		return new ResponseEntity<LtMastSuppliers>( ltMastSuppliersService.getPaymentDetailsById(supplierId), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping(value = "/getBySuppCode/{supplierCode}/{logTime}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> getBySuppCode(@PathVariable("supplierCode") String supplierCode,@PathVariable("logTime") String logTime) throws ServiceException {
		try {
		return new ResponseEntity<Status>( ltMastSuppliersService.getBySuppCode(supplierCode), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//  /suppliers/getsupplierinfo/{supplierCode}/{logTime}
	 
	@GetMapping(value = "/getsupplierinfo/{supplierCode}/{logTime}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> getSupplierDetails(@PathVariable("supplierCode") String supplierCode,@PathVariable("logTime") String logTime) throws ServiceException {
		try {
			return new ResponseEntity<Status>( ltMastSuppliersService.getSupplierInfromation(supplierCode), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
