package com.lonar.master.a2zmaster.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.http.ResponseEntity;

//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.LtCustomerSubs;
//import com.lonar.a2zcommons.model.LtCustomerSubsDeliveries;
//import com.lonar.a2zcommons.model.LtMastSuppliers;
//import com.lonar.a2zcommons.model.LtMastUsers;
//import com.lonar.a2zcommons.model.LtSupplierDeliveryTimings;
//import com.lonar.a2zcommons.model.LtVacationPeriod;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtCustomerSubs;
import com.lonar.master.a2zmaster.model.LtCustomerSubsDeliveries;
import com.lonar.master.a2zmaster.model.LtMastSuppliers;
import com.lonar.master.a2zmaster.model.LtMastUsers;
import com.lonar.master.a2zmaster.model.LtSupplierDeliveryTimings;
import com.lonar.master.a2zmaster.model.LtVacationPeriod;
import com.lonar.master.a2zmaster.model.Status;

public interface LtCustomerSubsService {

	Status save(LtCustomerSubs ltCustomerSubs, LtMastUsers user) throws ServiceException,ParseException;

	Status update(LtCustomerSubs ltCustomerSubs, LtMastUsers user) throws ServiceException,ParseException;
	
	Status timeLimitValidation(LtMastUsers user, LtCustomerSubs customerSubs);

	LtCustomerSubs getCustomerSubsById(Long subsId, LtMastUsers user) throws ServiceException;

	List<LtCustomerSubs> getAllActiveSubs(Long userId, String subType) throws ServiceException;
	
	List<LtCustomerSubs> getAllActiveSubs_v1(Long userId, String subType) throws ServiceException;
	
	List<LtCustomerSubs> getSubscriptionProduct(Long userId, Long supplierId) throws ServiceException;

	Status delete(Long subsId)  throws ServiceException;

	LtCustomerSubsDeliveries getSubsDeliveryById(Long deliveryId) throws ServiceException;

	Status updateDelivery(List<LtCustomerSubsDeliveries> ltCustomerSubsDeliveries) throws ServiceException;

	Status updateDeliveryStatus(List<LtCustomerSubsDeliveries> ltCustomerSubsDeliveries);
	
	List<LtCustomerSubsDeliveries> getSubsDeliveryByCustId(Long customerId) throws ServiceException;

	CustomeDataTable deliverySummary(LtCustomerSubs ltCustomerSubs) throws ServiceException;
	
	CustomeDataTable getDeliverysByDeliveryAgent(LtCustomerSubs ltCustomerSubsDeliveries );
	
	LtSupplierDeliveryTimings nextDeliveryTime(Long supplierId);
	

	List<LtCustomerSubs> getAllSubs(Long supplierId) throws ServiceException;
	
	List<LtCustomerSubs> getAllSubsByUserId(Long userId, String status) throws ServiceException;

	CustomeDataTable customerDeliverySummary(LtCustomerSubsDeliveries ltCustomerSubsDeliveries, String version) throws ServiceException;
	
	CustomeDataTable getSubscriber(LtMastUsers user, String userType);

	List<LtCustomerSubsDeliveries> deliverySummaryStatus(LtCustomerSubsDeliveries ltCustomerSubsDeliveries) throws ServiceException;
	
	List<LtCustomerSubsDeliveries> getDailySubs(LtCustomerSubsDeliveries ltCustomerSubsDeliveries) throws ServiceException;
	
	//ResponseEntity<Status> getDailySubsV1(LtCustomerSubsDeliveries ltCustomerSubsDeliveries) throws ServiceException;
	
    Status getVacationId(Long userId, Long supplierId);

	Status updateQuantity(LtCustomerSubsDeliveries ltCustomerSubsDeliveries) throws ServiceException;

	Status updateVacation(LtCustomerSubs ltCustomerSubs, LtMastUsers user) throws ServiceException;
	
	Status updateDeliveryQuantity(LtCustomerSubs customerSubs);
	
	Status updateDeliveryOnce(LtCustomerSubsDeliveries customerSubs, LtMastUsers user);

	LtVacationPeriod getVacation(Long vacationId) throws ServiceException;

	//Status addDeliveryLines(Long supplierId) throws ServiceException, ParseException;

	List<LtCustomerSubs> getAllActiveSubsByUser(Long userId) throws ServiceException;

	//ResponseEntity<Status> saveSubscription(LtCustomerSubs ltCustomerSubs);
	
	void addSubscriptionDelivery();
	
	ResponseEntity<Status> savePrepaidSubscription(LtCustomerSubs ltCustomerSubs, LtMastUsers user);
	ResponseEntity<Status> savePDOSubscription(LtCustomerSubs ltCustomerSubs, LtMastUsers user, LtMastSuppliers suppliers);

	int addVacationsToCustomerDelivery() throws Exception; 
	
	void addPostpaidSubscriptions();
	void addVacationsInDeliveryTable();
}
