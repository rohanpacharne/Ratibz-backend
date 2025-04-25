package com.lonar.master.a2zmaster.service;

import java.util.List;
import java.util.Optional;

import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtMastUserAddresses;
import com.lonar.master.a2zmaster.model.Status;

public interface LtMastUserAddressesService {


	Status save(LtMastUserAddresses addresses) throws ServiceException;

	Status updateAddress(LtMastUserAddresses address);

	Status deleteAddress(Long userAddressId);

	Status getAddressById( Long userId);

	Status getAllAddresses();

}
