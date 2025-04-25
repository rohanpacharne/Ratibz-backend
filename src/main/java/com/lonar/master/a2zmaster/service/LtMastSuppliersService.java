package com.lonar.master.a2zmaster.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

//import com.lonar.a2zcommons.model.LtMastSuppliers;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtMastSuppliers;
import com.lonar.master.a2zmaster.model.Status;

public interface LtMastSuppliersService {

	LtMastSuppliers getLtMastSuppliersById(Long supplierId) throws ServiceException;

	List<LtMastSuppliers> getAllSuppliers()  throws ServiceException;

	Status getBySuppCode(String supplierCode) throws ServiceException;

	Status getSupplierInfromation(String supplierCode) throws ServiceException;

	LtMastSuppliers getPaymentDetailsById(Long supplierId) throws ServiceException;
	
	ResponseEntity<Status> saveNewSupplier(LtMastSuppliers supplier );
}
