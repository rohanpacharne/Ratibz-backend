package com.lonar.master.a2zmaster.dao;

import java.util.List;

//import com.lonar.a2zcommons.model.LedgerRecords;
//import com.lonar.a2zcommons.model.LtInvoicePayments;
//import com.lonar.a2zcommons.model.LtInvoices;
//import com.lonar.a2zcommons.model.LtMastUsers;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LedgerRecords;
import com.lonar.master.a2zmaster.model.LtInvoicePayments;
import com.lonar.master.a2zmaster.model.LtInvoices;
import com.lonar.master.a2zmaster.model.LtMastUsers;

public interface LtInvoicePaymentsDao {

	LtInvoicePayments save(LtInvoicePayments ltInvoicePayments) throws ServiceException;

	boolean acknowledgePayment(Long paymentId)  throws ServiceException;

	List<LedgerRecords> ledgerByUserId(Long userId) throws ServiceException;
	
	List<LedgerRecords> getPrepaidLedgerByUserId(Long userId) throws ServiceException;

	LtInvoicePayments getInvoicePaymentById(Long invoicePaymentId) throws ServiceException;

	LtInvoicePayments getInvPaymentByInvoiceId(Long invoiceId) throws ServiceException;

	List<LtInvoicePayments> getAllLtInvoicePaymentsByInvoiceId(Long invoiceId) throws ServiceException;
	
	//Double getBalanceAmount(Long userId, Long supplierId) throws Exception;
	
	Double getPrepaidBalanceAmount(Long userId,Long supplierId) throws Exception;
	
	Double getPrepaidWalletBalance(Long userId,Long supplierId, String userType) throws Exception;
	
	Double getPostpaidBalanceAmount(Long userId,Long supplierId) throws Exception;

	Double getPDOBalanceAmount(Long userId,Long supplierId) throws Exception;
	
	void saveRefundPayment(LtInvoicePayments payment) throws Exception;
	
	List<LtMastUsers> getUsersforRefundNotification(Long userId, Long supplierId)throws Exception;
	
	void payPaymentFromWalletBalance(Long invoiceId) throws Exception;
	
	List<LtInvoices> getInvoiceForPaid(LtInvoicePayments invoicePayments) throws Exception;
	
	
}
