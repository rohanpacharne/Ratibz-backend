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

//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.LtSupplierCities;
import com.lonar.master.a2zmaster.common.BusinessException;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.LtSupplierCities;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.service.LtSupplierCitiesService;

@RestController
@RequestMapping("/api/city")
public class LtSupplierCitiesController implements CodeMaster{

	@Autowired
	LtSupplierCitiesService ltSupplierCitiesService;
	
	
	@RequestMapping(value = "/getById/{cityId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LtSupplierCities> getLtSupplierCitiesById(@PathVariable("cityId") Long cityId) throws ServiceException {
		return new ResponseEntity<LtSupplierCities>( ltSupplierCitiesService.getLtSupplierCitiesById(cityId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAllCities/{supplierId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtSupplierCities>> getAllLtSupplierCities(@PathVariable("supplierId") Long supplierId) throws ServiceException {
		return new ResponseEntity<List<LtSupplierCities>>( ltSupplierCitiesService.getAllLtSupplierCities(supplierId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAllCitiesByState/{stateId}/{supplierId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtSupplierCities>> getAllCitiesByState(@PathVariable("stateId") Long stateId,@PathVariable("supplierId") Long supplierId) throws ServiceException {
		return new ResponseEntity<List<LtSupplierCities>>( ltSupplierCitiesService.getAllCitiesByState(stateId,supplierId), HttpStatus.OK);
	}

	
}
