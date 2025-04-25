package com.lonar.master.a2zmaster.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lonar.master.a2zmaster.model.LtScheduler;
//import com.lonar.a2zcommons.model.LtScheduler;
import com.lonar.master.a2zmaster.repository.LtSchedulerRepository;
import com.lonar.master.a2zmaster.service.LtCustomerSubsService;
import com.lonar.master.a2zmaster.service.LtInvoicePaymentsService;
import com.lonar.master.a2zmaster.service.LtInvoicesService;
import com.lonar.master.a2zmaster.service.LtOrderHistoryService;
import com.lonar.master.a2zmaster.service.LtVacationService;
import com.lonar.master.a2zmaster.utils.UtilsMaster;

@Component
@EnableScheduling
public class ScheduledTasksDaily {

	@Autowired private LtInvoicesService ltInvoicesService;
	@Autowired private LtInvoicePaymentsService ltInvoicePaymentsService;
	@Autowired private LtCustomerSubsService ltCustomerSubsService;
	@Autowired private LtSchedulerRepository schedulerRepository;
	@Autowired private LtVacationService vacationService;
	@Autowired private LtOrderHistoryService orderHistoryService;
	
	//@Autowired private LtMastSuppliersDao mastSuppliersDao;
//	 private static final Logger logger = LoggerFactory.getLogger(ScheduledTasksDaily.class);
//	
//	 @Scheduled(cron = "*/5 * * * * *")
//	    public void runJob() {
//	        logger.info("Cron job is running at {}", System.currentTimeMillis());
//	    }
	

	@Scheduled(cron = "0 5 1 * * ?", zone = "GMT+5:30")
	public void setNotification() {
		LtScheduler scheduler =  schedulerRepository.save(new LtScheduler(null, UtilsMaster.getCurrentDateTime(), null, "NOTIFICATION", null, null));
		try {
			vacationService.checkVacation(); 
		} catch (Exception e) {
			e.printStackTrace();
			scheduler.setExceptionMsg(e.getMessage());
		}

		try {
			ltInvoicePaymentsService.paymentReminder();
		} catch (ServiceException e) {
			e.printStackTrace();
			scheduler.setExceptionMsg(scheduler.getExceptionMsg() +" ::: "+ e.getMessage());
		}
		
		try {
			ltInvoicePaymentsService.endSubscriptionNotificationSend();
		} catch (ServiceException e) {
			e.printStackTrace();
			scheduler.setExceptionMsg(scheduler.getExceptionMsg() +" ::: "+ e.getMessage());
		}
		
		try {
			orderHistoryService.addWalletbalance();
		}catch(Exception e) {
			e.printStackTrace();
		}

		scheduler.setCompletedDate(UtilsMaster.getCurrentDateTime());
		schedulerRepository.save(scheduler);
	}

	//@Scheduled(initialDelay = 1000, fixedRate = 1000 * 900 )
	@Scheduled(cron = "0 10 1 * * ?") //, zone = "GMT+5:30"
	public void createPostpaidInvoice() throws ServiceException {		
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		LtScheduler scheduler =  schedulerRepository.save(new LtScheduler(null, UtilsMaster.getCurrentDateTime(), null, "INVOICE_1.10", null, null));		
		try {
			ltInvoicesService.createPostpaidInvoice("D");
		} catch (Exception e) {
			e.printStackTrace();
		}		
		scheduler.setCompletedDate(UtilsMaster.getCurrentDateTime());
		schedulerRepository.save(scheduler);
	}

	@Scheduled(cron = "0 30 1 * * ?", zone = "GMT+5:30")
	public void setSubscriptionDelivery() {
		LtScheduler scheduler =  schedulerRepository.save(new LtScheduler(null, UtilsMaster.getCurrentDateTime() , null, "SUBSCRIPTION_1.15", null, null));
		try {
			ltCustomerSubsService.addSubscriptionDelivery(); 
			//int res =  ltCustomerSubsService.addVacationsToCustomerDelivery();
			scheduler.setDescription( scheduler.getDescription() + ":: DELIVERY ADDED = ");
		}catch(Exception e ) {
			e.printStackTrace();
			scheduler.setExceptionMsg(e.getMessage());
		}
		scheduler.setCompletedDate(UtilsMaster.getCurrentDateTime());
		schedulerRepository.save(scheduler);
	}
	
	@Scheduled(cron = "0 45 1 * * ?", zone = "GMT+5:30")
	public void setVacationForDelivery() {
		LtScheduler scheduler =  schedulerRepository.save(new LtScheduler(null, UtilsMaster.getCurrentDateTime() , null, "VACATION_1.45", null, null));
		try {
			  //ltCustomerSubsService.addSubscriptionDelivery(); 
			int res =  ltCustomerSubsService.addVacationsToCustomerDelivery();
			scheduler.setDescription( scheduler.getDescription() + ":: VACATION_UPDATED = "+ res);
		}catch(Exception e ) {
			e.printStackTrace();
			scheduler.setExceptionMsg(e.getMessage());
		}
		scheduler.setCompletedDate(UtilsMaster.getCurrentDateTime());
		schedulerRepository.save(scheduler);
	}
	
	
	@Scheduled(cron = "0 15 1 * * ?", zone = "GMT+5:30")
	public void updateRecord() {
		LtScheduler scheduler =  schedulerRepository.save(new LtScheduler(null, UtilsMaster.getCurrentDateTime(), null, "STATUS_1.30", null, null));
		try {
			int res = ltInvoicePaymentsService.updateCustomerSubscriptionStatusByDate();
			scheduler.setDescription(" CUSTOMER_SUB = "+ res);
		}catch(Exception e) {
			e.printStackTrace();
			scheduler.setExceptionMsg( e.getMessage());
		}
		
		try {
			int res =ltInvoicePaymentsService.updateVacatonStatusByDate();
			scheduler.setDescription(scheduler.getDescription() +" :: VACATION = "+ res);
		}catch(Exception e) {
			e.printStackTrace();
			scheduler.setExceptionMsg( e.getMessage());
		}
		try {
			int res = ltInvoicePaymentsService.updateDeliveryDateToCurrentDate();
			scheduler.setDescription(scheduler.getDescription() +" :: DELIVERY_ONCE = "+ res);
		}catch(Exception e) {
			e.printStackTrace();
			scheduler.setExceptionMsg( e.getMessage());
		}
		scheduler.setCompletedDate(UtilsMaster.getCurrentDateTime());
		schedulerRepository.save(scheduler);
	}

}
 