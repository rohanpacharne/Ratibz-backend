package com.lonar.master.a2zmaster.dao;

import java.util.List;
import java.util.Optional;

import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtMastUserAddresses;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.service.LtMastUserAddressesService;

public interface LtMastUserAddressesDao {

	LtMastUserAddresses save(LtMastUserAddresses addresses) throws ServiceException;

	LtMastUserAddresses updateAddress(LtMastUserAddresses updatedAddress);

	Status deleteAddress(Long userAddressId);

//	List<LtMastUserAddresses> getAddressById( Long userId);

	List<LtMastUserAddresses> getAllAddresses();

	Object getAddressById(Long userId);

	

}
