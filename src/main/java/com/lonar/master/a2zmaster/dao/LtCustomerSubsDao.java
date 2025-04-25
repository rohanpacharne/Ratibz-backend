package com.lonar.master.a2zmaster.dao;

import java.util.Date;
import java.util.List;

//import com.lonar.a2zcommons.model.LtCustomerSubs;
//import com.lonar.a2zcommons.model.LtMastUsers;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtCustomerSubs;
import com.lonar.master.a2zmaster.model.LtMastUsers;

public interface LtCustomerSubsDao {

	LtCustomerSubs save(LtCustomerSubs ltCustomerSubs) throws Exception;

	LtCustomerSubs getCustomerSubsById(Long subsId) throws ServiceException;

	String getSubscriptionStatus(Long subsId, Long supplierId) throws ServiceException;
	
	List<LtCustomerSubs> getAllActiveSubs(Long userId, String subType) throws ServiceException;
	
	List<LtCustomerSubs> getAllActiveSubs_v1(Long userId, String subType) throws ServiceException;
	
	List<LtCustomerSubs> getSubscriptionProduct(Long userId, Long supplierId)  throws ServiceException;

	LtCustomerSubs delete(Long subsId) throws ServiceException;

	List<LtCustomerSubs> getAllSubsBySupplier(Long supplierId, String invoiceCycle) throws ServiceException;

	List<LtCustomerSubs> getAllSubs(Long supplierId) throws ServiceException;
	
	List<LtCustomerSubs> getAllSubsByUserId(Long userId, String status) throws ServiceException;

	List<LtMastUsers> getAllActiveUsersBySupplier(Long supplierId, String invoiceCycle) throws ServiceException;
	
	List<LtMastUsers> getPostpaidUsersForCreateInvoice(String invoiceCycle) throws ServiceException;

	List<LtCustomerSubs> getAllActiveSubsBySupplier(Long supplierId) throws ServiceException;

	List<LtCustomerSubs> getAllActiveSubsBySubsDelDate(Long userId, String date) throws ServiceException;

	List<LtCustomerSubs> getAllActiveSubsForInvoice(Long userId)  throws ServiceException;

	List<LtCustomerSubs> getAllActiveSubsByUser(Long userId)  throws ServiceException;

	LtCustomerSubs update(LtCustomerSubs ltCustomerSubs) throws ServiceException;
	
	List<LtCustomerSubs> getMonthlyWeeklyCustomerSubs(Long supplierId)throws ServiceException;
	
	Long getAllActiveSubsCountBySupplier(Long supplierId) throws Exception;
	Long getAllActiveUsersCountBySupplier(Long supplierId) throws Exception;
	Long getMonthlyWeeklyCustomerSubsCount(Long supplierId)throws Exception;
	
	List<LtCustomerSubs> getSubscriptionsListByDate(Date date, String strDate, String weekDay,  Long userId , Long supplierId, Long invoiceId)throws Exception;
	
	List<LtCustomerSubs> getNewAlternateDaySubsctionList(Date date) throws Exception;
	
	List<LtCustomerSubs> getAlternateDaySubsctionList(Date date) throws Exception;
	
	int addVacationsToCustomerDelivery() throws Exception;
	
	Long getVacationId(Long userId, Long supplierId) throws Exception;
	
	void updateDeliveryQuantity(LtCustomerSubs customerSubs) throws Exception;
	
	List<LtCustomerSubs> getTodaysEndSubscriptions();
	
	List<LtCustomerSubs> getActiveOrdersReport(Long supplierId)throws ServiceException;
	
	List<LtMastUsers> getUsersForAddnNewDelivery() throws Exception;
	
	LtCustomerSubs getCustomerSubscriptionByInvoiceId(Long invoiceId)throws Exception;
	
	String getProductNameBySubId(Long subId)throws Exception;

	List<LtCustomerSubs> getSubscriptionsByDate(Date date, String day );
	List<Long> getAddedSubIds(Date date);
	
	List<Long> getDeliveryIdForVacations();
	void addVacation(Long deliveryId);
}
