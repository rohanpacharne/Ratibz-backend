package com.lonar.master.a2zmaster.controller;

import java.rmi.ServerException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//import com.lonar.a2zcommons.model.LtSupplierDeliveryTimings;
import com.lonar.master.a2zmaster.common.BusinessException;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.LtSupplierDeliveryTimings;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.service.LtSupplierDeliveryTimingsService;

@RestController
@RequestMapping("/api/deliveryTiming")
public class LtSupplierDeliveryTimingsController implements CodeMaster{

	@Autowired
	LtSupplierDeliveryTimingsService ltSupplierDeliveryTimingsService;
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> save( @RequestBody LtSupplierDeliveryTimings ltSupplierDeliveryTimings) throws ServerException {
		try {
		return new ResponseEntity<Status>(ltSupplierDeliveryTimingsService.save(ltSupplierDeliveryTimings), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException(0, null, e);
		}
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> update( @RequestBody LtSupplierDeliveryTimings ltSupplierDeliveryTimings) throws ServerException {
		try {
			return new ResponseEntity<Status>(ltSupplierDeliveryTimingsService.update(ltSupplierDeliveryTimings), HttpStatus.OK);
			}catch(Exception e) {
				throw new BusinessException(0, null, e);
		}
	}
	
	@RequestMapping(value = "/getById/{deliveryTimeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LtSupplierDeliveryTimings> getLtSupplierDeliveryTimingsById(@PathVariable("deliveryTimeId") Long deliveryTimeId) throws ServiceException {
		return new ResponseEntity<LtSupplierDeliveryTimings>( ltSupplierDeliveryTimingsService.getLtSupplierDeliveryTimingsById(deliveryTimeId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAllDeliveryTimings/{supplierId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtSupplierDeliveryTimings>> getAllDeliveryTimings(@PathVariable("supplierId") Long supplierId) throws ServiceException {
		return new ResponseEntity<List<LtSupplierDeliveryTimings>>( ltSupplierDeliveryTimingsService.getAllDeliveryTimings(supplierId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/delete/{deliveryTimeId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> delete(@PathVariable("deliveryTimeId") Long deliveryTimeId) throws ServiceException {
		return new ResponseEntity<Status>( ltSupplierDeliveryTimingsService.delete(deliveryTimeId), HttpStatus.OK);
	}
}
