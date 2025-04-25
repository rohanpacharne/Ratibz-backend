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
//import com.lonar.a2zcommons.model.LtSupplierStates;
import com.lonar.master.a2zmaster.common.BusinessException;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtSupplierStates;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.service.LtSupplierStatesService;

@RestController
@RequestMapping("/api/state")
public class LtSupplierStatesController implements CodeMaster{

	@Autowired
	private LtSupplierStatesService ltSupplierStatesService;
	
	@RequestMapping(value = "/datatable/{supplierId}/{logTime}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomeDataTable getDataTable(@PathVariable("supplierId") Long supplierId, LtSupplierStates input,@PathVariable("logTime") String logTime) throws ServiceException{
		return ltSupplierStatesService.getDataTable(input,supplierId) ;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> save( @RequestBody LtSupplierStates ltSupplierStates) throws ServerException {
		try {
		return new ResponseEntity<Status>(ltSupplierStatesService.save(ltSupplierStates), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException(0, null, e);
		}
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> update( @RequestBody LtSupplierStates ltSupplierStates) throws ServerException {
		try {
			return new ResponseEntity<Status>(ltSupplierStatesService.update(ltSupplierStates), HttpStatus.OK);
			}catch(Exception e) {
				throw new BusinessException(0, null, e);
		}
	}
	
	@RequestMapping(value = "/getById/{stateId}/{logTime}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LtSupplierStates> getStatesById(@PathVariable("stateId") Long stateId,@PathVariable("logTime") String logTime) throws ServiceException {
		return new ResponseEntity<LtSupplierStates>( ltSupplierStatesService.getStatesById(stateId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAllStates/{supplierId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtSupplierStates>> getAllStates(@PathVariable("supplierId") Long supplierId) throws ServiceException {
		return new ResponseEntity<List<LtSupplierStates>>( ltSupplierStatesService.getAllStates(supplierId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/delete/{stateId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> delete(@PathVariable("stateId") Long stateId) throws ServiceException {
		return new ResponseEntity<Status>( ltSupplierStatesService.delete(stateId), HttpStatus.OK);
	}
	
}
