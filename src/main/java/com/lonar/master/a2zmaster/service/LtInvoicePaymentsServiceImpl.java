package com.lonar.master.a2zmaster.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//import com.lonar.a2zcommons.model.Ledger;
//import com.lonar.a2zcommons.model.LedgerRecords;
//import com.lonar.a2zcommons.model.LtCustomerSubs;
//import com.lonar.a2zcommons.model.LtInvoicePayments;
//import com.lonar.a2zcommons.model.LtInvoices;
//import com.lonar.a2zcommons.model.LtMastEmailToken;
//import com.lonar.a2zcommons.model.LtMastSuppliers;
//import com.lonar.a2zcommons.model.LtMastUsers;
//import com.lonar.a2zcommons.model.LtWalletBalance;
import com.lonar.master.a2zmaster.common.MailSendServiceCall;
import com.lonar.master.a2zmaster.common.NotificationServiceCall;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.dao.LtCustomerSubsDao;
import com.lonar.master.a2zmaster.dao.LtInvoicePaymentsDao;
import com.lonar.master.a2zmaster.dao.LtInvoicesDao;
import com.lonar.master.a2zmaster.dao.LtMastEmailTokenDao;
import com.lonar.master.a2zmaster.dao.LtMastSuppliersDao;
import com.lonar.master.a2zmaster.dao.LtMastUsersDao;
import com.lonar.master.a2zmaster.dao.LtProductsDao;
import com.lonar.master.a2zmaster.dao.LtWalletBalanceDao;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.Ledger;
import com.lonar.master.a2zmaster.model.LedgerRecords;
import com.lonar.master.a2zmaster.model.LtCustomerSubs;
import com.lonar.master.a2zmaster.model.LtInvoicePayments;
import com.lonar.master.a2zmaster.model.LtInvoices;
import com.lonar.master.a2zmaster.model.LtMastEmailToken;
import com.lonar.master.a2zmaster.model.LtMastSuppliers;
//import com.lonar.master.a2zmaster.model.LtMastEmailToken;
//import com.lonar.master.a2zmaster.model.LtMastSuppliers;
import com.lonar.master.a2zmaster.model.LtMastUsers;
import com.lonar.master.a2zmaster.model.LtWalletBalance;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.repository.LtInvoicePaymentsRepository;
import com.lonar.master.a2zmaster.utils.UtilsMaster;

@Service
public class LtInvoicePaymentsServiceImpl implements LtInvoicePaymentsService, CodeMaster {

	DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");

	@Autowired private LtMastUsersDao ltMastUsersDao;
	@Autowired private LtMastCommonMessageService ltMastCommonMessageService;
	@Autowired private LtInvoicePaymentsDao ltInvoicePaymentsDao;
	@Autowired private LtInvoicePaymentsRepository invoicePaymentsRepository;
	@Autowired private LtInvoicesDao ltInvoicesDao;
	@Autowired private LtMastSuppliersDao ltMastSuppliersDao;
	@Autowired private LtMastEmailTokenDao ltMastEmailTokenDao;
	@Autowired private NotificationServiceCall notificationServiceCall;
	@Autowired private LtProductsDao ltProductsDao;
	@Autowired private LtCustomerSubsDao customerSubsDao; 
	@Autowired private LtOrderHistoryService orderHistoryService;
	@Autowired private LtWalletBalanceDao walletBalanceDao;
	
	@Override
	public Status savePaymentWithFile(LtInvoicePayments ltInvoicePayments, MultipartFile multipartFile)
			throws ServiceException {
		Status status = new Status();

		status = checkPayAmountForInvoice(ltInvoicePayments);
		if (status != null) {
			return status;
		}
		
		LtInvoicePayments invoicePayments = null;
		
		if(ltInvoicePayments.getPaymentId() != null) {
			Optional<LtInvoicePayments>  paymentsOptional = invoicePaymentsRepository.findById(ltInvoicePayments.getPaymentId());
			//if(paymentsOptional.isPresent() ) {
				//LtInvoicePayments invoicePayments = paymentsOptional.get();
				invoicePayments = paymentsOptional.get();
				
				if(ltInvoicePayments.getStatus().equals("PAID")){
					invoicePayments.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
					invoicePayments.setLastUpdatedBy(ltInvoicePayments.getLastUpdateLogin());
					invoicePayments.setStatus(ltInvoicePayments.getStatus());
					invoicePayments.setPayPartnerName(ltInvoicePayments.getPayPartnerName());
					invoicePayments.setRemark(ltInvoicePayments.getRemark());
					invoicePayments.setFromWalletBalance(ltInvoicePayments.getFromWalletBalance());
					invoicePayments.setPayAmount(ltInvoicePayments.getPayAmount());				
					ltInvoicePayments = ltInvoicePaymentsDao.save(invoicePayments);
				}else {
					
					//ltInvoicePayments.setCreationDate(UtilsMaster.getCurrentDateTime());
					ltInvoicePayments.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
					//ltInvoicePayments.setCreatedBy(ltInvoicePayments.getLastUpdateLogin());
					ltInvoicePayments.setLastUpdatedBy(ltInvoicePayments.getLastUpdateLogin());
					ltInvoicePayments.setPaytmInoviceId(ltInvoicePayments.getPaytmInoviceId());
					// set Transction date
					ltInvoicePayments.setTransactionDate(UtilsMaster.getCurrentDateTime());
					ltInvoicePayments = ltInvoicePaymentsDao.save(ltInvoicePayments);
					
				}
			//}
		}else {		
		// ltInvoicePayments.setStatus("PENDING_ACK");
			ltInvoicePayments.setCreationDate(UtilsMaster.getCurrentDateTime());
			ltInvoicePayments.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
			ltInvoicePayments.setCreatedBy(ltInvoicePayments.getLastUpdateLogin());
			ltInvoicePayments.setLastUpdatedBy(ltInvoicePayments.getLastUpdateLogin());
			ltInvoicePayments.setPaytmInoviceId(ltInvoicePayments.getPaytmInoviceId());

			// set Transction date
			ltInvoicePayments.setTransactionDate(UtilsMaster.getCurrentDateTime());
			
			ltInvoicePayments = ltInvoicePaymentsDao.save(ltInvoicePayments);
		}
		if(ltInvoicePayments.getStatus().equals("PENDING_ACK") ) {
			orderHistoryService.saveInvoiceOrderHistory(ltInvoicePayments, "Paid", 0d ,"CUSTOMER");	
		}else {
			orderHistoryService.saveInvoiceOrderHistory(ltInvoicePayments, "Invoice Acknowledged", 0d ,"SUPPLIER");
		}
		
		if (ltInvoicePayments.getPaymentId() != null) {
			if (multipartFile != null) {
				if (saveImage(ltInvoicePayments, multipartFile)) {
					LtInvoices ltInvoices = ltInvoicesDao.getInvoicesById(ltInvoicePayments.getInvoiceId());
					if (ltInvoices != null) {
						ltInvoices.setStatus(ltInvoicePayments.getStatus());
						ltInvoices.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
						ltInvoicesDao.createInvoice(ltInvoices);
						ltInvoicePayments.setInvoiceNumber(ltInvoices.getInvoiceNumber());
					}
					status = ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
					status.setData(ltInvoicePayments.getPaymentId());
					saveNotification(ltInvoicePayments);
				} else {
					status = ltMastCommonMessageService.getCodeAndMessage(INSERT_FAIL);
					return status;
				}
				makeEntryInEmailTemplate("invoicePaid", ltInvoicePayments);
				return status;
			} else {
				LtInvoices ltInvoices = ltInvoicesDao.getInvoicesById(ltInvoicePayments.getInvoiceId());
				if (ltInvoices != null) {
					ltInvoices.setStatus(ltInvoicePayments.getStatus());
					ltInvoices.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
					ltInvoicesDao.createInvoice(ltInvoices);
					ltInvoicePayments.setInvoiceNumber(ltInvoices.getInvoiceNumber());
				}
			}
			status = ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
			status.setData(ltInvoicePayments.getPaymentId());
			saveNotification(ltInvoicePayments);
			makeEntryInEmailTemplate("invoicePaid", ltInvoicePayments);
			return status;
		} else {
			status = ltMastCommonMessageService.getCodeAndMessage(INSERT_FAIL);
			return status;
		}
	}

	private Status checkPayAmountForInvoice(LtInvoicePayments ltInvoicePayments) throws ServiceException {
		Status status = new Status();
		List<LtInvoicePayments> invoicePaymentsList = ltInvoicePaymentsDao
				.getAllLtInvoicePaymentsByInvoiceId(ltInvoicePayments.getInvoiceId());
 
		if (!invoicePaymentsList.isEmpty()) {
			if (invoicePaymentsList.size() > 1) {
//				status.setCode(INSERT_FAIL);
				status = ltMastCommonMessageService.getCodeAndMessage(INSERT_FAIL);
				status.setMessage("Full amount is paid for invoice ");
				return status;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	private void saveNotification(LtInvoicePayments ltInvoicePayments) throws ServiceException {
		LtMastUsers ltMastUsers = ltMastUsersDao.getUserById(ltInvoicePayments.getLastUpdateLogin());
		if (ltMastUsers.getUserType().equals("CUSTOMER")) {

			LtMastUsers ltMastUser = ltMastUsersDao.getUserById(ltInvoicePayments.getUserId());

			notificationServiceCall.saveCustomerNotification(ltMastUser,
					"Hi, You have made a payment of Rs. " + ltInvoicePayments.getPayAmount() + " for Invoice No "
							+ ltInvoicePayments.getInvoiceNumber() + ", Thank You!!!.");

			List<LtMastUsers> ltMastUserSupplier = ltMastUsersDao.getUserByType("SUPPLIER",
					ltInvoicePayments.getSupplierId());
			notificationServiceCall.saveSupplierNotification(ltMastUserSupplier.get(0),
					"Hi, " + ltMastUser.getUserName() + " has  made a payment of Rs. "
							+ ltInvoicePayments.getPayAmount() + " for Invoice No "
							+ ltInvoicePayments.getInvoiceNumber() + ". Please, acknowledge the payment");

		} else {
			LtMastUsers ltMastUser = ltMastUsersDao.getUserById(ltInvoicePayments.getUserId());
			notificationServiceCall.saveCustomerNotification(ltMastUser,
					"Hi, " + ltMastUser.getUserName() + "  your payment of Rs. " + ltInvoicePayments.getPayAmount()
							+ " has been received successfully, Thank You!!!.");
		}
	}

	private void makeEntryInEmailTemplate(String templateName, LtInvoicePayments ltInvoicePayments)
			throws ServiceException {
		LtMastUsers ltMastUsers = ltInvoicesDao.getUserMailById(ltInvoicePayments.getUserId());
		LtInvoices ltInvoices = ltInvoicesDao.getInvoicesById(ltInvoicePayments.getInvoiceId());
		LtMastSuppliers ltMastSupplier = ltMastSuppliersDao.getLtMastSuppliersById(ltInvoicePayments.getSupplierId());
		if (ltMastUsers != null) {
			LtMastEmailToken ltMastEmailToken = new LtMastEmailToken();
			ltMastEmailToken.setSendTo(ltMastSupplier.getPrimaryEmail());
			ltMastEmailToken.setEmailStatus("SENDING");
			ltMastEmailToken.setEmailTitle(ltInvoices.getInvoiceNumber() + " is pending for acknowledgement");
			ltMastEmailToken.setEmailTemplate(templateName);
			ltMastEmailToken.setExpiredWithin(1296000L);
			ltMastEmailToken.setSendDate(UtilsMaster.getCurrentDateTime());
			ltMastEmailToken.setFailureCount(0L);
			ltMastEmailToken.setTransactionId(1L);
			ltMastEmailToken.setSupplierId(ltInvoices.getSupplierId());

			ltMastEmailToken.setEmailObject("invoiceNumber=" + ltInvoices.getInvoiceNumber() + "#$#$amount="
					+ ltInvoices.getFinalAmount() + "#$#$customerName=" + ltMastUsers.getUserName());

			//Long emailtokenId = 
					ltMastEmailTokenDao.makeEntryInEmailToken(ltMastEmailToken);
			MailSendServiceCall mailSendServiceCall = new MailSendServiceCall();
			mailSendServiceCall.callToMailService(ltMastEmailToken);
		}

	}

	private boolean saveImage(LtInvoicePayments ltInvoicePayments, MultipartFile uploadfile) {
		String saveDirectory = null;
		String fileExtentions = ".jpeg,.jpg,.png,.bmp,.pdf";
		String extension;
		try {
			saveDirectory = ltProductsDao.getSystemValue(ltInvoicePayments.getSupplierId(), "FILE_UPLOAD_PATH");
			
			File dir = new File(
					saveDirectory + "Supplier_" + ltInvoicePayments.getSupplierId() + "/" + "Invoice_Images" + "/");
			if (!dir.isDirectory()) {
				if (dir.mkdirs()) {
					System.out.println("Directory created");
				} else {
					System.out.println("Error in directory creation");
				}
			}

			byte[] bytes = uploadfile.getBytes();
			String[] fileFrags = uploadfile.getOriginalFilename().split("\\.");
			extension = fileFrags[fileFrags.length - 1];
			if (!fileExtentions.toUpperCase().contains(extension.toUpperCase())) {
				return false;
			} else {
				dir = new File(new String(saveDirectory + "Supplier_" + ltInvoicePayments.getSupplierId() + "/"
						+ "Invoice_Images" + "/" + uploadfile.getOriginalFilename()).replaceAll("amp;", ""));
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(dir));
				stream.write(bytes);
				stream.close();
 
				ltInvoicePayments.setPaymentSlip("Supplier_" + ltInvoicePayments.getSupplierId() + "/"
						+ "Invoice_Images" + "/" + uploadfile.getOriginalFilename());
				LtInvoicePayments ltInvoicePayment = ltInvoicePaymentsDao.save(ltInvoicePayments);
				if (ltInvoicePayment.getPaymentSlip() != null) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	@Override
	public Status acknowledgePayment(Long paymentId) throws ServiceException {
		Status status = new Status();
		if (ltInvoicePaymentsDao.acknowledgePayment(paymentId)) {
			status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
			LtInvoicePayments ltInvoicePayments = ltInvoicePaymentsDao.getInvoicePaymentById(paymentId);
			LtMastUsers ltMastUser = ltMastUsersDao.getUserById(ltInvoicePayments.getUserId());
			notificationServiceCall.saveCustomerNotification(ltMastUser,
					"Hi, " + ltMastUser.getUserName() + "  your payment of Rs. " + ltInvoicePayments.getPayAmount()
							+ " has been received successfully, Thank You!!!.");
		} else {
			status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
		}
		return status;
	}

	@Override
	public Ledger ledgerByUserId(Long userId) throws ServiceException {
		Ledger ledger = new Ledger();
		ledger.setInvoiceTotal(0D);
		ledger.setPaymentTotal(0D);
		List<LedgerRecords> ledgerRecords = null;
		String isPrepaid = ltMastSuppliersDao.getIsPrepaidSupplierFlagByUserId(userId);

		if(isPrepaid != null && (isPrepaid.equalsIgnoreCase("Y") || isPrepaid.equalsIgnoreCase("PDO"))) {
			 ledgerRecords = ltInvoicePaymentsDao.getPrepaidLedgerByUserId(userId);
		}else {
			ledgerRecords = ltInvoicePaymentsDao.ledgerByUserId(userId);
		}
		if (ledgerRecords != null) {
			for (LedgerRecords ledgerRecord : ledgerRecords) {
				if (ledgerRecord.getInvoiceAmount() != null) {
					ledger.setInvoiceTotal(ledger.getInvoiceTotal() + ledgerRecord.getInvoiceAmount());
				}
				if (ledgerRecord.getPaymentAmount() != null) {
					ledger.setPaymentTotal(ledger.getPaymentTotal() + ledgerRecord.getPaymentAmount());
				}
			}
			ledger.setOutstanding(ledger.getInvoiceTotal() - ledger.getPaymentTotal());
			ledger.setLedgerRecordsList(ledgerRecords);
		}
		return ledger;
	}

	@Override
	public LtInvoicePayments getInvoicePaymentById(Long invoicePaymentId) throws ServiceException {
		return ltInvoicePaymentsDao.getInvoicePaymentById(invoicePaymentId);
	}

	@Override
	public LtInvoicePayments getInvPaymentByInvoiceId(Long invoiceId) throws ServiceException {
		String fileOpenPath = null;
		LtInvoicePayments ltInvoicePayments = ltInvoicePaymentsDao.getInvPaymentByInvoiceId(invoiceId);
			if (ltInvoicePayments != null) {
				LtInvoices ltInvoices = ltInvoicesDao.getInvoicesById(ltInvoicePayments.getInvoiceId());
				if (ltInvoices != null) {
					ltInvoicePayments.setFromDeliveryDate(ltInvoices.getFromDeliveryDate());
					ltInvoicePayments.setToDeliveryDate(ltInvoices.getToDeliveryDate());
					ltInvoicePayments.setInvoiceDate(ltInvoices.getInvoiceDate());
					ltInvoicePayments.setFinalAmount(ltInvoices.getFinalAmount());
					ltInvoicePayments.setInvoiceNumber(ltInvoices.getInvoiceNumber());
				}
				if(ltInvoicePayments.getPayAmount() == null ) {
					ltInvoicePayments.setPayAmount(0d);
				}
				
				if (ltInvoicePayments.getPaymentSlip() != null) {
					fileOpenPath = ltProductsDao.getSystemValue(ltInvoicePayments.getSupplierId(), "FILE_OPEN_PATH");
					ltInvoicePayments.setImagePath(fileOpenPath + ltInvoicePayments.getPaymentSlip());
				}
			}

		return ltInvoicePayments;
	}

	@Override
	public Status paymentReminder() throws ServiceException {
			List<LtInvoices> ltInvoicesList = ltInvoicesDao.getAllPendingInvoicesBySupplier();
			if (ltInvoicesList != null) {
				ltInvoicesList.forEach((invoices) -> {
					try {
						notificationServiceCall.saveCustomerNotification(invoices.getUserId() ,invoices.getSupplierId() ,
								"Hi, your bill of amount " + invoices.getFinalAmount() + " generated on "
										+ dateFormat.format(invoices.getInvoiceDate())
										+ " is pending for payment. Invoice number- " + invoices.getInvoiceNumber());
					} catch (ServiceException e) {
						e.printStackTrace();
					}
				});
			}
		return null;
	}
	
	@Override
	public Status endSubscriptionNotificationSend() throws ServiceException {
		List<LtCustomerSubs> customerSubs = customerSubsDao.getTodaysEndSubscriptions();
		if(customerSubs != null && !customerSubs.isEmpty()) {
			customerSubs.forEach(subscription -> {
				try {
					notificationServiceCall.saveCustomerNotification(subscription.getUserId() ,subscription.getSupplierId() ,
							"Your subscription of product "+subscription.getProductName()+" is about to end on "+
									dateFormat.format(subscription.getEndDate()) +", Please add new subscription.");
				} catch (ServiceException e) {
					e.printStackTrace();
				}
			});
		}
		return null;
	}
	

	@Override
	public ResponseEntity<Status> getBalanceAmount(Long userId, Long supplierId) {
		Status status = new Status();
		try { 
			String isPrepaid = ltMastSuppliersDao.getPrepaidSupplierFlag(supplierId);
			
			if(isPrepaid != null && isPrepaid.trim().equalsIgnoreCase("Y") ) {
				status.setData(ltInvoicePaymentsDao.getPrepaidBalanceAmount(userId, supplierId ));
				return new ResponseEntity<Status>(status, HttpStatus.OK);
			}else if(isPrepaid != null && isPrepaid.trim().equalsIgnoreCase("PDO") ){
				status.setData(ltInvoicePaymentsDao.getPDOBalanceAmount(userId, supplierId ));
				return new ResponseEntity<Status>(status, HttpStatus.OK);
			} else {
				status.setData(ltInvoicePaymentsDao.getPostpaidBalanceAmount(userId, supplierId ));
				return new ResponseEntity<Status>(status, HttpStatus.OK);
			}
		}catch(Exception e) {
			e.printStackTrace();
			status.setMessage(e.getMessage());
		}			
		return new ResponseEntity<Status>(status, HttpStatus.EXPECTATION_FAILED );
	}

	@Override
	public ResponseEntity<Status> getWalletBalance(Long userId, Long supplierId) {
		Status status = new Status();
		try { 
			Double balance = 0d;
			String isPrepaid = ltMastSuppliersDao.getPrepaidSupplierFlag(supplierId);
			
			if(isPrepaid != null && isPrepaid.trim().equalsIgnoreCase("Y") ) {
				balance = ltInvoicePaymentsDao.getPrepaidWalletBalance(userId, supplierId, "Y" );
				if(balance == null) {
					balance  = 0d;
				}
				status.setData(balance);
				return new ResponseEntity<Status>(status, HttpStatus.OK);
			}else if(isPrepaid != null && isPrepaid.trim().equalsIgnoreCase("PDO") ){
				balance = ltInvoicePaymentsDao.getPrepaidWalletBalance(userId, supplierId, "PDO");
				if(balance == null) {
					balance  = 0d;
				}
				status.setData(balance);
				return new ResponseEntity<Status>(status, HttpStatus.OK);
			} else {
				balance = walletBalanceDao.getWalletbalance(userId, supplierId);
				if(balance == null) {
					balance  = 0d;
				}
				status.setData(balance);
				return new ResponseEntity<Status>(status, HttpStatus.OK);
			}
		}catch(Exception e) {
			e.printStackTrace();
			status.setMessage(e.getMessage());
		}			
		return new ResponseEntity<Status>(status, HttpStatus.EXPECTATION_FAILED );
	}
	
	@Override
	public int updateDeliveryDateToCurrentDate() throws Exception{
		return	ltInvoicesDao.updateDeliveryDateToCurrentDate();
	}
	
	@Override
	public int updateVacatonStatusByDate() throws Exception {
		return	ltInvoicesDao.updateVacatonStatusByDate();
	}
	
	@Override
	public int updateCustomerSubscriptionStatusByDate() throws Exception {
		return ltInvoicesDao.updateCustomerSubscriptionStatusByDate();
	}

	@Override
	public Status saveRefundPayment(LtWalletBalance walletBalance) throws Exception {
		walletBalance.setDescription("REFUND");
		walletBalanceDao.saveRefundPayment(walletBalance);
		List<LtMastUsers> users = ltInvoicePaymentsDao.getUsersforRefundNotification(walletBalance.getUserId(), walletBalance.getSupplierId());
		//List<LtInvoices> invoices = ltInvoicePaymentsDao.getInvoiceForPaid(payment);
		users.forEach(user -> {
			try {
				orderHistoryService.saveOrderHistoryWalletBalanceAdded(walletBalance.getRemark() , walletBalance.getAmount()
						, walletBalance.getCreatedBy(), walletBalance.getLastUpdatedLogin(), user.getUserId() ,user.getSupplierId());
				
				notificationServiceCall.saveCustomerNotification(user.getUserId() ,user.getSupplierId() ,
						"Hi "+ user.getUserName()+" Rs. "+walletBalance.getAmount()+" added in your wallet balance.");
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		});
		return ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
	}
	
	/*@Override
	public Status saveRefundPayment(LtInvoicePayments payment) throws Exception {
		ltInvoicePaymentsDao.saveRefundPayment(payment);
		List<LtMastUsers> users = ltInvoicePaymentsDao.getUsersforRefundNotification(payment);
		List<LtInvoices> invoices = ltInvoicePaymentsDao.getInvoiceForPaid(payment);
		users.forEach(user -> {
			try {
				
				if(!invoices.isEmpty()) {
					List<LtInvoices> invoiceList = invoices.stream().filter(invoice -> invoice.getUserId().equals(user.getUserId()))
							  .sorted(Comparator.comparingDouble(inv -> inv.getInvoiceAmount()))
							  .collect(Collectors.toList());
					Double invAmt = 0d; 
					for(LtInvoices inv : invoiceList) {
						invAmt = invAmt + inv.getInvoiceAmount();
						if(invAmt <= payment.getPayAmount()) {
							try {
							ltInvoicePaymentsDao.payPaymentFromWalletBalance(inv.getInvoiceId());
							}catch(Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
				orderHistoryService.saveOrderHistoryWalletBalanceAdded(payment.getRemark() , payment.getPayAmount()
						, payment.getCreatedBy(), payment.getLastUpdateLogin(), user.getUserId() ,user.getSupplierId());
				
				notificationServiceCall.saveCustomerNotification(user.getUserId() ,user.getSupplierId() ,
						"Hi "+ user.getUserName()+" Rs. "+payment.getPayAmount()+" added in your wallet balance.");
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		});
		return ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
	}*/
	
	/*	@Override
	public ResponseEntity<Status> getPrepaidBalanceAmount(Long userId, Long supplierId) {
		Status status = new Status();
		try { 
			status.setData(ltInvoicePaymentsDao.getPrepaidBalanceAmount(userId, supplierId ));
			return new ResponseEntity<Status>(status, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			status.setMessage(e.getMessage());
		}			
		return new ResponseEntity<Status>(status, HttpStatus.EXPECTATION_FAILED );
	}
*/
}
