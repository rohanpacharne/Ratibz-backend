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
//import com.lonar.a2zcommons.model.CustomeDataTable;s
import com.lonar.master.a2zmaster.common.BusinessException;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtSupplierFaqs;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.service.LtSupplierFaqsService;

@RestController
@RequestMapping("/api/faq")
public class LtSupplierFaqsController implements CodeMaster{
	
	@Autowired
	LtSupplierFaqsService ltSupplierFaqsService;
	
	@RequestMapping(value = "/datatable/{supplierId}/{logTime}", method= RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomeDataTable getDataTable(@PathVariable("supplierId") Long supplierId, LtSupplierFaqs input,@PathVariable("logTime") String logTime) throws ServiceException{
		return ltSupplierFaqsService.getDataTable(supplierId,input);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> save( @RequestBody LtSupplierFaqs ltSupplierFaqs) throws ServerException {
		try {
		return new ResponseEntity<Status>(ltSupplierFaqsService.save(ltSupplierFaqs), HttpStatus.OK);
		}catch(Exception e) {
			throw new BusinessException(0, null, e);
		}
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> update( @RequestBody LtSupplierFaqs ltSupplierFaqs) throws ServerException {
		try {
			return new ResponseEntity<Status>(ltSupplierFaqsService.update(ltSupplierFaqs), HttpStatus.OK);
			}catch(Exception e) {
				throw new BusinessException(0, null, e);
		}
	}
	
	@RequestMapping(value = "/getById/{faqId}/{logTime}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LtSupplierFaqs> getLtSupplierFaqsById(@PathVariable("faqId") Long faqId,@PathVariable("logTime") String logTime) throws ServiceException {
		return new ResponseEntity<LtSupplierFaqs>( ltSupplierFaqsService.getLtSupplierFaqsById(faqId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAllActiveFaq/{supplierId}/{logTime}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtSupplierFaqs>> getAllActiveLtSupplierFaqs(@PathVariable("supplierId") Long supplierId,@PathVariable("logTime") String logTime) throws ServiceException {
		return new ResponseEntity<List<LtSupplierFaqs>>( ltSupplierFaqsService.getAllActiveLtSupplierFaqs(supplierId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/delete/{faqId}/{logTime}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> delete(@PathVariable("faqId") Long faqId,@PathVariable("logTime") String logTime) throws ServiceException {
		return new ResponseEntity<Status>( ltSupplierFaqsService.delete(faqId), HttpStatus.OK);
	}

}
