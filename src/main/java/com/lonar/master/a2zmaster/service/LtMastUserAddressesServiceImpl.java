package com.lonar.master.a2zmaster.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.dao.LtMastUserAddressesDao;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.LtMastUserAddresses;
import com.lonar.master.a2zmaster.model.Status;

@Service
public class LtMastUserAddressesServiceImpl implements LtMastUserAddressesService,CodeMaster{
	
	@Autowired
	LtMastUserAddressesDao addressesDao;
	
	@Autowired
	LtMastCommonMessageService ltMastCommonMessageService;

	@Override
	public Status save(LtMastUserAddresses addresses) throws ServiceException {
		
		Status status=new Status();
		
		status.setData(addressesDao.save(addresses));
		
		if(!status.equals(null)) {
			status=ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
		}
		return status;
	}
	
	
	public Status updateAddress(LtMastUserAddresses updatedAddress) {
		
		Status status=new Status();
		
		try {
		
		LtMastUserAddresses result = addressesDao.updateAddress(updatedAddress);

		if(result != null) {
			status=ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
		}else {
			status=ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
		}
		 status.setData(result);
		}catch(Exception ex) {
			
		}
		return status;
		
	}


	@Override
	public Status deleteAddress(Long userAddressId) {
		
		return addressesDao.deleteAddress(userAddressId);
	}


	@Override
	public Status getAddressById(Long userId) {
		
		Status status=new Status();
		
		status.setData(addressesDao.getAddressById(userId));
		if(!status.equals(null)) {
			status.setCode(1);
			status.setMessage("success");
		}
		return status;
	}


	@Override
	public Status getAllAddresses() {
		
		Status status=new Status();
		
		 status.setData(addressesDao.getAllAddresses()) ;
		
		if(status !=null) {
			
			status.setCode(1);
			status.setMessage("success");
		}
		
		return status;
	}
	
	 
        
	

}
