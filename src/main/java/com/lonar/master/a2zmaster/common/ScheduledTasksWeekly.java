package com.lonar.master.a2zmaster.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.lonar.master.a2zmaster.model.LtScheduler;

//import com.lonar.a2zcommons.model.LtScheduler;

import com.lonar.master.a2zmaster.repository.LtSchedulerRepository;
import com.lonar.master.a2zmaster.service.LtInvoicesService;
import com.lonar.master.a2zmaster.utils.UtilsMaster;

@Component
@EnableScheduling
public class ScheduledTasksWeekly {

	@Autowired
	private LtInvoicesService ltInvoicesService;

	//@Autowired private LtMastSuppliersService ltMastSuppliersService;
	// @Autowired private LtMastSuppliersDao mastSuppliersDao;
	
	@Autowired private LtSchedulerRepository schedulerRepository;
	
	//@Scheduled(initialDelay = 1000, fixedRate = 1000 * 900 )
	@Scheduled(cron="0 15,45 1 * * Mon")
    public void reportCurrentTime() throws ServiceException {
    	SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    	LtScheduler scheduler =  schedulerRepository.save(new LtScheduler(null, UtilsMaster.getCurrentDateTime(), null, "WEEKLY", null, null));
	    	try {
	    		//createInvoice();
	    		ltInvoicesService.createPostpaidInvoice("W");
			}catch(Exception e) {
				e.printStackTrace();
				scheduler.setExceptionMsg( e.getMessage());
			}
    	scheduler.setCompletedDate(UtilsMaster.getCurrentDateTime());
		schedulerRepository.save(scheduler);
    }

	private void createInvoice() {
		try {
			/*List<LtMastSuppliers> supplierList = mastSuppliersDao.getAllPostpaidSuppliers();
			if(supplierList!=null && !supplierList.isEmpty()) {
				for(LtMastSuppliers supplier : supplierList) {
					try {
						 ltInvoicesService.createPostpaidInvoice(supplier, "W", null);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}*/
			//System.out.println("CREATE INVOICE INVOICE WEEKLY START ..........................");
			//ltInvoicesService.createPostpaidInvoice("W");
			//System.out.println("CREATE INVOICE INVOICE WEEKLY ..........................END");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
