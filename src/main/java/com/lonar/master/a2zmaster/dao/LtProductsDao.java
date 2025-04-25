package com.lonar.master.a2zmaster.dao;

import java.util.List;

//import com.lonar.a2zcommons.model.LtProducts;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtProducts;

public interface LtProductsDao {

	Long getLtProductsCount(LtProducts input, Long supplierId) throws ServiceException;

	List<LtProducts> getLtProductsDataTable(LtProducts input, Long supplierId) throws ServiceException;

	LtProducts save(LtProducts ltProducts) throws ServiceException;

	List<LtProducts> findByProductName(String productName, Long supplierId) throws ServiceException;

	LtProducts getLtProductById(Long productId) throws ServiceException;

	List<LtProducts> getAllActiveProducts(Long supplierId) throws ServiceException;

	LtProducts delete(Long productId) throws ServiceException;

	List<LtProducts> getAllActiveProductsByType(Long typeId, Long supplierId) throws ServiceException;

	List<LtProducts> getAllActiveProductByCategory(Long categoryId) throws ServiceException;

	List<LtProducts> findByProductCode(String productCode, Long supplierId) throws ServiceException;

	List<LtProducts> getAllActiveProductByCategoryForUser(Long categoryId) throws ServiceException;

	List<LtProducts> getCustomerSubscribeProduct(Long supplierId, Long customerId) throws Exception;
	
	String getSystemValue(Long supplierId, String variableName );

	LtProducts findById(Long productId);
}
