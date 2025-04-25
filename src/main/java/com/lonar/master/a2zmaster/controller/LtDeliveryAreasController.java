package com.lonar.master.a2zmaster.controller;

import java.rmi.ServerException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.LtDeliveryAreas;
import com.lonar.master.a2zmaster.common.BusinessException;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtDeliveryAreas;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.service.LtDeliveryAreasService;

@RestController
@RequestMapping("/api/deliveryAreas")
public class LtDeliveryAreasController implements CodeMaster {

	@Autowired
	LtDeliveryAreasService ltDeliveryAreasService;
	
	@RequestMapping(value = "/datatable/{supplierId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomeDataTable getDataTable(@PathVariable("supplierId") Long supplierId, LtDeliveryAreas input
			,@RequestParam(value = "search", required = false) String search) throws ServiceException{
		
		 if (search != null && !search.isEmpty()) {
		        input.setArea(search);
		    }
		return ltDeliveryAreasService.getDataTable(input,supplierId) ;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> save( @RequestBody LtDeliveryAreas ltDeliveryAreas) throws ServerException {
		try {
		return new ResponseEntity<Status>(ltDeliveryAreasService.save(ltDeliveryAreas), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException(0, null, e);
		}
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> update( @RequestBody LtDeliveryAreas ltDeliveryAreas) throws ServerException {
		try {
			return new ResponseEntity<Status>(ltDeliveryAreasService.update(ltDeliveryAreas), HttpStatus.OK);
			}catch(Exception e) {
				e.printStackTrace();
				throw new BusinessException(0, null, e);
		}
	}
	
	@GetMapping(value = "/getById/{areaId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LtDeliveryAreas> getLtDeliveryAreasById(@PathVariable("areaId") Long areaId) throws ServiceException {
		return new ResponseEntity<LtDeliveryAreas>( ltDeliveryAreasService.getLtDeliveryAreasById(areaId), HttpStatus.OK);
	}
	
	@GetMapping(value = "/getAllActiveDeliveryAreas/{supplierId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtDeliveryAreas>> getAllActiveDeliveryAreas(@PathVariable("supplierId") Long supplierId) throws ServiceException {
		return new ResponseEntity<List<LtDeliveryAreas>>( ltDeliveryAreasService.getAllActiveDeliveryAreas(supplierId), HttpStatus.OK);
	}
	
	@GetMapping(value = "/getAllActiveAreasByCity/{cityId}/{supplierId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtDeliveryAreas>> getAllActiveAreasByCity(@PathVariable("cityId") Long cityId,@PathVariable("supplierId") Long supplierId) throws ServiceException {
		return new ResponseEntity<List<LtDeliveryAreas>>( ltDeliveryAreasService.getAllActiveAreasByCity(cityId,supplierId), HttpStatus.OK);
	}
	
	@GetMapping(value = "/getAllActiveAreasByPin/{pincode}/{supplierId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtDeliveryAreas>> getAllActiveAreasByPin(@PathVariable("pincode") String pincode,@PathVariable("supplierId") Long supplierId) throws ServiceException {
		return new ResponseEntity<List<LtDeliveryAreas>>( ltDeliveryAreasService.getAllActiveAreasByPin(pincode,supplierId), HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/getpincodes/{supplierId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtDeliveryAreas>> getAllPinCodes(@PathVariable("supplierId") Long supplierId ) throws ServiceException {
		return new ResponseEntity<List<LtDeliveryAreas>>( ltDeliveryAreasService.getAllPinCodes(supplierId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/delete/{areaId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> delete(@PathVariable("areaId") Long areaId) throws ServiceException {
		return new ResponseEntity<Status>( ltDeliveryAreasService.delete(areaId), HttpStatus.OK);
	}

	
	
}
