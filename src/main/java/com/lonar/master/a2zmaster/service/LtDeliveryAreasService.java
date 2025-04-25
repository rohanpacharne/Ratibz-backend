package com.lonar.master.a2zmaster.service;

import java.util.List;

//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.LtDeliveryAreas;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtDeliveryAreas;
import com.lonar.master.a2zmaster.model.Status;

public interface LtDeliveryAreasService {

	CustomeDataTable getDataTable(LtDeliveryAreas input, Long supplierId) throws ServiceException;

	Status save(LtDeliveryAreas ltDeliveryAreas) throws ServiceException;

	Status update(LtDeliveryAreas ltDeliveryAreas) throws ServiceException;

	LtDeliveryAreas getLtDeliveryAreasById(Long areaId) throws ServiceException;

	List<LtDeliveryAreas> getAllActiveDeliveryAreas(Long supplierId) throws ServiceException;

	Status delete(Long areaId) throws ServiceException;

	List<LtDeliveryAreas> getAllActiveAreasByCity(Long cityId, Long supplierId) throws ServiceException;

	List<LtDeliveryAreas> getAllActiveAreasByPin(String pincode, Long supplierId) throws ServiceException;
	
	List<LtDeliveryAreas> getAllPinCodes(Long supplierId) throws ServiceException;
	
}
