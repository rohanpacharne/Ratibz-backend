package com.lonar.master.a2zmaster.service;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

//import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.InvoiceLinesData;
//import com.lonar.a2zcommons.model.InvoicePdfData;
//import com.lonar.a2zcommons.model.LtCustomerSubs;
//import com.lonar.a2zcommons.model.LtCustomerSubsDeliveries;
//import com.lonar.a2zcommons.model.LtInvoices;
//import com.lonar.a2zcommons.model.LtMastEmailToken;
//import com.lonar.a2zcommons.model.LtMastSuppliers;
//import com.lonar.a2zcommons.model.LtMastUsers;
//import com.lonar.a2zcommons.model.LtSubscrptionOffer;
import com.lonar.master.a2zmaster.common.BusinessException;
import com.lonar.master.a2zmaster.common.CalculateDate;
import com.lonar.master.a2zmaster.common.MailSendServiceCall;
import com.lonar.master.a2zmaster.common.NotificationServiceCall;
//import com.lonar.master.a2zmaster.common.NotificationServiceCall;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.dao.LtCustomerSubsDao;
import com.lonar.master.a2zmaster.dao.LtCustomerSubsDeliveriesDao;
import com.lonar.master.a2zmaster.dao.LtInvoicesDao;
import com.lonar.master.a2zmaster.dao.LtMastEmailTokenDao;
import com.lonar.master.a2zmaster.dao.LtMastSuppliersDao;
import com.lonar.master.a2zmaster.dao.LtMastUsersDao;
import com.lonar.master.a2zmaster.dao.LtProductsDao;
import com.lonar.master.a2zmaster.dao.LtSubscrptionOfferDao;
import com.lonar.master.a2zmaster.invoicepdf.InvoicePDFGenarate;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.InvoiceLinesData;
import com.lonar.master.a2zmaster.model.InvoicePdfData;
import com.lonar.master.a2zmaster.model.LtCustomerSubs;
//import com.lonar.master.a2zmaster.model.LtCustomerSubs;
import com.lonar.master.a2zmaster.model.LtCustomerSubsDeliveries;
import com.lonar.master.a2zmaster.model.LtInvoices;
import com.lonar.master.a2zmaster.model.LtMastEmailToken;
import com.lonar.master.a2zmaster.model.LtMastSuppliers;
import com.lonar.master.a2zmaster.model.LtMastUsers;
import com.lonar.master.a2zmaster.model.LtSubscrptionOffer;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.repository.LtMastSupplierRepositoty;
import com.lonar.master.a2zmaster.utils.UtilsMaster;

@Service
public class LtInvoicesServiceImpl implements LtInvoicesService, CodeMaster {

	DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");

	@Autowired private LtCustomerSubsDao ltCustomerSubsDao;

	@Autowired private LtInvoicesDao ltInvoicesDao;

	@Autowired private LtCustomerSubsDeliveriesDao ltCustomerSubsDeliveriesDao;

	@Autowired private LtSubscrptionOfferDao ltSubscrptionOfferDao;

	@Autowired private LtMastEmailTokenDao ltMastEmailTokenDao;

	@Autowired private LtMastSuppliersDao ltMastSuppliersDao;

	@Autowired private LtMastUsersDao ltMastUsersDao;

	@Autowired private NotificationServiceCall notificationServiceCall;

	@Autowired private MailSendServiceCall mailSendServiceCall;

	@Autowired private LtProductsDao ltProductsDao;
	
	@Autowired private LtMastSupplierRepositoty supplierRepositoty;
	
	@Autowired LtMastCommonMessageService ltMastCommonMessageService;
	
	@Override
	public Status createInvoice(Long supplierId, String invoiceCycle, String date1)
			throws ServiceException, ParseException {
		CalculateDate calculateDate = new CalculateDate();
		List<LtCustomerSubs> subsList = ltCustomerSubsDao.getAllSubsBySupplier(supplierId, invoiceCycle);
		if (invoiceCycle.toUpperCase().equals("M")) {
			for (LtCustomerSubs ltCustomerSubs : subsList) {
				LtInvoices ltInvoices = new LtInvoices();
				ltInvoices.setSupplierId(supplierId);
				ltInvoices.setUserId(ltCustomerSubs.getUserId());
				ltInvoices.setInvoiceNumber("INV||" + UtilsMaster.getCurrentDateTime().getTime());
				ltInvoices.setInvoiceDate(UtilsMaster.getCurrentDateTime());
				ltInvoices.setSubsId(ltCustomerSubs.getSubsId());
				ltInvoices.setSubsMonth(ltCustomerSubs.getCreationDate());
				if (ltCustomerSubs.getStartDate().after(getFromDeliveryDate(UtilsMaster.getCurrentDateTime()))) {
					ltInvoices.setFromDeliveryDate(ltCustomerSubs.getStartDate());
				} else {
					ltInvoices.setFromDeliveryDate(getFromDeliveryDate(UtilsMaster.getCurrentDateTime()));
				}
				ltInvoices.setToDeliveryDate(toDeliveryDate(UtilsMaster.getCurrentDateTime()));
				ltInvoices.setInvoiceAmount(calculateInvoiceAmount(ltCustomerSubs.getUserId(),
						ltInvoices.getFromDeliveryDate(), ltInvoices.getToDeliveryDate()));
				ltInvoices.setDiscountAmount(calculateDiscountAmt(ltCustomerSubs.getUserId(), ltInvoices));

				if (ltInvoices.getDiscountAmount() != null) {
					ltInvoices.setFinalAmount(ltInvoices.getInvoiceAmount() - ltInvoices.getDiscountAmount());
				} else {
					ltInvoices.setFinalAmount(ltInvoices.getInvoiceAmount());
				}
				ltInvoices.setStatus("PENDING");
				ltInvoices.setCreatedBy(-1L);
				ltInvoices.setCreationDate(UtilsMaster.getCurrentDateTime());
				ltInvoices.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
				ltInvoices = ltInvoicesDao.createInvoice(ltInvoices);
				if (ltInvoices.getInvoiceId() != null) {
					ltCustomerSubsDeliveriesDao.updateInvoiceId(ltInvoices);
				}
				makeEntryInEmailTemplate("invoiceCreation", ltInvoices);
			}

		} else {
			Date date = UtilsMaster.getCurrentDateTime();
			for (LtCustomerSubs ltCustomerSubs : subsList) {
				LtInvoices ltInvoices = new LtInvoices();
				ltInvoices.setSupplierId(supplierId);
				ltInvoices.setUserId(ltCustomerSubs.getUserId());
				ltInvoices.setInvoiceNumber("INV" + UtilsMaster.getCurrentDateTime().getTime());
				ltInvoices.setInvoiceDate(UtilsMaster.getCurrentDateTime());
				ltInvoices.setSubsId(ltCustomerSubs.getSubsId());
				ltInvoices.setSubsMonth(ltCustomerSubs.getCreationDate());
				ltInvoices.setFromDeliveryDate(new SimpleDateFormat("yyyy-MM-dd")
						.parse(calculateDate.lastMondayDate(calculateDate.utilToYYYYMMDD(date))));
				ltInvoices.setToDeliveryDate(new SimpleDateFormat("yyyy-MM-dd")
						.parse(calculateDate.lastSundayDate(calculateDate.utilToYYYYMMDD(date))));
				ltInvoices.setInvoiceAmount(calculateInvoiceAmount(ltCustomerSubs.getSubsId(),
						ltInvoices.getFromDeliveryDate(), ltInvoices.getToDeliveryDate()));
				ltInvoices.setDiscountAmount(calculateDiscountAmt(ltCustomerSubs.getUserId(), ltInvoices));
				if (ltInvoices.getDiscountAmount() != null) {
					ltInvoices.setFinalAmount(ltInvoices.getInvoiceAmount() - ltInvoices.getDiscountAmount());
				} else {
					ltInvoices.setFinalAmount(ltInvoices.getInvoiceAmount());
				}
				ltInvoices.setStatus("PENDING");
				ltInvoices.setCreatedBy(-1L);
				ltInvoices.setCreationDate(UtilsMaster.getCurrentDateTime());
				ltInvoices.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
				ltInvoices = ltInvoicesDao.createInvoice(ltInvoices);
				if (ltInvoices.getInvoiceId() != null) {
					ltCustomerSubsDeliveriesDao.updateInvoiceId(ltInvoices);
				}
				makeEntryInEmailTemplate("invoiceCreation", ltInvoices);
			}
		}
		return null;
	}

	private void makeEntryInEmailTemplate(String templateName, LtInvoices ltInvoices) throws ServiceException {
		LtMastUsers ltMastUsers = ltInvoicesDao.getUserMailById(ltInvoices.getUserId());
		LtMastSuppliers ltMastSupplier = ltMastSuppliersDao.getLtMastSuppliersById(ltInvoices.getSupplierId());
		if (ltMastUsers != null) {
			LtMastEmailToken ltMastEmailToken = new LtMastEmailToken();
			ltMastEmailToken.setSendTo(ltMastUsers.getEmail());
			ltMastEmailToken.setEmailStatus("SENDING");
			ltMastEmailToken.setEmailTitle(ltMastSupplier.getSupplierName() + " Invoice for "
					+ dateFormat.format(ltInvoices.getFromDeliveryDate()) + " To "
					+ dateFormat.format(ltInvoices.getToDeliveryDate()));
			ltMastEmailToken.setEmailTemplate(templateName);
			ltMastEmailToken.setExpiredWithin(1296000L);
			ltMastEmailToken.setSendDate(UtilsMaster.getCurrentDateTime());
			ltMastEmailToken.setFailureCount(0L);
			ltMastEmailToken.setTransactionId(1L);
			ltMastEmailToken.setSupplierId(ltInvoices.getSupplierId());

			ltMastEmailToken.setEmailObject("fromDate=" + dateFormat.format(ltInvoices.getFromDeliveryDate())
					+ "#$#$toDate=" + dateFormat.format(ltInvoices.getToDeliveryDate()) + "#$#$amount="
					+ ltInvoices.getFinalAmount() + "#$#$supplierName=" + ltMastSupplier.getSupplierName());

			if (ltMastEmailToken.getSendTo() != null) {
				Long emailtokenId = ltMastEmailTokenDao.makeEntryInEmailToken(ltMastEmailToken);
				if (emailtokenId != null) {
					mailSendServiceCall.callToMailService(ltMastEmailToken);
				}
			}
		}
	}

	private Date toDeliveryDate(Date date) {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE)); // changed calendar to cal

		Date lastDateOfPreviousMonth = cal.getTime();
		return lastDateOfPreviousMonth;
	}

	private Date getFromDeliveryDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DATE, 1);
		Date firstDateOfPreviousMonth = cal.getTime();
		return firstDateOfPreviousMonth;
	}

	private Double calculateDiscountAmt(Long userId, LtInvoices ltInvoices) throws ServiceException {
		Double discountAmount = 0D;
		List<LtCustomerSubs> subsList = ltCustomerSubsDao.getAllActiveSubs(userId, null);
		if (!subsList.isEmpty()) {
			for (LtCustomerSubs ltCustomerSubs : subsList) {
				if (ltCustomerSubs.getStatus().equals(ACTIVE))
					if (ltCustomerSubs.getOfferId() != null) {
						LtSubscrptionOffer ltSubscrptionOffer = ltSubscrptionOfferDao
								.getSubscrptionOfferById(ltCustomerSubs.getOfferId());
						if (ltSubscrptionOffer != null) {
							if (ltSubscrptionOffer.getStartDate().before(ltCustomerSubs.getStartDate())
									&& ltSubscrptionOffer.getEndDate().after(ltCustomerSubs.getStartDate())) {
								if (ltSubscrptionOffer.getOfferType().toUpperCase().equals("AMOUNT")) {
									discountAmount = discountAmount + ltSubscrptionOffer.getOfferValue();
								} else {
									Double invoiceAmount = calculateInvoiceAmount(ltCustomerSubs.getSubsId(),
											ltInvoices.getFromDeliveryDate(), ltInvoices.getToDeliveryDate());
									if (invoiceAmount != null && ltSubscrptionOffer.getOfferValue() != null) {
										discountAmount = discountAmount
												+ (invoiceAmount * ltSubscrptionOffer.getOfferValue()) / 100;
									}
								}

							}
						}

					}
			}

		}

		return discountAmount;
	}

	private Double calculateInvoiceAmount(Long userId, Date from, Date to) throws ServiceException {
		return ltCustomerSubsDeliveriesDao.calculateInvoiceAmount(userId, from, to);
	}

	@Override
	public List<LtInvoices> getAllInvoices(Long userId) throws ServiceException {
		return ltInvoicesDao.getAllInvoices(userId);
	}

	@Override
	public CustomeDataTable getAllInvoicesBySupplier(Long supplierId, LtInvoices ltInvoices) throws ServiceException {
		CustomeDataTable customeDataTable = new CustomeDataTable();
		String isPrepaid = ltMastSuppliersDao.getPrepaidSupplierFlag(supplierId);
		Long count = 0l;
		List<LtInvoices> invoiceList = null;
		if(isPrepaid != null && (isPrepaid.equalsIgnoreCase("Y") || isPrepaid.equalsIgnoreCase("PDO") ) ) {
			count = ltInvoicesDao.getAllPrepaidInvoicesCountBySupplier(supplierId, ltInvoices);
			invoiceList = ltInvoicesDao.getAllPrepaidInvoicesBySupplier(supplierId, ltInvoices);
		}else {
			count = ltInvoicesDao.getAllInvoicesBySupplierCount(supplierId, ltInvoices);
			invoiceList = ltInvoicesDao.getAllInvoicesBySupplier(supplierId, ltInvoices);
		}
		customeDataTable.setRecordsTotal(count);
		customeDataTable.setRecordsFiltered(count);
		customeDataTable.setData(invoiceList);
		return customeDataTable;
	}

	@Override
	public LtInvoices getInvoicesById(Long invoiceId) throws ServiceException {
		String fileOpenPath = null;
		LtInvoices ltInvoices = ltInvoicesDao.getInvoicesById(invoiceId);
		if (ltInvoices != null) {
			List<LtCustomerSubsDeliveries> deliveryList = ltInvoicesDao.getDeliveriesByInvoiceId(invoiceId);
			
			fileOpenPath = ltProductsDao.getSystemValue(ltInvoices.getSupplierId(), "FILE_OPEN_PATH");
			
			for (LtCustomerSubsDeliveries customerSubsDeliveries : deliveryList) {
				customerSubsDeliveries.setProductImagePath(fileOpenPath + customerSubsDeliveries.getProductImage());
			}
			ltInvoices.setSubsDeliveries(deliveryList);
		}
		return ltInvoices;
	}

	
	@Transactional
	@Override
	public Status create(Long supplierId , String invoiceCycle, String logTime) throws ServiceException, ParseException {
		Optional<LtMastSuppliers> opSupplier = supplierRepositoty.findById(supplierId);
		Status status = new Status();
		LtMastSuppliers supplier = opSupplier.get();
		List<LtMastUsers> usersList = ltCustomerSubsDao.getAllActiveUsersBySupplier(supplier.getSupplierId(), invoiceCycle);
		if (usersList != null) {
				for (LtMastUsers mastUser : usersList) {
					this.createOnePostpaidInvoice(mastUser, invoiceCycle);
				}
//				status.setCode(SUCCESS);
				status = ltMastCommonMessageService.getCodeAndMessage(SUCCESS);
				status.setMessage("Invoice created");
		} else {
			status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
			status.setMessage("No user found");
		}
		return status;
	}	
	
	
	@Override
	public void createPostpaidInvoice(String invoiceCycle) {
		try {
			List<LtMastUsers> usersList = ltCustomerSubsDao.getPostpaidUsersForCreateInvoice(invoiceCycle);
			if (usersList != null) {
				for (LtMastUsers mastUser : usersList) {
					this.createOnePostpaidInvoice(mastUser, invoiceCycle);
				}
			}		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	@Transactional
	private void createOnePostpaidInvoice(LtMastUsers user,  String invoiceCycle ) throws ServiceException, ParseException {
		try {
		Double invoiceAmount = 0d;
		
		Long userId = user.getUserId();
		Long supplierId = user.getSupplierId();
		LtInvoices ltInvoices = new LtInvoices();
		ltInvoices.setSupplierId(supplierId);
		ltInvoices.setUserId(userId);
		ltInvoices.setInvoiceNumber("INV||" + UtilsMaster.getCurrentDateTime().getTime());
		ltInvoices.setInvoiceDate(UtilsMaster.getCurrentDateTime());

			if (invoiceCycle.equals("D")) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, -1);
				Date pyear = cal.getTime();
				ltInvoices.setFromDeliveryDate(pyear);
				ltInvoices.setToDeliveryDate(pyear);
				System.out.println(   cal.getTime());
				if (ltInvoices.getInvoiceAmount() == null) {
					ltInvoices.setInvoiceAmount(0D);
				}
			} else if (invoiceCycle.equals("W")) {
				CalculateDate calculateDate = new CalculateDate();
				String lastMondayDate = calculateDate.lastMondayDate(calculateDate.utilToYYYYMMDD(UtilsMaster.getCurrentDateTime()));
				Date fromDate = new SimpleDateFormat("yyyy-MM-dd").parse(lastMondayDate);
				ltInvoices.setFromDeliveryDate(fromDate);
				String lastSundayDate = calculateDate.lastSundayDate(calculateDate.utilToYYYYMMDD(UtilsMaster.getCurrentDateTime()));
				Date toDate = new SimpleDateFormat("yyyy-MM-dd").parse(lastSundayDate);
				ltInvoices.setToDeliveryDate(toDate);
			} else if (invoiceCycle.equals("M")) {
				ltInvoices.setFromDeliveryDate(getFromDeliveryDate(UtilsMaster.getCurrentDateTime()));
				ltInvoices.setToDeliveryDate(toDeliveryDate(UtilsMaster.getCurrentDateTime()));
			} 

			invoiceAmount = ltCustomerSubsDeliveriesDao.calculateInvoiceAmount(userId , ltInvoices.getFromDeliveryDate(), ltInvoices.getToDeliveryDate() );
			ltInvoices.setStatus("PENDING");
		
			//System.out.println(" user.getUserId() " + user.getUserId()+ " :: "+ user.getSupplierId()+" "+ invoiceAmount);
			
			if (invoiceAmount == null) { invoiceAmount = 0D; }

			if (ltInvoices.getInvoiceAmount() == null) { 
				ltInvoices.setInvoiceAmount(0D); 
			}
			
			ltInvoices.setInvoiceAmount(ltInvoices.getInvoiceAmount() + invoiceAmount);
			
			if (ltInvoices.getDiscountAmount() == null) {
				ltInvoices.setDiscountAmount(0D);
			}
			
			if (calculateDiscountAmt(userId, ltInvoices) != null) {
				ltInvoices.setDiscountAmount( ltInvoices.getDiscountAmount() + calculateDiscountAmt(userId, ltInvoices));
			}
			
			if (ltInvoices.getDiscountAmount() != null) {
				ltInvoices.setFinalAmount(ltInvoices.getInvoiceAmount() - ltInvoices.getDiscountAmount());
			} else {
				ltInvoices.setFinalAmount(ltInvoices.getInvoiceAmount());
			}
			
			ltInvoices.setCreatedBy(-1L);
			ltInvoices.setCreationDate(UtilsMaster.getCurrentDateTime());
			ltInvoices.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
			ltInvoices.setLastUpdatedBy(-1L);
			ltInvoices.setLastUpdateLogin(-1L);
			
			if (ltInvoices.getInvoiceAmount() != null && ltInvoices.getInvoiceAmount() > 0) {
				System.out.println(" INVOICE CREATED "  +ltInvoices.getUserId());
				ltInvoices = ltInvoicesDao.createInvoice(ltInvoices);

				if (ltInvoices.getInvoiceId() != null) {
					ltCustomerSubsDeliveriesDao.updateInvoiceId(ltInvoices);
					
					notificationServiceCall.saveCustomerNotification(user, "Hi, " + user.getUserName() + " your invoice " + ltInvoices.getInvoiceNumber()
									+ ", " + dateFormat.format(ltInvoices.getInvoiceDate()) + ", " + ltInvoices.getFinalAmount() + "  has been generated");
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public LtInvoices createPrepaidDeliveryOnceInvoice(LtCustomerSubs customerSubs, String paymentStatus ) {
		try {
			LtMastUsers user = ltMastUsersDao.getUserById(customerSubs.getUserId());
			Double invoiceAmount = customerSubs.getCustomerRate() * customerSubs.getSubsQuantity();
			return this.createPrepaymentInvoice(customerSubs, invoiceAmount, user, paymentStatus );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Double calculatInvoiceAmount(LtCustomerSubs customerSubs) {
		try {
			Date strDate = customerSubs.getStartDate(); 
			Date endDate = customerSubs.getEndDate();
			int noOfDay = 0;
			
			if(customerSubs.getSubscriptionType().equalsIgnoreCase("MONTHLY")) {
				while(strDate.compareTo(endDate) <= 0 ) {				
					if(customerSubs.getAlternateDay() != null && customerSubs.getAlternateDay().equalsIgnoreCase("Y") ) {
						strDate = UtilsMaster.addDays(strDate, 2);
						++noOfDay;
					}else {
						if(customerSubs.getMonday() != null  && customerSubs.getMonday().equalsIgnoreCase("Y")) {
							++noOfDay;
						}else if(customerSubs.getTuesday() != null  && customerSubs.getTuesday().equalsIgnoreCase("Y") ) {
							++noOfDay;
						}else if(customerSubs.getWednesday() != null  && customerSubs.getWednesday().equalsIgnoreCase("Y") ) {
							++noOfDay;
						}else if(customerSubs.getThursday() != null  && customerSubs.getThursday().equalsIgnoreCase("Y") ) {
							++noOfDay;
						}else if(customerSubs.getFriday() != null  && customerSubs.getFriday().equalsIgnoreCase("Y") ) {
							++noOfDay;
						}else if(customerSubs.getSaturday() != null  && customerSubs.getSaturday().equalsIgnoreCase("Y") ) {
							++noOfDay;
						}else if(customerSubs.getSunday() != null  && customerSubs.getSunday().equalsIgnoreCase("Y") ) {
							++noOfDay;
						}
						strDate = UtilsMaster.addDays(strDate, 1);
					} 
				}
				return  noOfDay * customerSubs.getCustomerRate() * customerSubs.getSubsQuantity();
			}else {
				return  customerSubs.getCustomerRate() * customerSubs.getSubsQuantity();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0d;
	}
	
	@Override
	public LtInvoices createPrepaidInvoiceByDate(LtCustomerSubs customerSubs, String paymentStatus) {
		LtMastUsers user;
		try {
			user = ltMastUsersDao.getUserById(customerSubs.getUserId());			
			Double invoiceAmount = this.calculatInvoiceAmount(customerSubs);
			return this.createPrepaymentInvoice(customerSubs, invoiceAmount, user, paymentStatus );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	private LtInvoices createPrepaymentInvoice(LtCustomerSubs customerSubs, Double invoiceAmount, LtMastUsers user, String paymentStatus ) {
		try {
			LtInvoices ltInvoices = new LtInvoices();
			ltInvoices.setSubsId(customerSubs.getSubsId());
			ltInvoices.setSupplierId(customerSubs.getSupplierId() );
			ltInvoices.setUserId(customerSubs.getUserId() );
			ltInvoices.setInvoiceNumber("INV||" + UtilsMaster.getCurrentDateTime().getTime());
			ltInvoices.setInvoiceDate(UtilsMaster.getCurrentDateTime());
			ltInvoices.setFromDeliveryDate(customerSubs.getStartDate() );
			ltInvoices.setToDeliveryDate(customerSubs.getEndDate() );
			//ltInvoices.setStatus("PAID");
			ltInvoices.setStatus(paymentStatus);
			ltInvoices.setDiscountAmount(0D);
			ltInvoices.setInvoiceAmount(invoiceAmount);
			ltInvoices.setFinalAmount(ltInvoices.getInvoiceAmount() - ltInvoices.getDiscountAmount());
	
			ltInvoices.setCreatedBy(-1L);
			ltInvoices.setCreationDate(UtilsMaster.getCurrentDateTime());
			ltInvoices.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
			ltInvoices.setLastUpdatedBy(-1L);
			ltInvoices.setLastUpdateLogin(-1L);
			
			ltInvoices = ltInvoicesDao.createInvoice(ltInvoices);
			//System.out.println("1 = " +ltInvoices.getInvoiceId());
			if(paymentStatus.equalsIgnoreCase("PAID")) {
				// ltCustomerSubsDeliveriesDao.updateInvoicePayment(ltInvoices.getInvoiceId(), ltInvoices.getSubsId() );
			}
			
			notificationServiceCall.saveCustomerNotification(user, "Hi, " + user.getUserName() + " your invoice " + ltInvoices.getInvoiceNumber()
				+ ", " + dateFormat.format(ltInvoices.getInvoiceDate()) + ", " + ltInvoices.getFinalAmount() + "  has been generated");
			return ltInvoices;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<InvoicePdfData> getInvoicesData(Long supplierId) throws ServiceException {
		String fileUploadPath = null;
		Double totalQty = 0D;
		Double totalLineAmt = 0D;
		List<InvoicePdfData> invoiceDataList = ltInvoicesDao.getInvoicesData(supplierId);
		if (invoiceDataList != null && !invoiceDataList.isEmpty()) {
			for (InvoicePdfData invoicePdfData : invoiceDataList) {
				List<InvoiceLinesData> lineData = ltCustomerSubsDeliveriesDao
						.getDeliveryRecordsByInvoiceId(invoicePdfData.getInvoiceId());
				if (!lineData.isEmpty()) {
					for (InvoiceLinesData invoiceLinesData : lineData) {
						if (invoiceLinesData.getDeliveryQuantity() != null)
							totalQty = totalQty + invoiceLinesData.getDeliveryQuantity();
						if (invoiceLinesData.getLineAmount() != null)
							totalLineAmt = totalLineAmt + invoiceLinesData.getLineAmount();
					}
					invoicePdfData.setInvoiceLines(lineData);
				}
				invoicePdfData.setTotalQty(totalQty);
				invoicePdfData.setTotalLineAmt(totalLineAmt);

				
				//String fileOpenPath =  ltProductsDao.getSystemValue(invoicePdfData.getSupplierId(), "FILE_OPEN_PATH");
				fileUploadPath = ltProductsDao.getSystemValue(invoicePdfData.getSupplierId(), "FILE_UPLOAD_PATH");
				String iconPath = fileUploadPath;					
				fileUploadPath = fileUploadPath + "Supplier_" + invoicePdfData.getSupplierId() + "/" + "Invoice_pdf"+ "/";
				File dir = new File(fileUploadPath);
				if (!dir.isDirectory()) {
					dir.mkdirs();
				}
				InvoicePDFGenarate invoicePDFGenarate = new InvoicePDFGenarate();
				invoicePDFGenarate.createPDF("Invoice" + invoicePdfData.getInvoiceId(), fileUploadPath, invoicePdfData, iconPath );
 
			}

		}
		return invoiceDataList;
	}

	private InvoiceLinesData getLineAmountData(LtCustomerSubs customerSubs, Date  strDate){
		InvoiceLinesData linesData = new InvoiceLinesData();
		linesData.setDeliveryDate(UtilsMaster.dateForyyyyMMdd.format(strDate));
		linesData.setProductName(customerSubs.getProductName());
		linesData.setUom(customerSubs.getProductUom());
		linesData.setDeliveryQuantity(customerSubs.getSubsQuantity());
		linesData.setInvoiceRate(customerSubs.getCustomerRate());
		linesData.setLineAmount( customerSubs.getSubsQuantity() * customerSubs.getCustomerRate());
		return linesData;
	}
	
	private List<InvoiceLinesData> createPrepaidInvoiceLines(LtCustomerSubs customerSubs ) {
		
		Date strDate = customerSubs.getStartDate(); 
		Date endDate = customerSubs.getEndDate();
		List<InvoiceLinesData>  linesDatas = new ArrayList<>();
		
		if(customerSubs.getSubscriptionType().equalsIgnoreCase("ONCE")) {
			linesDatas.add(this.getLineAmountData(customerSubs, strDate));
			return linesDatas;
		}
		
		while(strDate.compareTo(endDate) <= 0 ) {
			if(customerSubs.getAlternateDay() != null && customerSubs.getAlternateDay().equalsIgnoreCase("Y") ) {
				linesDatas.add(this.getLineAmountData(customerSubs, strDate));
				strDate = UtilsMaster.addDays(strDate, 2);
			}else {
				if(customerSubs.getMonday() != null  && customerSubs.getMonday().equalsIgnoreCase("Y")) {
					linesDatas.add(this.getLineAmountData(customerSubs, strDate));
				}else if(customerSubs.getTuesday() != null  && customerSubs.getTuesday().equalsIgnoreCase("Y") ) {
					linesDatas.add(this.getLineAmountData(customerSubs, strDate));
				}else if(customerSubs.getWednesday() != null  && customerSubs.getWednesday().equalsIgnoreCase("Y") ) {
					linesDatas.add(this.getLineAmountData(customerSubs, strDate));
				}else if(customerSubs.getThursday() != null  && customerSubs.getThursday().equalsIgnoreCase("Y") ) {
					linesDatas.add(this.getLineAmountData(customerSubs, strDate));
				}else if(customerSubs.getFriday() != null  && customerSubs.getFriday().equalsIgnoreCase("Y") ) {
					linesDatas.add(this.getLineAmountData(customerSubs, strDate));
				}else if(customerSubs.getSaturday() != null  && customerSubs.getSaturday().equalsIgnoreCase("Y") ) {
					linesDatas.add(this.getLineAmountData(customerSubs, strDate));
				}else if(customerSubs.getSunday() != null  && customerSubs.getSunday().equalsIgnoreCase("Y") ) {
					linesDatas.add(this.getLineAmountData(customerSubs, strDate));
				}
				strDate = UtilsMaster.addDays(strDate, 1);
			} 
		}
		return linesDatas;
	}
	
	
	@Override
	public Status createPdf(Long invoiceId) throws ServiceException {
		String fileOpenPath = null;
		String fileUploadPath = null;
		Status status = new Status();
		Double totalQty = 0D;
		Double totalLineAmt = 0D;
		
		String isPrepaid = ltMastSuppliersDao.getIsPrepaidSupplierFlagByInvoiceId(invoiceId);
		InvoicePdfData invoiceData  = null;
		
		if(isPrepaid != null && (isPrepaid.equalsIgnoreCase("Y") || isPrepaid.equalsIgnoreCase("PDO") )) {
			invoiceData = ltInvoicesDao.getPrepaidInvoiceDataByInvoiceId(invoiceId);
		}else {
			invoiceData = ltInvoicesDao.getInvoiceDataByInvoiceId(invoiceId);
		}
		
		if(invoiceData == null) {
			invoiceData = ltInvoicesDao.getInvoiceDataByInvoiceId(invoiceId);
		}
		
		LtCustomerSubs customerSubs = null;
		try {
			if(isPrepaid != null && ( isPrepaid.equalsIgnoreCase("Y") || isPrepaid.equalsIgnoreCase("PDO")) ) {
				customerSubs = ltCustomerSubsDao.getCustomerSubscriptionByInvoiceId(invoiceId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		if (invoiceData != null) {
			List<InvoiceLinesData> lineData = null;
			//System.out.println("isPrepaid = " + isPrepaid );
			//System.out.println("customerSubs.getSubscriptionType() " + customerSubs.getSubscriptionType());
			
			if(isPrepaid != null && isPrepaid.equalsIgnoreCase("Y") )  {
				 lineData = this.createPrepaidInvoiceLines(customerSubs);
			} else if (isPrepaid != null && isPrepaid.equalsIgnoreCase("PDO")
					  &&  customerSubs != null && customerSubs.getSubscriptionType() != null
					  && customerSubs.getSubscriptionType().equalsIgnoreCase("ONCE")
					  && customerSubs.getStatus().equalsIgnoreCase("ACTIVE") ){
				lineData = ltCustomerSubsDeliveriesDao.getDeliveryRecordsBySubId(customerSubs.getSubsId() );
			} else if (isPrepaid != null && isPrepaid.equalsIgnoreCase("PDO")
					  &&  customerSubs != null && customerSubs.getSubscriptionType() != null
					  && customerSubs.getSubscriptionType().equalsIgnoreCase("ONCE") ){
					  // && customerSubs.getStatus().equalsIgnoreCase("PENDING") ){
					lineData = this.createPrepaidInvoiceLines(customerSubs);
			}else {
				 lineData = ltCustomerSubsDeliveriesDao.getDeliveryRecordsByInvoiceId(invoiceData.getInvoiceId());
			}
			
			//System.out.println( "lineData " + lineData.size() );
			
			if (!lineData.isEmpty()) {
				for (InvoiceLinesData invoiceLinesData : lineData) {
					if (invoiceLinesData.getDeliveryQuantity() != null) {
						totalQty = totalQty + invoiceLinesData.getDeliveryQuantity();
					}
					if (invoiceLinesData.getLineAmount() != null) {
						totalLineAmt = totalLineAmt + invoiceLinesData.getLineAmount();
					}
					//System.out.println("invoiceLinesData.getLineAmount() "+ invoiceLinesData.getLineAmount());
				}
				invoiceData.setInvoiceLines(lineData);
			}
			
			invoiceData.setTotalQty(totalQty);
			invoiceData.setTotalLineAmt(totalLineAmt);
 			//System.out.println(totalQty + " totalLineAmt = "+totalLineAmt);
 			
			fileOpenPath = ltProductsDao.getSystemValue(invoiceData.getSupplierId(), "FILE_OPEN_PATH");
			fileUploadPath = ltProductsDao.getSystemValue(invoiceData.getSupplierId(), "FILE_UPLOAD_PATH");
			String iconPath = fileUploadPath;			
			fileUploadPath = fileUploadPath + "Supplier_" + invoiceData.getSupplierId() + "/" + "Invoice_pdf" + "/";
			//fileUploadPath = "E:\\A2Z_workspace\\report\\invoice\\";
			
			File dir = new File(fileUploadPath);
			
			if (!dir.isDirectory()) { 
				dir.mkdirs(); 
			}
			
			InvoicePDFGenarate invoicePDFGenarate = new InvoicePDFGenarate();
			String statusCode = invoicePDFGenarate.createPDF("Invoice" + invoiceData.getInvoiceId(), fileUploadPath, invoiceData, iconPath );
			if (statusCode.equals("SUCCESS")) {
//				status.setCode(SUCCESS);
				status = ltMastCommonMessageService.getCodeAndMessage(SUCCESS);
				status.setMessage(fileOpenPath + "Supplier_" + invoiceData.getSupplierId() + "/" + "Invoice_pdf" + "/"
						+ "Invoice" + invoiceData.getInvoiceId() + ".pdf");
			}

		}
		return status;
	}

	@Override
	public CustomeDataTable getAllInvoicesByUser(Long userId, LtInvoices ltInvoices) throws ServiceException {

		CustomeDataTable customeDataTable = new CustomeDataTable();
		String isPrepaid = ltMastSuppliersDao.getIsPrepaidSupplierFlagByUserId(userId);
		Long count = 0l;
		List<LtInvoices> invoiceList = null;
		
		if(isPrepaid != null && ( isPrepaid.equalsIgnoreCase("Y") || isPrepaid.equalsIgnoreCase("PDO")) ) {
			count = ltInvoicesDao.getAllPrepaidInvoicesByUserCount(userId, ltInvoices);
			invoiceList = ltInvoicesDao.getAllPrepaidInvoicesByUser(userId, ltInvoices);
		}else {
			count = ltInvoicesDao.getAllInvoicesByUserCount(userId, ltInvoices);
			invoiceList = ltInvoicesDao.getAllInvoicesByUser(userId, ltInvoices);
		}
		
		customeDataTable.setRecordsTotal(count);
		customeDataTable.setRecordsFiltered(count);
		customeDataTable.setData(invoiceList);
		return customeDataTable;
	}

	@Override
	public Status createInvoice(LtInvoices ltInvoice) {
		try {
			Status status = new Status();
			if (ltInvoice.getUserId() != null) {
				status = createInvoiceForUser(ltInvoice);
			} else {
				Long count =  ltCustomerSubsDeliveriesDao.getSubsDeliveriesCountByDatesAndSupplierId(ltInvoice);
				if( count == null || count < 1 ) {
//					status.setCode(FAIL);
					status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
					status.setMessage("No subscription found or Invoice(s) already created.");	
					return status;
				}
				
				List<LtMastUsers> userList = ltMastUsersDao.getAllUsersBySupplierId(ltInvoice.getSupplierId());
				if (!userList.isEmpty()) {
					for (LtMastUsers ltMastUsers : userList) {
						ltInvoice.setUserId(ltMastUsers.getUserId());
						status = this.createInvoiceForUser(ltInvoice);
					}
//					status.setCode(SUCCESS);
					status = ltMastCommonMessageService.getCodeAndMessage(SUCCESS);
					status.setMessage("Invoice(s) created"); 
				}else {
//					status.setCode(SUCCESS);
					status = ltMastCommonMessageService.getCodeAndMessage(SUCCESS);
					status.setMessage("No subscription found");	
				}				
			}
			return status;
		}catch(Exception e) {
			e.printStackTrace();
			throw new BusinessException(0, null, e);
		}
	}

	private Status createInvoiceForUser(LtInvoices ltInvoice) throws ServiceException {
		Status status = new Status();
		
		List<LtCustomerSubsDeliveries> ltCustomerSubsDeliveriesList = ltCustomerSubsDeliveriesDao.getSubsDeliveriesBetDates(ltInvoice);
		if( ltCustomerSubsDeliveriesList == null || ltCustomerSubsDeliveriesList.size() < 1 ) {
//			status.setCode(FAIL);
			status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
			status.setMessage("No subscription found or Invoice(s) already created.");	
			return status;
		}
		
		LtMastUsers ltMastUsers = ltMastUsersDao.getUserById(ltInvoice.getUserId());
		
		if (ltMastUsers != null) {
			LtInvoices ltInvoices = new LtInvoices();
			ltInvoices.setSupplierId(ltInvoice.getSupplierId());
			ltInvoices.setUserId(ltMastUsers.getUserId());
			ltInvoices.setInvoiceNumber("INV||" + UtilsMaster.getCurrentDateTime().getTime());
			ltInvoices.setInvoiceDate(UtilsMaster.getCurrentDateTime());

			List<LtCustomerSubs> subsList = ltCustomerSubsDao.getAllActiveSubsForInvoice(ltMastUsers.getUserId());
			if (!subsList.isEmpty()) {
				ltInvoices.setFromDeliveryDate(ltInvoice.getFromDeliveryDate());
				ltInvoices.setToDeliveryDate(ltInvoice.getToDeliveryDate());

				Double invoiceAmount = calculateInvoiceAmount(ltMastUsers.getUserId(), ltInvoices.getFromDeliveryDate(),
						ltInvoices.getToDeliveryDate());
				if (invoiceAmount == null) {
					invoiceAmount = 0D;
				}
				if (ltInvoices.getInvoiceAmount() == null) {
					ltInvoices.setInvoiceAmount(0D);
				}
				
				ltInvoices.setInvoiceAmount(ltInvoices.getInvoiceAmount() + invoiceAmount);

				if (ltInvoices.getDiscountAmount() == null) {
					ltInvoices.setDiscountAmount(0D);
				}
				/*
				 * if (calculateDiscountAmt(ltMastUsers.getUserId(), ltInvoices) != null) {
				 * ltInvoices.setDiscountAmount( ltInvoices.getDiscountAmount() +
				 * calculateDiscountAmt(ltMastUsers.getUserId(), ltInvoices)); }
				 */
				for (LtCustomerSubs ltCustomerSubs : subsList) {
					ltInvoices.setDiscountAmount(ltInvoices.getDiscountAmount()
							+ calculateDiscountAmtAgainstSubs(ltCustomerSubs, ltInvoices));
				}

				if (ltInvoices.getDiscountAmount() != null) {
					ltInvoices.setFinalAmount(ltInvoices.getInvoiceAmount() - ltInvoices.getDiscountAmount());
				} else {
					ltInvoices.setFinalAmount(ltInvoices.getInvoiceAmount());
				}

				ltInvoices.setStatus("PENDING");
				ltInvoices.setCreatedBy(-1L);
				ltInvoices.setCreationDate(UtilsMaster.getCurrentDateTime());
				ltInvoices.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
				ltInvoices.setLastUpdatedBy(-1L);
				ltInvoices.setLastUpdateLogin(-1L);
				if (ltInvoices.getInvoiceAmount() != null && ltInvoices.getInvoiceAmount() > 0) {
					ltInvoices = ltInvoicesDao.createInvoice(ltInvoices);
					if (ltInvoices.getInvoiceId() != null) {
						ltCustomerSubsDeliveriesDao.updateInvoiceId(ltInvoices);
//						status.setCode(SUCCESS);
						status = ltMastCommonMessageService.getCodeAndMessage(SUCCESS);
						status.setMessage("Invoice(s) created");
						LtMastUsers ltMastUser = ltMastUsersDao.getUserById(ltInvoices.getUserId());
						notificationServiceCall.saveCustomerNotification(ltMastUser,
								"Hi, " + ltMastUser.getUserName() + " your invoice " + ltInvoices.getInvoiceNumber()
										+ ", " + dateFormat.format(ltInvoices.getInvoiceDate()) + ", Rs. "
										+ ltInvoices.getFinalAmount() + "  has been generated");

						Status status1 = createPdf(ltInvoices.getInvoiceId());
						if (status1 != null) {
							status.setData(status1.getMessage());
						}
					}
				//	makeEntryInEmailTemplate("invoiceCreation", ltInvoices);
				}else {
//					status.setCode(FAIL);
					status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
					status.setMessage("No Subscriptions found");
				}
			} else {
//				status.setCode(FAIL);
				status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
				status.setMessage("No Subscriptions found");
			}
		}
		return status;
	}

	private Double calculateDiscountAmtAgainstSubs(LtCustomerSubs ltCustomerSubs, LtInvoices ltInvoices)
			throws ServiceException {
		Double discountAmount = 0D;
		if (ltCustomerSubs.getStatus().equals(ACTIVE)) {
			if (ltCustomerSubs.getOfferId() != null) {
				LtSubscrptionOffer ltSubscrptionOffer = ltSubscrptionOfferDao
						.getSubscrptionOfferById(ltCustomerSubs.getOfferId());
				if (ltSubscrptionOffer != null) {
					if (ltSubscrptionOffer.getStartDate().before(ltCustomerSubs.getStartDate())
							&& ltSubscrptionOffer.getEndDate().after(ltCustomerSubs.getStartDate())) {
						if (ltSubscrptionOffer.getOfferType().toUpperCase().equals("AMOUNT")) {
							discountAmount = discountAmount + ltSubscrptionOffer.getOfferValue();
							
						} else {
							Double invoiceAmount = calculateInvoiceAmountForSubs(ltCustomerSubs.getSubsId(),
									ltInvoices.getFromDeliveryDate(), ltInvoices.getToDeliveryDate());
							discountAmount = discountAmount
									+ (invoiceAmount * ltSubscrptionOffer.getOfferValue()) / 100;
						}

					}
				}

			}
		}
		return discountAmount;
	}

	private Double calculateInvoiceAmountForSubs(Long subsId, Date fromDeliveryDate, Date toDeliveryDate)
			throws ServiceException {
		return ltCustomerSubsDeliveriesDao.calculateInvoiceAmountForSubs(subsId, fromDeliveryDate, toDeliveryDate);

	}

}
