package com.lonar.master.a2zmaster.controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lonar.master.a2zmaster.model.LtCustomerSubsDeliveries;
import com.lonar.master.a2zmaster.model.LtMastProductTypes;
import com.lonar.master.a2zmaster.model.LtMastSuppliers;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.service.LtMastProductTypesService;

@RestController
@RequestMapping("/api/customer")
public class LtMastProductTypesController {
	
	@Autowired
	LtMastProductTypesService ltMastProductTypesService;
	
	
	
//	 @RequestMapping(value = "/allProductTypes" ,method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
//	 public List<LtMastProductTypes> getAllProductTypes(LtMastProductTypes input) {       
//		 return ltMastProductTypesService.getAllProductTypes(input);
//	 }
	
	@RequestMapping(value = "/allProductTypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<LtMastProductTypes>> getAllProductTypes(
				@RequestParam(value = "search", required = false) String search,
				@RequestParam(value = "start", required = false) Integer start,
			    @RequestParam(value = "length", required = false) Integer length) {
		
		
		 LtMastProductTypes input = new LtMastProductTypes();
//		   
		    if (search != null && !search.isEmpty()) {
		    	input.setProductType(search);
		    }
		    
		    input.setStart(start);
		    input.setLength(length);
		
		    List<LtMastProductTypes> result = ltMastProductTypesService.getAllProductTypes(input);
		    
		    if (result == null || result.isEmpty()) {
		        return ResponseEntity.noContent().build(); // 204 No Content
		    } else {
		        return ResponseEntity.ok(result); // 200 OK with data
		    }
		}

	 
	 @GetMapping("/getTodaysDeliveryDetails")
	 public ResponseEntity<Status> getDeliveryDetails(@RequestBody LtCustomerSubsDeliveries request) {
	     return new ResponseEntity<Status>( ltMastProductTypesService.getDeliveryDetails(request),HttpStatus.OK);
	 }

//	 @GetMapping("/getAllSupplierList")
//	 public List<LtMastSuppliers> getAllSupplierList(@RequestBody LtMastSuppliers input) {
//		 return ltMastProductTypesService.getAllSupplierList(input);
//	 }
	 
	 @PostMapping("/getAllSupplierList")
	 public ResponseEntity< Status> getAllSupplierList(
	     @RequestBody LtMastSuppliers ltMastSuppliers ,
	     @RequestParam(required = false, defaultValue = "0") int start,
	     @RequestParam(required = false, defaultValue = "10") int length,
	     @RequestParam(required = false, defaultValue = "1") int columnNo
	 ) {
	     LtMastSuppliers input = new LtMastSuppliers();
//	     input.setPinCode(pinCode);
	     input.setStart(start);
	     input.setLength(length);
	     input.setColumnNo(columnNo);
	     return new ResponseEntity<Status> (ltMastProductTypesService.getAllSupplierList(ltMastSuppliers,input),HttpStatus.OK);
	 }


}
