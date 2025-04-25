package com.lonar.master.a2zmaster.dao;

import java.util.List;

import org.hibernate.service.spi.ServiceException;

import com.lonar.master.a2zmaster.model.LtSupplierCities;

//import com.lonar.a2zcommons.model.LtSupplierCities;

public interface LtSupplierCitiesDao {

	Long getLtSupplierCitiesCount(LtSupplierCities input, Long supplierId) throws ServiceException;

	List<LtSupplierCities> getLtSupplierCitiesDataTable(LtSupplierCities input, Long supplierId) throws ServiceException;

	LtSupplierCities save(LtSupplierCities ltSupplierCities) throws ServiceException;

	LtSupplierCities getLtSupplierCitiesById(Long cityId) throws ServiceException;

	List<LtSupplierCities> findByCityName(String city, Long supplierId) throws ServiceException;

	LtSupplierCities delete(Long cityId) throws ServiceException;

	List<LtSupplierCities> getAllLtSupplierCities(Long supplierId) throws ServiceException;

	List<LtSupplierCities> getAllCitiesByState(Long stateId, Long supplierId) throws ServiceException;

	boolean update(LtSupplierCities ltSupplierCities) throws ServiceException;


}
