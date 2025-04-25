package com.lonar.master.a2zmaster.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.LtMastUsers;
//import com.lonar.a2zcommons.model.LtProductCategories;
//import com.lonar.a2zcommons.model.LtProducts;
//import com.lonar.a2zcommons.model.ProductWithCategory;
import com.lonar.master.a2zmaster.common.BusinessException;
import com.lonar.master.a2zmaster.common.NotificationServiceCall;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.dao.LtMastUsersDao;
import com.lonar.master.a2zmaster.dao.LtProductCategoriesDao;
import com.lonar.master.a2zmaster.dao.LtProductsDao;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtMastUsers;
//import com.lonar.master.a2zmaster.model.LtMastUsers;
import com.lonar.master.a2zmaster.model.LtProductCategories;
import com.lonar.master.a2zmaster.model.LtProducts;
import com.lonar.master.a2zmaster.model.ProductWithCategory;
import com.lonar.master.a2zmaster.model.Status;

@Service
public class LtProductsServiceImpl implements LtProductsService, CodeMaster {

	DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");

	@Autowired private LtProductsDao ltProductsDao;

	@Autowired
	private LtMastCommonMessageService ltMastCommonMessageService;

	@Autowired
	private LtProductCategoriesDao ltProductCategoriesDao;

	@Autowired
	private LtMastUsersDao ltMastUsersDao;

	@Autowired
	private NotificationServiceCall notificationServiceCall;

	@Override
	public CustomeDataTable getDataTable(Long supplierId, LtProducts input) throws ServiceException {
		String fileOpenPath = "";
		CustomeDataTable customeDataTable = new CustomeDataTable();
		Long count = ltProductsDao.getLtProductsCount(input, supplierId);
		customeDataTable.setRecordsTotal(count);
		customeDataTable.setRecordsFiltered(count);
		List<LtProducts> ltProductList = ltProductsDao.getLtProductsDataTable(input, supplierId);
		if (ltProductList != null) {
			/*Map<Long, Map<String, String>> map = A2zMasterManagementApplication.configMap;
			Map<String, String> configPropertyMap = map.get(supplierId);
			
			if (configPropertyMap != null) {
				fileOpenPath = configPropertyMap.get("FILE_OPEN_PATH");
			}*/
			
			//  'FILE_UPLOAD_PATH'
			// 1056 
//			fileOpenPath = ltProductsDao.getSystemValue(supplierId, "FILE_OPEN_PATH");
			fileOpenPath = "http://3.109.247.56:9092/ratibz/";
			
			for (LtProducts ltProducts : ltProductList) {
				if (ltProducts.getProductImage() != null) {
					ltProducts.setImagePath(fileOpenPath + ltProducts.getProductImage());
				}
			}
		}
		customeDataTable.setData(ltProductList);
		return customeDataTable;
	}

	@Override
	public Status save(LtProducts ltProducts) throws ServiceException {
		Status status = new Status();
		ltProducts.setCreatedBy(ltProducts.getLastUpdateLogin());
		ltProducts.setLastUpdateDate(new Date());
		ltProducts.setCreationDate(new Date());
		ltProducts.setLastUpdatedBy(ltProducts.getLastUpdateLogin());
		ltProducts.setStartDate(new Date());
		ltProducts.setStatus("ACTIVE");
		status = checkDuplicate(ltProducts);
		if (status != null) {
			return status;
		}
		ltProducts = ltProductsDao.save(ltProducts);
		if (ltProducts.getProductId() != null) {
			status = ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
			status.setData(ltProducts.getProductId());
			saveNotification(ltProducts);
		} else {
			status = ltMastCommonMessageService.getCodeAndMessage(INSERT_FAIL);
		}
		return status;
	}

	private void saveNotification(LtProducts ltProducts) throws ServiceException {
		List<LtMastUsers> usersList;
		try {
			usersList = ltMastUsersDao.getAllUsersBySupplierId(ltProducts.getSupplierId());
			for (LtMastUsers ltMastUsers : usersList) {
				notificationServiceCall.saveCustomerNotification(ltMastUsers,
						"Hi, New product " + ltProducts.getProductName()
								+ " has been added & will be available for subscription from "
								+ dateFormat.format(ltProducts.getStartDate()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Status checkDuplicate(LtProducts ltProducts) throws ServiceException {
		Status status = new Status();
		List<LtProducts> ltProductsList1 = ltProductsDao.findByProductCode(ltProducts.getProductCode(),
				ltProducts.getSupplierId());
		if (!ltProductsList1.isEmpty()) {
			if (ltProducts.getProductId() == null) {
//				status.setCode(FAIL);
				status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
				status.setMessage("Product code already exists.");
				return status;
			} else if (!ltProductsList1.get(0).getProductId().equals(ltProducts.getProductId())) {
//				status.setCode(FAIL);
				status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
				status.setMessage("Product code already exists.");
				return status;
			}
		}

		List<LtProducts> ltProductsList = ltProductsDao.findByProductName(ltProducts.getProductName(),
				ltProducts.getSupplierId());
		if (!ltProductsList.isEmpty()) {
			if (ltProducts.getProductId() == null) {
//				status.setCode(FAIL);
				status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
				status.setMessage("Product name already exists.");
				return status;
			} else if (!ltProductsList.get(0).getProductId().equals(ltProducts.getProductId())) {
//				status.setCode(FAIL);
				status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
				status.setMessage("Product name already exists.");
				return status;
			}
		}

		return null;
		/*
		 * if (!ltProductsList.isEmpty() && (ltProducts.getProductId() == null ? true :
		 * !ltProducts.getProductId().equals(ltProductsList.get(0).getProductId()))) {
		 * status.setCode(FAIL); status.setMessage("Product type already exists.");
		 * return status; }else return null;
		 */
	}

	@Override
	public Status update(LtProducts ltProducts) throws ServiceException {
		Status status = new Status();
		ltProducts.setLastUpdateDate(new Date());
		ltProducts.setLastUpdatedBy(ltProducts.getLastUpdateLogin());
		ltProducts.setStartDate(new Date());
		ltProducts.setStatus("ACTIVE");
		status = checkDuplicate(ltProducts);
		if (status != null) {
			return status;
		}

		ltProducts = ltProductsDao.save(ltProducts);
		if (ltProducts.getProductId() != null) {
			status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
			status.setData(ltProducts.getProductId());
		} else {
			status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
		}
		return status;
	}

	@Override
	public LtProducts getLtProductById(Long productId) throws ServiceException {
		LtProducts ltProducts = ltProductsDao.getLtProductById(productId);
		if (ltProducts != null) {
			/*Map<Long, Map<String, String>> map = A2zMasterManagementApplication.configMap;
			Map<String, String> configPropertyMap = map.get(ltProducts.getSupplierId());
			if (configPropertyMap != null) {
				String fileOpenPath = configPropertyMap.get("FILE_OPEN_PATH");
				*/
//				String fileOpenPath = ltProductsDao.getSystemValue(ltProducts.getSupplierId(), "FILE_OPEN_PATH");
				String fileOpenPath = "http://3.109.247.56:9092/ratibz/";
				
				if (ltProducts.getProductImage() != null) {
					ltProducts.setImagePath(fileOpenPath + ltProducts.getProductImage());
				}
			//}
			return ltProducts;
		}
		return ltProducts;
	}

	@Override
	public List<LtProducts> getAllActiveProducts(Long supplierId) throws ServiceException {
		String fileOpenPath = null;
		try {
		List<LtProducts> productList = ltProductsDao.getAllActiveProducts(supplierId);
		if (productList != null) {
/*			Map<Long, Map<String, String>> map = A2zMasterManagementApplication.configMap;
			Map<String, String> configPropertyMap = map.get(supplierId);
			if (configPropertyMap != null) {
				fileOpenPath = configPropertyMap.get("FILE_OPEN_PATH");
			}*/
			fileOpenPath = ltProductsDao.getSystemValue(supplierId, "FILE_OPEN_PATH");
			for (LtProducts ltProducts : productList) {
				if (ltProducts.getProductImage() != null) {
					ltProducts.setImagePath(FILE_OPEN_PATH + ltProducts.getProductImage());
				}
			}
		}
		return productList;
		}catch(Exception ex) {
			ex.printStackTrace();
			return new ArrayList<LtProducts>();
		}
	}

	@Override
	public Status delete(Long productId) throws ServiceException {
		Status status = new Status();
		LtProducts ltProducts = ltProductsDao.delete(productId);
		if (ltProducts == null) {
			status = ltMastCommonMessageService.getCodeAndMessage(DELETE_SUCCESSFULLY);
		} else {
			status = ltMastCommonMessageService.getCodeAndMessage(DELETE_FAIL);
		}
		return status;
	}

	@Override
	public Status saveProductWithFile(LtProducts ltProducts, MultipartFile multipartFile) throws ServiceException {
		Status status = new Status();
		ltProducts.setCreationDate(new Date());
		ltProducts.setLastUpdateDate(new Date());
		ltProducts.setCreatedBy(ltProducts.getLastUpdateLogin());
		ltProducts.setLastUpdatedBy(ltProducts.getLastUpdateLogin());
		System.out.println("ltProducts2::"+ltProducts);
		if (ltProducts.getDisplayFlag() == null) {
			ltProducts.setDisplayFlag("Y");
		}
		status = checkDuplicate(ltProducts);
		if (status != null) {
			return status;
		}
		boolean isNewProduct = true;
		if(ltProducts.getProductId() != null && ltProducts.getProductId() > 0 ) {
			isNewProduct = false;
		}
		ltProducts = ltProductsDao.save(ltProducts);
		if (ltProducts.getProductId() != null) {
			if (multipartFile != null) {
				if (this.saveImage(ltProducts, multipartFile)) {
					status = ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
					status.setData(ltProducts.getProductId());
					if(isNewProduct) {
//						ProductNotificationThread productNotificationThread = new ProductNotificationThread(ltProducts,ltMastUsersDao,notificationServiceCall);
//						productNotificationThread.start();
					}
					//saveNotification(ltProducts);
				} else {
					status = ltMastCommonMessageService.getCodeAndMessage(INSERT_FAIL);
					return status;
				}
				return status;
			} else {
				status = ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
				status.setData(ltProducts.getProductId());
				if(isNewProduct) {
					ProductNotificationThread productNotificationThread = new ProductNotificationThread(ltProducts,ltMastUsersDao,notificationServiceCall);
//					productNotificationThread.start();
				}
				//saveNotification(ltProducts);
				return status;
			}
		} else {
			status = ltMastCommonMessageService.getCodeAndMessage(INSERT_FAIL);
			return status;
		}
	}

	private boolean saveImage(LtProducts ltProducts, MultipartFile uploadfile) {
		String saveDirectory = "";
		String fileExtentions = ".jpeg,.jpg,.png,.bmp,.pdf";
		String extension;
		try {
			/*Map<Long, Map<String, String>> map = A2zMasterManagementApplication.configMap;
			Map<String, String> configPropertyMap = map.get(ltProducts.getSupplierId());
			if (configPropertyMap != null) {
				saveDirectory = configPropertyMap.get("FILE_UPLOAD_PATH");
			}
			*/
			
//			saveDirectory = ltProductsDao.getSystemValue(ltProducts.getSupplierId(), "FILE_UPLOAD_PATH");
			
			saveDirectory = "/home/lonar-admin/rohan/opt/uploads/";
			
			File dir = new File(saveDirectory + "Supplier_" + ltProducts.getSupplierId() + "/" + "Product_Images" + "/");
			if (!dir.isDirectory()) {
				if (dir.mkdirs()) {
					System.out.println("Directory created");
				} else {
					System.out.println("Error in directory creation");
				}
			}

			byte[] bytes = uploadfile.getBytes();
			String[] fileFrags = uploadfile.getOriginalFilename().split("\\.");
			extension = fileFrags[fileFrags.length - 1];
			if (!fileExtentions.contains(extension)) {
				// fileUploadMsg = messageSource.getMessage("validfiletype", null, "Default",
				// Locale.getDefault());
				return false;
			} else {
				dir = new File(new String(saveDirectory + "Supplier_" + ltProducts.getSupplierId() + "/"
						+ "Product_Images" + "/" + uploadfile.getOriginalFilename()).replaceAll("amp;", ""));
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(dir));
				stream.write(bytes);
				stream.close();
				/*
				 * ltProducts.setProductImage(new String( "/Supplier_"
				 * +ltProducts.getSupplierId()+"/"+"Product_Images"+"/"+
				 * uploadfile.getOriginalFilename()).replaceAll("amp;", ""));
				 */
				ltProducts.setProductImage("Supplier_" + ltProducts.getSupplierId() + "/" + "Product_Images" + "/"
						+ uploadfile.getOriginalFilename());
				LtProducts ltProduct = ltProductsDao.save(ltProducts);
				if (ltProduct.getProductImage() != null) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
//	 @Override
//	    public Status updateProductWithFile(LtProducts ltProducts, MultipartFile multipartFile) throws ServiceException {
//	        Status status = new Status();
//
//	        // Validate Product ID
//	        if (ltProducts.getProductId() == null) {
//	        	status = ltMastCommonMessageService.getCodeAndMessage(INPUT_IS_EMPTY);
//	            return status;
//	            }
//
//	        // Fetch the existing product from DB
//	        LtProducts existingProduct = ltProductsDao.findById(ltProducts.getProductId());
//	        if (existingProduct == null) {
//	        	status = ltMastCommonMessageService.getCodeAndMessage(INPUT_IS_EMPTY);
//	            return status;
//	        }
//
//	        // Update fields
//	        existingProduct.setProductName(ltProducts.getProductName());
//	        existingProduct.setProductRate(ltProducts.getProductRate());
//	        existingProduct.setProductUom(ltProducts.getProductUom());
//	        existingProduct.setProductDesc(ltProducts.getProductDesc());
//	        existingProduct.setStatus(ltProducts.getStatus());
//	        existingProduct.setLastUpdateDate(new Date());
//	        existingProduct.setLastUpdatedBy(ltProducts.getLastUpdateLogin());
//
//	        // Update image if provided
//	        if (multipartFile != null) {
//	            if (!saveImage(existingProduct, multipartFile)) {
//	            	status = ltMastCommonMessageService.getCodeAndMessage(INPUT_IS_EMPTY);
//	                return status;
//	            }
//	        }
//
//	        ltProductsDao.save(existingProduct);
//	        status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
//	        status.setData(existingProduct.getProductId());
//	        return status;
//	    }
//	

	@Override
	public Status updateProductWithFile(LtProducts ltProducts, MultipartFile multipartFile) throws ServiceException {
	    Status status = new Status();

	    // Validate Product ID
	    if (ltProducts.getProductId() == null) {
	        System.out.println("Product ID is null. Returning with status: INPUT_IS_EMPTY");
	        status = ltMastCommonMessageService.getCodeAndMessage(INPUT_IS_EMPTY);
	        
	        return status;
	    }

	    // Fetch the existing product from DB
	    System.out.println("Fetching product with ID: " + ltProducts.getProductId());
	    LtProducts existingProduct = ltProductsDao.findById(ltProducts.getProductId());
	    if (existingProduct == null) {
	        System.out.println("No product found with ID: " + ltProducts.getProductId());
	        status = ltMastCommonMessageService.getCodeAndMessage(INPUT_IS_EMPTY);
	        return status;
	    }

	    // Update fields
	    System.out.println("Updating product fields for product ID: " + ltProducts.getProductId());
	    existingProduct.setProductName(ltProducts.getProductName());
	    existingProduct.setProductRate(ltProducts.getProductRate());
	    existingProduct.setProductUom(ltProducts.getProductUom());
	    existingProduct.setProductDesc(ltProducts.getProductDesc());
	    existingProduct.setStatus(ltProducts.getStatus());
	    existingProduct.setLastUpdateDate(new Date());
	    existingProduct.setLastUpdatedBy(ltProducts.getLastUpdateLogin());
	    existingProduct.setCategoryId(ltProducts.getCategoryId());
	    existingProduct.setProductTypeId(ltProducts.getProductTypeId());
	    existingProduct.setDisplayFlag(ltProducts.getDisplayFlag());
	    existingProduct.setCollectContainer(ltProducts.getCollectContainer());
	    existingProduct.setHsnCode(ltProducts.getHsnCode());
	    

	    // Update image if provided
	    if (multipartFile != null) {
	        System.out.println("Processing image for product ID: " + ltProducts.getProductId());
	        if (!saveImage(existingProduct, multipartFile)) {
	            System.out.println("Failed to save image for product ID: " + ltProducts.getProductId());
	            status = ltMastCommonMessageService.getCodeAndMessage(INPUT_IS_EMPTY);
	            return status;
	        }
	    }

	    System.out.println("Saving updated product to the database: " + existingProduct.getProductId());
	    ltProductsDao.save(existingProduct);
	    
	    status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
	    status.setData(existingProduct.getProductId());
	    System.out.println("Product updated successfully. Product ID: " + existingProduct.getProductId());
	    return status;
	}


	@Override
	public List<ProductWithCategory> getAllActiveProductsByType(Long typeId, Long supplierId, Long userId)
			throws ServiceException {
		String fileOpenPath = null;

		LtMastUsers ltMastUsers = ltMastUsersDao.getUserById(userId);

		List<ProductWithCategory> list = new ArrayList<ProductWithCategory>();
		List<LtProductCategories> categoryList = ltProductCategoriesDao.getAllActiveCategoryByType(typeId);
		if (categoryList != null) {
			for (LtProductCategories ltProductCategories : categoryList) {

				List<LtProducts> productList = null;
				if (ltMastUsers.getUserType().equals("SUPPLIER")) {

					productList = ltProductsDao.getAllActiveProductByCategory(ltProductCategories.getCategoryId());
				} else {
					productList = ltProductsDao.getAllActiveProductByCategoryForUser(ltProductCategories.getCategoryId());
				}
				if (productList != null) {
					/*Map<Long, Map<String, String>> map = A2zMasterManagementApplication.configMap;
					Map<String, String> configPropertyMap = map.get(supplierId);
					if (configPropertyMap != null) {
						fileOpenPath = configPropertyMap.get("FILE_OPEN_PATH");
					}*/					
//					fileOpenPath = ltProductsDao.getSystemValue(supplierId, "FILE_OPEN_PATH");
					
					fileOpenPath = "http://3.109.247.56:9092/ratibz/";
					
					for (LtProducts ltProducts : productList) {
						if (ltProducts.getProductImage() != null && !ltProducts.getProductImage().equals("")) {
							ltProducts.setImagePath(fileOpenPath + ltProducts.getProductImage());
						} else {
							ltProducts.setImagePath(null);
						}
					}
					ProductWithCategory productWithCategory = new ProductWithCategory();
					productWithCategory.setProductCategory(ltProductCategories.getCategoryName());
					productWithCategory.setProductList(productList);
					list.add(productWithCategory);
				}
			}
		}
		return list;
	}

	@Override
	public ResponseEntity<List<LtProducts>> getCustomerSubscribeProduct(Long supplierId, Long customerId) {
		 try {
			 return new ResponseEntity<List<LtProducts>>( ltProductsDao.getCustomerSubscribeProduct(supplierId, customerId), HttpStatus.OK) ;
		 }catch(Exception e) {
			 e.printStackTrace();
			 throw new BusinessException(0, null, e  );
		 }
	}

}
