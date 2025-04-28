package com.lonar.master.a2zmaster.service;

import java.util.Date;
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
		
		Status status = new Status();

	    Date now = new Date();
	    Long userId = addresses.getUserId();

	    addresses.setCreatedBy(userId);
	    addresses.setCreationDate(now);
	    addresses.setLastUpdateDate(now);
	    addresses.setLastUpdateLogin(userId);

	    LtMastUserAddresses savedAddress = addressesDao.save(addresses);

	    if (savedAddress != null) {
	        status = ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
	        status.setData(savedAddress);
	    } else {
	        status = ltMastCommonMessageService.getCodeAndMessage(INSERT_FAIL);
	    }

	    return status;
	}
	
	
	public Status updateAddress(LtMastUserAddresses updatedAddress) {
		
		Status status=new Status();
		
		try {
		updatedAddress.setLastUpdatedBy(updatedAddress.getUserId()); // Assuming getUserId() returns the updater's ID
	    updatedAddress.setLastUpdateDate(new Date());
	    updatedAddress.setLastUpdateLogin(updatedAddress.getUserId());
		LtMastUserAddresses result = addressesDao.updateAddress(updatedAddress);

		if(result != null) {
			status=ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
			status.setData(result);
		}else {
			status=ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
		}
		 
		}catch(Exception ex) {
			status = ltMastCommonMessageService.getCodeAndMessage(EXCEPTION);
		}
		return status;
		
	}


	@Override
	public Status deleteAddress(Long userAddressId) {
		return addressesDao.deleteAddress(userAddressId);
	}


	@Override
	public Status getAddressById(Long userId) {
		
		    Status status = new Status();

		    List<LtMastUserAddresses> addressList = (List<LtMastUserAddresses>) addressesDao.getAddressById(userId);
		   
		    if (addressList != null && !addressList.isEmpty()) {
		        status = ltMastCommonMessageService.getCodeAndMessage(DATA_FETCHED_SUCCESSFULLY);
		        status.setData(addressList);
		    } else {
		        status = ltMastCommonMessageService.getCodeAndMessage(ERROR_FETCHING_DATA);
		    }

		    return status;

	}


	@Override
	public Status getAllAddresses() {
		
		Status status = new Status();

	    List<LtMastUserAddresses> addressList = (List<LtMastUserAddresses>) addressesDao.getAllAddresses();
	   
	    if (addressList != null && !addressList.isEmpty()) {
	        status = ltMastCommonMessageService.getCodeAndMessage(DATA_FETCHED_SUCCESSFULLY);
	        status.setData(addressList);
	    } else {
	        status = ltMastCommonMessageService.getCodeAndMessage(ERROR_FETCHING_DATA);
	    }

	    return status;
	}
	
	 
        
	

}
