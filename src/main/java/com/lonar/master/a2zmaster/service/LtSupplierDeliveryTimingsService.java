package com.lonar.master.a2zmaster.service;

import java.util.List;

//import com.lonar.a2zcommons.model.LtSupplierDeliveryTimings;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtSupplierDeliveryTimings;
import com.lonar.master.a2zmaster.model.Status;

public interface LtSupplierDeliveryTimingsService {

	Status save(LtSupplierDeliveryTimings ltSupplierDeliveryTimings) throws ServiceException;

	Status update(LtSupplierDeliveryTimings ltSupplierDeliveryTimings) throws ServiceException;

	LtSupplierDeliveryTimings getLtSupplierDeliveryTimingsById(Long deliveryTimeId) throws ServiceException;

	Status delete(Long deliveryTimeId) throws ServiceException;

	List<LtSupplierDeliveryTimings> getAllDeliveryTimings(Long supplierId) throws ServiceException;

}
