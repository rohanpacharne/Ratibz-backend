package com.lonar.master.a2zmaster.service;

import java.util.List;

//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.LtSupplierCities;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtSupplierCities;
import com.lonar.master.a2zmaster.model.Status;

public interface LtSupplierCitiesService {

	CustomeDataTable getDataTable(LtSupplierCities input, Long supplierId) throws ServiceException;

	Status save(LtSupplierCities ltSupplierCities) throws ServiceException;

	Status update(LtSupplierCities ltSupplierCities) throws ServiceException;

	LtSupplierCities getLtSupplierCitiesById(Long cityId) throws ServiceException;

	Status delete(Long cityId) throws ServiceException;

	List<LtSupplierCities> getAllLtSupplierCities(Long supplierId) throws ServiceException;

	List<LtSupplierCities> getAllCitiesByState(Long stateId, Long supplierId) throws ServiceException;

}
