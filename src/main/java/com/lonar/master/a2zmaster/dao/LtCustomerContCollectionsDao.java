package com.lonar.master.a2zmaster.dao;

import java.util.List;

//import com.lonar.a2zcommons.model.LtCustomerContCollections;
//import com.lonar.a2zcommons.model.LtCustomerSubsDeliveries;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtCustomerContCollections;
import com.lonar.master.a2zmaster.model.LtCustomerSubsDeliveries;

public interface LtCustomerContCollectionsDao {

	LtCustomerContCollections save(LtCustomerContCollections ltCustomerContCollections) throws ServiceException;

	LtCustomerContCollections getLtCustomerContCollectionsById(Long conCollId) throws ServiceException;

	LtCustomerContCollections delete(Long conCollId) throws ServiceException;

	List<LtCustomerSubsDeliveries> collectionSummary(LtCustomerContCollections ltCustomerContCollections) throws ServiceException;

	Long getDataTableCount(LtCustomerContCollections ltCustomerContCollections, Long supplierId) throws ServiceException;

	List<LtCustomerSubsDeliveries> getDataTable(LtCustomerContCollections ltCustomerContCollections, Long supplierId, String isOwnContainer) throws ServiceException;

	Long getConCollectedByUserProductSubsDeliveryDate(LtCustomerSubsDeliveries customerSubsDeliveries) throws ServiceException;
	
	Double getContainerDeliveredQuantity(Long subId) throws Exception;
	Long getContainerCollectedQuantity(Long subId) throws Exception;
	
	Long getCollectionQtyReportCount(LtCustomerContCollections input) throws Exception;
	List<LtCustomerContCollections> getCollectionQtyReport(LtCustomerContCollections input) throws Exception;
	
	Double getCollectedBalanceQty(LtCustomerContCollections input, String isOwnContainer)throws Exception;
	
	LtCustomerContCollections getByDeliveryIdAndSupplierId(Long deliveryId, Long supplierId)throws Exception;

}
