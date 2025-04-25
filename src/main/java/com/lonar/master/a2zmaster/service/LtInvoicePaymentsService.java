package com.lonar.master.a2zmaster.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

//import com.lonar.a2zcommons.model.Ledger;
//import com.lonar.a2zcommons.model.LtInvoicePayments;
//import com.lonar.a2zcommons.model.LtWalletBalance;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.Ledger;
import com.lonar.master.a2zmaster.model.LtInvoicePayments;
import com.lonar.master.a2zmaster.model.LtWalletBalance;
import com.lonar.master.a2zmaster.model.Status;

public interface LtInvoicePaymentsService {

	Status savePaymentWithFile(LtInvoicePayments ltInvoicePayments, MultipartFile multipartFile) throws ServiceException;

	Status acknowledgePayment(Long paymentId) throws ServiceException;

	Ledger ledgerByUserId(Long userId) throws ServiceException;

	LtInvoicePayments getInvoicePaymentById(Long invoicePaymentId) throws ServiceException;

	LtInvoicePayments getInvPaymentByInvoiceId(Long invoiceId) throws ServiceException;

	Status paymentReminder() throws ServiceException;
	
	Status endSubscriptionNotificationSend() throws ServiceException;

	ResponseEntity<Status>  getBalanceAmount(Long userId,Long supplierId);
	
	ResponseEntity<Status>  getWalletBalance(Long userId,Long supplierId);
	
	
	//ResponseEntity<Status>  getPrepaidBalanceAmount(Long userId,Long supplierId);
	
	int updateDeliveryDateToCurrentDate()throws Exception;
	
	int updateVacatonStatusByDate() throws Exception;
	
	int updateCustomerSubscriptionStatusByDate() throws Exception;
	
	Status saveRefundPayment(LtWalletBalance payment) throws Exception;
	
}
