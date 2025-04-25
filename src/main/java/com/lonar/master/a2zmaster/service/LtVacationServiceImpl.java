package com.lonar.master.a2zmaster.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.lonar.a2zcommons.model.LtCustomerSubs;
//import com.lonar.a2zcommons.model.LtMastUsers;
//import com.lonar.a2zcommons.model.LtVacationPeriod;
import com.lonar.master.a2zmaster.common.CalculateDate;
import com.lonar.master.a2zmaster.common.NotificationServiceCall;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.dao.LtCustomerSubsDao;
import com.lonar.master.a2zmaster.dao.LtCustomerSubsDeliveriesDao;
import com.lonar.master.a2zmaster.dao.LtMastSuppliersDao;
import com.lonar.master.a2zmaster.dao.LtMastUsersDao;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.LtCustomerSubs;
import com.lonar.master.a2zmaster.model.LtMastUsers;
import com.lonar.master.a2zmaster.model.LtVacationPeriod;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.utils.UtilsMaster;

@Service
public class LtVacationServiceImpl implements LtVacationService, CodeMaster {

	
	@Autowired private LtMastUsersDao ltMastUsersDao;
	@Autowired private LtCustomerSubsDeliveriesDao ltCustomerSubsDeliveriesDao;
	@Autowired private NotificationServiceCall notificationServiceCall;
	@Autowired private LtOrderHistoryService orderHistoryService;
	@Autowired private LtMastCommonMessageService ltMastCommonMessageService;
	@Autowired private LtCustomerSubsDao ltCustomerSubsDao;
	@Autowired private LtMastSuppliersDao mastSuppliersDao;
	
	DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");
	
	@Override
	public Status checkVacation() throws ServiceException {
		
		List<LtVacationPeriod> ltVacationPeriodList = ltCustomerSubsDeliveriesDao.getAllActiveVacationBySupplier();
		if (ltVacationPeriodList != null) {

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(UtilsMaster.getCurrentDateTime());
			calendar.add(Calendar.DAY_OF_YEAR, -1);
			Date yesterday = calendar.getTime();
			
			for (LtVacationPeriod ltVacationPeriod : ltVacationPeriodList) {
				ltVacationPeriod.setStatus(INACTIVE);
				ltVacationPeriod = ltCustomerSubsDeliveriesDao.saveVacation(ltVacationPeriod);
				LtMastUsers ltMastUser = ltMastUsersDao.getUserById(ltVacationPeriod.getUserId());
				
				orderHistoryService.saveOrderHistoryVacation("Vacation Ended", dateFormat.format( yesterday ), ltMastUser.getUserId() ,ltMastUser.getSupplierId() );
				
				notificationServiceCall.saveSupplierNotification(ltMastUser,
						"Hi, Vacation for " + ltMastUser.getUserName() +" has ended on " +dateFormat.format( yesterday )   //+ dateFormat.format(ltCustomerSubs.getVacationStartDate())
								+ ", all their subscriptions will be resumed from  " + dateFormat.format( UtilsMaster.getCurrentDateTime() ));
				
				notificationServiceCall.saveCustomerNotification( ltMastUser,   //ltMastUserSupplier.get(0),
						"Welcome Back! Your vacation has ended on " + dateFormat.format(yesterday)  
						        + ", all your subscriptions will be resumed from "+ dateFormat.format(UtilsMaster.getCurrentDateTime()));
				
				try {
					List<LtMastUsers> superviser  = ltMastUsersDao.getAllSuperviserBySupplierId(ltMastUser.getSupplierId());
					superviser.forEach(user -> {
						try {
							notificationServiceCall.saveCustomerNotification(user,
									"Hi, Vacation for " + ltMastUser.getUserName() +" has ended on " +dateFormat.format(yesterday )   //+ dateFormat.format(ltCustomerSubs.getVacationStartDate())
											+ ", all their su.bscriptions will be resumed from  " + dateFormat.format( UtilsMaster.getCurrentDateTime() ));
						} catch (ServiceException e) {
							e.printStackTrace();
						}		
					});
				} catch (Exception e) {
					e.printStackTrace();
				}				
			}
		}
		return null;
	}

//	public Status checkStartDateValidation(LtCustomerSubs ltCustomerSubs) {
//		Status status = new Status();
//		CalculateDate calculateDate = new CalculateDate();
//		String startDate = calculateDate.utilToYYYYMMDD(UtilsMaster.getCurrentDateTime());
//		String endDate = calculateDate.findLast(startDate);
//		try {
//			if (ltCustomerSubs.getStartDate().after(new SimpleDateFormat("yyyy-MM-dd").parse(endDate))) {
//				status.setCode(FAIL);
//				status.setMessage("Oops! Action failed. Please try again.");
//				return status;
//			} else {
//				return null;
//			}
//		} catch (ParseException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
	
	public Status checkStartDateValidation(LtCustomerSubs ltCustomerSubs) {
	    System.out.println("Start: checkStartDateValidation method");
	    Status status = new Status();
	    System.out.println("Created new Status object");
	    
	    CalculateDate calculateDate = new CalculateDate();
	    System.out.println("Created CalculateDate object");
	    
	    String startDate = calculateDate.utilToYYYYMMDD(UtilsMaster.getCurrentDateTime());
	    System.out.println("Current date converted to startDate: " + startDate);
	    
	    String endDate = calculateDate.findLast(startDate);
	    System.out.println("Calculated endDate: " + endDate);
	    
	    try {
	        System.out.println("Parsing endDate to Date object");
	        Date parsedEndDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
	        System.out.println("Parsed endDate: " + parsedEndDate);
	        
	        System.out.println("Checking if subscription startDate is after endDate");
	        if (ltCustomerSubs.getStartDate().after(parsedEndDate)) {
	            System.out.println("Start date is after end date");
//	            status.setCode(FAIL);
				status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
	            status.setMessage("Oops! Action failed. Please try again.");
	            System.out.println("Returning failure status");
	            return status;
	        } else {
	            System.out.println("Start date is within the valid range");
	            return null;
	        }
	    } catch (ParseException e) {
	        e.printStackTrace();
	        System.out.println("Exception occurred: " + e.getMessage());
	        return null;
	    }
	}

	
	private Status checkForActiveSubs(LtCustomerSubs ltCustomerSubs) throws ServiceException {
		Status status = new Status();
		List<LtCustomerSubs> ltCustomerSubsList = ltCustomerSubsDao.getAllActiveSubsForInvoice(ltCustomerSubs.getUserId());
		if (ltCustomerSubsList.isEmpty()) {
//			status.setCode(FAIL);
			status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
			status.setMessage("You don't have active subscription to set vacation");
			return status;
		}
		return null;
	}
	
	@Override
	public Status updateVacation(LtCustomerSubs ltCustomerSubs, LtMastUsers loginUser) throws ServiceException {
		Date stDate = ltCustomerSubs.getVacationStartDate();
		Date enDate = ltCustomerSubs.getVacationEndDate();
		String vacationStatus = ltCustomerSubs.getStatus();
		Status status = new Status();

		ltCustomerSubs.setStartDate(ltCustomerSubs.getVacationStartDate());
		
		Status status1 = this.checkStartDateValidation(ltCustomerSubs);
		if (status1 != null) {
			return status1;
		}

		Status status3 = this.checkForActiveSubs(ltCustomerSubs);
		
		if (status3 != null) {
			return status3;
		}
		
		if (ltCustomerSubs.getStatus().equals(ACTIVE)) {			
			if (true) {
				ltCustomerSubsDeliveriesDao.updateDelivery(ltCustomerSubs, ltCustomerSubs.getVacationId());
				LtVacationPeriod ltVacationPeriod = ltCustomerSubsDeliveriesDao.getVacation(ltCustomerSubs.getVacationId());
			
				Calendar vacationStartDate = new GregorianCalendar();
				vacationStartDate.setTime( ltVacationPeriod.getStartDate());
				
				ltVacationPeriod.setEndDate(UtilsMaster.getCurrentDateTime());
				ltVacationPeriod.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
				ltVacationPeriod.setLastUpdatedBy(ltCustomerSubs.getLastUpdateLogin());
				ltVacationPeriod.setLastUpdateLogin(ltCustomerSubs.getLastUpdateLogin());
				ltVacationPeriod.setStatus(INACTIVE);
				ltVacationPeriod = ltCustomerSubsDeliveriesDao.saveVacation(ltVacationPeriod);
				status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
				orderHistoryService.saveOrderHistoryVacation("Vacation Ended",  dateFormat.format( UtilsMaster.getCurrentDateTime() )
						
                , ltCustomerSubs.getUserId() ,ltCustomerSubs.getSupplierId() );
				try {
					LtMastUsers user =   loginUser; //  ltMastUsersDao.getUserById(ltCustomerSubs.getLastUpdateLogin());
	
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(UtilsMaster.getCurrentDateTime());
					calendar.add(Calendar.DAY_OF_YEAR, 1);
					Date tomorrow = calendar.getTime();
					
					Calendar todayDate = new GregorianCalendar();	
					
					if(todayDate.before(vacationStartDate)) {
						if( user.getUserType().equalsIgnoreCase("SUPPLIER")) {
							notificationServiceCall.saveCustomerNotification(ltVacationPeriod.getUserId(), ltVacationPeriod.getSupplierId() 
									 , "Hi, Your vacation has ended on " + dateFormat.format(UtilsMaster.getCurrentDateTime()) +" by supplier." );
						}else {
							notificationServiceCall.saveCustomerNotification(ltVacationPeriod.getUserId(), ltVacationPeriod.getSupplierId() 
									 , "Hi, Your vacation has ended on " + dateFormat.format(UtilsMaster.getCurrentDateTime()));	
						}
						
						if (user.getUserType().equals("CUSTOMER")) {
							notificationServiceCall.saveSupplierNotification(ltVacationPeriod.getSupplierId(),
									"Hi, Vacation for " + user.getUserName() +" has ended on " +dateFormat.format(UtilsMaster.getCurrentDateTime()));
						}
						status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
						return status;
					}else {						
						LtMastUsers ltMastUser = ltMastUsersDao.getUserById(ltVacationPeriod.getUserId());
						notificationServiceCall.saveCustomerNotification( ltVacationPeriod.getUserId(), ltVacationPeriod.getSupplierId(), //ltMastUser,
								"Hi, Your vacation has ended on " + dateFormat.format(UtilsMaster.getCurrentDateTime()) 
				        	    + ", all your subscriptions will be resumed from "+ dateFormat.format(tomorrow));					
						
						if (user.getUserType().equals("CUSTOMER")) {
							notificationServiceCall.saveSupplierNotification(ltVacationPeriod.getSupplierId(),  //ltMastUserSupplier.get(0),
									"Hi, Vacation for " + user.getUserName() +" has ended on " +dateFormat.format(UtilsMaster.getCurrentDateTime()) 
											+ ", all their subscriptions will be resumed from  " + dateFormat.format( tomorrow ));
						}					
						
						List<LtMastUsers> superviser  = ltMastUsersDao.getAllSuperviserBySupplierId(ltVacationPeriod.getSupplierId());
						superviser.forEach(user1 -> {
							try {
								notificationServiceCall.saveCustomerNotification(user1,
										"Hi, Vacation for " + ltMastUser.getUserName() +" has ended on " +dateFormat.format(UtilsMaster.getCurrentDateTime())   //+ dateFormat.format(ltCustomerSubs.getVacationStartDate())
												+ ", all their subscriptions will be resumed from  " + dateFormat.format( tomorrow ));
							} catch (ServiceException e) {
								e.printStackTrace();
							}		
						});
						status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
						return status;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
			}
			return status;
		}else if(ltCustomerSubs.getStatus().equals(PAUSED)) {
			 
			LtVacationPeriod vacationPeriod = ltCustomerSubsDeliveriesDao.getVacationByUser(ltCustomerSubs.getUserId());

			if (vacationPeriod != null && ltCustomerSubs.getVacationId() == null) {
//					status.setCode(FAIL);
					status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
					status.setMessage("You have an existing vacation, can not add another vacation ");
					return status;
			}
			
			if (ltCustomerSubs.getVacationId() != null && ltCustomerSubs.getVacationId() > 0 ) {
				ltCustomerSubsDeliveriesDao.careateActiveDelivery(ltCustomerSubs.getUserId() , ltCustomerSubs.getVacationId());
			}

			ltCustomerSubs.setStatus(vacationStatus);
			LtVacationPeriod ltVacationPeriod = new LtVacationPeriod();
			ltVacationPeriod.setVacationId(ltCustomerSubs.getVacationId());
			ltVacationPeriod.setStartDate(stDate);
			ltVacationPeriod.setEndDate(enDate);
			ltVacationPeriod.setStatus(ACTIVE);
			ltVacationPeriod.setSupplierId(ltCustomerSubs.getSupplierId());
			ltVacationPeriod.setUserId(ltCustomerSubs.getUserId());
			ltVacationPeriod.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
			ltVacationPeriod.setCreationDate(UtilsMaster.getCurrentDateTime());
			ltVacationPeriod.setCreatedBy(ltCustomerSubs.getLastUpdateLogin());
			ltVacationPeriod.setLastUpdatedBy(ltCustomerSubs.getLastUpdateLogin());
			ltVacationPeriod.setLastUpdateLogin(ltCustomerSubs.getLastUpdateLogin());
			if(ltVacationPeriod.getVacationId() ==null ) {
			orderHistoryService.saveOrderHistoryVacation("Vacation Set","From "+ dateFormat.format( stDate ) +" to "+ dateFormat.format( enDate ) 
			                    , ltCustomerSubs.getUserId() ,ltCustomerSubs.getSupplierId() );
			}else {
				orderHistoryService.saveOrderHistoryVacation("Vacation Changed","From "+ dateFormat.format( stDate ) +" to "+ dateFormat.format( enDate ) 
                , ltCustomerSubs.getUserId() ,ltCustomerSubs.getSupplierId() );
			}
			ltVacationPeriod = ltCustomerSubsDeliveriesDao.saveVacation(ltVacationPeriod);
			
				if (ltVacationPeriod.getVacationId() != null) {
					ltCustomerSubs.setSubsQuantity(0D);			
					boolean flag = false;
					String isPrepaid = mastSuppliersDao.getPrepaidSupplierFlag(ltCustomerSubs.getSupplierId());
					if(isPrepaid != null && isPrepaid.equalsIgnoreCase("Y")) {
						flag = ltCustomerSubsDeliveriesDao.setPrepaidPausedDelivery(vacationStatus,  
								  ltCustomerSubs.getUserId() , ltVacationPeriod.getVacationId(), stDate, enDate );
					}else {
						flag = ltCustomerSubsDeliveriesDao.careatPausedDelivery(vacationStatus,  
							  ltCustomerSubs.getUserId() , ltVacationPeriod.getVacationId(), stDate, enDate );
					}
					
					if (flag) {		
						status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
						LtMastUsers user = ltMastUsersDao.getUserById(ltCustomerSubs.getLastUpdateLogin());
						if (user.getUserType().equals("CUSTOMER")) {							
							notificationServiceCall.saveCustomerNotification( ltCustomerSubs.getUserId(), ltCustomerSubs.getSupplierId(), //ltMastUser,
									"Hi, you have set vacation for " + dateFormat.format(ltCustomerSubs.getVacationStartDate())
											+ " to " + dateFormat.format(ltCustomerSubs.getVacationEndDate())
											+ ", during this period all subscriptions will be paused ");

							LtMastUsers ltMastUser = ltMastUsersDao.getUserById(ltCustomerSubs.getUserId());
							notificationServiceCall.saveSupplierNotification( ltCustomerSubs.getSupplierId(), //ltMastUserSupplier.get(0),
									"Hi, " + ltMastUser.getUserName() + " has set vacation for "
											+ dateFormat.format(ltCustomerSubs.getVacationStartDate()) + " to "
											+ dateFormat.format(ltCustomerSubs.getVacationEndDate()));
							status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
							return status;
						} else {
							notificationServiceCall.saveCustomerNotification(ltCustomerSubs.getUserId(), ltCustomerSubs.getSupplierId(), //  ltMastUser,
									"Hi, Supplier has set vacation for you from "
											+ dateFormat.format(ltCustomerSubs.getVacationStartDate()) + " to "
											+ dateFormat.format(ltCustomerSubs.getVacationEndDate())
											+ ", during this period all subscriptions will be paused  ");
							status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
							return status;
						}
					} else {
						status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
					}
				} else {
					status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
				}
		}
		status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
		return status;
	}

	
}
