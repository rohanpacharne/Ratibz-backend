package com.lonar.master.a2zmaster.dao;

import java.util.List;

import org.hibernate.service.spi.ServiceException;

import com.lonar.master.a2zmaster.model.LtSupplierStates;

//import com.lonar.a2zcommons.model.LtSupplierStates;

public interface LtSupplierStatesDao {

	Long getLtSupplierStatesCount(LtSupplierStates input, Long supplierId) throws ServiceException;

	List<LtSupplierStates> getLtSupplierStatesDataTable(LtSupplierStates input, Long supplierId) throws ServiceException;

	LtSupplierStates save(LtSupplierStates ltSupplierStates) throws ServiceException;

	List<LtSupplierStates> findByStateName(String supplierState, Long supplierId) throws ServiceException;

	LtSupplierStates getStatesById(Long stateId) throws ServiceException;

	LtSupplierStates delete(Long stateId) throws ServiceException;

	List<LtSupplierStates> getAllStates(Long supplierId)  throws ServiceException;

	boolean update(LtSupplierStates ltSupplierStates) throws ServiceException;

	List<LtSupplierStates> getByStateName(String supplierState, Long supplierId) throws ServiceException;

}
