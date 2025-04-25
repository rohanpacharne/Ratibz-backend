package com.lonar.master.a2zmaster.dao;

import java.util.List;

//import com.lonar.a2zcommons.model.LtMastSuppliers;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtAboutUs;
import com.lonar.master.a2zmaster.model.LtMastSuppliers;
import com.lonar.master.a2zmaster.model.LtSupplierCities;
import com.lonar.master.a2zmaster.model.LtSupplierStates;

public interface LtMastSuppliersDao {

	LtMastSuppliers getLtMastSuppliersById(Long supplierId) throws ServiceException;

	List<LtMastSuppliers> getAllSuppliers() throws ServiceException;

	List<LtMastSuppliers> ltMastSuppliersDao(String supplierCode) throws ServiceException;

	LtMastSuppliers getPaymentDetailsById(Long supplierId) throws ServiceException;
	
	String getPrepaidSupplierFlag(Long supplierId) throws ServiceException;
	
	String getIsPrepaidSupplierFlagByUserId(Long userId) throws ServiceException;

	String getIsPrepaidSupplierFlagByInvoiceId(Long invoiceId) throws ServiceException;
	
	List<LtMastSuppliers> getAllPostpaidSuppliers() throws ServiceException;

	List<LtMastSuppliers> ltMastSuppliersLikeName(String supplierName) throws ServiceException;

	List<LtMastSuppliers> ltMastSuppliersByCode(String supplierCode) throws ServiceException;

	List<LtMastSuppliers> getSuppliersLikeMobileNo(Long mobileNo) throws ServiceException;

	LtAboutUs getSupplierAboutUs(Long supplierId) throws Exception;

	LtSupplierStates getSupplierState(Long supplierId) throws ServiceException;

	LtSupplierCities getSupplierCity(Long supplierId) throws ServiceException;

}
