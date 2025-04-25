package com.lonar.master.a2zmaster.dao;

import java.util.List;

//import com.lonar.a2zcommons.model.LtDeliveryAreas;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtDeliveryAreas;

public interface LtDeliveryAreasDao {

	Long getLtDeliveryAreasCount(LtDeliveryAreas input, Long supplierId) throws ServiceException;

	List<LtDeliveryAreas> getLtDeliveryAreasDataTable(LtDeliveryAreas input, Long supplierId) throws ServiceException;

	LtDeliveryAreas save(LtDeliveryAreas ltDeliveryAreas) throws ServiceException;

	List<LtDeliveryAreas> findByAreaName(String area, Long supplierId) throws ServiceException;

	LtDeliveryAreas getLtDeliveryAreasById(Long areaId) throws ServiceException;

	List<LtDeliveryAreas> getAllActiveDeliveryAreas(Long supplierId) throws ServiceException;

	LtDeliveryAreas delete(Long areaId) throws ServiceException;

	List<LtDeliveryAreas> getAllActiveAreasByCity(Long cityId, Long supplierId) throws ServiceException;

	List<LtDeliveryAreas> getAllActiveAreasByPin(String pincode, Long supplierId) throws ServiceException;

	boolean update(LtDeliveryAreas ltDeliveryAreas) throws ServiceException;

	List<LtDeliveryAreas> getByAreaName(String area, Long supplierId)  throws ServiceException;
	
	List<LtDeliveryAreas> getAllPinCodes(Long supplierId) throws ServiceException;

}
