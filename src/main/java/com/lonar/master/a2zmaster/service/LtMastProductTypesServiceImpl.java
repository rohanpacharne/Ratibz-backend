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
	LtMastCommonMessageService ltMastCommonMessageService;


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
		
		try {
		
			List<LtMastSuppliers> list = ltMastProductTypesDao.getAllSupplierList(ltMastSuppliers,input);
		
				if(list!=null && !list.isEmpty()) {
					status = ltMastCommonMessageService.getCodeAndMessage(DATA_FETCHED_SUCCESSFULLY);
					status.setData(list);
				}else {
					status = ltMastCommonMessageService.getCodeAndMessage(ERROR_FETCHING_DATA);
				}
			}catch(Exception ex) {
				ex.printStackTrace();
				status = ltMastCommonMessageService.getCodeAndMessage(EXCEPTION);
		}
		return status;
	}
	
	

}
