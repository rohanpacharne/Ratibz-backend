package com.lonar.master.a2zmaster.service;

import java.text.ParseException;
import java.util.List;

//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.InvoicePdfData;
//import com.lonar.a2zcommons.model.LtCustomerSubs;
//import com.lonar.a2zcommons.model.LtInvoices;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.InvoicePdfData;
import com.lonar.master.a2zmaster.model.LtCustomerSubs;
import com.lonar.master.a2zmaster.model.LtInvoices;
import com.lonar.master.a2zmaster.model.Status;

public interface LtInvoicesService {

	Status createInvoice(Long supplierId, String invoiceCycle, String logTime2) throws ServiceException, ParseException;

	List<LtInvoices> getAllInvoices(Long userId) throws ServiceException;

	CustomeDataTable getAllInvoicesBySupplier(Long supplierId, LtInvoices input) throws ServiceException;

	LtInvoices getInvoicesById(Long invoiceId) throws ServiceException;

	Status create(Long supplierId, String invoiceCycle, String logTime) throws ServiceException, ParseException;
	
	//Status createPostpaidInvoice(LtMastSuppliers supplier, String invoiceCycle, String logTime) throws ServiceException, ParseException;

	void createPostpaidInvoice(String invoiceCycle);
	
	List<InvoicePdfData> getInvoicesData(Long supplierId) throws ServiceException;

	Status createPdf(Long invoiceId) throws ServiceException;

	CustomeDataTable getAllInvoicesByUser(Long supplierId, LtInvoices input) throws ServiceException;

	Status createInvoice(LtInvoices ltInvoices);
	
	//void createPrepaidInvoice();
	
	LtInvoices createPrepaidDeliveryOnceInvoice(LtCustomerSubs customerSubs, String paymentStatus );

	LtInvoices createPrepaidInvoiceByDate(LtCustomerSubs customerSubs, String paymentStatus);

	Double calculatInvoiceAmount(LtCustomerSubs customerSubs);
}
