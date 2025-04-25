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
public class ScheduledTasksMonthly {

	@Autowired
	private LtInvoicesService ltInvoicesService;

	//@Autowired private LtMastSuppliersService ltMastSuppliersService;
	
	@Autowired
	private LtSchedulerRepository schedulerRepository;
	
	//@Autowired private LtMastSuppliersDao mastSuppliersDao;
	

	
	@Scheduled(cron="0 5 1 1 * *") //on the first day of the month
    public void reportCurrentTime() throws ServiceException {
    	SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    	LtScheduler scheduler =  schedulerRepository.save(new LtScheduler(null, UtilsMaster.getCurrentDateTime(), null, "MONTHLY_1.05", null, null));
		try {
			//this.createInvoice();
			ltInvoicesService.createPostpaidInvoice("M");
		}catch(Exception e) {
			e.printStackTrace();
			scheduler.setExceptionMsg( e.getMessage());
		}
		scheduler.setCompletedDate(UtilsMaster.getCurrentDateTime());
		schedulerRepository.save(scheduler);
    }

	private void createInvoice() {
		try {
			//List<LtMastSuppliers> supplierList = ltMastSuppliersService.getAllSuppliers();
			/*List<LtMastSuppliers> supplierList = mastSuppliersDao.getAllPostpaidSuppliers();
			if(supplierList!=null && !supplierList.isEmpty()) {
				for(LtMastSuppliers supplier : supplierList) {
					try {
						 ltInvoicesService.createPostpaidInvoice(supplier, "M", null);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}*/
			//ltInvoicesService.createPostpaidInvoice("M");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
