package com.lonar.master.a2zmaster.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//import com.lonar.a2zcommons.model.LtMastSysVariables;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtMastSysVariables;
import com.lonar.master.a2zmaster.service.LtMastSysVariablesService;

@RestController
@RequestMapping("/api/sysvariable")
public class LtMastSysVariablesController {
	
	@Autowired
	LtMastSysVariablesService ltMastSysVariablesService;
	
	@RequestMapping(value = "/",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<List<LtMastSysVariables>> loadConfiguration() throws ServiceException{
		return new ResponseEntity<List<LtMastSysVariables>>(ltMastSysVariablesService.loadAllConfiguration(), HttpStatus.OK);
	}
	

}
