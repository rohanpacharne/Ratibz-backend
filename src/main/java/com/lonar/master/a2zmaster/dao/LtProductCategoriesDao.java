package com.lonar.master.a2zmaster.dao;

import java.util.List;

//import com.lonar.a2zcommons.model.LtProductCategories;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtProductCategories;

public interface LtProductCategoriesDao {

	Long getLtProductCategoriesCount(LtProductCategories input, Long supplierId) throws ServiceException;

	List<LtProductCategories> getLtProductCategoriesDataTable(LtProductCategories input, Long supplierId) throws ServiceException;

	LtProductCategories save(LtProductCategories ltProductCategories) throws ServiceException;

	List<LtProductCategories> findByProductCategoryName(String categoryName, Long supplierId) throws ServiceException;

	LtProductCategories getLtProductCategoriesById(Long categoryId) throws ServiceException;

	LtProductCategories delete(Long categoryId) throws ServiceException;

	List<LtProductCategories> getAllActiveCategories(Long supplierId) throws ServiceException;

	List<LtProductCategories> getAllActiveCategoryByType(Long typeId) throws ServiceException;

	

	List<LtProductCategories> getAllActiveCategoriesByTypeSupplier(Long typeId, Long supplierId) throws ServiceException;

}
