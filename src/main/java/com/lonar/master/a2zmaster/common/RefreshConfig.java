package com.lonar.master.a2zmaster.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lonar.master.a2zmaster.A2zMasterManagementApplication;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.LtMastSysVariables;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.service.LtMastCommonMessageService;
import com.lonar.master.a2zmaster.service.LtMastSysVariablesService;




@RestController
public class RefreshConfig implements CodeMaster {

	@Autowired
	LtMastSysVariablesService ltMastSysVariablesService;
	
	@Autowired
	LtMastCommonMessageService ltMastCommonMessageService;
	
	@GetMapping(value="/refreshUserManagement")
	public ResponseEntity<Status> getAllConfiguration() throws ServiceException {
		Status status = new Status();
		try
		{
			A2zMasterManagementApplication.configMap.clear();
			
			
			System.out.println(" MASTER DATA REFRESH.....  ");
			
			List<LtMastSysVariables> ltMastSysVariablesList = ltMastSysVariablesService.loadAllConfiguration();
			//System.out.println("ltMastSysVariablesList "+ltMastSysVariablesList);
			 Iterator<LtMastSysVariables> itr=ltMastSysVariablesList.iterator();
				while(itr.hasNext()) {
					LtMastSysVariables ltMastSysVariables=itr.next();
					Set<Long> set = A2zMasterManagementApplication.configMap.keySet();
					if(set.contains(ltMastSysVariables.getSupplierId())) {
						Map<String,String> myMap = A2zMasterManagementApplication.configMap.get(ltMastSysVariables.getSupplierId());
						Set<String> variableNameSet = myMap.keySet();
						if(!variableNameSet.contains(ltMastSysVariables.getVariableName())) {
							myMap.put(ltMastSysVariables.getVariableName(), ltMastSysVariables.getSystemValue());
							A2zMasterManagementApplication.configMap.put(ltMastSysVariables.getSupplierId(), myMap);
						}
					}else {
						Map<String,String> myMap = new HashMap<String,String>();
						myMap.put(ltMastSysVariables.getVariableName(), ltMastSysVariables.getSystemValue());
						A2zMasterManagementApplication.configMap.put(ltMastSysVariables.getSupplierId(), myMap);							
					}
				}
//				status.setCode(CodeMaster.UPDATE_SUCCESSFULLY);
//				//status.setMessage("Record Updated Successfully");
//				status.setMessage("Action successful");
				status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
				return new ResponseEntity<Status>(status, HttpStatus.OK);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new ServiceException(0,"Update Fail",e);
		}
    }
}
