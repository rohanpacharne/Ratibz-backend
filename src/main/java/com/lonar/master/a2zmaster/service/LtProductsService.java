package com.lonar.master.a2zmaster.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.LtProducts;
//import com.lonar.a2zcommons.model.ProductWithCategory;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtProducts;
import com.lonar.master.a2zmaster.model.ProductWithCategory;
import com.lonar.master.a2zmaster.model.Status;

public interface LtProductsService {

	CustomeDataTable getDataTable(Long supplierId, LtProducts input) throws ServiceException;

	Status save(LtProducts ltProducts) throws ServiceException;

	Status update(LtProducts ltProducts) throws ServiceException;

	LtProducts getLtProductById(Long productId) throws ServiceException;

	List<LtProducts> getAllActiveProducts(Long supplierId) throws ServiceException;

	Status delete(Long productId) throws ServiceException;

	Status saveProductWithFile(LtProducts ltProducts, MultipartFile multipartFile) throws ServiceException;

	List<ProductWithCategory> getAllActiveProductsByType(Long typeId, Long supplierId, Long userId) throws ServiceException;
	
	ResponseEntity<List<LtProducts>> getCustomerSubscribeProduct( Long supplierId, Long customerId  );

	Status updateProductWithFile(LtProducts ltProducts, MultipartFile multipartFile) throws ServiceException;

}
