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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.LtDeliveryAreaAgents;
import com.lonar.master.a2zmaster.common.BusinessException;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtDeliveryAreaAgents;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.service.LtDeliveryAreaAgentsService;

@RestController
@RequestMapping("/api/deliveryAreaAgents")
public class LtDeliveryAreaAgentsController implements CodeMaster {

	@Autowired
	LtDeliveryAreaAgentsService ltDeliveryAreaAgentsService;
	
	@RequestMapping(value = "/datatable/{areaId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomeDataTable getDataTable(@PathVariable("areaId") Long areaId, LtDeliveryAreaAgents input,
			@RequestParam(value = "search", required = false) String search) throws ServiceException{
		
		System.out.println("in datatable:"+input+"   areaId:"+areaId);
		 if (search != null && !search.isEmpty()) {
		        input.setArea(search);
		    }
		return ltDeliveryAreaAgentsService.getDataTable(input,areaId) ;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> save( @RequestBody LtDeliveryAreaAgents ltDeliveryAreaAgents) throws ServerException {
		try {
		return new ResponseEntity<Status>(ltDeliveryAreaAgentsService.save(ltDeliveryAreaAgents), HttpStatus.OK);
		}catch(Exception e) {
			throw new BusinessException(0, null, e);
		}
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> update( @RequestBody LtDeliveryAreaAgents ltDeliveryAreaAgents) throws ServerException {
		try {
			return new ResponseEntity<Status>(ltDeliveryAreaAgentsService.update(ltDeliveryAreaAgents), HttpStatus.OK);
			}catch(Exception e) {
				throw new BusinessException(0, null, e);
		}
	}
	
	@RequestMapping(value = "/getById/{areaBoyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LtDeliveryAreaAgents> getLtDeliveryAreaAgentsById(@PathVariable("areaBoyId") Long areaBoyId) throws ServiceException {
		return new ResponseEntity<LtDeliveryAreaAgents>( ltDeliveryAreaAgentsService.getLtDeliveryAreaAgentsById(areaBoyId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAllDeliveryAreaAgents/{areaId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtDeliveryAreaAgents>> getAllDeliveryAreaAgents(@PathVariable("areaId") Long areaId) throws ServiceException {
		return new ResponseEntity<List<LtDeliveryAreaAgents>>( ltDeliveryAreaAgentsService.getAllDeliveryAreaAgents(areaId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAllDeliveryAgents/{supplierId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtDeliveryAreaAgents>> getAllDeliveryAgents(@PathVariable("supplierId") Long supplierId) throws ServiceException {
		return new ResponseEntity<List<LtDeliveryAreaAgents>>( ltDeliveryAreaAgentsService.getAllDeliveryAgents(supplierId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/delete/{areaBoyId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> delete(@PathVariable("areaBoyId") Long areaBoyId) throws ServiceException {
		return new ResponseEntity<Status>( ltDeliveryAreaAgentsService.delete(areaBoyId), HttpStatus.OK);
	}
	
}
