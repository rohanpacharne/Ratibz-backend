package com.lonar.master.a2zmaster.dao;

import java.util.List;

import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtAboutUs;
import com.lonar.master.a2zmaster.model.LtMastCompany;
import com.lonar.master.a2zmaster.model.LtMastSupplierRegistration;
import com.lonar.master.a2zmaster.model.LtMastSuppliers;
import com.lonar.master.a2zmaster.model.LtMastSuppliers1;
import com.lonar.master.a2zmaster.model.LtSupplierDeliveryTimings;

//import com.users.usersmanagement.common.ServiceException;
//import com.users.usersmanagement.model.LtAboutUs;
//import com.users.usersmanagement.model.LtMastCompany;
//import com.users.usersmanagement.model.LtMastSupplierRegistration;
//import com.users.usersmanagement.model.LtMastSuppliers;
//import com.users.usersmanagement.model.LtSupplierDeliveryTimings;

public interface LtMastSuppliersRegDao {

	LtMastSupplierRegistration saveLtMastSuppliersReg(LtMastSupplierRegistration ltMastSupplierRegistration) throws ServiceException;

	LtMastSupplierRegistration getLtMastSupplierRegById(Long supplierRegId) throws ServiceException;
	
	LtMastSuppliers getSupplierDetails(Long supplierId);
	
	LtMastSuppliers1 getSupplierAdditionalDetails(Long supplierId);
	
	LtAboutUs getSupplierContactDetails(Long supplierId);

	LtMastSuppliers getSupplierPaymentDetails(Long supplierId);
	
	LtMastCompany getCompanyPaymentDetails();
	
	List<LtSupplierDeliveryTimings> getSupplierDeliveryTiming(Long supplierId);
	
	boolean saveSysVariable( Long supplierId);
	
	boolean saveSupplierSMSDetails( Long supplierId);
	
	LtMastSuppliers1 getSupplierAboutUs(Long supplierId);
	
	void updateSupplierUpi(LtMastSuppliers mastSuppliers );
	void updateSupplierAboutUs(LtMastSuppliers mastSuppliers );
}
