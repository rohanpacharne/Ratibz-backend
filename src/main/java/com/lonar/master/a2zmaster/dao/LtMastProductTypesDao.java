package com.lonar.master.a2zmaster.dao;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.lonar.master.a2zmaster.model.LtCustomerSubsDeliveries;
import com.lonar.master.a2zmaster.model.LtMastProductTypes;
import com.lonar.master.a2zmaster.model.LtMastSuppliers;

public interface LtMastProductTypesDao {

	List<LtMastProductTypes> getAllProductTypes(LtMastProductTypes input);

	List<LtCustomerSubsDeliveries> getDeliveryDetails(LtCustomerSubsDeliveries request);

	List<LtMastSuppliers> getAllSupplierList(LtMastSuppliers ltMastSuppliers, LtMastSuppliers input);

}
