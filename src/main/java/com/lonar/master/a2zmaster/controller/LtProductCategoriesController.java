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
//import com.lonar.a2zcommons.model.LtProductCategories;
import com.lonar.master.a2zmaster.common.BusinessException;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtProductCategories;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.service.LtProductCategoriesService;

@RestController
@RequestMapping("/api/productcategories")
public class LtProductCategoriesController implements CodeMaster {

	@Autowired
	LtProductCategoriesService ltProductCategoriesService;
	
	@RequestMapping(value = "/datatable/{supplierId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomeDataTable getDataTable(@PathVariable("supplierId") Long supplierId,
			@RequestParam(value = "search", required = false) String search, LtProductCategories input) throws ServiceException{
		 if (search != null && !search.isEmpty()) {
		        input.setCategoryName(search);
		    }

		return ltProductCategoriesService.getDataTable(input,supplierId) ;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> save( @RequestBody LtProductCategories ltProductCategories) throws ServerException {
		try {
		return new ResponseEntity<Status>(ltProductCategoriesService.save(ltProductCategories), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException(0, null, e);
		}
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> update( @RequestBody LtProductCategories ltProductCategories) throws ServerException {
		try {
			return new ResponseEntity<Status>(ltProductCategoriesService.update(ltProductCategories), HttpStatus.OK);
			}catch(Exception e) {
				throw new BusinessException(0, null, e);
		}
	}
	
	@RequestMapping(value = "/getById/{categoryId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LtProductCategories> getLtProductCategoriesById(@PathVariable("categoryId") Long categoryId) throws ServiceException {
		return new ResponseEntity<LtProductCategories>( ltProductCategoriesService.getLtProductCategoriesById(categoryId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAllActiveCategories/{supplierId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtProductCategories>> getAllActiveCategories(@PathVariable("supplierId") Long supplierId) throws ServiceException {
		return new ResponseEntity<List<LtProductCategories>>( ltProductCategoriesService.getAllActiveCategories(supplierId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAllActiveCategoriesByType/{typeId}/{supplierId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtProductCategories>> getAllActiveCategoriesByType(@PathVariable("typeId") Long typeId,@PathVariable("supplierId") Long supplierId) throws ServiceException {
		return new ResponseEntity<List<LtProductCategories>>( ltProductCategoriesService.getAllActiveCategoriesByType(typeId,supplierId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/delete/{categoryId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> delete(@PathVariable("categoryId") Long categoryId) throws ServiceException {
		return new ResponseEntity<Status>( ltProductCategoriesService.delete(categoryId), HttpStatus.OK);
	}
}
