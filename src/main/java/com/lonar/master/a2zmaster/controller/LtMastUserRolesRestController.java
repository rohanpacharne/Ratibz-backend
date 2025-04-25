/*package com.users.usersmanagement.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.users.usersmanagement.common.BusinessException;
import com.users.usersmanagement.model.CodeMaster;
import com.users.usersmanagement.model.CustomeDataTable;
import com.users.usersmanagement.model.LtMastRoles;
import com.users.usersmanagement.model.LtMastUserRoles;
import com.users.usersmanagement.model.Status;
import com.users.usersmanagement.repository.LtMastRolesRepository;
import com.users.usersmanagement.repository.LtMastUserRolesRepository;
import com.users.usersmanagement.service.LtMastCommonMessageService;
import com.users.usersmanagement.service.LtMastUserRolesService;

@RestController
@RequestMapping("/api/userroles")
public class LtMastUserRolesRestController implements CodeMaster{
	final String restBaseUrl = "/API/userroles";
	static final Logger logger = Logger.getLogger(LtMastUserRolesRestController.class);
	@Autowired
	LtMastUserRolesRepository ltMastUserRolesRepository;
	@Autowired
	LtMastUserRolesService ltMastUserRolesService;
	@Autowired
	LtMastRolesRepository ltMastRolesRepository;
	
	@Autowired
	LtMastCommonMessageService ltMastCommonMessageService;

	
	@RequestMapping(value = "/datatable/{logTime}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomeDataTable getDataTable( LtMastUserRoles input,@PathVariable("logTime") String logTime){
		input.setSort("asc");
		input.setColumnNo(1);
		input.setStart(0);
		input.setColumnNo(1);
		input.setLength(10);
		return ltMastUserRolesService.getDataTable(input) ;
	}

	// -------------------Retrieve All UserRole-----------------------------
	@RequestMapping(value = "/getAll/{logTime}", method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtMastUserRoles>> listAllLtMastUserRoles(@PathVariable("logTime") String logTime) {
	
		List<LtMastUserRoles> ltMastUserRoles =new ArrayList<LtMastUserRoles>();
		try
		{
		ltMastUserRoles = (List<LtMastUserRoles>) ltMastUserRolesRepository.findAll();
		for (LtMastUserRoles ltMastUserRoles2 : ltMastUserRoles) {
			LtMastRoles ltP2pRoles = new LtMastRoles();
			if(ltMastUserRoles2.getRoleId()!=null)
				if (ltMastRolesRepository.exists(ltMastUserRoles2.getRoleId()))
				ltP2pRoles = ltMastRolesRepository.findOne(ltMastUserRoles2.getRoleId());
			if (ltP2pRoles != null) {
				ltMastUserRoles2.setRoleName(ltP2pRoles.getRoleName());
				ltMastUserRoles2.setRoleDesc(ltP2pRoles.getRoleDesc());
			}
		}
		if (ltMastUserRoles.isEmpty()) {
			return new ResponseEntity<List<LtMastUserRoles>>(ltMastUserRoles, HttpStatus.OK);
		}
		} catch (Exception e) {
			throw new BusinessException(INTERNAL_SERVER_ERROR, null, e);
		}
	
		return new ResponseEntity<List<LtMastUserRoles>>(ltMastUserRoles, HttpStatus.OK);
	}
	
	// -------------------Retrieve All Active UserRole-----------------------------
			@RequestMapping(value = "/getAllActive/{logTime}", method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
			public ResponseEntity<List<LtMastUserRoles>> listAllActiveLtMastUserRoles(@PathVariable("logTime") String logTime) {
			
				List<LtMastUserRoles> ltMastUserRoles =new ArrayList<LtMastUserRoles>();
				try
				{
				ltMastUserRoles = (List<LtMastUserRoles>) ltMastUserRolesService.findAllActive();
				for (LtMastUserRoles ltMastUserRoles2 : ltMastUserRoles) {
					LtMastRoles ltMastRoles = new LtMastRoles();
					if(ltMastUserRoles2.getRoleId()!=null)
						if (ltMastRolesRepository.exists(ltMastUserRoles2.getRoleId()))
						ltMastRoles = ltMastRolesRepository.findOne(ltMastUserRoles2.getRoleId());
					if (ltMastRoles != null) {
						ltMastUserRoles2.setRoleName(ltMastRoles.getRoleName());
						ltMastUserRoles2.setRoleDesc(ltMastRoles.getRoleDesc());
					}
				}
				if (ltMastUserRoles.isEmpty()) {
				
					return new ResponseEntity<List<LtMastUserRoles>>(ltMastUserRoles, HttpStatus.OK);
				}
				} catch (Exception e) {
					logger.error("ERROR "+ e );
					e.printStackTrace();
					return new ResponseEntity<List<LtMastUserRoles>>(ltMastUserRoles, HttpStatus.OK);
				}
			
				return new ResponseEntity<List<LtMastUserRoles>>(ltMastUserRoles, HttpStatus.OK);
			}
			
	// -------------------Retrieve Single UserRole----------------------------
	@RequestMapping(value = "/getByID/{id}/{logTime}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LtMastUserRoles> getByIDLtMastUserRoles(@PathVariable("id") String id,@PathVariable("logTime") String logTime) {
		
		LtMastUserRoles ltMastUserRoles = null;
		try {
			if (ltMastUserRolesRepository.exists(Long.parseLong(id))) {
				ltMastUserRoles = ltMastUserRolesRepository.findOne(Long.parseLong(id));
				
				LtMastRoles ltMastRoles = new LtMastRoles();
				if(ltMastUserRoles.getRoleId()!=null)
					if (ltMastRolesRepository.exists(ltMastUserRoles.getRoleId()))
					ltMastRoles = ltMastRolesRepository.findOne(ltMastUserRoles.getRoleId());
				if (ltMastRoles != null) {
					ltMastUserRoles.setRoleName(ltMastRoles.getRoleName());
					ltMastUserRoles.setRoleDesc(ltMastRoles.getRoleDesc());
				}
			}
			else {
				
				return new ResponseEntity<LtMastUserRoles>(ltMastUserRoles, HttpStatus.OK);
			}
		} catch (Exception e) {
			throw new BusinessException(INTERNAL_SERVER_ERROR, null, e);
		}
	
		return new ResponseEntity<LtMastUserRoles>(ltMastUserRoles, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/saveuserrole", method = RequestMethod.POST )
	public Status saveuserrole(@RequestBody LtMastUserRoles ltMastUserRoles) throws Exception {
		try {
			return ltMastUserRolesService.saveUserRole(ltMastUserRoles);
		}catch(Exception e) {
				throw new BusinessException(INTERNAL_SERVER_ERROR, null, e);
		}
		
	}
	
	@RequestMapping(value = "/updateuserrole", method = RequestMethod.POST )
	public Status updateUserRole(@RequestBody LtMastUserRoles ltMastUserRoles) throws Exception {
		try {
			return ltMastUserRolesService.updateUserRole(ltMastUserRoles);
		}catch(Exception e) {
				throw new BusinessException(INTERNAL_SERVER_ERROR, null, e);
		}
		
	}
	
	@RequestMapping(value = "/getrolebyuserid/{id}/{logTime}", method = RequestMethod.GET)
	public List<LtMastUserRoles> getRoleByUserId(@PathVariable("id") Long id,@PathVariable("logTime") String logTime) throws Exception {
		try {
			return ltMastUserRolesService.getRoleByUserId(id);
		}catch(Exception e) {
				throw new BusinessException(INTERNAL_SERVER_ERROR, null, e);
		}
		
	}	
	
	@RequestMapping(value = "/getuserrolebyid/{id}/{logTime}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public LtMastUserRoles getUserRoleById(@PathVariable("id") Long id,@PathVariable("logTime") String logTime) throws Exception {
		try {
			return ltMastUserRolesService.getUserRoleById(id);
		}catch(Exception e) {
				throw new BusinessException(INTERNAL_SERVER_ERROR, null, e);
		}
		
	}	
	

	
	// -------------------Retrieve Single UserRole----------------------------
	@RequestMapping(value = "/deleteById/{id}/{logTime}", method = RequestMethod.GET, produces =MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> deleteLtMastUserRolesByID(@PathVariable("id") String id,@PathVariable("logTime") String logTime) {
		Status status = new Status();
		try {
			if (ltMastUserRolesRepository.exists(Long.parseLong(id))) {
				ltMastUserRolesRepository.delete(Long.parseLong(id));
				 status=ltMastCommonMessageService.getCodeAndMessage(DELETE_SUCCESSFULLY);
					if( status.getMessage()==null)
					{
						status.setCode(SUCCESS);
						status.setMessage("Error in finding message! The action is completed successfully.");
					}
			}
			else
			{
				 status=ltMastCommonMessageService.getCodeAndMessage(DELETE_FAIL);
					if( status.getMessage()==null)
					{
						status.setCode(FAIL);
						status.setMessage("Error in finding message! The action is completed unsuccessfully.");
					}
				return new ResponseEntity<Status>(status,HttpStatus.OK);

			}

		} catch(org.springframework.dao.DataIntegrityViolationException e)
		{
			
			e.printStackTrace();
			status=ltMastCommonMessageService.getCodeAndMessage(ENTITY_CANNOT_DELETE);
			return new ResponseEntity<Status>(status, HttpStatus.OK);
		}
		catch (Exception e) {
			throw new BusinessException(INTERNAL_SERVER_ERROR, null, e);
		}
		
		return new ResponseEntity<Status>(status,HttpStatus.OK);	}


}*/