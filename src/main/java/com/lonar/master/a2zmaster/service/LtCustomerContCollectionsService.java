package com.lonar.master.a2zmaster.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.LtCustomerContCollections;
//import com.lonar.a2zcommons.model.LtCustomerSubsDeliveries;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtCustomerContCollections;
import com.lonar.master.a2zmaster.model.LtCustomerSubsDeliveries;
import com.lonar.master.a2zmaster.model.Status;

public interface LtCustomerContCollectionsService {

	Status save(LtCustomerContCollections ltCustomerContCollections) throws ServiceException;

	Status update(LtCustomerContCollections ltCustomerContCollections) throws ServiceException;

	LtCustomerContCollections getLtCustomerContCollectionsById(Long conCollId) throws ServiceException;

	Status delete(Long conCollId) throws ServiceException;

	List<LtCustomerSubsDeliveries> collectionSummary(LtCustomerContCollections ltCustomerContCollections) throws ServiceException;

	CustomeDataTable getDataTable(LtCustomerContCollections ltCustomerContCollections, Long supplierId) throws ServiceException;
	
	LtCustomerContCollections getContainerDeliverAndCollectedQty(Long subId);
	
	ResponseEntity<Status> saveDelivereAndCollectQty(LtCustomerContCollections collections);
	
	ResponseEntity<CustomeDataTable> getCollectionQtyReport( LtCustomerContCollections input );

}
