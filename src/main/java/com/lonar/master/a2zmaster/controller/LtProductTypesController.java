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

//import com.lonar.a2zcommons.model.CustomeDataTable;s
import com.lonar.master.a2zmaster.common.BusinessException;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtProductTypes;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.service.LtProductTypesService;

@RestController
@RequestMapping("/api/productType")
public class LtProductTypesController implements CodeMaster{

	@Autowired
	LtProductTypesService ltProductTypesService;
	
	
	@RequestMapping(value = "/datatable/{supplierId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomeDataTable getDataTable(@PathVariable("supplierId") Long supplierId,	@RequestParam(value = "search", required = false) String search, LtProductTypes input,
			 @RequestParam(value = "start", defaultValue = "0") Integer start,
		        @RequestParam(value = "length", defaultValue = "10") Integer length
		        ) throws ServiceException{
		 if (search != null && !search.isEmpty()) {
		        input.setProductType(search);
		    }

		return ltProductTypesService.getDataTable(input,supplierId) ;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> save( @RequestBody LtProductTypes ltProductTypes) throws ServerException {
		try {
		return new ResponseEntity<Status>(ltProductTypesService.save(ltProductTypes), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException(0, null, e);
		}
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> update( @RequestBody LtProductTypes ltProductTypes) throws ServerException {
		try {
			return new ResponseEntity<Status>(ltProductTypesService.update(ltProductTypes), HttpStatus.OK);
			}catch(Exception e) {
				throw new BusinessException(0, null, e);
		}
	}
	
	@RequestMapping(value = "/getById/{productTypeId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LtProductTypes> getLtProductTypesById(@PathVariable("productTypeId") Long productTypeId) throws ServiceException {
		return new ResponseEntity<LtProductTypes>( ltProductTypesService.getLtProductTypesById(productTypeId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAllActiveProductType/{supplierId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtProductTypes>> getAllActiveProductType(@PathVariable("supplierId") Long supplierId) throws ServiceException {
		return new ResponseEntity<List<LtProductTypes>>( ltProductTypesService.getAllActiveProductType(supplierId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/delete/{productTypeId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> delete(@PathVariable("productTypeId") Long productTypeId) throws ServiceException {
		return new ResponseEntity<Status>( ltProductTypesService.delete(productTypeId), HttpStatus.OK);
	}
	
	
}
