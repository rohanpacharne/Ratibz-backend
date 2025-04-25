package com.lonar.master.a2zmaster.service;

import java.util.List;

//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.LtSupplierStates;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtSupplierStates;
import com.lonar.master.a2zmaster.model.Status;

public interface LtSupplierStatesService {

	CustomeDataTable getDataTable(LtSupplierStates input, Long supplierId) throws ServiceException;

	Status save(LtSupplierStates ltSupplierStates) throws ServiceException;

	Status update(LtSupplierStates ltSupplierStates) throws ServiceException;

	LtSupplierStates getStatesById(Long stateId) throws ServiceException;

	Status delete(Long stateId) throws ServiceException;

	List<LtSupplierStates> getAllStates(Long supplierId) throws ServiceException;

}
