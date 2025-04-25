package com.lonar.master.a2zmaster.dao;

import java.util.List;

//import com.lonar.a2zcommons.model.LtSupplierDeliveryTimings;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtSupplierDeliveryTimings;

public interface LtSupplierDeliveryTimingsDao {

	LtSupplierDeliveryTimings save(LtSupplierDeliveryTimings ltSupplierDeliveryTimings) throws ServiceException;

	LtSupplierDeliveryTimings getLtSupplierDeliveryTimingsById(Long deliveryTimeId) throws ServiceException;

	List<LtSupplierDeliveryTimings> getAllDeliveryTimings(Long supplierId) throws ServiceException;

	LtSupplierDeliveryTimings getDeliveryTiming(Long supplierId, String deliveryTime) throws ServiceException;
	
	LtSupplierDeliveryTimings getDeliveryTiming(Long supplierId, Long subId  ) throws ServiceException;
	
	LtSupplierDeliveryTimings delete(Long deliveryTimeId) throws ServiceException;
	
	LtSupplierDeliveryTimings getNextDeliveryTiming(Long supplierId) throws ServiceException;

}
