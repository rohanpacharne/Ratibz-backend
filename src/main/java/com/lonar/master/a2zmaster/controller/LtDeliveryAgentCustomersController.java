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
//import com.lonar.a2zcommons.model.DeliveryAgentCustomers;
//import com.lonar.a2zcommons.model.LtDeliveryAgentCustomers;
import com.lonar.master.a2zmaster.common.BusinessException;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.DeliveryAgentCustomers;
import com.lonar.master.a2zmaster.model.LtDeliveryAgentCustomers;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.service.LtDeliveryAgentCustomersService;

@RestController
@RequestMapping("/api/deliveryagentCustomer")
public class LtDeliveryAgentCustomersController implements CodeMaster {

	@Autowired
	LtDeliveryAgentCustomersService ltDeliveryAgentCustomersService;
	
	
	@RequestMapping(value = "/datatable/{supplierId}/{logTime}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomeDataTable getDataTable(@PathVariable("supplierId") Long supplierId, LtDeliveryAgentCustomers input,@PathVariable("logTime") String logTime) throws ServiceException{
		return ltDeliveryAgentCustomersService.getDataTable(input,supplierId) ;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> save( @RequestBody LtDeliveryAgentCustomers ltDeliveryAgentCustomers) throws ServerException {
		try {
		return new ResponseEntity<Status>(ltDeliveryAgentCustomersService.save(ltDeliveryAgentCustomers), HttpStatus.OK);
		}catch(Exception e) {
			throw new BusinessException(0, null, e);
		}
	}
	
	// API to assign customer to delivery agent
	@RequestMapping(value = "/assignCustToAgent", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> assignCustToAgent( @RequestBody DeliveryAgentCustomers deliveryAgentCustomers) throws ServerException {
		try {
		return new ResponseEntity<Status>(ltDeliveryAgentCustomersService.assignCustToAgent(deliveryAgentCustomers), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException(0, null, e);
		}
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> update( @RequestBody LtDeliveryAgentCustomers ltDeliveryAgentCustomers) throws ServerException {
		try {
			return new ResponseEntity<Status>(ltDeliveryAgentCustomersService.update(ltDeliveryAgentCustomers), HttpStatus.OK);
			}catch(Exception e) {
				throw new BusinessException(0, null, e);
		}
	}
	
	@RequestMapping(value = "/getById/{deliveryAssId}/{logTime}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LtDeliveryAgentCustomers> getLtProductCategoriesById(@PathVariable("deliveryAssId") Long deliveryAssId,@PathVariable("logTime") String logTime) throws ServiceException {
		return new ResponseEntity<LtDeliveryAgentCustomers>( ltDeliveryAgentCustomersService.getLtDeliveryAgentCustomers(deliveryAssId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAllActiveDeliveryAgentCustomers/{supplierId}/{logTime}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtDeliveryAgentCustomers>> getAllActiveDeliveryAgentCustomers(@PathVariable("supplierId") Long supplierId,@PathVariable("logTime") String logTime) throws ServiceException {
		return new ResponseEntity<List<LtDeliveryAgentCustomers>>( ltDeliveryAgentCustomersService.getAllActiveDeliveryAgentCustomers(supplierId), HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getAllAssignedCustomers/{userId}/{logTime}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DeliveryAgentCustomers> getAllAssignedCustomers(@PathVariable("userId") Long userId,@PathVariable("logTime") String logTime) throws ServiceException {
		return new ResponseEntity<DeliveryAgentCustomers>( ltDeliveryAgentCustomersService.getAllAssignedCustomers(userId), HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/delete/{deliveryAssignmentId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> delete(@PathVariable("deliveryAssignmentId") Long deliveryAssignmentId) throws ServiceException {
		return new ResponseEntity<Status>( ltDeliveryAgentCustomersService.delete(deliveryAssignmentId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/v1/getAllCustomers/{supplierId}/{deliveryAgentId}/{logTime}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomeDataTable getAllCustomers(@PathVariable("supplierId") Long supplierId,@PathVariable("deliveryAgentId") Long deliveryAgentId, LtDeliveryAgentCustomers input,@PathVariable("logTime") String logTime) throws ServiceException{
		return ltDeliveryAgentCustomersService.getAllCustomers(input,supplierId,deliveryAgentId) ;
	}
}
