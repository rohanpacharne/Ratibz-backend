package com.lonar.master.a2zmaster.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.LtCustomerContCollections;
//import com.lonar.a2zcommons.model.LtCustomerSubsDeliveries;
//import com.lonar.a2zcommons.model.LtMastSuppliers;
//import com.lonar.a2zcommons.model.LtMastUsers;
//import com.lonar.a2zcommons.model.LtProducts;
import com.lonar.master.a2zmaster.common.BusinessException;
import com.lonar.master.a2zmaster.common.NotificationServiceCall;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.dao.LtCustomerContCollectionsDao;
import com.lonar.master.a2zmaster.dao.LtCustomerSubsDeliveriesDao;
import com.lonar.master.a2zmaster.dao.LtMastSuppliersDao;
import com.lonar.master.a2zmaster.dao.LtMastUsersDao;
import com.lonar.master.a2zmaster.dao.LtProductsDao;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtCustomerContCollections;
import com.lonar.master.a2zmaster.model.LtCustomerSubsDeliveries;
import com.lonar.master.a2zmaster.model.LtMastSuppliers;
import com.lonar.master.a2zmaster.model.LtProducts;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.utils.UtilsMaster;

@Service
public class LtCustomerContCollectionsServiceImpl implements LtCustomerContCollectionsService, CodeMaster {

	@Autowired
	private LtCustomerContCollectionsDao ltCustomerContCollectionsDao;
	
	@Autowired
	private LtMastCommonMessageService ltMastCommonMessageService;

	@Autowired
	private LtProductsDao ltProductsDao;

	@Autowired
	private LtMastUsersDao ltMastUsersDao;

	@Autowired
	private LtMastSuppliersDao mastSuppliersDao;
	
	@Autowired
	private NotificationServiceCall notificationServiceCall;
	
	@Autowired
	private LtCustomerSubsDeliveriesDao customerSubsDeliveriesDao;
	
	@Override
	public Status save(LtCustomerContCollections ltCustomerContCollections) throws ServiceException {
		Status status = new Status();
		ltCustomerContCollections.setCreationDate(UtilsMaster.getCurrentDateTime());
		ltCustomerContCollections.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
		ltCustomerContCollections.setCreatedBy(ltCustomerContCollections.getLastUpdateLogin());
		ltCustomerContCollections.setLastUpdatedBy(ltCustomerContCollections.getLastUpdateLogin());
		ltCustomerContCollections = ltCustomerContCollectionsDao.save(ltCustomerContCollections);
		if (ltCustomerContCollections.getContCollectionId() != null) {
			status = ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
		} else {
			status = ltMastCommonMessageService.getCodeAndMessage(INSERT_FAIL);
		}
		return status;
	}

	
	//original code
	
//	@Override
//	public Status update(LtCustomerContCollections ltCustomerContCollections) throws ServiceException {
//		Status status = new Status();
//		ltCustomerContCollections.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
//		ltCustomerContCollections.setLastUpdatedBy(ltCustomerContCollections.getCreatedBy());
//		
//		if(ltCustomerContCollections.getContCollectionId() == null || ltCustomerContCollections.getContCollectionId() < 1 ) {
//			ltCustomerContCollections.setCreationDate(UtilsMaster.getCurrentDateTime());				
//		}
//		ltCustomerContCollections.setDeliveryQuantity(0d);
//		
//		ltCustomerContCollections = ltCustomerContCollectionsDao.save(ltCustomerContCollections);
//		if (ltCustomerContCollections.getContCollectionId() != null) {
//			LtMastUsers ltMastUser = ltMastUsersDao.getUserById(ltCustomerContCollections.getUserId());
//			LtProducts ltProducts = ltProductsDao.getLtProductById(ltCustomerContCollections.getProductId());
//			notificationServiceCall.saveCustomerNotification(ltMastUser,
//					"Hi, containers of " + ltProducts.getProductName() + ", "
//							+ ltCustomerContCollections.getCollectedQuantity() + " " + ltProducts.getProductUom()
//							+ " " + " has been collected ");
//			//LtMastUsers ltLoginUser = ltMastUsersDao.getUserById(ltCustomerContCollections.getLastUpdateLogin());
//			LtMastUsers ltLoginUser = ltMastUsersDao.getUserById(ltCustomerContCollections.getCreatedBy());
//			if (ltLoginUser.getUserType().equals("DELIVERYSUPERVISOR")) {
//				//Hi, containers of <<Product Name>>, <<Modified Quantity>> <<unit>> has been collected from <<Customer Name>>
//				notificationServiceCall.saveSupplierNotification(ltLoginUser,
//						"Hi, containers of  " + ltProducts.getProductName() +", "+ltCustomerContCollections.getCollectedQuantity() + " "
//								+ ltProducts.getProductUom() + " has been collected from "
//								+ ltMastUser.getUserName());				
//			}
//			status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
//		} else {
//			status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
//		}
//		return status;
//	}
	
	@Override
	public Status update(LtCustomerContCollections ltCustomerContCollections) throws ServiceException {
	    Status status = new Status();
	    ltCustomerContCollections.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
	    ltCustomerContCollections.setLastUpdatedBy(ltCustomerContCollections.getCreatedBy());

	    if (ltCustomerContCollections.getContCollectionId() == null || ltCustomerContCollections.getContCollectionId() < 1) {
	        ltCustomerContCollections.setCreationDate(UtilsMaster.getCurrentDateTime());
	    }
	    ltCustomerContCollections.setDeliveryQuantity(0d);

	    ltCustomerContCollections = ltCustomerContCollectionsDao.save(ltCustomerContCollections);

	    if (ltCustomerContCollections.getContCollectionId() != null) {
	        status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
	    } else {
	        status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
	    }

	    return status;
	}

	
	

	@Override
	public LtCustomerContCollections getLtCustomerContCollectionsById(Long conCollId) throws ServiceException {
		return ltCustomerContCollectionsDao.getLtCustomerContCollectionsById(conCollId);
	}

	@Override
	public Status delete(Long conCollId) throws ServiceException {
		Status status = new Status();
		LtCustomerContCollections ltCustomerContCollections = ltCustomerContCollectionsDao.delete(conCollId);
		if (ltCustomerContCollections == null) {
			status = ltMastCommonMessageService.getCodeAndMessage(DELETE_SUCCESSFULLY);
		} else {
			status = ltMastCommonMessageService.getCodeAndMessage(DELETE_FAIL);
		}
		return status;
	}

	@Override
	public List<LtCustomerSubsDeliveries> collectionSummary(LtCustomerContCollections ltCustomerContCollections)
			throws ServiceException {
		String fileOpenPath = null;
		List<LtCustomerSubsDeliveries> ltCustomerSubsDeliveriesList = ltCustomerContCollectionsDao
				.collectionSummary(ltCustomerContCollections);
		if (ltCustomerSubsDeliveriesList != null && !ltCustomerSubsDeliveriesList.isEmpty()) {
			/*Map<Long, Map<String, String>> map = A2zMasterManagementApplication.configMap;
			Map<String, String> configPropertyMap = map.get(ltCustomerContCollections.getSupplierId());
			if (configPropertyMap != null) {
				fileOpenPath = configPropertyMap.get("FILE_OPEN_PATH");
			}*/
			
			fileOpenPath = ltProductsDao.getSystemValue(ltCustomerContCollections.getSupplierId(), "FILE_OPEN_PATH");
			
			for (LtCustomerSubsDeliveries ltCustomerSubsDeliveries : ltCustomerSubsDeliveriesList) {
				if (ltCustomerSubsDeliveries.getProductImage() != null) {
					ltCustomerSubsDeliveries
							.setProductImagePath(fileOpenPath + ltCustomerSubsDeliveries.getProductImage());
				}
			}
		}
		return ltCustomerSubsDeliveriesList;
	}

	@Override
	public CustomeDataTable getDataTable(LtCustomerContCollections ltCustomerContCollections, Long supplierId)
			throws ServiceException {
		String fileOpenPath = null;
		CustomeDataTable customeDataTable = new CustomeDataTable();
		Long count = ltCustomerContCollectionsDao.getDataTableCount(ltCustomerContCollections,supplierId);
	    customeDataTable.setRecordsTotal(count);
	    customeDataTable.setRecordsFiltered(count);
	    LtMastSuppliers mastSuppliers = mastSuppliersDao.getLtMastSuppliersById(supplierId);
	    
		List<LtCustomerSubsDeliveries> ltCustomerContCollectionsList = 
				ltCustomerContCollectionsDao.getDataTable(ltCustomerContCollections,supplierId, mastSuppliers.getOwnContainers() );
		if (ltCustomerContCollectionsList != null && !ltCustomerContCollectionsList.isEmpty()) {
			/*Map<Long, Map<String, String>> map = A2zMasterManagementApplication.configMap;
			Map<String, String> configPropertyMap = map.get(ltCustomerContCollections.getSupplierId());
			if (configPropertyMap != null) {
				fileOpenPath = configPropertyMap.get("FILE_OPEN_PATH");
			}*/			
			fileOpenPath = ltProductsDao.getSystemValue(ltCustomerContCollections.getSupplierId(), "FILE_OPEN_PATH");
			
			for (LtCustomerSubsDeliveries ltCustomerSubsDeliveries : ltCustomerContCollectionsList) {
				if (ltCustomerSubsDeliveries.getProductImage() != null) {
					ltCustomerSubsDeliveries
							.setProductImagePath(fileOpenPath + ltCustomerSubsDeliveries.getProductImage());
				}
			}
		}
		customeDataTable.setData(ltCustomerContCollectionsList);
		return customeDataTable;
	}

	@Override
	public LtCustomerContCollections getContainerDeliverAndCollectedQty(Long subId) {
		try {
			LtCustomerContCollections collections = new LtCustomerContCollections();
			  Double deliveredQty = ltCustomerContCollectionsDao.getContainerDeliveredQuantity(subId);
			  
			  if(deliveredQty != null && deliveredQty > 0) {
				  collections.setDeliveryQuantity( deliveredQty );  
			  }else {
				  collections.setDeliveryQuantity( 0d );
			  }
			  
			  Long collectedQty = ltCustomerContCollectionsDao.getContainerCollectedQuantity(subId);
			  if(collectedQty != null && collectedQty > 0) {
				  collections.setCollectedQuantity( collectedQty );  
			  }else {
				  collections.setCollectedQuantity( 0L );
			  }
			   
			  return collections;
		}catch(Exception e) {
			e.printStackTrace();
			 throw new BusinessException(0, null, e  );
		}
	}

	@Override
	public ResponseEntity<Status> saveDelivereAndCollectQty(LtCustomerContCollections collections) {
		try {
			Status status = new Status();
			LtCustomerSubsDeliveries deliveries = new LtCustomerSubsDeliveries(); 
			deliveries.setSupplierId( collections.getSupplierId());
			deliveries.setDeliveryDate(UtilsMaster.getCurrentDateTime());
			deliveries.setSubsId(collections.getSubsId());
			deliveries.setUserId(collections.getUserId());
			deliveries.setProductId(collections.getProductId());
			//deliveries.setSubsQuantity(collections.getDeliveredQuantity());
			deliveries.setDeliveryMode(collections.getDeliveryMode());
			//deliveries.setDeliveryQuantity(collections.getDeliveredQuantity());
			
			deliveries.setSubsQuantity(collections.getDeliveryQuantity());
			deliveries.setDeliveryQuantity(collections.getDeliveryQuantity());
			
			deliveries.setInvoiceRate(collections.getProductRate());
			deliveries.setCreatedBy(collections.getCreatedBy());
			deliveries.setLastUpdatedBy(collections.getCreatedBy());
			deliveries.setCreationDate(UtilsMaster.getCurrentDateTime());
			deliveries.setLastUpdateLogin(collections.getLastUpdateLogin());
			deliveries.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
			
			deliveries.setStatus("DELIVERED");
			
			deliveries = customerSubsDeliveriesDao.save(deliveries);
			
			collections.setStatus("DELIVERED");
			collections.setCreationDate(UtilsMaster.getCurrentDateTime());
			collections.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
			collections.setLastUpdatedBy(collections.getCreatedBy());
			collections.setCollectionDate(UtilsMaster.getCurrentDateTime());
			
			collections.setDeliveryId( deliveries.getDeliveryId() );
			
			//TODO add lastUpdateLogin field in LOGIN API
			ltCustomerContCollectionsDao.save(collections);
			status = ltMastCommonMessageService.getCodeAndMessage(SUCCESS);
//			status.setCode(SUCCESS);			
//			status.setMessage("Action successful");
			
			//Hi, Delivered Qty <<Qty>> <<UOM>>, Collected Qty <<Qty>> <<UOM>> of << Product Name>> on date <<CurrentDate>>
			
			LtProducts ltProducts = ltProductsDao.getLtProductById(collections.getProductId());
			
			notificationServiceCall.saveCustomerNotification(collections.getUserId(),  collections.getSupplierId() ,
					"Hi, Delivered Qty " + collections.getDeliveryQuantity() +" "+ ltProducts.getProductUom() +", Collected Qty "
							+ collections.getCollectedQuantity() +" "+ ltProducts.getProductUom() + " of " + ltProducts.getProductName()
							+ " on date  " + UtilsMaster.dateFormatdMMMyyyy.format(UtilsMaster.getCurrentDateTime()));
			
			//status.setMessage(" Delevered Successfully ");
			return new ResponseEntity<Status>(status, HttpStatus.OK);
		} catch(Exception e ) {
			e.printStackTrace();
			throw new BusinessException(0, null, e  );
		}
		
	}

	@Override
	public ResponseEntity<CustomeDataTable> getCollectionQtyReport(LtCustomerContCollections input) {
		 		
		try {
			CustomeDataTable customeDataTable = new CustomeDataTable();		
			
			LtMastSuppliers mastSuppliers = mastSuppliersDao.getLtMastSuppliersById(input.getSupplierId());
			
			customeDataTable.setBalanceQty( ltCustomerContCollectionsDao.getCollectedBalanceQty(input, mastSuppliers.getOwnContainers()));
			
			Long count = ltCustomerContCollectionsDao.getCollectionQtyReportCount( input );
			List<LtCustomerContCollections> contCollections = ltCustomerContCollectionsDao.getCollectionQtyReport( input );			
			customeDataTable.setRecordsTotal(count);
			customeDataTable.setData(contCollections);			
			return new ResponseEntity<CustomeDataTable>(customeDataTable, HttpStatus.OK );
		} catch (Exception e) {
			e.printStackTrace();
			//return null;
			throw new BusinessException(0, e.toString() , e);
		}
		
	}

}
