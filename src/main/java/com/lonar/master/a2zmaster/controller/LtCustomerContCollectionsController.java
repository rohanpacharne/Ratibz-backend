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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.LtCustomerContCollections;
//import com.lonar.a2zcommons.model.LtCustomerSubsDeliveries;
import com.lonar.master.a2zmaster.common.BusinessException;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtCustomerContCollections;
import com.lonar.master.a2zmaster.model.LtCustomerSubsDeliveries;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.service.LtCustomerContCollectionsService;

@RestController
@RequestMapping("/api/containerCollection")
public class LtCustomerContCollectionsController implements CodeMaster{

	@Autowired
	private LtCustomerContCollectionsService  ltCustomerContCollectionsService;
	
	// API to save collected containers quantity against a customer
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> save( @RequestBody LtCustomerContCollections ltCustomerContCollections) throws ServerException {
		try {
		return new ResponseEntity<Status>(ltCustomerContCollectionsService.save(ltCustomerContCollections), HttpStatus.OK);
		}catch(Exception e) {
			throw new BusinessException(0, null, e);
		}
	}
	
	// API to update/modify collected containers quantity against a customer
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> update( @RequestBody LtCustomerContCollections ltCustomerContCollections) throws ServerException {
		try {
			return new ResponseEntity<Status>(ltCustomerContCollectionsService.update(ltCustomerContCollections), HttpStatus.OK);
			}catch(Exception e) {
				throw new BusinessException(0, null, e);
		}
	}
	
	// API to edit collected containers quantity against a containerCollectionId
	@RequestMapping(value = "/getById/{conCollId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LtCustomerContCollections> getLtCustomerContCollectionsById(@PathVariable("conCollId") Long conCollId) throws ServiceException {
		return new ResponseEntity<LtCustomerContCollections>( ltCustomerContCollectionsService.getLtCustomerContCollectionsById(conCollId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/delete/{conCollId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> delete(@PathVariable("conCollId") Long conCollId) throws ServiceException {
		return new ResponseEntity<Status>( ltCustomerContCollectionsService.delete(conCollId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/collectionSummary", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtCustomerSubsDeliveries>> collectionSummary(@RequestBody LtCustomerContCollections ltCustomerContCollections)throws ServiceException {
		return new ResponseEntity<List<LtCustomerSubsDeliveries>>( ltCustomerContCollectionsService.collectionSummary(ltCustomerContCollections), HttpStatus.OK);
	}
	
	// API to show collected containers quantity to a supplier
	@RequestMapping(value = "/collectionDatatable/{supplierId}",  method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomeDataTable collectionDatatable(@PathVariable("supplierId") Long supplierId, LtCustomerContCollections ltCustomerContCollections)throws ServiceException {
		return ltCustomerContCollectionsService.getDataTable(ltCustomerContCollections,supplierId) ;
	}
	
	@GetMapping(value = "/getcontainerdeliverandcollectedqty/{subId}",   produces = MediaType.APPLICATION_JSON_VALUE)
	public LtCustomerContCollections getContainerDeliverAndCollectedQty(@PathVariable("subId") Long subId )throws ServiceException {
		return ltCustomerContCollectionsService.getContainerDeliverAndCollectedQty( subId ) ;
	}
	
	@PostMapping(value = "/savedelivereandcollectqty", consumes = MediaType.APPLICATION_JSON_VALUE,   produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> saveDelivereAndCollectQty(@RequestBody LtCustomerContCollections collections) {
		return ltCustomerContCollectionsService.saveDelivereAndCollectQty( collections ) ;
	}
	
	@GetMapping(value = "/containerdeliverandcollectedqtyreport",   produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomeDataTable> getCollectionQtyReport( LtCustomerContCollections input ){
		return ltCustomerContCollectionsService.getCollectionQtyReport( input ) ;
	}
	
	
}
