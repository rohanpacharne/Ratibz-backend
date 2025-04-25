package com.lonar.master.a2zmaster.controller;

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

import com.lonar.master.a2zmaster.common.BusinessException;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtMastModules;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.service.LtMastModulesService;



@RestController
@RequestMapping("/api/module")
public class LtMastModulesController implements CodeMaster {
	
	
	@Autowired
	LtMastModulesService ltMastModulesService;
	
	
	@RequestMapping(value = "/dataTable/{supplierId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomeDataTable getDataTable(@PathVariable("supplierId") Long supplierId,LtMastModules input) {
		try {
			return ltMastModulesService.getDataTable(input,supplierId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> addLtMastModules(@RequestBody LtMastModules ltMastModules) {
		Status status = new Status();
		try {
			status = ltMastModulesService.save(ltMastModules);
		} catch (Exception e) {
			throw new BusinessException(0, null, e);
		}
		return new ResponseEntity<Status>(status, HttpStatus.OK);
	}
		
	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> updateLtMastModules(@RequestBody LtMastModules ltMastModules) {
		Status status = new Status();
		try {
			status = ltMastModulesService.update(ltMastModules);
		} catch (Exception e) {
			throw new BusinessException(0, null, e);
		}
		return new ResponseEntity<Status>(status, HttpStatus.OK);
	}
		
	@RequestMapping(value = "/getByID/{moduleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LtMastModules> getLtMastModulesByID(@PathVariable("moduleId") Long moduleId) {
		try {
			return new ResponseEntity<LtMastModules>(ltMastModulesService.getLtMastModulesByID(moduleId), HttpStatus.OK);
		}catch (Exception e) {
			
			throw new BusinessException(0, null, e);
		}
	}
	
	@RequestMapping(value="/getActiveLikeName/{moduleName}/{supplierId}" , method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<List<LtMastModules>> getActiveLikeName(@PathVariable("moduleName") String moduleName,@PathVariable("supplierId") Long supplierId
			){
		try {
			return new ResponseEntity<List<LtMastModules>>(ltMastModulesService.getActiveLikeName(moduleName,supplierId), HttpStatus.OK);
		}catch (Exception e) {
			
			throw new BusinessException(0, null, e);
		}
	}
	
	@RequestMapping(value = "/delete/{moduleId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> delete(@PathVariable("moduleId") Long moduleId) {
		try {
			return new ResponseEntity<Status>(ltMastModulesService.delete(moduleId), HttpStatus.OK);
		} catch (Exception e) {
			throw new BusinessException(0, null, e);
		}
	}

}