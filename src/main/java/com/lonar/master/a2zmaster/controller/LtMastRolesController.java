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

import com.lonar.master.a2zmaster.common.BusinessException;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtMastRoleModules;
import com.lonar.master.a2zmaster.model.LtMastRoles;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.service.LtMastRolesService;



@RestController
@RequestMapping("/api/roles")
public class LtMastRolesController implements CodeMaster{

	@Autowired
	LtMastRolesService ltMastRolesService;
	
	
	@RequestMapping(value = "/datatable/{supplierId}/{logTime}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomeDataTable getDataTable(@PathVariable("supplierId") Long supplierId, LtMastRoles input,@PathVariable("logTime") String logTime) throws ServiceException{
		return ltMastRolesService.getDataTable(input,supplierId) ;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> save( @RequestBody LtMastRoles ltMastRoles) throws ServerException {
		try {
		return new ResponseEntity<Status>(ltMastRolesService.save(ltMastRoles), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException(0, null, e);
		}
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> update( @RequestBody LtMastRoles ltMastRoles) throws ServerException {
		try {
			return new ResponseEntity<Status>(ltMastRolesService.update(ltMastRoles), HttpStatus.OK);
			}catch(Exception e) {
				throw new BusinessException(0, null, e);
		}
	}
	
	@RequestMapping(value = "/getById/{roleId}/{logTime}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LtMastRoles> getRolesById(@PathVariable("roleId") Long roleId,@PathVariable("logTime") String logTime) throws ServiceException {
		return new ResponseEntity<LtMastRoles>( ltMastRolesService.getRolesById(roleId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAllActiveRole/{supplierId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtMastRoles>> getAllActiveRole(@PathVariable("supplierId") Long supplierId) throws ServiceException {
		return new ResponseEntity<List<LtMastRoles>>( ltMastRolesService.getAllActiveRole(supplierId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/saveRoleModule", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> saveLtMastRoleModules(@RequestBody  LtMastRoleModules ltMastRoleModule) throws BusinessException {
		try {
			return new ResponseEntity<Status>(ltMastRolesService.saveLtMastRoleModules(ltMastRoleModule), HttpStatus.OK);
		}catch(Exception e) {
			throw new BusinessException(0, null, e);
		}
	}
	
	@RequestMapping(value = "/getRoleModule/{roleId}/{logTime}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtMastRoleModules>> getRoleModule(@PathVariable("roleId") Long roleId,@PathVariable("logTime") String logTime) throws BusinessException {
		try {
			return new ResponseEntity<List<LtMastRoleModules>>( ltMastRolesService.getRoleModule(roleId), HttpStatus.OK);
		}catch(Exception e) {
			throw new BusinessException(0, null, e);
		}
	}
	
	@RequestMapping(value = "/getRoleModuleById/{roleFuncId}/{logTime}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LtMastRoleModules> getRoleModuleById(@PathVariable("roleFuncId") Long roleFuncId,@PathVariable("logTime") String logTime) throws ServiceException {
		return new ResponseEntity<LtMastRoleModules>( ltMastRolesService.getRoleModuleById(roleFuncId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/deleteRoleModule/{roleFuncId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> deleteRoleModule(@PathVariable("roleFuncId") Long roleFuncId) throws ServiceException {
		try {
			return new ResponseEntity<Status>(ltMastRolesService.deleteRoleModule(roleFuncId), HttpStatus.OK);
		} catch (Exception e) {
			throw new BusinessException(0, null, e);
		}
	}
	
	@RequestMapping(value = "/delete/{roleId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> delete(@PathVariable("roleId") Long roleId) {
		try {
			return new ResponseEntity<Status>(ltMastRolesService.delete(roleId), HttpStatus.OK);
		} catch (Exception e) {
			throw new BusinessException(0, null, e);
		}
	}
}
