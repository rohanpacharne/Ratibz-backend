package com.lonar.master.a2zmaster.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

//import com.lonar.a2zcommons.model.LtMastUsers;
//import com.lonar.a2zcommons.model.LtProducts;
import com.lonar.master.a2zmaster.common.NotificationServiceCall;
import com.lonar.master.a2zmaster.dao.LtMastUsersDao;
import com.lonar.master.a2zmaster.model.LtMastUsers;
import com.lonar.master.a2zmaster.model.LtProducts;

public class ProductNotificationThread extends Thread{
	DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");
	LtProducts ltProducts = null;
	LtMastUsersDao ltMastUsersDao = null;
	NotificationServiceCall notificationServiceCall =null;
	
	
	public ProductNotificationThread(LtProducts ltProducts, LtMastUsersDao ltMastUsersDao,
			NotificationServiceCall notificationServiceCall) {
		this.ltMastUsersDao = ltMastUsersDao;
		this.ltProducts = ltProducts;
		this.notificationServiceCall = notificationServiceCall;
	}

	public void run() {
		try {
		List<LtMastUsers> usersList = ltMastUsersDao.getAllUsersBySupplierId(ltProducts.getSupplierId());
		for (LtMastUsers ltMastUsers : usersList) {
			notificationServiceCall.saveCustomerNotification(ltMastUsers,
					"Hi, New product " + ltProducts.getProductName()
							+ " has been added & will be available for subscription from "
							+ dateFormat.format(ltProducts.getStartDate()));
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
