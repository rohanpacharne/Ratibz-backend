package com.lonar.master.a2zmaster.service;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtAboutUs;
import com.lonar.master.a2zmaster.model.LtMastLogins;
import com.lonar.master.a2zmaster.model.LtMastSupplierRegistration;
import com.lonar.master.a2zmaster.model.LtMastSuppliers;
import com.lonar.master.a2zmaster.model.LtSupplierInvoicePayments;
import com.lonar.master.a2zmaster.model.Status;



public interface LtMastSuppliersRegService {

	LtMastSupplierRegistration getLtMastSupplierRegById(Long supplierRegId) throws ServiceException;

	Status saveLtMastSuppliersReg(LtMastSupplierRegistration ltMastSupplierRegistration) throws ServiceException;
	
	ResponseEntity<Status> getSupplierAboutUs(Long supplierId);
	
	ResponseEntity<Status> sendSmsToNewSupplier(String mobileNo, HttpServletRequest httpRequest);	

	ResponseEntity<Status> saveNewSupplier(LtMastSuppliers mastSuppliers, HttpServletResponse response);
	
	ResponseEntity<Status> saveSupplierAdditionalDetails(LtMastSuppliers mastSuppliers);
	
	ResponseEntity<Status> saveSupplierContactDetails(LtAboutUs aboutUs); 
	
	ResponseEntity<Status> getSupplierDetails(Long supplierId);
	
	ResponseEntity<Status> getSupplierAdditionalDetails(Long supplierId);
	
	ResponseEntity<Status> getSupplierContactDetails(Long supplierId);
	
	ResponseEntity<Status> saveSupplierPaymentDetails(LtMastSuppliers mastSuppliers);
	
	ResponseEntity<Status> getSupplierPaymentDetails(Long supplierId);
	
	ResponseEntity<Status> getCompanyUPIDetails(Long supplierId);

	ResponseEntity<Status> validateSupplierRegistationOtp(LtMastLogins logins, HttpServletResponse response );
	
	ResponseEntity<Status> createSupplierInvoice(LtSupplierInvoicePayments payment );
	
	ResponseEntity<Status> activateTrialSupplier(LtSupplierInvoicePayments payment );
	
	ResponseEntity<Status> saveSysVariable(Long supplierId);
	
	ResponseEntity<Status> getSupplierConfigurationDetails(Long supplierId);
	
	ResponseEntity<Status> saveSupplierConfigurationDetails(LtMastSuppliers mastSuppliers);
}
