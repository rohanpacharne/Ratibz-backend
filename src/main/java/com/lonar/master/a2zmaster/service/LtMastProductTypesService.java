package com.lonar.master.a2zmaster.service;

import java.util.List;

import com.lonar.master.a2zmaster.model.LtCustomerSubsDeliveries;
import com.lonar.master.a2zmaster.model.LtMastProductTypes;
import com.lonar.master.a2zmaster.model.LtMastSuppliers;
import com.lonar.master.a2zmaster.model.Status;

public interface LtMastProductTypesService {

	List<LtMastProductTypes> getAllProductTypes(LtMastProductTypes input);

	Status getDeliveryDetails(LtCustomerSubsDeliveries request);

	Status getAllSupplierList(LtMastSuppliers input, LtMastSuppliers ltMastSuppliers);

	

}
