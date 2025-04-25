package com.lonar.master.a2zmaster.dao;

import java.util.List;

//import com.lonar.a2zcommons.model.LtProductTypes;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtProductTypes;

public interface LtProductTypesDao {

	Long getLtProductTypesCount(LtProductTypes input, Long supplierId) throws ServiceException;

	List<LtProductTypes> getLtProductTypesDataTable(LtProductTypes input, Long supplierId) throws ServiceException;

	LtProductTypes save(LtProductTypes ltProductTypes) throws ServiceException;

	LtProductTypes getLtProductTypesById(Long productTypeId) throws ServiceException;

	List<LtProductTypes> findByProductTypeName(String productType, Long supplierId) throws ServiceException;

	LtProductTypes delete(Long productTypeId) throws ServiceException;

	List<LtProductTypes> getAllActiveProductType(Long supplierId) throws ServiceException;

}
