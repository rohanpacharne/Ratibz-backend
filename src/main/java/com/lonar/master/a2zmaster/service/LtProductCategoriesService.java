package com.lonar.master.a2zmaster.service;

import java.util.List;

//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.LtProductCategories;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtProductCategories;
import com.lonar.master.a2zmaster.model.Status;

public interface LtProductCategoriesService {

	CustomeDataTable getDataTable(LtProductCategories input, Long supplierId) throws ServiceException;

	Status save(LtProductCategories ltProductCategories) throws ServiceException;

	Status update(LtProductCategories ltProductCategories) throws ServiceException;

	LtProductCategories getLtProductCategoriesById(Long categoryId) throws ServiceException;

	Status delete(Long categoryId) throws ServiceException;

	List<LtProductCategories> getAllActiveCategories(Long supplierId) throws ServiceException;

	List<LtProductCategories> getAllActiveCategoriesByType(Long typeId, Long supplierId) throws ServiceException;

}
