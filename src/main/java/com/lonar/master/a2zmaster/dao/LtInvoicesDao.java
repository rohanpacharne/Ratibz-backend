package com.lonar.master.a2zmaster.dao;

import java.util.List;

//import com.lonar.a2zcommons.model.InvoicePdfData;
//import com.lonar.a2zcommons.model.LtCustomerSubsDeliveries;
//import com.lonar.a2zcommons.model.LtInvoices;
//import com.lonar.a2zcommons.model.LtMastUsers;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.dashboard.InvoicePayments;
import com.lonar.master.a2zmaster.model.InvoicePdfData;
import com.lonar.master.a2zmaster.model.LtCustomerSubsDeliveries;
import com.lonar.master.a2zmaster.model.LtInvoices;
import com.lonar.master.a2zmaster.model.LtMastUsers;

public interface LtInvoicesDao {
	LtInvoices createInvoice(LtInvoices ltInvoices) throws ServiceException;

	List<LtInvoices> getAllInvoices(Long userId) throws ServiceException;

	List<LtInvoices> getAllInvoicesBySupplier(Long supplierId, LtInvoices ltInvoices) throws ServiceException;
	List<LtInvoices> getAllPrepaidInvoicesBySupplier(Long supplierId, LtInvoices ltInvoices) throws ServiceException;
	
	
	LtMastUsers getUserMailById(Long userId) throws ServiceException;

	LtInvoices getInvoicesById(Long invoiceId) throws ServiceException;

	List<LtCustomerSubsDeliveries> getDeliveriesByInvoiceId(Long invoiceId) throws ServiceException;

	List<InvoicePdfData> getInvoicesData(Long supplierId) throws ServiceException;

	InvoicePdfData getInvoiceDataByInvoiceId(Long invoiceId) throws ServiceException;

	InvoicePdfData getPrepaidInvoiceDataByInvoiceId(Long invoiceId) throws ServiceException;
	
	Long getAllInvoicesBySupplierCount(Long supplierId, LtInvoices ltInvoices) throws ServiceException;
	
	Long getAllPrepaidInvoicesCountBySupplier(Long supplierId, LtInvoices ltInvoices) throws ServiceException;

	List<InvoicePayments> getInvoiceVsPayments(Long supplierId) throws ServiceException;

	Long getAllInvoicesByUserCount(Long supplierId, LtInvoices ltInvoices) throws ServiceException;
	List<LtInvoices> getAllInvoicesByUser(Long supplierId, LtInvoices ltInvoices) throws ServiceException;
	
	Long getAllPrepaidInvoicesByUserCount(Long supplierId, LtInvoices ltInvoices) throws ServiceException;
	List<LtInvoices> getAllPrepaidInvoicesByUser(Long supplierId, LtInvoices ltInvoices) throws ServiceException;
	
	List<LtInvoices> getAllPendingInvoicesBySupplier() throws ServiceException;

	void updateInvoice(LtInvoices ltInvoices) throws ServiceException;
	
	int updateDeliveryDateToCurrentDate()throws Exception;
	
	int updateVacatonStatusByDate() throws Exception;
	int updateCustomerSubscriptionStatusByDate() throws Exception;
	
	LtInvoices getInvoicesBySubId(Long subId, Long supplierId ) throws ServiceException;
	
	void updateInvoiceStatusAsPaid(Long userId, Long supplierId ) throws ServiceException;
}
