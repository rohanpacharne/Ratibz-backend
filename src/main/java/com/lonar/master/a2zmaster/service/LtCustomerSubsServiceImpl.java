package com.lonar.master.a2zmaster.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.LtCustomerContCollections;
//import com.lonar.a2zcommons.model.LtCustomerSubs;
//import com.lonar.a2zcommons.model.LtCustomerSubsDeliveries;
//import com.lonar.a2zcommons.model.LtInvoicePayments;
//import com.lonar.a2zcommons.model.LtInvoices;
//import com.lonar.a2zcommons.model.LtMastSuppliers;
//import com.lonar.a2zcommons.model.LtMastUsers;
//import com.lonar.a2zcommons.model.LtProducts;
//import com.lonar.a2zcommons.model.LtSupplierDeliveryTimings;
//import com.lonar.a2zcommons.model.LtVacationPeriod;
import com.lonar.master.a2zmaster.common.CalculateAlternateDates;
import com.lonar.master.a2zmaster.common.CalculateDate;
import com.lonar.master.a2zmaster.common.NotificationServiceCall;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.dao.LtCustomerContCollectionsDao;
import com.lonar.master.a2zmaster.dao.LtCustomerSubsDao;
import com.lonar.master.a2zmaster.dao.LtCustomerSubsDeliveriesDao;
import com.lonar.master.a2zmaster.dao.LtInvoicePaymentsDao;
import com.lonar.master.a2zmaster.dao.LtInvoicesDao;
import com.lonar.master.a2zmaster.dao.LtMastSuppliersDao;
import com.lonar.master.a2zmaster.dao.LtMastUsersDao;
import com.lonar.master.a2zmaster.dao.LtProductsDao;
import com.lonar.master.a2zmaster.dao.LtSupplierDeliveryTimingsDao;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtCustomerContCollections;
import com.lonar.master.a2zmaster.model.LtCustomerSubs;
import com.lonar.master.a2zmaster.model.LtCustomerSubsDeliveries;
import com.lonar.master.a2zmaster.model.LtInvoicePayments;
import com.lonar.master.a2zmaster.model.LtInvoices;
import com.lonar.master.a2zmaster.model.LtMastSuppliers;
import com.lonar.master.a2zmaster.model.LtMastUsers;
import com.lonar.master.a2zmaster.model.LtProducts;
import com.lonar.master.a2zmaster.model.LtSupplierDeliveryTimings;
import com.lonar.master.a2zmaster.model.LtVacationPeriod;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.repository.LtCustomerSubsDeliveriesRepository;
import com.lonar.master.a2zmaster.repository.LtProductsRepository;
import com.lonar.master.a2zmaster.utils.UtilsMaster;

@Service
public class LtCustomerSubsServiceImpl implements LtCustomerSubsService, CodeMaster {

	DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");

	@Autowired private LtMastUsersDao ltMastUsersDao;
	@Autowired private LtCustomerSubsDao ltCustomerSubsDao;
	@Autowired private LtMastCommonMessageService ltMastCommonMessageService;
	@Autowired private LtCustomerSubsDeliveriesDao ltCustomerSubsDeliveriesDao;
	@Autowired private LtProductsDao ltProductsDao;
	@Autowired private NotificationServiceCall notificationServiceCall;
	@Autowired private LtCustomerContCollectionsDao ltCustomerContCollectionsDao;
	@Autowired private LtInvoicePaymentsDao invoicePaymentsDao;
	@Autowired private LtSupplierDeliveryTimingsDao ltSupplierDeliveryTimingsDao;
	@Autowired private LtCustomerSubsDeliveriesRepository ltCustomerSubsDeliveriesRepository;
	@Autowired private LtInvoicesService ltInvoicesService;
	@Autowired private LtInvoicesDao invoicesDao;
	@Autowired private LtOrderHistoryService orderHistoryService;
	@Autowired private LtVacationService vacationService;
	@Autowired private LtInvoicePaymentsDao ltInvoicePaymentsDao;
	@Autowired private  LtMastSuppliersDao mastSuppliersDao;
	@Autowired private  LtProductsRepository productsRepository;
	//@Autowired private  LtCustomerSubsRepository customerSubsRepository;
//	@Autowired LtMastCommonMessageService ltMastCommonMessageService;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public Status  timeLimitValidation( LtMastUsers user, LtCustomerSubs customerSubs ) {	
		Status status = null;
		try {		
			if (user.getUserType().equals("CUSTOMER")) {	
				System.out.println("in timeLimitValidation");
				  LtSupplierDeliveryTimings  deliveryTimings =  ltSupplierDeliveryTimingsDao.getDeliveryTiming(customerSubs.getSupplierId()
						                          , customerSubs.getDeliveryTime());
				  status =  this.validateTime(deliveryTimings, customerSubs.getStartDate(),customerSubs.getSubscriptionType());
				  System.out.println("in time limi validation = "+status);
				  return status;
			}
			return status;
		}catch(Exception e) {
			e.printStackTrace();
			return ltMastCommonMessageService.getCodeAndMessage(EXCEPTION);
		}
		
	}
	
	private Status  timeLimitValidation(LtCustomerSubsDeliveries subsDeliveries ) {
		Status status = null;
		try {
		    LtSupplierDeliveryTimings deliveryTimings =  ltSupplierDeliveryTimingsDao.getDeliveryTiming(subsDeliveries.getSupplierId(), subsDeliveries.getSubsId());
			return this.validateTime(deliveryTimings, subsDeliveries.getDeliveryDate(), deliveryTimings.getSubscriptionType());
		}catch(Exception e) {
			e.printStackTrace();		
		}
		return status;
	}

	private Status validateTime(LtSupplierDeliveryTimings  deliveryTimings, Date startDate, String  subscriptionType) {
		Status status = new Status();
		try {		
			if(deliveryTimings != null ) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");			   
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				
				Calendar calender = Calendar.getInstance();
				calender.setTime(UtilsMaster.addDays( UtilsMaster.getCurrentDate(), 1) );
				
				Calendar cancelDateFrom = Calendar.getInstance();
				cancelDateFrom.setTime(startDate);
				
				String  strStartDate  = null;
				
				if(subscriptionType != null && subscriptionType.equalsIgnoreCase("MONTHLY")) {
					if(calender.before(cancelDateFrom)) {
						strStartDate =  format.format(startDate);	
					}else {
						strStartDate =  format.format(UtilsMaster.addDays( UtilsMaster.getCurrentDate(), 1));
					}
				}else {
					strStartDate =  format.format(startDate);
				}
				
				Date deliveryDate = dateFormat.parse(strStartDate+" "+deliveryTimings.getFromTime());
				Long diffInMiliSecond = deliveryDate.getTime() - UtilsMaster.getCurrentDateTime().getTime();
				Long timeDiffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMiliSecond);
				
				Long  timeLimit  = 0L;
				if(deliveryTimings.getTimeLimit() != null ) {
					timeLimit = Long.parseLong(deliveryTimings.getTimeLimit());
				}
				System.out.println("above if");
				if( timeDiffInMinutes < timeLimit ) {
					System.out.println("in if...");
//					status = new Status();
//					status.setCode(UPDATE_FAIL);
					status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
					status.setMessage("Last order time for the selected Delivery Time Slot was "+ deliveryTimings.getDeliveryTime() +". Please contact to your supplier.");
//					return status;
				}
			}else {
				status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
				
			}
			return status;
		}catch(Exception e) {
			e.printStackTrace();
			status = ltMastCommonMessageService.getCodeAndMessage(EXCEPTION);
			return status;
		}
//		status.setCode(1);
//		status.setMessage("SUCCESS");
//		System.out.println("status = "+status);
	}
	
//	@Override
//	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
//	public Status save(LtCustomerSubs customerSubs, LtMastUsers user) throws ServiceException, ParseException {
//		
//		Long lastLoginId = ltMastUsersDao.getLastLoginId(user.getUserId());
//		
//		Status status = null; 		
//		customerSubs.setCreatedBy(user.getUserId());
//		customerSubs.setLastUpdateLogin(lastLoginId);
//		customerSubs.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
//		customerSubs.setCreationDate(UtilsMaster.getCurrentDateTime());
//		customerSubs.setLastUpdatedBy(user.getUserId());
//		
//		status = vacationService.checkStartDateValidation(customerSubs);
//		
//		if (status != null) {
//			return status;
//		}
//		
//		status =  timeLimitValidation(user, customerSubs);
//		if (status != null && status.getCode() != 200 ) {
//			return status;
//		}
// 
//		//LtProducts ltProducts = ltProductsDao.getLtProductById(customerSubs.getProductId());
//		Optional<LtProducts> productOp  = productsRepository.findById(customerSubs.getProductId());
//		LtProducts ltProducts  = productOp.get();		
//		customerSubs.setCustomerRate(ltProducts.getProductRate());
//		
//		try {
//			//if(customerSubs.getSubsId() == null ) {
//				orderHistoryService.saveSubscriptionOrderHistory(customerSubs, null,ltProducts, null);
//			//}
//			customerSubs = ltCustomerSubsDao.save(customerSubs);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		if (customerSubs.getSubsId() != null) {
//			if (user.getUserType().equals("CUSTOMER")) {				
//				if (customerSubs.getSubscriptionType().equals(MONTHLY)) {
//					notificationServiceCall.saveCustomerNotification(user,
//							"Hi, " + user.getUserName() + " you have subscribed to " + ltProducts.getProductName()
//									+ " for " + customerSubs.getSubsQuantity() + " " + ltProducts.getProductUom()
//									+ "  from date " + dateFormat.format(this.getNotificationStartDate(customerSubs)));
//
//					notificationServiceCall.saveSupplierNotification(customerSubs.getSupplierId(),
//							"Hi, " + user.getUserName() + " has subscribed to " + ltProducts.getProductName()
//									+ " for " + customerSubs.getSubsQuantity() + " " + ltProducts.getProductUom()
//									+ "  from date " + dateFormat.format(this.getNotificationStartDate(customerSubs))+ ". Please Approve");
//				} else {
//					notificationServiceCall.saveCustomerNotification(user,
//							"Hi, " + user.getUserName() + " you have subscribed to " + ltProducts.getProductName()
//									+ " for " + customerSubs.getSubsQuantity() + " " + ltProducts.getProductUom()
//									+ "  for date " + dateFormat.format(  this.getNotificationStartDate(customerSubs)));
//					notificationServiceCall.saveSupplierNotification(customerSubs.getSupplierId(),
//							"Hi, " + user.getUserName() + " has subscribed to " + ltProducts.getProductName()
//									+ " for " + customerSubs.getSubsQuantity() + " " + ltProducts.getProductUom()
//									+ "  for date " + dateFormat.format(this.getNotificationStartDate(customerSubs))+ ". Please Approve");
//				}				
//			}
//			/* else if (userType.equals("SUPPLIER")) {
//				LtMastUsers ltMastUser = ltMastUsersDao.getUserById(customerSubs.getUserId());				
//				notificationServiceCall.saveCustomerNotification(ltMastUser,
//						"Hi, " + ltMastUser.getUserName() + " you have subscribed to " + ltProducts.getProductName()
//								+ " for " + customerSubs.getSubsQuantity() + " " + ltProducts.getProductUom()
//								+ "  from date " + dateFormat.format(customerSubs.getStartDate()));
//			}*/			
//			status = ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
//			status.setMessage("Order Placed!  Supplier's approval pending.");
//			status.setData(customerSubs.getSubsId());
//		} else {
//			status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
//		}
//		return status;
//	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public Status save(LtCustomerSubs customerSubs, LtMastUsers user) throws ServiceException, ParseException {
	    Status status = null;        

		try {
	    
	    System.out.println("Fetching last login ID for user: " + user.getUserId());
	    Long lastLoginId = ltMastUsersDao.getLastLoginId(user.getUserId());
	    
	    System.out.println("Setting customer subscription details.");
	    customerSubs.setCreatedBy(user.getUserId());
	    customerSubs.setLastUpdateLogin(lastLoginId);
	    customerSubs.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
	    customerSubs.setCreationDate(UtilsMaster.getCurrentDateTime());
	    customerSubs.setLastUpdatedBy(user.getUserId());
	    
	    System.out.println("Validating start date for the subscription.");
	    status = vacationService.checkStartDateValidation(customerSubs);
	    
	    if (status != null) {
	        System.out.println("Start date validation failed. Returning status: " + status);
	        return status;
	    }
	    
	    System.out.println("Validating time limits.");
	    status = timeLimitValidation(user, customerSubs);
	    if (status != null && status.getCode() != 200) {
	        System.out.println("Time limit validation failed. Returning status: " + status);
	        return status;
	    }

	    System.out.println("Fetching product details for productId: " + customerSubs.getProductId());
	    Optional<LtProducts> productOp  = productsRepository.findById(customerSubs.getProductId());
	    LtProducts ltProducts  = productOp.get();        
	    customerSubs.setCustomerRate(ltProducts.getProductRate());
	    
	    try {
	        System.out.println("Saving subscription order history.");
	        orderHistoryService.saveSubscriptionOrderHistory(customerSubs, null, ltProducts, null);
	        System.out.println("Saving customer subscription.");
	        customerSubs = ltCustomerSubsDao.save(customerSubs);
	    } catch (Exception e) {
	        System.out.println("Error while saving customer subscription: " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	    if (customerSubs.getSubsId() != null) {
	        System.out.println("Subscription saved successfully. Returning success status.");
	        status = ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
	        status.setMessage("Order Placed!  Supplier's approval pending.");
	        status.setData(customerSubs.getSubsId());
	    } else {
	        System.out.println("Failed to save subscription. Returning failure status.");
	        status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
	    }
	    return status;
		}catch(Exception ex) {
			ex.printStackTrace();
			status = ltMastCommonMessageService.getCodeAndMessage(EXCEPTION);
			return status;
		}
	}

	
//	
//	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
//	public ResponseEntity<Status> savePDOSubscription(LtCustomerSubs ltCustomerSubs, LtMastUsers loginUser, LtMastSuppliers suppliers) {
//		Status status = null; 
//		try {					
//				ltCustomerSubs.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
//				ltCustomerSubs.setCreationDate(UtilsMaster.getCurrentDateTime());
//				ltCustomerSubs.setLastUpdatedBy(ltCustomerSubs.getCreatedBy());			
//				status = vacationService.checkStartDateValidation(ltCustomerSubs);
//				
//				if (status != null) {
//					return new ResponseEntity<Status>(status, HttpStatus.EXPECTATION_FAILED );
//				}
//				
//				status = new Status();
//				
//				//LtProducts ltProducts = ltProductsDao.getLtProductById(ltCustomerSubs.getProductId());
//				Optional<LtProducts> productOp  = productsRepository.findById(ltCustomerSubs.getProductId());
//				LtProducts ltProducts  = productOp.get();		
//				
//				if(ltCustomerSubs.getCustomerRate() ==null ) {
//					ltCustomerSubs.setCustomerRate(ltProducts.getProductRate());
//				}
//				
//				if(ltCustomerSubs.getSubscriptionType().equalsIgnoreCase("ONCE") ) {
//					ltCustomerSubs.setEndDate(ltCustomerSubs.getStartDate());
//				}
//				
//				if(ltCustomerSubs.getSubsId() == null ) {
//					orderHistoryService.saveSubscriptionOrderHistory(ltCustomerSubs, null, ltProducts, "SUPPLIER");	
//				}
//				LtCustomerSubs  customerSubs = ltCustomerSubsDao.save(ltCustomerSubs);
//				
//				LtInvoices invoice = ltInvoicesService.createPrepaidDeliveryOnceInvoice(customerSubs, PENDING);
//				
//				List<Date> subscriptionDates = new ArrayList<Date>();
//				subscriptionDates.add(ltCustomerSubs.getStartDate());
//				customerSubs.setInvoiceId(invoice.getInvoiceId());
//				this.batchInsertForSubsDelivery(subscriptionDates, customerSubs, invoice);	
//
//				LtMastUsers ltMastUser = ltMastUsersDao.getUserById(ltCustomerSubs.getUserId());
//				
//				notificationServiceCall.saveCustomerNotification(ltMastUser,
//						"Hi, " + ltMastUser.getUserName() + " your subscription of "
//								+ ltProducts.getProductName() + " for " + ltCustomerSubs.getSubsQuantity() + " "
//								+ ltProducts.getProductUom() + "  from date "
//								+ dateFormat.format(ltCustomerSubs.getStartDate()) + " has been approved by "
//								+ loginUser.getUserName());
//
//				notificationServiceCall.saveSupervisorNotification(ltMastUser,
//						"Hi, " + ltMastUser.getUserName() + " has subscribed to "
//								+ ltCustomerSubs.getSubsQuantity() + " " + ltProducts.getProductUom() + "s of "
//								+ ltProducts.getProductName());
//
//				status.setMessage("Action successful");
//				status.setData(customerSubs);
//				try {
//					ltCustomerSubsDeliveriesDao.addInvoiceIdInDeliveryTable();
//				}catch(Exception e) {
//					e.printStackTrace();
//				}
//				return new ResponseEntity<Status>(status, HttpStatus.OK );
//			}catch(Exception e) {
//				e.printStackTrace();
//				status = new Status();
//				status.setData(e.getMessage());
//				return new ResponseEntity<Status>(status, HttpStatus.EXPECTATION_FAILED );
//			}
//	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public ResponseEntity<Status> savePDOSubscription(LtCustomerSubs ltCustomerSubs, LtMastUsers loginUser, LtMastSuppliers suppliers) {
		Status status = null;
		try {
			System.out.println("Start: savePDOSubscription method");
			
			ltCustomerSubs.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
			System.out.println("Last update date set: " + ltCustomerSubs.getLastUpdateDate());
			
			ltCustomerSubs.setCreationDate(UtilsMaster.getCurrentDateTime());
			System.out.println("Creation date set: " + ltCustomerSubs.getCreationDate());
			
			ltCustomerSubs.setLastUpdatedBy(ltCustomerSubs.getCreatedBy());
			System.out.println("Last updated by set to: " + ltCustomerSubs.getLastUpdatedBy());
			
			status = vacationService.checkStartDateValidation(ltCustomerSubs);
			System.out.println("Start date validation status: " + status);
			
			if (status != null) {
				System.out.println("Validation failed, returning EXPECTATION_FAILED");
				return new ResponseEntity<Status>(status, HttpStatus.EXPECTATION_FAILED);
			}
			
			status = new Status();
			
			Optional<LtProducts> productOp = productsRepository.findById(ltCustomerSubs.getProductId());
			System.out.println("Product fetched by ID: " + ltCustomerSubs.getProductId());
			
			LtProducts ltProducts = productOp.get();
			System.out.println("Product details: " + ltProducts);
			
			if (ltCustomerSubs.getCustomerRate() == null) {
				ltCustomerSubs.setCustomerRate(ltProducts.getProductRate());
				System.out.println("Customer rate set to product rate: " + ltProducts.getProductRate());
			}
			
			if (ltCustomerSubs.getSubscriptionType().equalsIgnoreCase("ONCE")) {
				ltCustomerSubs.setEndDate(ltCustomerSubs.getStartDate());
				System.out.println("Subscription type is ONCE. End date set to start date: " + ltCustomerSubs.getEndDate());
			}
			
			if (ltCustomerSubs.getSubsId() == null) {
				System.out.println("New subscription detected. Saving subscription order history.");
				orderHistoryService.saveSubscriptionOrderHistory(ltCustomerSubs, null, ltProducts, "SUPPLIER");
			}
			
			LtCustomerSubs customerSubs = ltCustomerSubsDao.save(ltCustomerSubs);
			System.out.println("Subscription saved with ID: " + customerSubs.getSubsId());
			
			LtInvoices invoice = ltInvoicesService.createPrepaidDeliveryOnceInvoice(customerSubs, PENDING);
			System.out.println("Invoice created with ID: " + invoice.getInvoiceId());
			
			List<Date> subscriptionDates = new ArrayList<Date>();
			subscriptionDates.add(ltCustomerSubs.getStartDate());
			System.out.println("Subscription start date added to list: " + ltCustomerSubs.getStartDate());
			
			customerSubs.setInvoiceId(invoice.getInvoiceId());
			System.out.println("Invoice ID set in subscription: " + customerSubs.getInvoiceId());
			
			this.batchInsertForSubsDelivery(subscriptionDates, customerSubs, invoice);
			System.out.println("Batch insert for subscription delivery completed");
			
			LtMastUsers ltMastUser = ltMastUsersDao.getUserById(ltCustomerSubs.getUserId());
			System.out.println("User fetched with ID: " + ltCustomerSubs.getUserId());
			
			notificationServiceCall.saveCustomerNotification(ltMastUser,
				"Hi, " + ltMastUser.getUserName() + " your subscription of " + ltProducts.getProductName() + " for "
				+ ltCustomerSubs.getSubsQuantity() + " " + ltProducts.getProductUom() + " from date "
				+ dateFormat.format(ltCustomerSubs.getStartDate()) + " has been approved by " + loginUser.getUserName());
			System.out.println("Customer notification sent");
			
			notificationServiceCall.saveSupervisorNotification(ltMastUser,
				"Hi, " + ltMastUser.getUserName() + " has subscribed to " + ltCustomerSubs.getSubsQuantity() + " "
				+ ltProducts.getProductUom() + "s of " + ltProducts.getProductName());
			System.out.println("Supervisor notification sent");
			
			status.setMessage("Action successful");
			status.setData(customerSubs);
			
			try {
				ltCustomerSubsDeliveriesDao.addInvoiceIdInDeliveryTable();
				System.out.println("Invoice ID added in delivery table");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Exception in adding Invoice ID to delivery table: " + e.getMessage());
			}
			
			return new ResponseEntity<Status>(status, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception caught: " + e.getMessage());
			status = new Status();
			status.setData(e.getMessage());
			return new ResponseEntity<Status>(status, HttpStatus.EXPECTATION_FAILED);
		}
	}

	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public ResponseEntity<Status> savePrepaidSubscription(LtCustomerSubs ltCustomerSubs, LtMastUsers loginUser) {
		Status status = null;//new Status();		
		try {
			if(loginUser.getUserType().equals("CUSTOMER") ) {
				ltCustomerSubs.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
				ltCustomerSubs.setCreationDate(UtilsMaster.getCurrentDateTime());
				ltCustomerSubs.setLastUpdatedBy(ltCustomerSubs.getCreatedBy());			
				status = vacationService.checkStartDateValidation(ltCustomerSubs);
				
				if (status != null) {
					return new ResponseEntity<Status>(status, HttpStatus.EXPECTATION_FAILED );
				}
				
				status = new Status();
				//LtProducts ltProducts = ltProductsDao.getLtProductById(ltCustomerSubs.getProductId());
				Optional<LtProducts> productOp  = productsRepository.findById(ltCustomerSubs.getProductId());
				LtProducts ltProducts  = productOp.get();		
				ltCustomerSubs.setCustomerRate(ltProducts.getProductRate());
				LtInvoicePayments invoicePayments = new LtInvoicePayments();				
				
				Double totalWalletBalannce = 0d;
	 
				Double invoiceAmount = ltInvoicesService.calculatInvoiceAmount(ltCustomerSubs);
				
				if(ltCustomerSubs.getWalletBalance() != null && ltCustomerSubs.getWalletBalance() > 0  ) {
					String isPrepaid = mastSuppliersDao.getPrepaidSupplierFlag(ltCustomerSubs.getSupplierId());
					if(isPrepaid != null && isPrepaid.trim().equalsIgnoreCase("Y") ) {
						totalWalletBalannce = ltInvoicePaymentsDao.getPrepaidWalletBalance(ltCustomerSubs.getUserId() , ltCustomerSubs.getSupplierId(), "Y" );
					}else if(isPrepaid != null && isPrepaid.trim().equalsIgnoreCase("PDO") ){
						totalWalletBalannce = ltInvoicePaymentsDao.getPrepaidWalletBalance(ltCustomerSubs.getUserId() , ltCustomerSubs.getSupplierId(), "PDO");
					}
				   if(totalWalletBalannce < ltCustomerSubs.getWalletBalance()){
						status.setCode(500);
						status.setMessage("Insufficiant balance for subscription.");
						return new ResponseEntity<Status>(status, HttpStatus.EXPECTATION_FAILED );
				   }
				}else {
					ltCustomerSubs.setWalletBalance(0d);
				}
				if(ltCustomerSubs.getPayAmount() == null  ) {
					ltCustomerSubs.setPayAmount(0d);
				}
				
				 
				if(invoiceAmount.equals(ltCustomerSubs.getWalletBalance() + ltCustomerSubs.getPayAmount())) {
					invoicePayments.setFromWalletBalance(ltCustomerSubs.getWalletBalance());
					invoicePayments.setPayAmount(ltCustomerSubs.getPayAmount());
				}else {
					status.setCode(500);
					status.setMessage("Invoice amount is incurrect. Invoice amount is "+invoiceAmount);
					return new ResponseEntity<Status>(status, HttpStatus.EXPECTATION_FAILED );
				}
				
				invoicePayments.setPayAmount(ltCustomerSubs.getPayAmount());
				
				invoicePayments.setSupplierId(ltCustomerSubs.getSupplierId());
				invoicePayments.setUserId(ltCustomerSubs.getUserId());			
				invoicePayments.setTxnId(ltCustomerSubs.getTxnId());
				invoicePayments.setPayReferenceNo(ltCustomerSubs.getTxnRef());
				invoicePayments.setResponseCode(ltCustomerSubs.getResponseCode());
				invoicePayments.setTxnStatus(ltCustomerSubs.getTxnStatus());
				
				invoicePayments.setCreationDate(UtilsMaster.getCurrentDateTime());
				invoicePayments.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
				invoicePayments.setCreatedBy(ltCustomerSubs.getCreatedBy());
				invoicePayments.setLastUpdatedBy(ltCustomerSubs.getCreatedBy());
				invoicePayments.setLastUpdateLogin(ltCustomerSubs.getLastUpdateLogin());
				invoicePayments.setPayMode(ltCustomerSubs.getPayMode());
				
				if(ltCustomerSubs.getSubscriptionType().equalsIgnoreCase("ONCE") ) {
					ltCustomerSubs.setEndDate(ltCustomerSubs.getStartDate());
				}
	
				if (loginUser.getUserType().equals("SUPPLIER")) {
					ltCustomerSubs.setStatus(ACTIVE);
					invoicePayments.setStatus("PENDING");
				}else {
					invoicePayments.setStatus("PAID");
				}
	
				if(ltCustomerSubs.getSubsId() == null ) {
					orderHistoryService.saveSubscriptionOrderHistory(ltCustomerSubs, invoiceAmount, ltProducts, loginUser.getUserType());	
				}
				
				LtCustomerSubs  customerSubs = ltCustomerSubsDao.save(ltCustomerSubs);
	
				LtInvoices invoice = null;
				if(ltCustomerSubs.getSubscriptionType().equalsIgnoreCase("ONCE")) {
					invoice = ltInvoicesService.createPrepaidDeliveryOnceInvoice(customerSubs, "PAID");
				}else {
					invoice = ltInvoicesService.createPrepaidInvoiceByDate(customerSubs, "PAID");
				}		
				orderHistoryService.saveSubscriptionOrderHistory(invoice, "Paid", ltCustomerSubs.getWalletBalance() ,"CUSTOMER");
				invoicePayments.setInvoiceId(invoice.getInvoiceId());	
				
				invoicePayments.setSubsId(customerSubs.getSubsId());			
				invoicePayments = invoicePaymentsDao.save(invoicePayments);			
				customerSubs.setInvoiceId(invoicePayments.getInvoiceId());
				 
					notificationServiceCall.saveCustomerNotification(loginUser,
							"Hi, " + loginUser.getUserName() + " you have subscribed to " + ltProducts.getProductName()+ " for " + ltCustomerSubs.getSubsQuantity() + " " 
									+ ltProducts.getProductUom()+ "  for date " + dateFormat.format(ltCustomerSubs.getStartDate()));
					
					notificationServiceCall.saveSupplierNotification(ltCustomerSubs.getSupplierId(),
							"Hi, " + loginUser.getUserName() + " has subscribed to " + ltProducts.getProductName()+ " for " + ltCustomerSubs.getSubsQuantity() 
							       + " " + ltProducts.getProductUom()+ "  for date " + dateFormat.format(ltCustomerSubs.getStartDate())+ ". Please Approve");
	
					
					if( ltCustomerSubs.getPayAmount() != null  && ltCustomerSubs.getPayAmount() >  0  ) {
						notificationServiceCall.saveCustomerNotification(loginUser,
							"Hi, You have made a payment of Rs. "+ ltCustomerSubs.getPayAmount() + " for "+ ltProducts.getProductName()
														+" "+ ltCustomerSubs.getSubsQuantity() +", Thank You!!!");
						notificationServiceCall.saveSupplierNotification(ltCustomerSubs.getSupplierId(), 
								"Hi, "+ loginUser.getUserName()+" has made a payment of Rs. "+ ltCustomerSubs.getPayAmount()+"." );
					}else {
						notificationServiceCall.saveCustomerNotification(loginUser,
								"Hi, You have made a payment of Rs. "+ invoiceAmount + " from balance amount for "+ ltProducts.getProductName()
															+" "+ ltCustomerSubs.getSubsQuantity() +", Thank You!!!");
						notificationServiceCall.saveSupplierNotification(ltCustomerSubs.getSupplierId(), 
								"Hi, "+ loginUser.getUserName()+" has made a payment of Rs. "+ invoiceAmount+" from balance amount." );
					}
				status.setMessage("Action successful");
				status.setData(customerSubs);
				return new ResponseEntity<Status>(status, HttpStatus.OK );
			}		
			status = new Status();
			status.setMessage("Add prepaid order not allowed to Supplier.");
			return new ResponseEntity<Status>(status, HttpStatus.EXPECTATION_FAILED );	
		}catch(Exception e) {
			e.printStackTrace();
			status = new Status();
			status.setData(e.getMessage());
			return new ResponseEntity<Status>(status, HttpStatus.EXPECTATION_FAILED );
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	private void batchInsertForSubsDelivery(List<Date> subscriptionDates, LtCustomerSubs ltCustomerSubs , LtInvoices invoice)
			throws ServiceException {
		List<LtCustomerSubsDeliveries> subsDeliveryList = new ArrayList<LtCustomerSubsDeliveries>();
		LtVacationPeriod ltVacationPeriod = ltCustomerSubsDeliveriesDao.getVacationByUser(ltCustomerSubs.getUserId());
		
		for (Date date : subscriptionDates){
 
			try {
				date = UtilsMaster.dateForyyyyMMdd.parse(UtilsMaster.dateForyyyyMMdd.format(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			LtCustomerSubsDeliveries ltCustomerSubsDeliveries = new LtCustomerSubsDeliveries();
			ltCustomerSubsDeliveries.setSupplierId(ltCustomerSubs.getSupplierId());
			ltCustomerSubsDeliveries.setDeliveryDate(date);
			ltCustomerSubsDeliveries.setSubsId(ltCustomerSubs.getSubsId());
			ltCustomerSubsDeliveries.setUserId(ltCustomerSubs.getUserId());
			ltCustomerSubsDeliveries.setProductId(ltCustomerSubs.getProductId());
			if (ltVacationPeriod != null) {
				if (date.after(ltVacationPeriod.getStartDate()) && date.before(ltVacationPeriod.getEndDate())) {
					ltCustomerSubsDeliveries.setVacationId(ltVacationPeriod.getVacationId());
					ltCustomerSubsDeliveries.setSubsQuantity(0D);
					ltCustomerSubsDeliveries.setDeliveryQuantity(0D);
					ltCustomerSubsDeliveries.setStatus("PAUSED");
				} else if (date.equals(ltVacationPeriod.getEndDate()) || date.equals(ltVacationPeriod.getStartDate())) {
					ltCustomerSubsDeliveries.setVacationId(ltVacationPeriod.getVacationId());
					ltCustomerSubsDeliveries.setSubsQuantity(0D);
					ltCustomerSubsDeliveries.setDeliveryQuantity(0D);
					ltCustomerSubsDeliveries.setStatus("PAUSED");
				} else {
					ltCustomerSubsDeliveries.setVacationId(ltVacationPeriod.getVacationId());
					ltCustomerSubsDeliveries.setSubsQuantity(ltCustomerSubs.getSubsQuantity());
					ltCustomerSubsDeliveries.setDeliveryQuantity(ltCustomerSubs.getSubsQuantity());
					ltCustomerSubsDeliveries.setStatus(ltCustomerSubs.getStatus());
				}
			} else {
				ltCustomerSubsDeliveries.setSubsQuantity(ltCustomerSubs.getSubsQuantity());
				ltCustomerSubsDeliveries.setDeliveryQuantity(ltCustomerSubs.getSubsQuantity());
				ltCustomerSubsDeliveries.setStatus(ltCustomerSubs.getStatus());
			}

			ltCustomerSubsDeliveries.setDeliveryMode(ltCustomerSubs.getDeliveryMode());
			ltCustomerSubsDeliveries.setInvoiceRate(ltCustomerSubs.getCustomerRate());
					
			if(invoice != null && invoice.getInvoiceId() != null) {
				Double lineAmount  =  ltCustomerSubsDeliveries.getInvoiceRate() * ltCustomerSubsDeliveries.getDeliveryQuantity();
				ltCustomerSubsDeliveries.setLineAmount( lineAmount );
				ltCustomerSubsDeliveries.setInvoiceId(invoice.getInvoiceId() );
			}else {
				ltCustomerSubsDeliveries.setInvoiceId(ltCustomerSubs.getInvoiceId());
			}
			
			ltCustomerSubsDeliveries.setStartDate(ltCustomerSubs.getStartDate());
			ltCustomerSubsDeliveries.setEndDate(ltCustomerSubs.getEndDate());
			ltCustomerSubsDeliveries.setCreationDate(UtilsMaster.getCurrentDateTime());
			ltCustomerSubsDeliveries.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
			ltCustomerSubsDeliveries.setLastUpdatedBy(-1L);
			ltCustomerSubsDeliveries.setCreatedBy(-1L);
			ltCustomerSubsDeliveries.setLastUpdateLogin(-1L);
			subsDeliveryList.add(ltCustomerSubsDeliveries);
		}
		ltCustomerSubsDeliveriesDao.batchInsertForSubsDelivery(subsDeliveryList);
	}

	
	public Date getNotificationStartDate(LtCustomerSubs customerSubs) {
		Date strDate = customerSubs.getStartDate();
		try {
			Date endDate =  UtilsMaster.addDays(strDate, 10);
			final SimpleDateFormat dayFormat = new SimpleDateFormat("EEE");
			
			if(customerSubs.getSubscriptionType().equalsIgnoreCase("MONTHLY")) {
				while(strDate.compareTo(endDate) <= 0 ) {
					String weekDay = dayFormat.format(strDate);
				/*	if(customerSubs.getAlternateDay() != null && customerSubs.getAlternateDay().equalsIgnoreCase("Y") ) {
						strDate = UtilsMaster.addDays(strDate, 2);
					}else {*/
						if( weekDay.equals("Mon")  && customerSubs.getMonday() != null  && customerSubs.getMonday().equalsIgnoreCase("Y")) {
							return strDate;
						}else if(weekDay.equals("Tue") && customerSubs.getTuesday() != null  && customerSubs.getTuesday().equalsIgnoreCase("Y") ) {
							return strDate;
						}else if(weekDay.equals("Wed") && customerSubs.getWednesday() != null  && customerSubs.getWednesday().equalsIgnoreCase("Y") ) {
							return strDate;
						}else if(weekDay.equals("Thu") && customerSubs.getThursday() != null  && customerSubs.getThursday().equalsIgnoreCase("Y") ) {
							return strDate;
						}else if(weekDay.equals("Fri") && customerSubs.getFriday() != null  && customerSubs.getFriday().equalsIgnoreCase("Y") ) {
							return strDate;
						}else if(weekDay.equals("Sat") && customerSubs.getSaturday() != null  && customerSubs.getSaturday().equalsIgnoreCase("Y") ) {
							return strDate;
						}else if(weekDay.equals("Sun") && customerSubs.getSunday() != null  && customerSubs.getSunday().equalsIgnoreCase("Y") ) {
							return strDate;
						}
						strDate = UtilsMaster.addDays(strDate, 1);
					} 
				//}
			}else {
				return strDate;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strDate;
	}

//	old code
	
//	@Override
//	@Transactional(propagation = Propagation.REQUIRES_NEW)  //, rollbackFor = Exception.class
//	public Status update(LtCustomerSubs ltCustomerSubs, LtMastUsers user) throws ServiceException, ParseException {
//		
//		//CalculateDate calculateDate = new CalculateDate();
//		String startDate = "";		
//		Status status = null;
//
//		if (ltCustomerSubs.getSubsId() != null) {
//			LtCustomerSubs customerSub = ltCustomerSubsDao.getCustomerSubsById(ltCustomerSubs.getSubsId());
//			//customerSubsRepository.findOne(ltCustomerSubs.getSubsId());
//			ltCustomerSubs.setCreatedBy(customerSub.getCreatedBy());
//			ltCustomerSubs.setCreationDate(customerSub.getCreationDate());
//		} else {
//			ltCustomerSubs.setCreatedBy(ltCustomerSubs.getLastUpdateLogin());
//			ltCustomerSubs.setCreationDate(UtilsMaster.getCurrentDateTime());
//		}
//		
//		ltCustomerSubs.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
//		ltCustomerSubs.setLastUpdatedBy(ltCustomerSubs.getLastUpdateLogin());
//
//		status = vacationService.checkStartDateValidation(ltCustomerSubs);		
//		if (status != null) {
//			return status;
//		}
//
//		status = this.timeLimitValidation(user, ltCustomerSubs);
//		if (status != null && status.getCode() != 200 ) {
//			return status;
//		}
//		
//		status = new Status();
//		//LtProducts ltProducts = ltProductsDao.getLtProductById(ltCustomerSubs.getProductId());
//		Optional<LtProducts> productOp  = productsRepository.findById(ltCustomerSubs.getProductId());
//		LtProducts ltProducts  = productOp.get();		
//		
//		try {
//			//if(ltCustomerSubs.getSubsId() == null || ltCustomerSubs.getStatus().equalsIgnoreCase(CANCELLED)
//				// ||  ltCustomerSubs.getStatus().equalsIgnoreCase(REJECTED) ) {
//			orderHistoryService.saveSubscriptionOrderHistory(ltCustomerSubs, null, ltProducts, user.getUserType());	
//			//}
//			ltCustomerSubs = ltCustomerSubsDao.save(ltCustomerSubs);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		
//		if( ltCustomerSubs.getSubsQuantity() > 0 ) {
//			String endDate = "";
//			
//			if (ltCustomerSubs.getSubscriptionType().equals(MONTHLY)){
//				if (ltCustomerSubs.getStatus().equals(ACTIVE)) {				
//					Long deliveryCount = ltCustomerSubsDeliveriesDao.getSubsDeliveryCountBySubsId(ltCustomerSubs.getSubsId());	
//					LtInvoices invoice = invoicesDao.getInvoicesBySubId(ltCustomerSubs.getSubsId(), ltCustomerSubs.getSupplierId());
//					
//					if (deliveryCount == null || deliveryCount < 1 ) {					
//							startDate = CalculateDate.utilToYYYYMMDD(ltCustomerSubs.getStartDate());					
//							endDate = "";
//							if (ltCustomerSubs.getEndDate() == null) {
//								endDate = CalculateDate.findLast(startDate);
//							} else {
//								endDate = CalculateDate.utilToYYYYMMDD(ltCustomerSubs.getEndDate());
//								endDate = CalculateDate.findLastSub(startDate, endDate);
//							}
//		
//							if (ltCustomerSubs.getAlternateDay() != null && ltCustomerSubs.getAlternateDay().equals("Y")) {
//								List<Date> subscriptionDates = CalculateAlternateDates.getAlternateDaysInDate(startDate, endDate, ltCustomerSubs.getAlternateDay());
//								this.batchInsertForSubsDelivery(subscriptionDates, ltCustomerSubs, invoice);
//							} else {
//								List<Date> subscriptionDates = CalculateDate.getDaysInDate(startDate, endDate,
//										ltCustomerSubs.getMonday(), ltCustomerSubs.getTuesday(), ltCustomerSubs.getWednesday(),
//										ltCustomerSubs.getThursday(), ltCustomerSubs.getFriday(), ltCustomerSubs.getSaturday(),
//										ltCustomerSubs.getSunday());
//								this.batchInsertForSubsDelivery(subscriptionDates, ltCustomerSubs, invoice);
//							}
//						} else {					
//								Date date = null;
//								String nextDate = null;
//								try {
//									date = ltCustomerSubsDeliveriesDao.getStartDate(ltCustomerSubs.getSupplierId(), ltCustomerSubs.getSubsId(), ltCustomerSubs.getStartDate());
//									if(date != null) {
//										startDate = CalculateDate.utilToYYYYMMDD(date);
//										nextDate = startDate;
//									}else {
//										startDate = CalculateDate.utilToYYYYMMDD(ltCustomerSubs.getLastUpdateDate());
//										date = ltCustomerSubs.getLastUpdateDate();
//										nextDate = CalculateDate.getNextDate(startDate);
//									}
//								} catch (Exception e) {
//									e.printStackTrace();
//								}
//								ltCustomerSubsDeliveriesDao.deleteExistingSubsDeliveries(ltCustomerSubs, date);
//								endDate = "";
//								if (ltCustomerSubs.getEndDate() == null) {
//									endDate = CalculateDate.findLast(startDate);
//								} else {
//									endDate = CalculateDate.utilToYYYYMMDD(ltCustomerSubs.getEndDate());
//									endDate = CalculateDate.findLastSub(startDate, endDate);
//								}						 
//								if (ltCustomerSubs.getAlternateDay() != null && ltCustomerSubs.getAlternateDay().equals("Y")) {
//									List<Date> subscriptionDates = CalculateAlternateDates.getAlternateDaysInDate(startDate, endDate, ltCustomerSubs.getAlternateDay());
//									this.batchInsertForSubsDelivery(subscriptionDates, ltCustomerSubs, invoice);
//								} else {
//									List<Date> subscriptionDates = CalculateDate.getDaysInDate(nextDate, endDate,
//											ltCustomerSubs.getMonday(), ltCustomerSubs.getTuesday(), ltCustomerSubs.getWednesday(), 
//											ltCustomerSubs.getThursday(), ltCustomerSubs.getFriday(), ltCustomerSubs.getSaturday(),
//											ltCustomerSubs.getSunday());
//									this.batchInsertForSubsDelivery(subscriptionDates, ltCustomerSubs, invoice);
//								}					
//						}
//						
//						LtMastUsers ltMastUser = ltMastUsersDao.getUserById(ltCustomerSubs.getUserId());
//											
//						if (ltProducts != null) {
//							if (ltCustomerSubs.getUserId() != ltCustomerSubs.getLastUpdatedBy()) {
//								notificationServiceCall.saveCustomerNotification(ltMastUser, "Hi, " + ltMastUser.getUserName() + " your subscription of "
//												+ ltProducts.getProductName() + " for " + ltCustomerSubs.getSubsQuantity() + " "+ ltProducts.getProductUom() + "  from date "
//												+ dateFormat.format( this.getNotificationStartDate(ltCustomerSubs)) + " has been approved by "+ user.getUserName());
//		
//								notificationServiceCall.saveSupervisorNotification(ltMastUser, "Hi, " + ltMastUser.getUserName() + " has subscribed to "
//												+ ltCustomerSubs.getSubsQuantity() + " " + ltProducts.getProductUom() + "s of "+ ltProducts.getProductName());
//							} 
//						}
//			} else if (ltCustomerSubs.getStatus().equals(CANCELLED)) {
//				ltCustomerSubsDeliveriesDao.deleteExistingSubsDeliveries(ltCustomerSubs);
//				try {
//					ltCustomerSubs = ltCustomerSubsDao.save(ltCustomerSubs);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				System.out.println("CANCELLED SUB..");
//				if (ltCustomerSubs != null) {
//					status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
//					if (user.getUserType().equals("CUSTOMER")) {
//						notificationServiceCall.saveSupplierNotification(ltCustomerSubs.getSupplierId(),
//								"Hi, " + user.getUserName() + " has cancelled their subscription of "
//										+ ltProducts.getProductName() + " from "+ dateFormat.format(ltCustomerSubs.getLastUpdateDate()));
//					} else {
//						LtMastUsers ltMastUser = ltMastUsersDao.getUserById(ltCustomerSubs.getUserId());
//						notificationServiceCall.saveCustomerNotification(ltMastUser, "Hi, " + ltMastUser.getUserName() + " your subscription of "
//										+ ltProducts.getProductName() + " for " + ltCustomerSubs.getSubsQuantity() + " "
//										+ ltProducts.getProductUom() + "  from date "
//										+ dateFormat.format(ltCustomerSubs.getStartDate()) + " has been cancelled by "
//										+ user.getUserName() + " , Please contact us for further assistance.");
//					}
//				} else {
//					status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
//				}
//				  //this.saveMinusInvoice(ltCustomerSubs.getSubsId(), ltCustomerSubs.getSupplierId());
//				return status;
//
//			} else if (ltCustomerSubs.getStatus().equals(PAUSED)) {
//				if (ltCustomerSubsDeliveriesDao.updateDelivery(ltCustomerSubs, null)) {
//					status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
//				} else {
//					status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
//				}
//				return status;
//			} else if (ltCustomerSubs.getStatus().equals(PENDING)) {
//				try {
//					ltCustomerSubs = ltCustomerSubsDao.save(ltCustomerSubs);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				if (ltCustomerSubs.getSubsId() != null) {
//					//LtProducts ltProducts = ltProductsDao.getLtProductById(ltCustomerSubs.getProductId());
//					if (user.getUserType().equals("CUSTOMER")) {
//						LtMastUsers ltMastUser = ltMastUsersDao.getUserById(ltCustomerSubs.getUserId());
//
//						if (ltCustomerSubs.getUserId() != ltCustomerSubs.getLastUpdateLogin()) {
//							notificationServiceCall.saveCustomerNotification(ltMastUser, "Hi, " + ltMastUser.getUserName() + " you have subscribed to "
//											+ ltProducts.getProductName() + " for " + ltCustomerSubs.getSubsQuantity()
//											+ " " + ltProducts.getProductUom() + "  from date "
//											+ dateFormat.format( this.getNotificationStartDate(ltCustomerSubs)));
//											//+ dateFormat.format( ltCustomerSubs.getLastUpdateDate()));
//						}
//						notificationServiceCall.saveSupplierNotification(ltCustomerSubs.getSupplierId(), "Hi, " + ltMastUser.getUserName() 
//										+ " has updated subscription of "+ ltProducts.getProductName() + " for " + ltCustomerSubs.getSubsQuantity() + " "
//										+ ltProducts.getProductUom() + "  from date "
//										+ dateFormat.format( this.getNotificationStartDate(ltCustomerSubs)) + ". Please Approve");
//										//+ dateFormat.format(ltCustomerSubs.getLastUpdateDate()) + ". Please Approve");
//
//					} else if (user.getUserType().equals("SUPPLIER")) {
//						LtMastUsers ltMastUser = ltMastUsersDao.getUserById(ltCustomerSubs.getUserId());
//
//						notificationServiceCall.saveCustomerNotification(ltMastUser,
//								"Hi, " + ltMastUser.getUserName() + " you have subscribed to "+ ltProducts.getProductName() + " for " + ltCustomerSubs.getSubsQuantity() + " "
//										+ ltProducts.getProductUom() + "  from date "
//										+ dateFormat.format(this.getNotificationStartDate(ltCustomerSubs)));
//										//+ dateFormat.format(ltCustomerSubs.getLastUpdateDate()));
//
//					}
//					status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
//					status.setMessage("Order Placed!  Supplier's approval pending.");
//					status.setData(ltCustomerSubs.getSubsId());
//				} else {
//					status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
//				}
//				//this.saveMinusInvoice(ltCustomerSubs.getSubsId() , ltCustomerSubs.getSupplierId() );
//				return status;
//
//			} else if (ltCustomerSubs.getStatus().equals(REJECTED)) {
//				ltCustomerSubsDeliveriesDao.deleteExistingSubsDeliveries(ltCustomerSubs);
//
//				startDate = CalculateDate.utilToYYYYMMDD(ltCustomerSubs.getStartDate());
//				endDate = CalculateDate.findLast(startDate);
//				status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
//
//				LtMastUsers ltMastUser = ltMastUsersDao.getUserById(ltCustomerSubs.getUserId());
//				//LtProducts ltProducts = ltProductsDao.getLtProductById(ltCustomerSubs.getProductId());
//
//				notificationServiceCall.saveCustomerNotification(ltMastUser, "Hi, " + ltMastUser.getUserName() + " your subscription of " + ltProducts.getProductName()
//								+ " for " + ltCustomerSubs.getSubsQuantity() + " " + ltProducts.getProductUom()
//								+ "  from date " + dateFormat.format(ltCustomerSubs.getStartDate())
//								+ " has been rejected by " + user.getUserName() + ", Please contact us for further assistance.");
//
//				ltCustomerSubs.setSubsDeliveryDate(new SimpleDateFormat("yyyy-MM-dd").parse(endDate));
//				try {
//					ltCustomerSubs = ltCustomerSubsDao.save(ltCustomerSubs);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				if (ltCustomerSubs.getSubsId() != null) {
//					status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
//					status.setData(ltCustomerSubs.getSubsId());
//					status.setMessage("Subscription has been Rejected");
//				} else {
//					status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
//				}
//				//this.saveMinusInvoice(ltCustomerSubs.getSubsId(), ltCustomerSubs.getSupplierId() );
//				return status;
//			}
//		} else if (ltCustomerSubs.getSubscriptionType().equals(ONCE)) {
//			
//			if (ltCustomerSubs.getStatus().equals(ACTIVE)) {
//				Boolean isDeliveryAvailable = false; 
//				try {
//					isDeliveryAvailable = ltCustomerSubsDeliveriesDao.isDeliveryAvailable(ltCustomerSubs.getSupplierId() , ltCustomerSubs.getSubsId(), ACTIVE);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				
//				if(user.getUserType().equalsIgnoreCase("SUPPLIER") &&  isDeliveryAvailable) {
//					
//					startDate = CalculateDate.utilToYYYYMMDD(ltCustomerSubs.getStartDate());
//					endDate = CalculateDate.utilToYYYYMMDD(ltCustomerSubs.getEndDate());
//					endDate = CalculateDate.findLastSub(startDate, endDate);
//					
//					ltCustomerSubsDeliveriesDao.updateDeliveryOnceDetails(ltCustomerSubs);
//					
//					LtMastUsers ltMastUser = ltMastUsersDao.getUserById(ltCustomerSubs.getUserId());
//					//LtProducts ltProducts = ltProductsDao.getLtProductById(ltCustomerSubs.getProductId());
//					
//					notificationServiceCall.saveCustomerNotification(ltMastUser, "Hi, " + ltMastUser.getUserName() + " your subscription of " 
//							+ ltProducts.getProductName() + " for " + ltCustomerSubs.getSubsQuantity() + " " + ltProducts.getProductUom()
//							+ "  for date " + dateFormat.format(ltCustomerSubs.getStartDate()) + " has been approved by " + user.getUserName());
//					
//				} else if(!isDeliveryAvailable) {
//					startDate = CalculateDate.utilToYYYYMMDD(ltCustomerSubs.getStartDate());
//					endDate = CalculateDate.utilToYYYYMMDD(ltCustomerSubs.getEndDate());
//					endDate = CalculateDate.findLastSub(startDate, endDate);
//					
//					List<Date> subscriptionDates = new ArrayList<Date>();
//	
//					subscriptionDates.add(ltCustomerSubs.getStartDate());
//					LtInvoices invoice = invoicesDao.getInvoicesBySubId(ltCustomerSubs.getSubsId(), ltCustomerSubs.getSupplierId());
//					this.batchInsertForSubsDelivery(subscriptionDates, ltCustomerSubs, invoice);
//					
//					LtMastUsers ltMastUser = ltMastUsersDao.getUserById(ltCustomerSubs.getUserId());
//					//LtProducts ltProducts = ltProductsDao.getLtProductById(ltCustomerSubs.getProductId());
//	
//					if (ltCustomerSubs.getUserId() != ltCustomerSubs.getLastUpdatedBy()) {
//						notificationServiceCall.saveCustomerNotification(ltMastUser, "Hi, " + ltMastUser.getUserName() + " your subscription of " 
//										+ ltProducts.getProductName() + " for " + ltCustomerSubs.getSubsQuantity() + " " + ltProducts.getProductUom()
//										+ "  for date " + dateFormat.format(ltCustomerSubs.getStartDate()) + " has been approved by " + user.getUserName());
//					}
//				}else {
//					status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
//					status.setData(ltCustomerSubs.getSubsId());
//					status.setMessage("Your subscription has been already Approved");	
//					return status;
//				}
//				
//			} else if (ltCustomerSubs.getStatus().equals(PENDING)) {
//				try {
//					ltCustomerSubs = ltCustomerSubsDao.save(ltCustomerSubs);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				if (ltCustomerSubs.getSubsId() != null) {
//					//LtProducts ltProducts = ltProductsDao.getLtProductById(ltCustomerSubs.getProductId());
//					if (user.getUserType().equals("CUSTOMER")) {
//						notificationServiceCall.saveCustomerNotification(user, "Hi, " + user.getUserName() + " you have subscribed to "
//										+ ltProducts.getProductName() + " for " + ltCustomerSubs.getSubsQuantity() + " "
//										+ ltProducts.getProductUom() + "  from date "+ dateFormat.format(ltCustomerSubs.getStartDate()));
//
//						notificationServiceCall.saveSupplierNotification(ltCustomerSubs.getSupplierId(), "Hi, " + user.getUserName() + " has subscribed to " 
//										+ ltProducts.getProductName()+ " for " + ltCustomerSubs.getSubsQuantity() + " " + ltProducts.getProductUom()
//										+ "  from date " + dateFormat.format(ltCustomerSubs.getStartDate())+ ". Please Approve");
//
//					}  
//					status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
//					status.setData(ltCustomerSubs.getSubsId());
//
//				} else {
//					status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
//				}
//				//this.saveMinusInvoice(ltCustomerSubs.getSubsId(), ltCustomerSubs.getSupplierId() );
//				return status;
//			} else if (ltCustomerSubs.getStatus().equals(REJECTED)) {
//				ltCustomerSubsDeliveriesDao.deleteExistingSubsDeliveries(ltCustomerSubs);
//
//				startDate = CalculateDate.utilToYYYYMMDD(ltCustomerSubs.getStartDate());
//				endDate = CalculateDate.findLast(startDate);
//				status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
//
//				LtMastUsers ltMastUser = ltMastUsersDao.getUserById(ltCustomerSubs.getUserId());
//				//LtProducts ltProducts = ltProductsDao.getLtProductById(ltCustomerSubs.getProductId());
//
//				notificationServiceCall.saveCustomerNotification(ltMastUser,
//						"Hi, " + ltMastUser.getUserName() + " your subscription of " + ltProducts.getProductName()
//								+ " for " + ltCustomerSubs.getSubsQuantity() + " " + ltProducts.getProductUom()
//								+ "  from date " + dateFormat.format(ltCustomerSubs.getStartDate())
//								+ " has been rejected by " + user.getUserName() + " , Please contact us for further assistance.");
//
//				ltCustomerSubs.setSubsDeliveryDate(new SimpleDateFormat("yyyy-MM-dd").parse(endDate));
//				try {
//					ltCustomerSubs = ltCustomerSubsDao.save(ltCustomerSubs);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				if (ltCustomerSubs.getSubsId() != null) {
//					status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
//					status.setData(ltCustomerSubs.getSubsId());
//					status.setMessage("Subscription has been Rejected");
//				} else {
//					status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
//				}
//				//this.saveMinusInvoice(ltCustomerSubs.getSubsId(), ltCustomerSubs.getSupplierId());
//				return status;
//			} else if (ltCustomerSubs.getStatus().equals(CANCELLED)) {
//				ltCustomerSubsDeliveriesDao.deleteExistingSubsDeliveries(ltCustomerSubs);
//				try {
//					ltCustomerSubs = ltCustomerSubsDao.save(ltCustomerSubs);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				if (ltCustomerSubs != null) {
//					status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
//					//LtProducts ltProducts = ltProductsDao.getLtProductById(ltCustomerSubs.getProductId());
//					if (user.getUserType().equals("CUSTOMER")) {
//						notificationServiceCall.saveSupplierNotification(ltCustomerSubs.getSupplierId(),
//								"Hi, " + user.getUserName() + " has cancelled their subscription of "
//										+ ltProducts.getProductName() + " from "+ dateFormat.format(ltCustomerSubs.getLastUpdateDate()));
//					} else {
//						LtMastUsers ltMastUser = ltMastUsersDao.getUserById(ltCustomerSubs.getUserId());
//						notificationServiceCall.saveCustomerNotification(ltMastUser,
//								"Hi, " + ltMastUser.getUserName() + " your subscription of "+ ltProducts.getProductName() + " for " + ltCustomerSubs.getSubsQuantity() + " "
//										+ ltProducts.getProductUom() + "  for date "+ dateFormat.format(ltCustomerSubs.getStartDate()) + " has been cancelled by "
//										+ user.getUserName() + " , Please contact us for further assistance.");
//					}
//				} else {
//					status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
//				}
//				//this.saveMinusInvoice(ltCustomerSubs.getSubsId(), ltCustomerSubs.getSupplierId() );
//				return status;
//				}
//			}
//			ltCustomerSubs.setSubsDeliveryDate(new SimpleDateFormat("yyyy-MM-dd").parse(endDate));
//			try {
//				ltCustomerSubs = ltCustomerSubsDao.save(ltCustomerSubs);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		else if(ltCustomerSubs.getSubsQuantity() == 0 ) {
//			try {
//				ltCustomerSubs = ltCustomerSubsDao.save(ltCustomerSubs);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		
//		if (ltCustomerSubs.getSubsId() != null) {
//			status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
//			status.setData(ltCustomerSubs.getSubsId());
//			//status.setMessage("Subscription has been Approved");
//		} else {
//			status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
//		}
//		return status;
//	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Status update(LtCustomerSubs ltCustomerSubs, LtMastUsers user) throws ServiceException, ParseException {

	    System.out.println("Start: update method");
	    String startDate = "";      
	    Status status = null;

	    if (ltCustomerSubs.getSubsId() != null) {
	        System.out.println("Updating existing subscription with ID: " + ltCustomerSubs.getSubsId());
	        LtCustomerSubs customerSub = ltCustomerSubsDao.getCustomerSubsById(ltCustomerSubs.getSubsId());
	        ltCustomerSubs.setCreatedBy(customerSub.getCreatedBy());
	        ltCustomerSubs.setCreationDate(customerSub.getCreationDate());
	    } else {
	        System.out.println("Creating new subscription");
	        ltCustomerSubs.setCreatedBy(ltCustomerSubs.getLastUpdateLogin());
	        ltCustomerSubs.setCreationDate(UtilsMaster.getCurrentDateTime());
	    }
	    
	    ltCustomerSubs.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
	    System.out.println("Last update date set: " + ltCustomerSubs.getLastUpdateDate());
	    
	    ltCustomerSubs.setLastUpdatedBy(ltCustomerSubs.getLastUpdateLogin());
	    System.out.println("Last updated by set: " + ltCustomerSubs.getLastUpdatedBy());
	    
	    status = vacationService.checkStartDateValidation(ltCustomerSubs);
	    System.out.println("Start date validation status: " + status);
	    
	    if (status != null) {
	        System.out.println("Validation failed, returning status");
	        return status;
	    }

	    status = this.timeLimitValidation(user, ltCustomerSubs);
	    System.out.println("Time limit validation status: " + status); 
	    
	    if (status != null && status.getCode() != 200) {
	        System.out.println("Time limit validation failed");
	        return status;
	    }

//	    status = new Status();
	    Optional<LtProducts> productOp  = productsRepository.findById(ltCustomerSubs.getProductId());
	    System.out.println("Fetching product by ID: " + ltCustomerSubs.getProductId());
	    
	    LtProducts ltProducts  = productOp.get();      
	    System.out.println("Product details fetched: " + ltProducts);
	    
	    try {
	        System.out.println("Saving subscription order history");
	        orderHistoryService.saveSubscriptionOrderHistory(ltCustomerSubs, null, ltProducts, user.getUserType());
	        ltCustomerSubs = ltCustomerSubsDao.save(ltCustomerSubs);
	        
	        if(ltCustomerSubs != null) {
	        	status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);	
	        }else {
	        	status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);		        	
	        }
	
	        System.out.println("Subscription saved with ID: " + ltCustomerSubs.getSubsId());
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Error while saving subscription: " + e.getMessage());
	    }

	    if (ltCustomerSubs.getSubsQuantity() > 0) {
	        System.out.println("Processing subscription quantity: " + ltCustomerSubs.getSubsQuantity());
	        String endDate = "";

	        if (ltCustomerSubs.getSubscriptionType().equals(MONTHLY)) {
	            System.out.println("Subscription type is MONTHLY");
	            if (ltCustomerSubs.getStatus().equals(ACTIVE)) {  
	                System.out.println("Subscription is ACTIVE");             
	                Long deliveryCount = ltCustomerSubsDeliveriesDao.getSubsDeliveryCountBySubsId(ltCustomerSubs.getSubsId());
	                System.out.println("Existing delivery count: " + deliveryCount);
	                LtInvoices invoice = invoicesDao.getInvoicesBySubId(ltCustomerSubs.getSubsId(), ltCustomerSubs.getSupplierId());
	                System.out.println("Fetched invoice: " + invoice);

	                if (deliveryCount == null || deliveryCount < 1) {
	                    startDate = CalculateDate.utilToYYYYMMDD(ltCustomerSubs.getStartDate());
	                    System.out.println("Start date calculated: " + startDate);
	                    endDate = ltCustomerSubs.getEndDate() == null ? CalculateDate.findLast(startDate) : CalculateDate.findLastSub(startDate, CalculateDate.utilToYYYYMMDD(ltCustomerSubs.getEndDate()));
	                    System.out.println("End date calculated: " + endDate);

	                    List<Date> subscriptionDates = (ltCustomerSubs.getAlternateDay() != null && ltCustomerSubs.getAlternateDay().equals("Y")) ?
	                        CalculateAlternateDates.getAlternateDaysInDate(startDate, endDate, ltCustomerSubs.getAlternateDay()) :
	                        CalculateDate.getDaysInDate(startDate, endDate, ltCustomerSubs.getMonday(), ltCustomerSubs.getTuesday(), ltCustomerSubs.getWednesday(),
	                            ltCustomerSubs.getThursday(), ltCustomerSubs.getFriday(), ltCustomerSubs.getSaturday(), ltCustomerSubs.getSunday());
	                    System.out.println("Generated subscription dates: " + subscriptionDates);
	                    
	                    this.batchInsertForSubsDelivery(subscriptionDates, ltCustomerSubs, invoice);
	                    System.out.println("Batch insert for subscription delivery completed");
	                } else {
	                    System.out.println("Existing deliveries found, updating...");
	                    Date date = null;
	                    try {
	                        date = ltCustomerSubsDeliveriesDao.getStartDate(ltCustomerSubs.getSupplierId(), ltCustomerSubs.getSubsId(), ltCustomerSubs.getStartDate());
	                        System.out.println("Fetched start date: " + date);
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                        System.out.println("Error fetching start date: " + e.getMessage());
	                    }
	                    startDate = date != null ? CalculateDate.utilToYYYYMMDD(date) : CalculateDate.getNextDate(CalculateDate.utilToYYYYMMDD(ltCustomerSubs.getLastUpdateDate()));
	                    ltCustomerSubsDeliveriesDao.deleteExistingSubsDeliveries(ltCustomerSubs, date);
	                    System.out.println("Deleted existing subscription deliveries");
	                    
	                    endDate = ltCustomerSubs.getEndDate() == null ? CalculateDate.findLast(startDate) : CalculateDate.findLastSub(startDate, CalculateDate.utilToYYYYMMDD(ltCustomerSubs.getEndDate()));
	                    System.out.println("Updated end date: " + endDate);
	                    
	                    List<Date> subscriptionDates = (ltCustomerSubs.getAlternateDay() != null && ltCustomerSubs.getAlternateDay().equals("Y")) ?
	                        CalculateAlternateDates.getAlternateDaysInDate(startDate, endDate, ltCustomerSubs.getAlternateDay()) :
	                        CalculateDate.getDaysInDate(startDate, endDate, ltCustomerSubs.getMonday(), ltCustomerSubs.getTuesday(), ltCustomerSubs.getWednesday(),
	                            ltCustomerSubs.getThursday(), ltCustomerSubs.getFriday(), ltCustomerSubs.getSaturday(), ltCustomerSubs.getSunday());
	                    System.out.println("Re-generated subscription dates: " + subscriptionDates);
	                    
	                    this.batchInsertForSubsDelivery(subscriptionDates, ltCustomerSubs, invoice);
	                    System.out.println("Batch insert for subscription delivery completed after update");
	                }
	            }
	        } else if (ltCustomerSubs.getSubscriptionType().equals(ONCE)) {
	        	
	        	if (ltCustomerSubs.getStatus().equals(ACTIVE)) {
	        		Boolean isDeliveryAvailable = false; 
	        		try {
	        			isDeliveryAvailable = ltCustomerSubsDeliveriesDao.isDeliveryAvailable(ltCustomerSubs.getSupplierId() , ltCustomerSubs.getSubsId(), ACTIVE);
	        		} catch (Exception e) {
	        			e.printStackTrace();
	        		}
	        		
	        		if(user.getUserType().equalsIgnoreCase("SUPPLIER") &&  isDeliveryAvailable) {
	        			
	        			startDate = CalculateDate.utilToYYYYMMDD(ltCustomerSubs.getStartDate());
	        			endDate = CalculateDate.utilToYYYYMMDD(ltCustomerSubs.getEndDate());
	        			endDate = CalculateDate.findLastSub(startDate, endDate);
	        			
	        			ltCustomerSubsDeliveriesDao.updateDeliveryOnceDetails(ltCustomerSubs);
	        			
	        			LtMastUsers ltMastUser = ltMastUsersDao.getUserById(ltCustomerSubs.getUserId());
	        			//LtProducts ltProducts = ltProductsDao.getLtProductById(ltCustomerSubs.getProductId());
	        			
	        			/*
	        			notificationServiceCall.saveCustomerNotification(ltMastUser, "Hi, " + ltMastUser.getUserName() + " your subscription of " 
	        					+ ltProducts.getProductName() + " for " + ltCustomerSubs.getSubsQuantity() + " " + ltProducts.getProductUom()
	        					+ "  for date " + dateFormat.format(ltCustomerSubs.getStartDate()) + " has been approved by " + user.getUserName());
	        			*/
	        			
	        		} else if(!isDeliveryAvailable) {
	        			startDate = CalculateDate.utilToYYYYMMDD(ltCustomerSubs.getStartDate());
	        			endDate = CalculateDate.utilToYYYYMMDD(ltCustomerSubs.getEndDate());
	        			endDate = CalculateDate.findLastSub(startDate, endDate);
	        			
	        			List<Date> subscriptionDates = new ArrayList<Date>();

	        			subscriptionDates.add(ltCustomerSubs.getStartDate());
	        			LtInvoices invoice = invoicesDao.getInvoicesBySubId(ltCustomerSubs.getSubsId(), ltCustomerSubs.getSupplierId());
	        			this.batchInsertForSubsDelivery(subscriptionDates, ltCustomerSubs, invoice);
	        			
	        			LtMastUsers ltMastUser = ltMastUsersDao.getUserById(ltCustomerSubs.getUserId());
	        			//LtProducts ltProducts = ltProductsDao.getLtProductById(ltCustomerSubs.getProductId());

	        			if (ltCustomerSubs.getUserId() != ltCustomerSubs.getLastUpdatedBy()) {
	        				/*
	        				notificationServiceCall.saveCustomerNotification(ltMastUser, "Hi, " + ltMastUser.getUserName() + " your subscription of " 
	        								+ ltProducts.getProductName() + " for " + ltCustomerSubs.getSubsQuantity() + " " + ltProducts.getProductUom()
	        								+ "  for date " + dateFormat.format(ltCustomerSubs.getStartDate()) + " has been approved by " + user.getUserName());
	        				*/
	        			}
	        		}else {
	        			status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
	        			status.setData(ltCustomerSubs.getSubsId());
	        			status.setMessage("Your subscription has been already Approved");	
	        			return status;
	        		}
	        		
	        	} else if (ltCustomerSubs.getStatus().equals(PENDING)) {
	        		try {
	        			ltCustomerSubs = ltCustomerSubsDao.save(ltCustomerSubs);
	        		} catch (Exception e) {
	        			e.printStackTrace();
	        		}
	        		if (ltCustomerSubs.getSubsId() != null) {
	        			//LtProducts ltProducts = ltProductsDao.getLtProductById(ltCustomerSubs.getProductId());
	        			if (user.getUserType().equals("CUSTOMER")) {
	        				/*
	        				notificationServiceCall.saveCustomerNotification(user, "Hi, " + user.getUserName() + " you have subscribed to "
	        								+ ltProducts.getProductName() + " for " + ltCustomerSubs.getSubsQuantity() + " "
	        								+ ltProducts.getProductUom() + "  from date "+ dateFormat.format(ltCustomerSubs.getStartDate()));

	        				notificationServiceCall.saveSupplierNotification(ltCustomerSubs.getSupplierId(), "Hi, " + user.getUserName() + " has subscribed to " 
	        								+ ltProducts.getProductName()+ " for " + ltCustomerSubs.getSubsQuantity() + " " + ltProducts.getProductUom()
	        								+ "  from date " + dateFormat.format(ltCustomerSubs.getStartDate())+ ". Please Approve");
	        				*/
	        			}  
	        			status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
	        			status.setData(ltCustomerSubs.getSubsId());

	        		} else {
	        			status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
	        		}
	        		//this.saveMinusInvoice(ltCustomerSubs.getSubsId(), ltCustomerSubs.getSupplierId() );
	        		return status;
	        	} else if (ltCustomerSubs.getStatus().equals(REJECTED)) {
	        		ltCustomerSubsDeliveriesDao.deleteExistingSubsDeliveries(ltCustomerSubs);

	        		startDate = CalculateDate.utilToYYYYMMDD(ltCustomerSubs.getStartDate());
	        		endDate = CalculateDate.findLast(startDate);
	        		status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);

	        		LtMastUsers ltMastUser = ltMastUsersDao.getUserById(ltCustomerSubs.getUserId());
	        		//LtProducts ltProducts = ltProductsDao.getLtProductById(ltCustomerSubs.getProductId());

	        		/*
	        		notificationServiceCall.saveCustomerNotification(ltMastUser,
	        				"Hi, " + ltMastUser.getUserName() + " your subscription of " + ltProducts.getProductName()
	        						+ " for " + ltCustomerSubs.getSubsQuantity() + " " + ltProducts.getProductUom()
	        						+ "  from date " + dateFormat.format(ltCustomerSubs.getStartDate())
	        						+ " has been rejected by " + user.getUserName() + " , Please contact us for further assistance.");
	        		*/

	        		ltCustomerSubs.setSubsDeliveryDate(new SimpleDateFormat("yyyy-MM-dd").parse(endDate));
	        		try {
	        			ltCustomerSubs = ltCustomerSubsDao.save(ltCustomerSubs);
	        		} catch (Exception e) {
	        			e.printStackTrace();
	        		}
	        		if (ltCustomerSubs.getSubsId() != null) {
	        			status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
	        			status.setData(ltCustomerSubs.getSubsId());
	        			status.setMessage("Subscription has been Rejected");
	        		} else {
	        			status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
	        		}
	        		//this.saveMinusInvoice(ltCustomerSubs.getSubsId(), ltCustomerSubs.getSupplierId());
	        		return status;
	        	} else if (ltCustomerSubs.getStatus().equals(CANCELLED)) {
	        		ltCustomerSubsDeliveriesDao.deleteExistingSubsDeliveries(ltCustomerSubs);
	        		try {
	        			ltCustomerSubs = ltCustomerSubsDao.save(ltCustomerSubs);
	        		} catch (Exception e) {
	        			e.printStackTrace();
	        		}
	        		if (ltCustomerSubs != null) {
	        			status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
	        			//LtProducts ltProducts = ltProductsDao.getLtProductById(ltCustomerSubs.getProductId());
	        			if (user.getUserType().equals("CUSTOMER")) {
	        				/*
	        				notificationServiceCall.saveSupplierNotification(ltCustomerSubs.getSupplierId(),
	        						"Hi, " + user.getUserName() + " has cancelled their subscription of "
	        								+ ltProducts.getProductName() + " from "+ dateFormat.format(ltCustomerSubs.getLastUpdateDate()));
	        				*/
	        			} else {
	        				LtMastUsers ltMastUser = ltMastUsersDao.getUserById(ltCustomerSubs.getUserId());
	        				/*
	        				notificationServiceCall.saveCustomerNotification(ltMastUser,
	        						"Hi, " + ltMastUser.getUserName() + " your subscription of "+ ltProducts.getProductName() + " for " + ltCustomerSubs.getSubsQuantity() + " "
	        								+ ltProducts.getProductUom() + "  for date "+ dateFormat.format(ltCustomerSubs.getStartDate()) + " has been cancelled by "
	        								+ user.getUserName() + " , Please contact us for further assistance.");
	        				*/
	        			}
	        		} else {
	        			status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
	        		}
	        		//this.saveMinusInvoice(ltCustomerSubs.getSubsId(), ltCustomerSubs.getSupplierId() );
	        		return status;
	        		}
	        	}
	        	ltCustomerSubs.setSubsDeliveryDate(new SimpleDateFormat("yyyy-MM-dd").parse(endDate));
	        	try {
	        		ltCustomerSubs = ltCustomerSubsDao.save(ltCustomerSubs);
	        	} catch (Exception e) {
	        		e.printStackTrace();
	        	}
	        }
	        else if(ltCustomerSubs.getSubsQuantity() == 0 ) {
	        	try {
	        		ltCustomerSubs = ltCustomerSubsDao.save(ltCustomerSubs);
	        	} catch (Exception e) {
	        		e.printStackTrace();
	        	}
	        }

	        if (ltCustomerSubs.getSubsId() != null) {
	        	status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
	        	status.setData(ltCustomerSubs.getSubsId());
	        	//status.setMessage("Subscription has been Approved");
	        } else {
	        	status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
	        }
	        return status;
	}
	
	
//	@Override
//	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
//	public LtCustomerSubs getCustomerSubsById(Long subsId, LtMastUsers user) throws ServiceException {
//		String fileOpenPath = null;
//
//		LtCustomerSubs ltCustomerSubs = ltCustomerSubsDao.getCustomerSubsById(subsId);
//		ltCustomerSubs.setIsEditable(true);
//		
//		if(!ltCustomerSubs.getStatus().equalsIgnoreCase(PENDING)) {
//			Status status = this.timeLimitValidation(user,  ltCustomerSubs );
//			if(status != null && status.getCode() == 4) { 
//				ltCustomerSubs.setIsEditable(false);
//			}else {
//				ltCustomerSubs.setIsEditable(true);
//			}
//		}
//		if(ltCustomerSubs.getSubscriptionType().equals(ONCE) && ltCustomerSubs.getStatus().equalsIgnoreCase(ACTIVE)) {
//			  String status = ltCustomerSubsDao.getSubscriptionStatus(ltCustomerSubs.getSubsId(), ltCustomerSubs.getSupplierId());
//			  if(status.equalsIgnoreCase("DELIVERED") ) {
//				  ltCustomerSubs.setStatus(status);
//			  }
//		}
//		
//		if (ltCustomerSubs != null) {
//			fileOpenPath = ltProductsDao.getSystemValue(ltCustomerSubs.getSupplierId(), "FILE_OPEN_PATH");			
//			if (ltCustomerSubs.getProductImage() != null && !ltCustomerSubs.getProductImage().equals("")) {
//				ltCustomerSubs.setProductImage(fileOpenPath + ltCustomerSubs.getProductImage());
//			}
//		}
//		return ltCustomerSubs;
//	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public LtCustomerSubs getCustomerSubsById(Long subsId, LtMastUsers user) throws ServiceException {
	    String fileOpenPath = null;
	    
	    System.out.println("Fetching subscription for ID: " + subsId);

	    LtCustomerSubs ltCustomerSubs = ltCustomerSubsDao.getCustomerSubsById(subsId);
	    System.out.println("Retrieved subscription: " + ltCustomerSubs);

	    if (ltCustomerSubs == null) {
	        System.out.println("No subscription found for ID: " + subsId);
	        return null;
	    }

	    ltCustomerSubs.setIsEditable(true);
	    System.out.println("Set isEditable to true");

	    if (!ltCustomerSubs.getStatus().equalsIgnoreCase(PENDING)) {
	        System.out.println("Status is not PENDING, validating time limits...");
	        Status status = this.timeLimitValidation(user, ltCustomerSubs);
	        System.out.println("Time limit validation result: " + status);

	        if (status != null && status.getCode() == 4) { 
	            ltCustomerSubs.setIsEditable(false);
	            System.out.println("Set isEditable to false due to status code 4");
	        } else {
	            ltCustomerSubs.setIsEditable(true);
	            System.out.println("Set isEditable to true");
	        }
	    }

	    if (ltCustomerSubs.getSubscriptionType().equals(ONCE) && ltCustomerSubs.getStatus().equalsIgnoreCase(ACTIVE)) {
	        System.out.println("Checking subscription status for ONCE type and ACTIVE status...");
	        String status = ltCustomerSubsDao.getSubscriptionStatus(ltCustomerSubs.getSubsId(), ltCustomerSubs.getSupplierId());
	        System.out.println("Retrieved subscription status: " + status);

	        if (status.equalsIgnoreCase("DELIVERED")) {
	            ltCustomerSubs.setStatus(status);
	            System.out.println("Updated subscription status to DELIVERED");
	        }
	    }

	    if (ltCustomerSubs != null) {
	        System.out.println("Fetching FILE_OPEN_PATH...");
//	        fileOpenPath = ltProductsDao.getSystemValue(ltCustomerSubs.getSupplierId(), "FILE_OPEN_PATH");
	        fileOpenPath = "http://3.109.247.56:9092/ratibz/";

	        System.out.println("Retrieved FILE_OPEN_PATH: " + fileOpenPath);

	        if (ltCustomerSubs.getProductImage() != null && !ltCustomerSubs.getProductImage().equals("")) {
	            ltCustomerSubs.setProductImage(fileOpenPath+ltCustomerSubs.getProductImage());
	            System.out.println("Updated product image path: " + ltCustomerSubs.getProductImage());
	        }
	    }

	    System.out.println("Returning subscription: " + ltCustomerSubs);
	    return ltCustomerSubs;
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public List<LtCustomerSubs> getAllActiveSubs(Long userId, String subType) throws ServiceException {
		String fileOpenPath = null;
		List<LtCustomerSubs> ltCustomerSubsList = ltCustomerSubsDao.getAllActiveSubs(userId, subType);
		if (ltCustomerSubsList != null && !ltCustomerSubsList.isEmpty()) {	
//			fileOpenPath = ltProductsDao.getSystemValue(ltCustomerSubsList.get(0).getSupplierId(), "FILE_OPEN_PATH");
			fileOpenPath = "http://3.109.247.56:9092/ratibz/";

			
			for (LtCustomerSubs ltCustomerSubs : ltCustomerSubsList) {
				if (ltCustomerSubs.getProductImage() != null && !ltCustomerSubs.getProductImage().equals("")) {
					ltCustomerSubs.setProductImage(fileOpenPath + ltCustomerSubs.getProductImage());
				}
			}
		}
		return ltCustomerSubsList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public List<LtCustomerSubs> getAllActiveSubs_v1(Long userId, String subType) throws ServiceException {
		String fileOpenPath = null;
		List<LtCustomerSubs> ltCustomerSubsList = ltCustomerSubsDao.getAllActiveSubs_v1(userId, subType);
		if (ltCustomerSubsList != null && !ltCustomerSubsList.isEmpty()) {	
//			fileOpenPath = ltProductsDao.getSystemValue(ltCustomerSubsList.get(0).getSupplierId(), "FILE_OPEN_PATH");
			fileOpenPath = "http://3.109.247.56:9092/ratibz/";
			
			for (LtCustomerSubs ltCustomerSubs : ltCustomerSubsList) {
				if (ltCustomerSubs.getProductImage() != null && !ltCustomerSubs.getProductImage().equals("")) {
					ltCustomerSubs.setProductImage(fileOpenPath + ltCustomerSubs.getProductImage());
				}
			}
		}
		return ltCustomerSubsList;
	}
	

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public List<LtCustomerSubs> getSubscriptionProduct(Long userId, Long supplierId) throws ServiceException {
		return ltCustomerSubsDao.getSubscriptionProduct(userId, supplierId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public Status delete(Long subsId) throws ServiceException {
		Status status = new Status();
		LtCustomerSubs ltCustomerSubs = ltCustomerSubsDao.delete(subsId);
		if (ltCustomerSubs == null) {
			status = ltMastCommonMessageService.getCodeAndMessage(DELETE_SUCCESSFULLY);
		} else {
			status = ltMastCommonMessageService.getCodeAndMessage(DELETE_FAIL);
		}
		return status;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public LtCustomerSubsDeliveries getSubsDeliveryById(Long deliveryId) throws ServiceException {
		String fileOpenPath = null;
		
		LtCustomerSubsDeliveries customerSubsDeliveries = ltCustomerSubsDeliveriesDao.getSubsDeliveryById(deliveryId);
		if (customerSubsDeliveries != null) {
//			fileOpenPath = ltProductsDao.getSystemValue(customerSubsDeliveries.getSupplierId(), "FILE_OPEN_PATH");
			fileOpenPath = "http://3.109.247.56:9092/ratibz/";

			customerSubsDeliveries.setProductImagePath(fileOpenPath + customerSubsDeliveries.getProductImage());
		}
		return customerSubsDeliveries;
	}

//	@Override
//	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
//	public Status updateDeliveryStatus(List<LtCustomerSubsDeliveries> customerSubsDeliverie){
//		try {
//			
//			for(LtCustomerSubsDeliveries deliveries : customerSubsDeliverie ) {
//				LtCustomerSubsDeliveries subsDeliveries = null;
//				if(deliveries.getStatus().equalsIgnoreCase("CANCELLED")) {
//					subsDeliveries = ltCustomerSubsDeliveriesDao.getSubsDeliveryById(deliveries.getDeliveryId(),  deliveries.getSupplierId());
//					orderHistoryService.saveOrderHistoryDeliveryCancelled(subsDeliveries, null);
//					if(subsDeliveries.getStatus().equalsIgnoreCase("ACTIVE")) {
//						subsDeliveries.setDeliveryQuantity(subsDeliveries.getSubsQuantity());
//					}
//				}
//				
//				ltCustomerSubsDeliveriesDao.updateDeliveryStatus(deliveries);
//				
//				if(deliveries.getStatus().equalsIgnoreCase("DELIVERED") ) {
//					subsDeliveries = ltCustomerSubsDeliveriesDao.getSubsDeliveryById(deliveries.getDeliveryId(),  deliveries.getSupplierId());	
//				}
//				
//				deliveries.setUserId(subsDeliveries.getUserId() );
//				deliveries.setProductId(subsDeliveries.getProductId());
//				deliveries.setDeliveryQuantity(subsDeliveries.getDeliveryQuantity());
//				deliveries.setDeliveryDate(subsDeliveries.getDeliveryDate());
//				deliveries.setSubsQuantity(subsDeliveries.getSubsQuantity());
//				deliveries.setContainersCollected(0l);
//				this.sendUpdateDeliveryNotification(deliveries);
//				//this.saveMinusInvoice(subsDeliveries.getSubsId(), subsDeliveries.getSupplierId());
//			}
//			return  ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);	
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		return  ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL); 
//	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public Status updateDeliveryStatus(List<LtCustomerSubsDeliveries> customerSubsDeliverie) {
	    try {
	        System.out.println("Starting updateDeliveryStatus for size: " + customerSubsDeliverie.size());

	        for (LtCustomerSubsDeliveries deliveries : customerSubsDeliverie) {
	            System.out.println("Processing Delivery ID: " + deliveries.getDeliveryId() + ", Status: " + deliveries.getStatus());

	            LtCustomerSubsDeliveries subsDeliveries = null;

	            if ("CANCELLED".equalsIgnoreCase(deliveries.getStatus())) {
	                subsDeliveries = ltCustomerSubsDeliveriesDao.getSubsDeliveryById(deliveries.getDeliveryId(), deliveries.getSupplierId());
	                System.out.println("Fetched delivery for CANCELLED status: " + subsDeliveries);

	                orderHistoryService.saveOrderHistoryDeliveryCancelled(subsDeliveries, null);
	                System.out.println("Saved order history for CANCELLED");

	                if ("ACTIVE".equalsIgnoreCase(subsDeliveries.getStatus())) {
	                    subsDeliveries.setDeliveryQuantity(subsDeliveries.getSubsQuantity());
	                    System.out.println("Set delivery quantity to subs quantity: " + subsDeliveries.getDeliveryQuantity());
	                }
	            }

	            ltCustomerSubsDeliveriesDao.updateDeliveryStatus(deliveries);
	            System.out.println("Updated delivery status in DB.");

	            if ("DELIVERED".equalsIgnoreCase(deliveries.getStatus())) {
	                subsDeliveries = ltCustomerSubsDeliveriesDao.getSubsDeliveryById(deliveries.getDeliveryId(), deliveries.getSupplierId());
	                System.out.println("Fetched delivery for DELIVERED status: " + subsDeliveries);
	            }

	            if (subsDeliveries != null) {
	                deliveries.setUserId(subsDeliveries.getUserId());
	                deliveries.setProductId(subsDeliveries.getProductId());
	                deliveries.setDeliveryQuantity(subsDeliveries.getDeliveryQuantity());
	                deliveries.setDeliveryDate(subsDeliveries.getDeliveryDate());
	                deliveries.setSubsQuantity(subsDeliveries.getSubsQuantity());
	                deliveries.setContainersCollected(0L);

	                System.out.println("Calling sendUpdateDeliveryNotification for delivery ID: " + deliveries.getDeliveryId());
	                this.sendUpdateDeliveryNotification(deliveries);
	                // System.out.println("Calling saveMinusInvoice...");
	                // this.saveMinusInvoice(subsDeliveries.getSubsId(), subsDeliveries.getSupplierId());
	            } else {
	                System.out.println("subsDeliveries is null for delivery ID: " + deliveries.getDeliveryId());
	            }
	        }

	        System.out.println("All deliveries processed successfully.");
	        return ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);

	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Exception occurred in updateDeliveryStatus()");
	    }

	    return ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
	}

	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public Status updateDeliveryOnce(LtCustomerSubsDeliveries customerSubs, LtMastUsers user) {
		try {
			ltCustomerSubsDeliveriesDao.updateDeliveryOnceStatus(customerSubs, user);
			
			if(user.getUserType().equalsIgnoreCase("CUSTOMER")) {
				String productName = ltCustomerSubsDao.getProductNameBySubId(customerSubs.getSubsId());
				notificationServiceCall.saveSupplierNotification(user.getSupplierId(),
					"Hi, " + user.getUserName() + " has cancelled their deliver once order of "
				          + productName + " on "+ dateFormat.format( UtilsMaster.getCurrentDate()) +".");
			}
			return  ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);	
		}catch(Exception e) {
			e.printStackTrace();
		}
		return  ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL); 
	}
	
	
//	private Status sendUpdateDeliveryNotification(LtCustomerSubsDeliveries ltCustomerSubsDeliveries) {
//		Status status = null;
//		try {
//			LtMastUsers ltMastUser = ltMastUsersDao.getUserById(ltCustomerSubsDeliveries.getLastUpdatedBy());
//			
//			if (ltCustomerSubsDeliveries.getStatus().equalsIgnoreCase("DELIVERED")) {
//				//LtProducts ltProducts = ltProductsDao.getLtProductById(ltCustomerSubsDeliveries.getProductId());
//				Optional<LtProducts> productOp  = productsRepository.findById(ltCustomerSubsDeliveries.getProductId());
//				LtProducts ltProducts  = productOp.get();
//				String mesage = "Hi, Delivered Qty " + ltCustomerSubsDeliveries.getDeliveryQuantity() +" "+ ltProducts.getProductUom()
//								+", Collected Qty "+ltCustomerSubsDeliveries.getContainersCollected() +" "+ ltProducts.getProductUom()
//				                +" of "+ltProducts.getProductName() + " on date "+ dateFormat.format(ltCustomerSubsDeliveries.getDeliveryDate());
//				
//				LtMastUsers ltMastCustomerUser = ltMastUsersDao.getUserById(ltCustomerSubsDeliveries.getUserId());
//				
//				notificationServiceCall.saveCustomerNotification(ltMastCustomerUser, mesage);
//			
//				
//				if (ltMastUser.getUserType().equals("DELIVERYSUPERVISOR")) {						
//					ltMastCustomerUser.setUserId( ltMastCustomerUser.getSupplierId());						
//					notificationServiceCall.saveSupplierNotification(ltMastUser,
//							"Hi, " + ltCustomerSubsDeliveries.getDeliveryQuantity() +" "+ltProducts.getProductUom()+ "s of "
//									+ ltProducts.getProductName() + " has been delivered to "
//									+ ltMastCustomerUser.getUserName());
//				}
//				status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
//				status.setData(ltCustomerSubsDeliveries.getDeliveryId());					
//			} else if (ltCustomerSubsDeliveries.getStatus().equalsIgnoreCase("CANCELLED")) {
//				//LtProducts ltProducts = ltProductsDao.getLtProductById(ltCustomerSubsDeliveries.getProductId());
//				Optional<LtProducts> productOp  = productsRepository.findById(ltCustomerSubsDeliveries.getProductId());
//				LtProducts ltProducts  = productOp.get();
//				LtMastUsers ltMastCustomerUser = ltMastUsersDao.getUserById(ltCustomerSubsDeliveries.getUserId());
//				if (ltMastUser.getUserType().equals("DELIVERYSUPERVISOR")) {						
//					notificationServiceCall.saveSupplierNotification(ltMastUser,
//							"Hi, " + ltCustomerSubsDeliveries.getSubsQuantity() +" "+ltProducts.getProductUom()+ "s of "
//									+ ltProducts.getProductName() + " has been cancelled for "
//									+ ltMastCustomerUser.getUserName());
//				}
//				
//				notificationServiceCall.saveCustomerNotification(ltMastCustomerUser,
//						"Hi, " + ltCustomerSubsDeliveries.getSubsQuantity() +" "+ltProducts.getProductUom()+ "s of "
//								+ ltProducts.getProductName() + " has been cancelled");
//				status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
//				status.setData(ltCustomerSubsDeliveries.getDeliveryId());
//			}else {
//				status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);	
//			}
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		return status;
//	}
	
//	private Status sendUpdateDeliveryNotification(LtCustomerSubsDeliveries ltCustomerSubsDeliveries) {
//	    Status status = null;
//	    try {
//	        LtMastUsers ltMastUser = ltMastUsersDao.getUserById(ltCustomerSubsDeliveries.getLastUpdatedBy());
//
//	        if (ltCustomerSubsDeliveries.getStatus().equalsIgnoreCase("DELIVERED")) {
//	            Optional<LtProducts> productOp = productsRepository.findById(ltCustomerSubsDeliveries.getProductId());
//	            System.out.println("productOp ="+productOp);
//	            if (productOp.isPresent()) {
//	                LtProducts ltProducts = productOp.get();
//	                status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
//	                status.setData(ltCustomerSubsDeliveries.getDeliveryId());
//	            } else {
//	                status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
//	            }
//
//	        } else if (ltCustomerSubsDeliveries.getStatus().equalsIgnoreCase("CANCELLED")) {
//	            Optional<LtProducts> productOp = productsRepository.findById(ltCustomerSubsDeliveries.getProductId());
//	            if (productOp.isPresent()) {
//	                status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
//	                status.setData(ltCustomerSubsDeliveries.getDeliveryId());
//	            } else {
//	                status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
//	            }
//
//	        } else {
//	            status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
//	        }
//
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
//	    }
//
//	    return status;
//	}

	private Status sendUpdateDeliveryNotification(LtCustomerSubsDeliveries ltCustomerSubsDeliveries) {
	    Status status = null;
	    try {
	        System.out.println("Fetching user by ID: " + ltCustomerSubsDeliveries.getLastUpdatedBy());
	        LtMastUsers ltMastUser = ltMastUsersDao.getUserById(ltCustomerSubsDeliveries.getLastUpdatedBy());
	        System.out.println("Fetched User: " + ltMastUser);

	        String statusType = ltCustomerSubsDeliveries.getStatus();
	        System.out.println("Delivery Status: " + statusType);

	        Optional<LtProducts> productOp = productsRepository.findById(ltCustomerSubsDeliveries.getProductId());
	        System.out.println("Product Optional: " + productOp);

	        if (statusType.equalsIgnoreCase("DELIVERED") || statusType.equalsIgnoreCase("CANCELLED")) {
	            if (productOp.isPresent()) {
	                System.out.println("Product found, returning success message.");
	                status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
	                status.setData(ltCustomerSubsDeliveries.getDeliveryId());
	            } else {
	                System.out.println("Product not found, returning fail message.");
	                status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
	            }
	        } else {
	            System.out.println("Unhandled status: " + statusType + ", returning fail message.");
	            status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Exception occurred in sendUpdateDeliveryNotification()");
	        status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
	    }

	    return status;
	}

	
	
//	@Override
//	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
//	public Status updateDelivery(List<LtCustomerSubsDeliveries> ltCustomerSubsDeliveriesList) throws ServiceException {
//		Status status = new Status();
//		for (LtCustomerSubsDeliveries ltCustomerSubsDeliveries : ltCustomerSubsDeliveriesList) {
//			ltCustomerSubsDeliveries.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
//			ltCustomerSubsDeliveries.setLastUpdatedBy(ltCustomerSubsDeliveries.getLastUpdateLogin());
//			Long containersCollected = ltCustomerSubsDeliveries.getContainersCollected();
//
//			LtCustomerSubsDeliveries subsDeliveries = null;
//			try {
//				subsDeliveries = ltCustomerSubsDeliveriesDao.getSubsDeliveryById(ltCustomerSubsDeliveries.getDeliveryId(),  ltCustomerSubsDeliveries.getSupplierId());		
//				System.out.println("subsDeliveries ="+subsDeliveries);
//				if(subsDeliveries == null) {
//					status.setMessage("Supplier id and delivery id does not match.");
//					status.setCode(501);
//					System.out.println("Status1="+status);
//					return status;
//				}
//			}catch(Exception e) {
//				e.printStackTrace();
//				status.setMessage("Supplier id and delivery id does not match.");
//				status.setCode(501);
//				System.out.println("Status2="+status);
//				return status;
//			}
//			
//			if(ltCustomerSubsDeliveries.getStatus().equals("CANCELLED")) {
//				subsDeliveries.setDeliveryQuantity(0d);
//			}else {
//				subsDeliveries.setDeliveryQuantity(ltCustomerSubsDeliveries.getDeliveryQuantity());
//			}
//
//			subsDeliveries.setStatus(ltCustomerSubsDeliveries.getStatus());
//			subsDeliveries.setDeliveryDate(ltCustomerSubsDeliveries.getDeliveryDate());
//			subsDeliveries.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
//			subsDeliveries.setLastUpdateLogin(ltCustomerSubsDeliveries.getLastUpdateLogin());
//			subsDeliveries.setLastUpdatedBy(ltCustomerSubsDeliveries.getLastUpdateLogin());
//			
//			if (subsDeliveries.getInvoiceRate() == null) {
//				//LtProducts ltProducts = ltProductsDao.getLtProductById(subsDeliveries.getProductId());
//				Optional<LtProducts> productOp  = productsRepository.findById(subsDeliveries.getProductId());
//				LtProducts ltProducts  = productOp.get();
//				System.out.println("ltProducts ="+ltProducts);
//				if (ltProducts != null) {
//					subsDeliveries.setInvoiceRate(ltProducts.getProductRate());
//				} else {
//					subsDeliveries.setInvoiceRate(0D);
//				}
//			}
//			
//			ltCustomerSubsDeliveries = ltCustomerSubsDeliveriesDao.save(subsDeliveries);
//			
//			if(containersCollected == null ) {
//				ltCustomerSubsDeliveries.setContainersCollected(0l);
//			}else {
//				ltCustomerSubsDeliveries.setContainersCollected(containersCollected);
//			}
//			
//			LtCustomerContCollections ltCustomerContCollections = null ; 
//			try {
//				ltCustomerContCollections  = ltCustomerContCollectionsDao.getByDeliveryIdAndSupplierId(
//						ltCustomerSubsDeliveries.getDeliveryId(), ltCustomerSubsDeliveries.getSupplierId());
//			} catch (Exception e) {
//				e.printStackTrace();
//			} 
//			
//			if(ltCustomerContCollections != null &&  ltCustomerContCollections.getContCollectionId() != null) {
//				ltCustomerContCollections.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
//				ltCustomerContCollections.setDeliveryQuantity(ltCustomerSubsDeliveries.getDeliveryQuantity());
//				ltCustomerContCollections.setStatus(ltCustomerSubsDeliveries.getStatus());
//				ltCustomerContCollections.setLastUpdateLogin(ltCustomerSubsDeliveries.getLastUpdateLogin());
//				ltCustomerContCollections.setLastUpdatedBy(ltCustomerSubsDeliveries .getLastUpdatedBy());
//				ltCustomerContCollections.setCollectedQuantity(ltCustomerSubsDeliveries.getContainersCollected());
//				ltCustomerContCollections = ltCustomerContCollectionsDao.save(ltCustomerContCollections);
//			}else if (ltCustomerSubsDeliveries.getDeliveryId() != null && ltCustomerContCollections == null ) {
//				
//				if (ltCustomerSubsDeliveries.getContainersCollected() != null) {
//					ltCustomerContCollections = new LtCustomerContCollections();
//					
//					ltCustomerContCollections.setDeliveryId(ltCustomerSubsDeliveries.getDeliveryId() );
//					ltCustomerContCollections.setDeliveryQuantity(ltCustomerSubsDeliveries.getDeliveryQuantity());
//					
//					ltCustomerContCollections.setSupplierId(ltCustomerSubsDeliveries.getSupplierId());
//					ltCustomerContCollections.setSubsId(ltCustomerSubsDeliveries.getSubsId());
//					ltCustomerContCollections.setCollectionDate(ltCustomerSubsDeliveries.getDeliveryDate());
//					ltCustomerContCollections.setUserId(ltCustomerSubsDeliveries.getUserId());
//					ltCustomerContCollections.setProductId(ltCustomerSubsDeliveries.getProductId());
//					ltCustomerContCollections.setStatus(ltCustomerSubsDeliveries.getStatus());
//					ltCustomerContCollections.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
//					ltCustomerContCollections.setLastUpdateLogin(ltCustomerSubsDeliveries.getLastUpdateLogin());
//					ltCustomerContCollections.setCreatedBy(ltCustomerSubsDeliveries.getLastUpdateLogin());
//					ltCustomerContCollections.setCreationDate(UtilsMaster.getCurrentDateTime());
//					ltCustomerContCollections.setLastUpdatedBy(ltCustomerSubsDeliveries.getLastUpdateLogin());
//					ltCustomerContCollections.setCollectedQuantity(ltCustomerSubsDeliveries.getContainersCollected());
//					ltCustomerContCollections = ltCustomerContCollectionsDao.save(ltCustomerContCollections);
//				}
//			}
//			 status = this.sendUpdateDeliveryNotification(ltCustomerSubsDeliveries);
//			} 
//		System.out.println("Ststus ="+status);
//		return status;
//	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public Status updateDelivery(List<LtCustomerSubsDeliveries> ltCustomerSubsDeliveriesList) throws ServiceException {
		Status status = new Status();
		System.out.println("===== Starting updateDelivery API =====");
		
		for (LtCustomerSubsDeliveries ltCustomerSubsDeliveries : ltCustomerSubsDeliveriesList) {
			System.out.println("Processing delivery ID: " + ltCustomerSubsDeliveries.getDeliveryId());

			ltCustomerSubsDeliveries.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
			ltCustomerSubsDeliveries.setLastUpdatedBy(ltCustomerSubsDeliveries.getLastUpdateLogin());
			Long containersCollected = ltCustomerSubsDeliveries.getContainersCollected();

			LtCustomerSubsDeliveries subsDeliveries = null;
			try {
				subsDeliveries = ltCustomerSubsDeliveriesDao.getSubsDeliveryById(
					ltCustomerSubsDeliveries.getDeliveryId(),
					ltCustomerSubsDeliveries.getSupplierId()
				);
				System.out.println("Fetched Subs Delivery: " + subsDeliveries);
				if (subsDeliveries == null) {
					System.out.println("Supplier ID and Delivery ID do not match.");
					status.setMessage("Supplier id and delivery id does not match.");
					status.setCode(501);
					return status;
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Exception while fetching Subs Delivery.");
				status.setMessage("Supplier id and delivery id does not match.");
				status.setCode(501);
				return status;
			}

			if ("CANCELLED".equals(ltCustomerSubsDeliveries.getStatus())) {
				subsDeliveries.setDeliveryQuantity(0d);
				System.out.println("Status is CANCELLED, setting deliveryQuantity to 0.");
			} else {
				subsDeliveries.setDeliveryQuantity(ltCustomerSubsDeliveries.getDeliveryQuantity());
				System.out.println("Updated deliveryQuantity: " + ltCustomerSubsDeliveries.getDeliveryQuantity());
			}

			subsDeliveries.setStatus(ltCustomerSubsDeliveries.getStatus());
			subsDeliveries.setDeliveryDate(ltCustomerSubsDeliveries.getDeliveryDate());
			subsDeliveries.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
			subsDeliveries.setLastUpdateLogin(ltCustomerSubsDeliveries.getLastUpdateLogin());
			subsDeliveries.setLastUpdatedBy(ltCustomerSubsDeliveries.getLastUpdateLogin());

			if (subsDeliveries.getInvoiceRate() == null) {
				Optional<LtProducts> productOp = productsRepository.findById(subsDeliveries.getProductId());
				if (productOp.isPresent()) {
					LtProducts ltProducts = productOp.get();
					subsDeliveries.setInvoiceRate(ltProducts.getProductRate());
					System.out.println("Set invoiceRate from product: " + ltProducts.getProductRate());
				} else {
					subsDeliveries.setInvoiceRate(0D);
					System.out.println("Product not found. Set invoiceRate to 0.");
				}
			}

			ltCustomerSubsDeliveries = ltCustomerSubsDeliveriesDao.save(subsDeliveries);
			System.out.println("Saved subsDeliveries: " + ltCustomerSubsDeliveries);

			if (containersCollected == null) {
				ltCustomerSubsDeliveries.setContainersCollected(0L);
				System.out.println("containersCollected was null. Set to 0.");
			} else {
				ltCustomerSubsDeliveries.setContainersCollected(containersCollected);
				System.out.println("containersCollected: " + containersCollected);
			}

			LtCustomerContCollections ltCustomerContCollections = null;
			try {
				ltCustomerContCollections = ltCustomerContCollectionsDao.getByDeliveryIdAndSupplierId(
					ltCustomerSubsDeliveries.getDeliveryId(),
					ltCustomerSubsDeliveries.getSupplierId()
				);
				System.out.println("Fetched container collection: " + ltCustomerContCollections);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error while fetching container collection.");
			}

			if (ltCustomerContCollections != null && ltCustomerContCollections.getContCollectionId() != null) {
				ltCustomerContCollections.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
				ltCustomerContCollections.setDeliveryQuantity(ltCustomerSubsDeliveries.getDeliveryQuantity());
				ltCustomerContCollections.setStatus(ltCustomerSubsDeliveries.getStatus());
				ltCustomerContCollections.setLastUpdateLogin(ltCustomerSubsDeliveries.getLastUpdateLogin());
				ltCustomerContCollections.setLastUpdatedBy(ltCustomerSubsDeliveries.getLastUpdatedBy());
				ltCustomerContCollections.setCollectedQuantity(ltCustomerSubsDeliveries.getContainersCollected());
				ltCustomerContCollections = ltCustomerContCollectionsDao.save(ltCustomerContCollections);
				System.out.println("Updated existing container collection: " + ltCustomerContCollections);
			} else if (ltCustomerSubsDeliveries.getDeliveryId() != null && ltCustomerContCollections == null) {
				if (ltCustomerSubsDeliveries.getContainersCollected() != null) {
					ltCustomerContCollections = new LtCustomerContCollections();
					ltCustomerContCollections.setDeliveryId(ltCustomerSubsDeliveries.getDeliveryId());
					ltCustomerContCollections.setDeliveryQuantity(ltCustomerSubsDeliveries.getDeliveryQuantity());
					ltCustomerContCollections.setSupplierId(ltCustomerSubsDeliveries.getSupplierId());
					ltCustomerContCollections.setSubsId(ltCustomerSubsDeliveries.getSubsId());
					ltCustomerContCollections.setCollectionDate(ltCustomerSubsDeliveries.getDeliveryDate());
					ltCustomerContCollections.setUserId(ltCustomerSubsDeliveries.getUserId());
					ltCustomerContCollections.setProductId(ltCustomerSubsDeliveries.getProductId());
					ltCustomerContCollections.setStatus(ltCustomerSubsDeliveries.getStatus());
					ltCustomerContCollections.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
					ltCustomerContCollections.setLastUpdateLogin(ltCustomerSubsDeliveries.getLastUpdateLogin());
					ltCustomerContCollections.setCreatedBy(ltCustomerSubsDeliveries.getLastUpdateLogin());
					ltCustomerContCollections.setCreationDate(UtilsMaster.getCurrentDateTime());
					ltCustomerContCollections.setLastUpdatedBy(ltCustomerSubsDeliveries.getLastUpdateLogin());
					ltCustomerContCollections.setCollectedQuantity(ltCustomerSubsDeliveries.getContainersCollected());
					ltCustomerContCollections = ltCustomerContCollectionsDao.save(ltCustomerContCollections);
					System.out.println("Created new container collection: " + ltCustomerContCollections);
				}
			}

			System.out.println("Calling sendUpdateDeliveryNotification()...");
			status = this.sendUpdateDeliveryNotification(ltCustomerSubsDeliveries);
			System.out.println("Notification status: " + status);
		}

		System.out.println("===== End of updateDelivery API =====");
		System.out.println("Final Status: " + status);
		return status;
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public List<LtCustomerSubsDeliveries> getSubsDeliveryByCustId(Long customerId) throws ServiceException {
		return ltCustomerSubsDeliveriesDao.getSubsDeliveryByCustId(customerId);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public CustomeDataTable getDeliverysByDeliveryAgent(LtCustomerSubs customerSubs){
		CustomeDataTable customeDataTable = new CustomeDataTable();
		try {
			LtSupplierDeliveryTimings deliveryTimings = ltSupplierDeliveryTimingsDao.getNextDeliveryTiming(customerSubs.getSupplierId());
			customerSubs.setDeliveryDate(deliveryTimings.getDeliveryDate());
			customerSubs.setDeliveryTime(deliveryTimings.getDeliveryTime());
			customeDataTable.setHeader(deliveryTimings);	
			List<LtCustomerSubsDeliveries> deliveryList = ltCustomerSubsDeliveriesDao.getDeliverySummaryForDeliveryAgent(customerSubs);
		
			if (deliveryList != null && deliveryList.size() > 0) {
				customeDataTable.setRecordsTotal(Long.parseLong(deliveryList.size() + ""));
				customeDataTable.setRecordsFiltered(Long.parseLong(deliveryList.size() + ""));
				customeDataTable.setData(deliveryList);
			}
			}catch(Exception e) {
				e.printStackTrace();
			}
		return customeDataTable;
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public LtSupplierDeliveryTimings nextDeliveryTime(Long supplierId) {
		try {
			return ltSupplierDeliveryTimingsDao.getNextDeliveryTiming(supplierId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

//	@Override
//	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
//	public CustomeDataTable deliverySummary(LtCustomerSubs ltCustomerSubsDeliveries) throws ServiceException {
//		CustomeDataTable customeDataTable = new CustomeDataTable();
//		String fileOpenPath = null;
//						  
//		List<LtCustomerSubsDeliveries> deliveryList = ltCustomerSubsDeliveriesDao.deliverySummary(ltCustomerSubsDeliveries);
//
//		if (deliveryList != null && deliveryList.size() > 0) {
//			fileOpenPath = ltProductsDao.getSystemValue(ltCustomerSubsDeliveries.getSupplierId(), "FILE_OPEN_PATH");
//			for (LtCustomerSubsDeliveries ltCustomerSubsDeliverie : deliveryList) {
//				ltCustomerSubsDeliverie.setProductImagePath(fileOpenPath + ltCustomerSubsDeliverie.getProductImage());
//			}
//			customeDataTable.setRecordsTotal(Long.parseLong(deliveryList.size() + ""));
//			customeDataTable.setRecordsFiltered(Long.parseLong(deliveryList.size() + ""));
//			customeDataTable.setData(deliveryList);
//		} else {
//			LtCustomerSubs ltCustomerSubs = ltCustomerSubsDeliveriesDao.getNextDeliveryDateTime(ltCustomerSubsDeliveries.getSupplierId());
//			ltCustomerSubs.setLength(ltCustomerSubsDeliveries.getLength());
//			ltCustomerSubs.setStart(ltCustomerSubsDeliveries.getStart());
//			List<LtCustomerSubsDeliveries> tomorrowsDeliveryList = ltCustomerSubsDeliveriesDao.deliverySummary(ltCustomerSubs);
//
//			if (tomorrowsDeliveryList != null && tomorrowsDeliveryList.size() > 0) {
//				fileOpenPath = ltProductsDao.getSystemValue(ltCustomerSubsDeliveries.getSupplierId(), "FILE_OPEN_PATH");
//				
//				for (LtCustomerSubsDeliveries ltCustomerSubsDeliverie : tomorrowsDeliveryList) {
//					ltCustomerSubsDeliverie.setProductImagePath(fileOpenPath + ltCustomerSubsDeliverie.getProductImage());
//				}
//			}
//			customeDataTable.setRecordsTotal(Long.parseLong(tomorrowsDeliveryList.size() + ""));
//			customeDataTable.setRecordsFiltered(Long.parseLong(tomorrowsDeliveryList.size() + ""));
//			customeDataTable.setData(tomorrowsDeliveryList);
//		}
//		return customeDataTable;
//	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public CustomeDataTable deliverySummary(LtCustomerSubs ltCustomerSubsDeliveries) throws ServiceException {
	    System.out.println("Entering deliverySummary method...");

	    CustomeDataTable customeDataTable = new CustomeDataTable();
	    String fileOpenPath = null;

	    System.out.println("Fetching delivery list...");
	    List<LtCustomerSubsDeliveries> deliveryList = ltCustomerSubsDeliveriesDao.deliverySummary(ltCustomerSubsDeliveries);

	    if (deliveryList != null && !deliveryList.isEmpty()) {
	        System.out.println("Delivery list is not empty. Size: " + deliveryList.size());

//	        fileOpenPath = ltProductsDao.getSystemValue(ltCustomerSubsDeliveries.getSupplierId(), "FILE_OPEN_PATH");

	        fileOpenPath = "http://3.109.247.56:9092/ratibz/";
	        System.out.println("File open path: " + fileOpenPath);

	        for (LtCustomerSubsDeliveries ltCustomerSubsDeliverie : deliveryList) {
	            System.out.println("Processing delivery: " + ltCustomerSubsDeliverie);
	            ltCustomerSubsDeliverie.setProductImagePath(fileOpenPath + ltCustomerSubsDeliverie.getProductImage());
	            System.out.println("Updated product image path: " + ltCustomerSubsDeliverie.getProductImagePath());
	        }

	        customeDataTable.setRecordsTotal(Long.parseLong(deliveryList.size() + ""));
	        customeDataTable.setRecordsFiltered(Long.parseLong(deliveryList.size() + ""));
	        customeDataTable.setData(deliveryList);
	        System.out.println("Returning delivery list...");
	    } else {
	        System.out.println("No deliveries found. Fetching next delivery date time...");

	        LtCustomerSubs ltCustomerSubs = ltCustomerSubsDeliveriesDao.getNextDeliveryDateTime(ltCustomerSubsDeliveries.getSupplierId());
	        System.out.println("Next delivery date time fetched: " + ltCustomerSubs);

	        if (ltCustomerSubs == null) {
	            System.out.println("ltCustomerSubs is null! Returning empty data table.");
	            return customeDataTable;
	        }

	        ltCustomerSubs.setLength(ltCustomerSubsDeliveries.getLength());
	        ltCustomerSubs.setStart(ltCustomerSubsDeliveries.getStart());

	        System.out.println("Fetching tomorrow's delivery list...");
	        List<LtCustomerSubsDeliveries> tomorrowsDeliveryList = ltCustomerSubsDeliveriesDao.deliverySummary(ltCustomerSubs);

	        if (tomorrowsDeliveryList != null && !tomorrowsDeliveryList.isEmpty()) {
	            System.out.println("Tomorrow's delivery list found. Size: " + tomorrowsDeliveryList.size());

	            fileOpenPath = ltProductsDao.getSystemValue(ltCustomerSubsDeliveries.getSupplierId(), "FILE_OPEN_PATH");
	            System.out.println("File open path: " + fileOpenPath);

	            for (LtCustomerSubsDeliveries ltCustomerSubsDeliverie : tomorrowsDeliveryList) {
	                System.out.println("Processing tomorrow's delivery: " + ltCustomerSubsDeliverie);
	                ltCustomerSubsDeliverie.setProductImagePath(fileOpenPath + ltCustomerSubsDeliverie.getProductImage());
	                System.out.println("Updated product image path: " + ltCustomerSubsDeliverie.getProductImagePath());
	            }
	        } else {
	            System.out.println("No deliveries found for tomorrow.");
	        }

	        customeDataTable.setRecordsTotal(Long.parseLong(tomorrowsDeliveryList.size() + ""));
	        customeDataTable.setRecordsFiltered(Long.parseLong(tomorrowsDeliveryList.size() + ""));
	        customeDataTable.setData(tomorrowsDeliveryList);
	        System.out.println("Returning tomorrow's delivery list...");
	    }

	    System.out.println("Exiting deliverySummary method.");
	    return customeDataTable;
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public List<LtCustomerSubs> getAllSubs(Long supplierId) throws ServiceException {
		return ltCustomerSubsDao.getAllSubs(supplierId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public CustomeDataTable customerDeliverySummary(LtCustomerSubsDeliveries ltCustomerSubsDeliveries, String version)
			throws ServiceException {
		
		CustomeDataTable customeDataTable = new CustomeDataTable();
		try {
			String fileOpenPath = null;
			Long total = ltCustomerSubsDeliveriesDao.customerDeliverySummaryTotal(ltCustomerSubsDeliveries);
			
			List<LtCustomerSubsDeliveries> deliveryList = ltCustomerSubsDeliveriesDao.customerDeliverySummary(ltCustomerSubsDeliveries, version);
			
			//String isPrepaid = ltMastSuppliersDao.getPrepaidSupplierFlag(supplierId);			
			
			if (deliveryList != null && deliveryList.size() > 0) {
				fileOpenPath = ltProductsDao.getSystemValue(ltCustomerSubsDeliveries.getSupplierId(), "FILE_OPEN_PATH");
				for (LtCustomerSubsDeliveries ltCustomerSubsDeliverie : deliveryList) {
					ltCustomerSubsDeliverie.setProductImagePath(fileOpenPath + ltCustomerSubsDeliverie.getProductImage());
				}
			}
			
			customeDataTable.setRecordsTotal(total);
			customeDataTable.setRecordsFiltered(total);
			customeDataTable.setData(deliveryList);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return customeDataTable;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public CustomeDataTable getSubscriber(LtMastUsers user, String userType) {
		CustomeDataTable customeDataTable = new CustomeDataTable();
		try {
			//Long total = ltCustomerSubsDeliveriesDao.getSubscriberCount(user, userType);
			
			Integer total = user.getLength();
			List<LtMastUsers> users = ltCustomerSubsDeliveriesDao.getSubscribers(user, userType);
			
			if( users.size() == total ) {
				total = user.getStart() + user.getLength()  +5;
			}else {
				total = user.getStart() + users.size();
			}
		 
			total = ltCustomerSubsDeliveriesDao.getCustomerCount(user.getSupplierId());
			
			customeDataTable.setRecordsTotal(Long.parseLong(total+"") );
			customeDataTable.setRecordsFiltered(Long.parseLong(total+""));
			customeDataTable.setData(users);
			return customeDataTable;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<LtCustomerSubsDeliveries> deliverySummaryStatus(LtCustomerSubsDeliveries ltCustomerSubsDeliveries)
			throws ServiceException {
		return ltCustomerSubsDeliveriesDao.deliverySummaryStatus(ltCustomerSubsDeliveries);
		
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public List<LtCustomerSubsDeliveries> getDailySubs(LtCustomerSubsDeliveries ltCustomerSubsDeliveries)
			throws ServiceException {
		String fileOpenPath = null;
		List<LtCustomerSubsDeliveries> deliveryList = ltCustomerSubsDeliveriesDao.getDailySubs(ltCustomerSubsDeliveries);
		if (deliveryList != null && deliveryList.size() > 0) {
			fileOpenPath = ltProductsDao.getSystemValue(ltCustomerSubsDeliveries.getSupplierId(), "FILE_OPEN_PATH");
			for (LtCustomerSubsDeliveries ltCustomerSubsDeliverie : deliveryList) {
				if(ltCustomerSubsDeliverie.getStatus().equalsIgnoreCase("PAUSED") ) {
					ltCustomerSubsDeliverie.setDeliveryQuantity(0d);
					ltCustomerSubsDeliverie.setInvoiceRate(0d);
				}
				ltCustomerSubsDeliverie.setdQty(ltCustomerSubsDeliverie.getDeliveryQuantity());
				ltCustomerSubsDeliverie.setProductImagePath(fileOpenPath + ltCustomerSubsDeliverie.getProductImage());
			}
			return deliveryList;
		}else {
			deliveryList = ltCustomerSubsDeliveriesDao.getVacationId(ltCustomerSubsDeliveries);
			if(!deliveryList.isEmpty()) {
			return deliveryList.stream().map(obj -> { obj.setStatus("PAUSED"); obj.setdQty(0d); return obj; }).collect(Collectors.toList());
			}else {
				return deliveryList;	
			}
			
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public Status updateQuantity(LtCustomerSubsDeliveries ltCustomerSubsDeliveries) throws ServiceException {
		Status status =  null ;
		
		Optional<LtCustomerSubsDeliveries> opSubsDeliveries = ltCustomerSubsDeliveriesRepository.findById(ltCustomerSubsDeliveries.getDeliveryId());		
		LtCustomerSubsDeliveries subsDeliveries = opSubsDeliveries.get();
		if (subsDeliveries != null) {
			subsDeliveries.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
			subsDeliveries.setLastUpdatedBy(ltCustomerSubsDeliveries.getLastUpdateLogin());
			
			

			status = this.timeLimitValidation(subsDeliveries);
			if (status != null && status.getCode() != 200 ) {
				return status;
			}
			Optional<LtProducts> productOp  = productsRepository.findById(subsDeliveries.getProductId());
			LtProducts ltProducts  = productOp.get();
			status = new Status();
			String isPrepaid = mastSuppliersDao.getPrepaidSupplierFlag(subsDeliveries.getSupplierId());
			
			//orderHistoryService.saveOrderHistoryWalletBalanceAdded(walletBalance.getRemark() , walletBalance.getAmount()
			//  , walletBalance.getCreatedBy(), walletBalance.getLastUpdatedLogin(), user.getUserId() ,user.getSupplierId());

			String description = dateFormat.format(subsDeliveries.getDeliveryDate()) +", "
					+ltProducts.getProductName()+" qty "+subsDeliveries.getDeliveryQuantity();

			
			if(isPrepaid != null && isPrepaid.equalsIgnoreCase("Y") 
					         && subsDeliveries.getDeliveryQuantity() >  ltCustomerSubsDeliveries.getDeliveryQuantity() ) {
				   
				Double qty = subsDeliveries.getDeliveryQuantity() -  ltCustomerSubsDeliveries.getDeliveryQuantity();    
				orderHistoryService.saveOrderHistoryWalletBalanceAdded( description , qty * subsDeliveries.getInvoiceRate() ,
						subsDeliveries.getCreatedBy(),  subsDeliveries.getLastUpdateLogin(), subsDeliveries.getUserId(), subsDeliveries.getSupplierId());
				
			}else if(isPrepaid != null && isPrepaid.equalsIgnoreCase("PDO") 
					    && subsDeliveries.getDeliveryQuantity() >  ltCustomerSubsDeliveries.getDeliveryQuantity()  ) {
				
				Double qty = subsDeliveries.getDeliveryQuantity() -  ltCustomerSubsDeliveries.getDeliveryQuantity();
				orderHistoryService.saveOrderHistoryWalletBalanceAdded( description, qty * subsDeliveries.getInvoiceRate(), 
						 subsDeliveries.getCreatedBy(),subsDeliveries.getLastUpdateLogin(), subsDeliveries.getUserId(), subsDeliveries.getSupplierId());
			}
			
			if(ltCustomerSubsDeliveries.getDeliveryQuantity()==null) {
				subsDeliveries.setDeliveryQuantity(ltCustomerSubsDeliveries.getSubsQuantity());
			}else {
				subsDeliveries.setDeliveryQuantity(ltCustomerSubsDeliveries.getDeliveryQuantity());
			}
			

			orderHistoryService.saveUpdatedQuantity(description, subsDeliveries.getUserId(), subsDeliveries.getSupplierId());
			
			subsDeliveries = ltCustomerSubsDeliveriesDao.save(subsDeliveries);
			
			if (subsDeliveries.getDeliveryId() != null) {
				status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
				status.setData(ltCustomerSubsDeliveries.getDeliveryId());
				
				LtMastUsers ltMastUser = ltMastUsersDao.getUserById(subsDeliveries.getUserId());
				
				if (ltCustomerSubsDeliveries.getStatus() == CANCELLED) {
					notificationServiceCall.saveCustomerNotification(ltMastUser,
							"Hi, " + ltMastUser.getUserName() + " You delivery for subscription "
									+ ltProducts.getProductName() + " for " + subsDeliveries.getDeliveryQuantity()
									+ " has been cancelled");
				} else if (ltCustomerSubsDeliveries.getStatus() == "DELIVERED") {
					notificationServiceCall.saveCustomerNotification(ltMastUser,
							"Hi, " + ltMastUser.getUserName() + " You delivery for subscription "
									+ ltProducts.getProductName() + " for " + subsDeliveries.getDeliveryQuantity()
									+ "  has been modified & delivered.");
				} else {
					List<LtMastUsers> ltMastUserSupplier = ltMastUsersDao.getUserByType("SUPPLIER", subsDeliveries.getSupplierId());
					notificationServiceCall.saveSupplierNotification(ltMastUserSupplier.get(0),
							"Hi, " + ltMastUser.getUserName() + " has updated their subscription of "
									+ ltProducts.getProductName() + " for " + ltCustomerSubsDeliveries.getSubsQuantity()
									+ " " + ltProducts.getProductUom() + " for "+ dateFormat.format(subsDeliveries.getDeliveryDate()));
				}
			} else {
				status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
			}
			return status;
		}
		return status;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public Status updateVacation(LtCustomerSubs ltCustomerSubs, LtMastUsers loginUser) throws ServiceException {
		return vacationService.updateVacation(ltCustomerSubs, loginUser);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public LtVacationPeriod getVacation(Long vacationId) throws ServiceException {
		return ltCustomerSubsDeliveriesDao.getVacation(vacationId);
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public List<LtCustomerSubs> getAllActiveSubsByUser(Long userId) throws ServiceException {
		String fileOpenPath = null;
		List<LtCustomerSubs> ltCustomerSubsList = ltCustomerSubsDao.getAllActiveSubsByUser(userId);
		if (ltCustomerSubsList != null && !ltCustomerSubsList.isEmpty()) {
//			fileOpenPath = ltProductsDao.getSystemValue(ltCustomerSubsList.get(0).getSupplierId(), "FILE_OPEN_PATH");
			fileOpenPath = "http://3.109.247.56:9092/ratibz/";

			for (LtCustomerSubs ltCustomerSubs : ltCustomerSubsList) {
				if (ltCustomerSubs.getProductImage() != null && !ltCustomerSubs.getProductImage().equals("")) {
					ltCustomerSubs.setProductImage(fileOpenPath + ltCustomerSubs.getProductImage());
				}
			}
		}
		return ltCustomerSubsList;
	}
	

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public Status updateDeliveryQuantity(LtCustomerSubs customerSubs) {
		Status status = null;
		try {
			ltCustomerSubsDao.updateDeliveryQuantity(customerSubs);
			status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
			
			try {
				String productName = ltCustomerSubsDao.getProductNameBySubId(customerSubs.getSubsId());
				notificationServiceCall.saveCustomerNotification(customerSubs.getUserId(), customerSubs.getSupplierId(), 
						"Hi, your subscription quantity is changed to "+ customerSubs.getSubsQuantity() +" for "+ productName  +" from "+ 
				           dateFormat.format(customerSubs.getStartDate()) +" to "+ dateFormat.format(customerSubs.getEndDate()) +" by supplier." );
			} catch (ServiceException e) {
				e.printStackTrace();
			}	
			return status;
		}catch(Exception e) {
			e.printStackTrace();
		}
		status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
		return status;
	}
	
	
	public void  addSubscriptionDelivery() {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(UtilsMaster.getCurrentDateTime());
			final SimpleDateFormat dayFormat = new SimpleDateFormat("EEE");
			
		   // List<LtMastUsers> users = ltCustomerSubsDao.getUsersForAddnNewDelivery(); 
			//if(!users.isEmpty()) {
				//System.out.println( users.size() );
				for(int i = 1; i<= 60; i++ ) { //60 				
					calendar.add(Calendar.DAY_OF_YEAR, 1);
					Date date = calendar.getTime();
					String weekDay = dayFormat.format(calendar.getTime());
					String strDate = simpleDateFormat.format(date);
					//System.out.print( UtilsMaster.getCurrentDateTime());
					ltCustomerSubsDao.getSubscriptionsListByDate(date, strDate, weekDay,  null , null , null );
					//for(LtMastUsers user: users) {
						//ltCustomerSubsDao.getSubscriptionsListByDate(date, strDate, weekDay,  user.getUserId() , user.getSupplierId(), null );
					//}
				}
			//}
			
			calendar.setTime(UtilsMaster.getCurrentDateTime());
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date date = calendar.getTime();
			List<LtCustomerSubs> customerSubsList = ltCustomerSubsDao.getNewAlternateDaySubsctionList( date );
			List<LtCustomerSubs> customerSubs = new ArrayList<>();
			customerSubsList.forEach(sub -> {
				customerSubs.clear();
				customerSubs.add(sub);
				this.addDailySubscription(customerSubs, sub.getStartDate()); 
			});
			
			customerSubsList = ltCustomerSubsDao.getAlternateDaySubsctionList( date );
						
			Calendar rowMaxDate = new GregorianCalendar();
			rowMaxDate.add(Calendar.DAY_OF_YEAR, 61);			
			Calendar endDate = new GregorianCalendar();
			
			customerSubsList.forEach(sub -> {				
				calendar.setTime(sub.getDeliveryDate());	
				if(sub.getEndDate() != null ) {
					endDate.setTime(sub.getEndDate());
					endDate.add(Calendar.DAY_OF_YEAR, 1);
				}else {
					endDate.setTime(rowMaxDate.getTime());
				}
				
				for(int i = 0; i < 31 ;i++ ) {
					customerSubs.clear();
					calendar.add(Calendar.DAY_OF_YEAR, 2);					
					if(calendar.before(rowMaxDate) && calendar.before(endDate)){
						Date deliveryDate = calendar.getTime();
						customerSubs.add(sub);
						this.addDailySubscription(customerSubs, deliveryDate );
					}
				}
			});			 
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void addPostpaidSubscriptions() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(UtilsMaster.getCurrentDateTime());
		final SimpleDateFormat dayFormat = new SimpleDateFormat("EEE");
		for(int i = 1; i<= 30; i++ ) { //60 				
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date date = calendar.getTime();
			String weekDay = dayFormat.format(calendar.getTime());
			//System.out.println( date );
			List<LtCustomerSubs> customerSubs = ltCustomerSubsDao.getSubscriptionsByDate(date, weekDay);
			List<Long> addedSubIds =  ltCustomerSubsDao.getAddedSubIds(date);
			List<LtCustomerSubs> newDeliveryList = new ArrayList<>();
			for(LtCustomerSubs customerSub : customerSubs ) {
				if(!addedSubIds.contains(customerSub.getSubsId())) {
					System.out.print( ", "+ customerSub.getSubsId());
					newDeliveryList.add(customerSub);
				}
			}
			if(newDeliveryList.size() > 0 ) {
				this.addDailySubscription(newDeliveryList , date);
			}
		}
	}

	@Override
	public void addVacationsInDeliveryTable() {
		try {
			ltCustomerSubsDao.addVacationsToCustomerDelivery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*List<Long> deliveryIds = ltCustomerSubsDao.getDeliveryIdForVacations();
		for(Long deliveryId : deliveryIds) {
			ltCustomerSubsDao.addVacation(deliveryId);
		}*/
	}
	
	private void addDailySubscription(List<LtCustomerSubs> customerSubsList, Date date) {
		try {		
			List<LtCustomerSubsDeliveries> subsDeliveryList = new ArrayList<LtCustomerSubsDeliveries>();		
			for(LtCustomerSubs ltCustomerSubs : customerSubsList) {
					LtCustomerSubsDeliveries customerSubsDeliveries = new LtCustomerSubsDeliveries();
					customerSubsDeliveries.setSupplierId(ltCustomerSubs.getSupplierId());
					customerSubsDeliveries.setDeliveryDate(date);
					customerSubsDeliveries.setSubsId(ltCustomerSubs.getSubsId());
					customerSubsDeliveries.setUserId(ltCustomerSubs.getUserId());
					customerSubsDeliveries.setProductId(ltCustomerSubs.getProductId());
					customerSubsDeliveries.setInvoiceId(ltCustomerSubs.getInvoiceId());
					
					customerSubsDeliveries.setSubsQuantity(ltCustomerSubs.getSubsQuantity());
					customerSubsDeliveries.setDeliveryQuantity(ltCustomerSubs.getSubsQuantity());
					customerSubsDeliveries.setStatus(ltCustomerSubs.getStatus());
										
					customerSubsDeliveries.setDeliveryMode(ltCustomerSubs.getDeliveryMode());
					customerSubsDeliveries.setInvoiceRate(ltCustomerSubs.getCustomerRate());
					customerSubsDeliveries.setStartDate(ltCustomerSubs.getStartDate());
					customerSubsDeliveries.setEndDate(ltCustomerSubs.getEndDate());
					customerSubsDeliveries.setCreationDate(UtilsMaster.getCurrentDateTime());
					customerSubsDeliveries.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
					customerSubsDeliveries.setLastUpdatedBy(-1L);
					customerSubsDeliveries.setCreatedBy(-1L);
					customerSubsDeliveries.setLastUpdateLogin(-1L);
					subsDeliveryList.add(customerSubsDeliveries);
			}
			ltCustomerSubsDeliveriesDao.batchInsertForSubsDelivery(subsDeliveryList);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public int addVacationsToCustomerDelivery() throws Exception {
		return  ltCustomerSubsDao.addVacationsToCustomerDelivery();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public Status getVacationId(Long userId, Long supplierId) {
		Status status = null;
		 try {
			Long vacationId = ltCustomerSubsDao.getVacationId(userId, supplierId);
			status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
			status.setData(vacationId);
			return status;
		} catch (Exception e) {
			e.printStackTrace();
		}
		 status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
		return status;
	}

	@Override
	public List<LtCustomerSubs> getAllSubsByUserId(Long userId, String status) throws ServiceException {
		// TODO Auto-generated method stub
		try {
			List<LtCustomerSubs> list = ltCustomerSubsDao.getAllSubsByUserId(userId, status);
			for(LtCustomerSubs ltCustomerSubs:list) {
				ltCustomerSubs.setImagePath(FILE_OPEN_PATH+ltCustomerSubs.getProductImage());
			}
			return list;
		}catch(Exception ex) {
			ex.printStackTrace();
			return new ArrayList<LtCustomerSubs>();
		}
	}


/*	private void saveMinusInvoice(Long subId, Long supplierId ){
		ltCustomerSubsDeliveriesDao.addInvoiceIdInDeliveryTable();
		
		List<LtCustomerSubsDeliveries> subsDeliverieList;
		try {
			subsDeliverieList = ltCustomerSubsDeliveriesDao.getDeliveryDatailsForMinusInvoice(subId, supplierId);
			Double quantity  = 0d;
			Double invoiceAmount = 0d;
			LtCustomerSubsDeliveries subsDeliveries  = null;
			if(subsDeliverieList.isEmpty() || subsDeliverieList.get(0).getInvoiceId() == null   ) {
				// In Rejected status..
				List<LtInvoices> invoices = ltCustomerSubsDeliveriesDao.getInvoiceBySubId(subId, supplierId); 
				if(!invoices.isEmpty()) {
					LtInvoices invoice = invoices.get(0);
					subsDeliveries = new LtCustomerSubsDeliveries();
					invoiceAmount = invoice.getFinalAmount(); 
					subsDeliveries.setUserId( invoice.getUserId());
					subsDeliveries.setInvoiceId(invoice.getInvoiceId());
				}
			}else { // In Cancel status..
				subsDeliveries = subsDeliverieList.get(0);
				quantity   =   subsDeliveries.getSubsQuantity() - subsDeliveries.getDeliveryQuantity();
				invoiceAmount = quantity * subsDeliveries.getInvoiceRate();
			}
			
			
			if(subsDeliveries.getInvoiceId() != null && subsDeliveries.getInvoiceId() > 0 ) {
				List<LtInvoices> invoices = ltCustomerSubsDeliveriesDao.getMinusInvoice(subId, supplierId);
				
				LtInvoices ltInvoices = null;
				
				if(!invoices.isEmpty()) {
					ltInvoices = invoices.get(0);
				}else {
					ltInvoices = new LtInvoices();	
				}
							
				ltInvoices.setSupplierId(supplierId);
				ltInvoices.setSubsId(subId);
				ltInvoices.setUserId(subsDeliveries.getUserId());
				ltInvoices.setInvoiceNumber("INV||" + UtilsMaster.getCurrentDateTime().getTime());
				ltInvoices.setInvoiceDate(UtilsMaster.getCurrentDateTime());
				
				ltInvoices.setInvoiceAmount(-invoiceAmount);
				ltInvoices.setDiscountAmount(0D);
				ltInvoices.setFinalAmount(-invoiceAmount);
		
				ltInvoices.setStatus("PENDING");
				ltInvoices.setCreatedBy(-1L);
				ltInvoices.setCreationDate(UtilsMaster.getCurrentDateTime());
				ltInvoices.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
				ltInvoices.setLastUpdatedBy(-1L);
				ltInvoices.setLastUpdateLogin(-1L);
	
				ltInvoices = ltInvoicesDao.createInvoice(ltInvoices);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	

}
