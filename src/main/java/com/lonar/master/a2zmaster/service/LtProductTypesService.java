package com.lonar.master.a2zmaster.service;

import java.util.List;

//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.LtProductTypes;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtProductTypes;
import com.lonar.master.a2zmaster.model.Status;

public interface LtProductTypesService {

	CustomeDataTable getDataTable(LtProductTypes input, Long supplierId) throws ServiceException;

	Status save(LtProductTypes ltProductTypes) throws ServiceException;

	Status update(LtProductTypes ltProductTypes) throws ServiceException;

	LtProductTypes getLtProductTypesById(Long productTypeId) throws ServiceException;

	Status delete(Long productTypeId) throws ServiceException;

	List<LtProductTypes> getAllActiveProductType(Long supplierId) throws ServiceException;

}
