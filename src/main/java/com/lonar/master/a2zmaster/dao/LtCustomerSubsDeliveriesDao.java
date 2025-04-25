package com.lonar.master.a2zmaster.dao;

import java.util.Date;
import java.util.List;

//import com.lonar.a2zcommons.model.DataTableRequest;
//import com.lonar.a2zcommons.model.InvoiceLinesData;
//import com.lonar.a2zcommons.model.LtCustomerSubs;
//import com.lonar.a2zcommons.model.LtCustomerSubsDeliveries;
//import com.lonar.a2zcommons.model.LtInvoices;
//import com.lonar.a2zcommons.model.LtMastUsers;
//import com.lonar.a2zcommons.model.LtSupplierDeliveryTimings;
//import com.lonar.a2zcommons.model.LtVacationPeriod;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.DataTableRequest;
import com.lonar.master.a2zmaster.model.InvoiceLinesData;
import com.lonar.master.a2zmaster.model.LtCustomerSubs;
import com.lonar.master.a2zmaster.model.LtCustomerSubsDeliveries;
import com.lonar.master.a2zmaster.model.LtInvoices;
import com.lonar.master.a2zmaster.model.LtMastUsers;
import com.lonar.master.a2zmaster.model.LtSupplierDeliveryTimings;
import com.lonar.master.a2zmaster.model.LtVacationPeriod;

public interface LtCustomerSubsDeliveriesDao {

	void batchInsertForSubsDelivery(List<LtCustomerSubsDeliveries> subsDeliveryList) throws ServiceException;

	Integer deleteExistingSubsDeliveries(LtCustomerSubs ltCustomerSubs) throws ServiceException;

	Integer deleteExistingSubsDeliveries(LtCustomerSubs ltCustomerSubs, Date startDate) throws ServiceException;
	
	List<LtCustomerSubsDeliveries> getSubsDeliveryBySubsId(Long subsId) throws ServiceException;
	
	Long getSubsDeliveryCountBySubsId(Long subsId) throws ServiceException;
	
	LtCustomerSubsDeliveries getSubsDeliveryById(Long deliveryId ) throws ServiceException;

	LtCustomerSubsDeliveries getSubsDeliveryById(Long deliveryId, Long supplierId ) throws ServiceException;
	
	LtCustomerSubsDeliveries save(LtCustomerSubsDeliveries ltCustomerSubsDeliveries) throws ServiceException;

	List<LtCustomerSubsDeliveries> getSubsDeliveryByCustId(Long customerId) throws ServiceException;

	void updateInvoiceId(LtInvoices ltInvoices) throws ServiceException;
	public void updateSubscriptionDelivery(LtInvoices ltInvoices) throws ServiceException;
	
	void updatePrepaymentSubscriptionDelivery(LtInvoices ltInvoices) throws ServiceException;
	
	List<LtCustomerSubsDeliveries> getVacationId(LtCustomerSubsDeliveries customerSubsDeliveries) throws ServiceException;
	
	void updateInvoicePayment(Long  invoiceId, Long subsid ) throws ServiceException;

	Double calculateInvoiceAmount(Long subsId, Date from, Date to) throws ServiceException;

	List<LtCustomerSubsDeliveries> deliverySummary(LtCustomerSubs ltCustomerSubsDeliveries) throws ServiceException;
	
	List<LtCustomerSubsDeliveries> getDeliverySummaryForDeliveryAgent(LtCustomerSubs ltCustomerSubs)throws ServiceException;

	List<LtCustomerSubsDeliveries> customerDeliverySummary(LtCustomerSubsDeliveries ltCustomerSubsDeliveries, String version ) throws ServiceException;

	List<LtCustomerSubsDeliveries> deliverySummaryStatus(LtCustomerSubsDeliveries ltCustomerSubsDeliveries) throws ServiceException;

	List<InvoiceLinesData> getDeliveryRecordsByInvoiceId(Long invoiceId) throws ServiceException;

	List<InvoiceLinesData> getDeliveryRecordsBySubId(Long subId) throws ServiceException;
	
	List<LtCustomerSubsDeliveries> getDailySubs(LtCustomerSubsDeliveries ltCustomerSubsDeliveries) throws ServiceException;

	boolean updateDelivery(LtCustomerSubs ltCustomerSubs, Long vacationId) throws ServiceException;
	
	void updateDeliveryStatus(LtCustomerSubsDeliveries customerDeliveries)throws ServiceException;
	
	void updateDeliveryOnceStatus(LtCustomerSubsDeliveries customerSubsDeliveries, LtMastUsers user) throws ServiceException;
	
	boolean careatPausedDelivery(String status,  Long userId, Long vacationId, Date strDate, Date endDate )throws ServiceException;
	
	boolean setPrepaidPausedDelivery(String status,  Long userId, Long vacationId, Date strDate, Date endDate )throws ServiceException;
	
	boolean careateActiveDelivery(Long userId, Long vacationId) throws ServiceException;

	LtVacationPeriod saveVacation(LtVacationPeriod ltVacationPeriod) throws ServiceException;

	LtVacationPeriod getVacation(Long vacationId) throws ServiceException;

	LtVacationPeriod getVacationByUser(Long userId) throws ServiceException;

	List<LtCustomerSubsDeliveries> getNextDeliveries(LtSupplierDeliveryTimings supplierDeliveryTimings) throws ServiceException;

	List<LtCustomerSubsDeliveries> exportDeliveriesByDeliveryAgent(LtCustomerSubsDeliveries ltCustomerSubsDeliveries) throws ServiceException;

	Double calculateInvoiceAmountForSubs(Long subsId, Date fromDeliveryDate, Date toDeliveryDate) throws ServiceException;

	LtCustomerSubs getNextDeliveryDateTime(Long supplierId) throws ServiceException;

	void deleteVacation(LtVacationPeriod ltVacationPeriod) throws ServiceException;

	Long customerDeliverySummaryTotal(LtCustomerSubsDeliveries ltCustomerSubsDeliveries) throws ServiceException;
	
	Long getSubscriberCount(LtMastUsers user, String userType)throws ServiceException;
	
	Integer getCustomerCount(Long supplierId);
	
	List<LtMastUsers> getSubscribers(LtMastUsers user, String userType)throws ServiceException;
	
	List<LtVacationPeriod> getAllActiveVacationBySupplier() throws ServiceException;

	List<LtCustomerSubsDeliveries> getSubsDeliveriesBetDates(LtInvoices ltInvoice) throws ServiceException;
	
	List<LtCustomerSubsDeliveries> getPrepaymentSubsDeliveries() throws Exception;
	
	List<LtCustomerSubsDeliveries> getPrepaymentSubsDeliveries(Long supplierId, Long UserId) throws Exception;
	
    Long getActiveSubscriptionCount( Date fromDate, Date toDate, Long supplierId, Long userId) throws Exception;
    
    Long getSubsDeliveriesCountByDatesAndSupplierId(LtInvoices ltInvoice)throws Exception;
    
    List<LtCustomerSubsDeliveries> getCollectionLedger(Long supplierId, Long userId, Long productId, DataTableRequest request) throws Exception;
    Long getCollectionLedgerCount(Long supplierId, Long userId, Long productId) throws Exception;
    
    Long getCollectionLedgerBalance (Long supplierId, Long userId, Long productId, String isOwnContainer )throws Exception;

    boolean isDeliveryAvailable(Long supplierId,  Long subscriptionId, String status )throws Exception;
    
    void updateDeliveryOnceDetails(LtCustomerSubs customerSubs);
    
    Date getStartDate(Long supplierId, Long subscriptionId, Date startDate)throws Exception;

    void updateDeliveryLineAmount(Long subId, Long userId, Long suppplierId);

    Double customerDeliverySummaryTotal(Long subId, Long userId, Long suppplierId);
    
    void addInvoiceIdInDeliveryTable();
    
    List<LtCustomerSubsDeliveries> getDeliveryDatailsForMinusInvoice(Long subId, Long supplierId) throws Exception;
    List<LtInvoices> getMinusInvoice(Long subId, Long supplierId) throws Exception;
    List<LtInvoices> getInvoiceBySubId(Long subId, Long supplierId) throws Exception;
    void addInvoiceIdInDeliveryTable(LtInvoices invoice);
}
	

