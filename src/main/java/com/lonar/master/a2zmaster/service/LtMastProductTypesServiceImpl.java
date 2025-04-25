package com.lonar.master.a2zmaster.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lonar.master.a2zmaster.dao.LtMastProductTypesDao;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.LtCustomerSubsDeliveries;
import com.lonar.master.a2zmaster.model.LtMastProductTypes;
import com.lonar.master.a2zmaster.model.LtMastSuppliers;
import com.lonar.master.a2zmaster.model.Status;

import scala.util.Success;

@Service
public class LtMastProductTypesServiceImpl implements LtMastProductTypesService,CodeMaster{

	@Autowired
	LtMastProductTypesDao ltMastProductTypesDao;
	
	@Autowired
	LtMastCommonMessageService commonMessageService;


	@Override
	public List<LtMastProductTypes> getAllProductTypes(LtMastProductTypes input) {
		
		return ltMastProductTypesDao.getAllProductTypes(input);
	}
	
	
	public Status getDeliveryDetails( LtCustomerSubsDeliveries request) {
		
		Status status=new Status();
		
		if(request.getDeliveryDate() == null) {
			
			request.setDeliveryDate(new Date());
		}
		
		status.setData(ltMastProductTypesDao.getDeliveryDetails(request));
		if(status != null) {
			
			status.setCode(1);
			status.setMessage("success");
		}else {
			
			status.setMessage("null");
		}
		
	    return status;
	}


	@Override
	public Status getAllSupplierList(LtMastSuppliers ltMastSuppliers,LtMastSuppliers input) {
		
		Status status =new Status();
		
			status.setData(ltMastProductTypesDao.getAllSupplierList(ltMastSuppliers,input));
			
			if(status != null) {
				
				status.setCode(1);
				status.setMessage("success");
			}else {
				
				status.setMessage("null");
			}
			
		return status;
	}
	
	

}
