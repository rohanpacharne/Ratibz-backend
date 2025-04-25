package com.lonar.master.a2zmaster.controller;

import java.rmi.ServerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//import com.lonar.a2zcommons.model.LtInviteCust;
import com.lonar.master.a2zmaster.common.BusinessException;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.LtInviteCust;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.service.LtInviteCustomerService;

@RestController
@RequestMapping("/api/invite")
public class LtInviteCustomerController implements CodeMaster {

	@Autowired
	LtInviteCustomerService ltInviteCustomerService;
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> save( @RequestBody LtInviteCust ltInviteCust) throws ServerException {
		try {
		return new ResponseEntity<Status>(ltInviteCustomerService.save(ltInviteCust), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException(0, null, e);
		}
	}
	
}
