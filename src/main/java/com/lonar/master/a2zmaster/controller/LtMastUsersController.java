package com.lonar.master.a2zmaster.controller;

import java.io.IOException;
import java.util.List;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

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

import com.lonar.master.a2zmaster.common.BusinessException;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtMastCaptcha;
import com.lonar.master.a2zmaster.model.LtMastLogins;
import com.lonar.master.a2zmaster.model.LtMastUsers;
import com.lonar.master.a2zmaster.model.ResponceEntity;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.service.LtMastUsersService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping(value = "/api/users")
public class LtMastUsersController implements CodeMaster {

	@Autowired
	private LtMastUsersService ltMastUsersService;
	
	//@Autowired private Authorization authorization;
	

	
	
	@RequestMapping(value = "/getcaptcha", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> getCaptcha(@RequestBody LtMastCaptcha mastCaptcha)  throws ServiceException, IOException{
		System.out.println("in users Controller");
		return new ResponseEntity<Status>(ltMastUsersService.getCaptcha(mastCaptcha), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sendOTP", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> sendOTP(@RequestBody LtMastUsers ltMastUsers, HttpServletRequest httpServletRequest)  
			throws ServiceException, IOException{		
		return new ResponseEntity<Status>(ltMastUsersService.sendOTPToUser(ltMastUsers, httpServletRequest), HttpStatus.OK);
	}
	
	

	@RequestMapping(value = "/login", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponceEntity login(@RequestBody LtMastLogins ltMastLogins, HttpServletRequest httpServletRequest, HttpServletResponse response) throws ServiceException{
		return ltMastUsersService.login(ltMastLogins, httpServletRequest, response);
	}
	
//	@RequestMapping(value = "/datatable/{supplierId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	public CustomeDataTable getDataTable(@PathVariable("supplierId") Long supplierId, LtMastUsers input) throws ServiceException{
//		return ltMastUsersService.getDataTable(input,supplierId) ;
//	}
	
	@RequestMapping(value = "/datatable/{supplierId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomeDataTable getDataTable(@PathVariable("supplierId") Long supplierId,LtMastUsers input,
			 	@RequestParam(value = "search", required = false) String search,
		        @RequestParam(value = "column", required = false) Integer column,
		        @RequestParam(value = "limit", required = false) Integer limit,
		        @RequestParam(value = "offset", required = false) Integer offset) throws ServiceException{
		
		 if (search != null && !search.isEmpty()) {
		        input.setUserName(search);
		    }
		
		System.out.println("LtMASTUSERINPUT::"+input);
		System.out.println("in datatable:");
		return ltMastUsersService.getDataTable(input,supplierId) ;
	}
	
	@RequestMapping(value = "/getUserById/{userId}", method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LtMastUsers> getUserById(@PathVariable("userId") Long userId) throws ServiceException, IOException{
		return new ResponseEntity<LtMastUsers>(ltMastUsersService.getUserById(userId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getUserById/{userId}/{supplierId}", method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LtMastUsers> gelttUserById(@PathVariable("userId") Long userId,@PathVariable("supplierId")Long supplierId) throws ServiceException, IOException{
		return new ResponseEntity<LtMastUsers>(ltMastUsersService.getltUserById(userId,supplierId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> save(@RequestBody LtMastUsers ltMastUsers) throws ServiceException{
		System.out.println("save ltMastUsers ::"+ltMastUsers);
		System.out.println("save ltMastUsers ::"+ltMastUsers.getAreaId());
		return new ResponseEntity<Status>(ltMastUsersService.save(ltMastUsers), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/delete/{userId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> delete(@PathVariable("userId") Long userId) {
		try {
			return new ResponseEntity<Status>(ltMastUsersService.delete(userId), HttpStatus.OK);
		} catch (Exception e) {
			throw new BusinessException(0, null, e);
		}
	}
	
	@RequestMapping(value = "/getUserByType/{userType}/{supplierId}", method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtMastUsers>> getUserByType(@PathVariable("userType") String userType,@PathVariable("supplierId") Long supplierId) throws ServiceException, IOException{
		return new ResponseEntity<List<LtMastUsers>>(ltMastUsersService.getUserByType(userType,supplierId), HttpStatus.OK);
	}

	
	@RequestMapping(value = "/update", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> update(@RequestBody LtMastUsers ltMastUsers) throws ServiceException{
		
		System.out.println("getLastUpdateLogin::"+ltMastUsers.getLastUpdateLogin());
//		System.out.println("LTMASTUSERS::"+ltMastUsers);
		return new ResponseEntity<Status>(ltMastUsersService.update(ltMastUsers), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAllUserBySupplier/{supplierId}", method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomeDataTable> getAllUserBySupplier(@PathVariable("supplierId") Long supplierId,
		 LtMastUsers ltMastUsers,@RequestParam(value = "search", required = false) String search) throws ServiceException, IOException{
		
		if (search != null && !search.isEmpty()) {
			 ltMastUsers.setUserName(search);
		    }
		return new ResponseEntity<CustomeDataTable>(ltMastUsersService.getAllUserBySupplier(supplierId,ltMastUsers), HttpStatus.OK);
	}
	
	
}